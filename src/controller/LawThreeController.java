package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import motion.DisplayObject;
import motion.Utility;
import motion.Vector2D;
import motion.force.Force;
import motion.movingobject.Earth;
import motion.movingobject.MovingObject;
import motion.movingobject.Rocket;
import motion.movingobject.RocketGas;
import motion.staticobject.Block;
import motion.staticobject.RocketBase;
import motion.staticobject.StaticObject;

public class LawThreeController extends LawSceneController {
	/**Canvas with object Pane*/
	@FXML
	private Pane lawThreePane;
	Rectangle clip;
	
	/** The main object */
	private Rocket rocket;
	
	/** Display rocket's movement*/
	@FXML
	private Label p1,p2,f_rocket,f_gas,a,v, f_net;
	
	/** F_engine buttons*/
	@FXML
	private Button lawThreeBurnMoreGas, lawThreeBurnLessGas;
	
	/** Forces*/
	private Force F_rocket,F_gas, P1, P2, N;

	
	public LawThreeController() {
	}

	@Override
	protected void setupCameraPane() {
		clip = new Rectangle(0,0, 850, 420);
		lawThreePane.setClip(clip);
		
		//Move camera: pane.translatey = -clip.y
        lawThreePane.translateYProperty().bind(clip.yProperty().multiply(-1));
        	
	}

	@Override
	protected void initPane() {
		//create blocks: 
        int numHorizontalBlocks = 50 ;
        int numVerticalBlocks = 1000;
        int offsetY = 9; //block on bottom left has (x,y) = (0,offsetY) * SIZE_UNIT
        int offsetX = 9; //rocket base on bottom left has (x,y) = (offsetX,0) * SIZE_UNIT
        
        for (int i = 0; i < numHorizontalBlocks * Utility.SIZE_UNIT; i+= Utility.SIZE_UNIT) {
			DisplayObject block = new Block(lawThreePane, i, Utility.SIZE_UNIT*offsetY);
			allDisplayObjects.add(block);			
		}
        
        for (int i = 0; i < numVerticalBlocks * Utility.SIZE_UNIT; i+= Utility.SIZE_UNIT) {
			DisplayObject block = new Block(lawThreePane, 0, -i + offsetY * Utility.SIZE_UNIT);
			allDisplayObjects.add(block);			
		}
		
        //Rocket base & Rocket
        StaticObject rocket_base = new RocketBase(lawThreePane, offsetX * Utility.SIZE_UNIT, 
        		(offsetY - 6) * Utility.SIZE_UNIT);
        this.rocket = new Rocket(lawThreePane, (offsetX + 1) * Utility.SIZE_UNIT,
        		(offsetY - 6) * Utility.SIZE_UNIT, 10);
        
        allDisplayObjects.add(rocket_base);
        allDisplayObjects.add(rocket);
        
        //Rocket gas
        MovingObject rocketGas = new RocketGas(lawThreePane, rocket);
        allDisplayObjects.add(rocketGas);
        
        /**
         * TODO: 
         * 1. Create Gas as MovingObject. Bind Gas.Y to Rocket.Y-Rocket.Height
         * 2. Create force P1, P2. P1 is on Rocket and P2 is on Earth (another moving object?)
         * 3. Create N on Rocket. N - P1 + Fgas = ma => N's forceVector changes in real-time
         */
        //Earth
        MovingObject earth = new Earth(lawThreePane, (offsetX - 1) * Utility.SIZE_UNIT, offsetY * Utility.SIZE_UNIT);
        allDisplayObjects.add(earth);
        
        //Force
        P1 = new Force(lawThreePane, 0, 5, rocket, "P1");
        N = new Force(lawThreePane, 0, -5, rocket, "N");
        N.setRelativePositionOfForceToObject(Utility.SIZE_UNIT, rocket.getHeight()/2);
        
        P2 = new Force(lawThreePane, 0, -5, earth, "P2");
        P2.setRelativeXofTextToForce(-1);
        
        F_rocket = new Force(lawThreePane, 0, 0, rocket, "F_rocket");
        F_gas = new Force(lawThreePane, 0, 0, rocketGas, "F_gas");
        
        //shorten F_gas, F_rocket arrow
        F_rocket.setArrowLengthMultiplier(0.1);
        F_gas.setArrowLengthMultiplier(0.1);
        
        //reposition F_rocket
        F_rocket.setRelativePositionOfForceToObject(0, -2 * Utility.SIZE_UNIT);
        
        allDisplayObjects.add(P1);
        allDisplayObjects.add(N);
        allDisplayObjects.add(P2);
        allDisplayObjects.add(F_rocket);
        allDisplayObjects.add(F_gas);
        
	}
	
	@Override
	protected void updatePane(double elapsedSeconds) {
		super.updatePane(elapsedSeconds);
		
		//update label
		this.p1.setText("P1 = " + this.P1.getForceVector());
		this.p2.setText("P2 = " + this.P2.getForceVector());
		this.f_rocket.setText("F_rocket = " + this.F_rocket.getForceVector());
		this.f_gas.setText("F_gas = " + this.F_gas.getForceVector());
		this.v.setText("v = " + rocket.getVelocity());		
		this.a.setText("a = " + rocket.getAcceleration());		
		this.f_net.setText("Fnet = " + rocket.getSumForceVector());
		
		//update clip
		double clipY = clip.getY();
		double rocketLayoutY = rocket.getView().getLayoutY();
		
		//DEBUG Camera
		//System.out.println("clipY: " + clipY + " rocketLayoutY: " + rocketLayoutY);
				
		if( clipY >= rocketLayoutY - clip_min_y *Utility.SIZE_UNIT) { //clip starts to go backwards
			clip.setY(
					(rocketLayoutY - clip_min_y *Utility.SIZE_UNIT <= 0)? rocketLayoutY - clip_min_y *Utility.SIZE_UNIT : 0
					);
			
		} else if( clipY <= rocketLayoutY - clip_max_y *Utility.SIZE_UNIT) { //clip starts to follow
			clip.setY(rocketLayoutY - clip_max_y *Utility.SIZE_UNIT);
		}
		
		//update force N: N - P1 + F_rocket = 0. If F_rocket > P1 => rocket flies => N=0
		double difference = F_rocket.getForceVector().getY() + P1.getForceVector().getY();
		if(difference <= 0) {
			N.getForceVector().set(0, 0);
		} else {
			//N.getForceVector().set(0, -difference);
		}
		//DEBUG: force vector of N
		//System.out.println("Force vector N: (" + N.getForceVector().getX() + ","+ N.getForceVector().getY()+"). Difference: " + difference);
	}
	
	/** Called when burnMoreGas button is clicked*/
	public void burnMoreGasBtnClick(ActionEvent e) {
		//System.out.println("F_gas is now: (0, "  +F_gas.getForceVector().getY() + "). More Gas");
		F_rocket.getForceVector().add(new Vector2D(0, -2 * Utility.DEFAULT_FORCE));
		F_gas.getForceVector().add(new Vector2D(0, 2 * Utility.DEFAULT_FORCE));
	}
	
	/** Called when burnLessGas button is clicked*/
	public void burnLessGasBtnClick(ActionEvent e) {
		//System.out.println("F_gas is now: (0, "  +F_gas.getForceVector().getY() + "). Less Gas");
		if(F_gas.getForceVector().getY() > 0) { //if f_gas is still positive
			F_rocket.getForceVector().add(new Vector2D(0, 2 * Utility.DEFAULT_FORCE));
			F_gas.getForceVector().add(new Vector2D(0, -2 * Utility.DEFAULT_FORCE));
		}
		
	}
}
