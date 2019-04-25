package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import motion.DisplayObject;
import motion.Vector2D;
import motion.force.Force;
import motion.movingobject.MovingObject;
import motion.movingobject.Rocket;
import motion.staticobject.Block;
import motion.staticobject.RocketBase;
import motion.staticobject.StaticObject;

public class LawThreeController extends LawSceneController {
	/**Canvas with object Pane*/
	@FXML
	private Pane lawThreePane;
	Rectangle clip;
	
	/** The main object */
	private MovingObject rocket;
	
	/** Display rocket's movement*/
	@FXML
	private Label p1,p2,f_rocket,f_gas,a,v, f_net;
	
	/** F_engine buttons*/
	@FXML
	private Button lawThreeBurnMoreGas, lawThreeBurnLessGas;
	
	/** Forces*/
	private Force F_rocket,F_gas, P1, P2;

	
	public LawThreeController() {
	}

	@Override
	protected void setupCameraPane() {
		clip = new Rectangle(0,0, 520, 260);
		lawThreePane.setClip(clip);
		
	}

	@Override
	protected void initPane() {
		//create blocks: 
        int numHorizontalBlocks = 50 ;
        int numVerticalBlocks = 1000;
        int offsetY = 11; //block on bottom left has (x,y) = (0,11) * SIZE_UNIT
        int offsetX = 9; //rocket base on bottom left has (x,y) = (9,0) * SIZE_UNIT
        
        for (int i = 0; i < numHorizontalBlocks * SIZE_UNIT; i+= SIZE_UNIT) {
			DisplayObject block = new Block(lawThreePane, i, SIZE_UNIT*offsetY);
			allDisplayObjects.add(block);			
		}
        
        for (int i = 0; i < numVerticalBlocks * SIZE_UNIT; i+= SIZE_UNIT) {
			DisplayObject block = new Block(lawThreePane, 0, -i + offsetY * SIZE_UNIT);
			allDisplayObjects.add(block);			
		}
		
        //Rocket base & Rocket
        StaticObject rocket_base = new RocketBase(lawThreePane, offsetX * SIZE_UNIT, 
        		(offsetY/2) * SIZE_UNIT);
        this.rocket = new Rocket(lawThreePane, (offsetX + 1) * SIZE_UNIT,
        		(offsetY/2) * SIZE_UNIT, 10);
        
        allDisplayObjects.add(rocket_base);
        allDisplayObjects.add(rocket);
        
        /**
         * TODO: 
         * 1. Create Gas as MovingObject. Bind Gas.Y to Rocket.Y-Rocket.Height
         * 2. Create force P1, P2. P1 is on Rocket and P2 is on Earth (another moving object?)
         * 3. Create N on Rocket. N - P1 + Fgas = ma => N's forceVector changes in real-time
         */
        
        //Force
        P1 = new Force(lawThreePane, 0, 10, rocket, "P1");
        P2 = new Force(lawThreePane, 0, -10, rocket, "P2");
        F_rocket = new Force(lawThreePane, 0, -2, rocket, "F_rocket");
        F_gas = new Force(lawThreePane, 0, 2, rocket, "F_gas");
        
        allDisplayObjects.add(P1);
        allDisplayObjects.add(P2);
        allDisplayObjects.add(F_rocket);
        allDisplayObjects.add(F_gas);
        
	}
	
	@Override
	protected void updatePane(double elapsedSeconds) {
		super.updatePane(elapsedSeconds);
		
		//update label
		this.p1.setText("P1 = " + this.P1.getForceVector());
		this.p2.setText("P1 = " + this.P2.getForceVector());
		this.f_rocket.setText("P1 = " + this.F_rocket.getForceVector());
		this.f_gas.setText("P1 = " + this.F_gas.getForceVector());
		this.v.setText("v = " + rocket.getVelocity());		
		this.a.setText("a = " + rocket.getAcceleration());		
		this.f_net.setText("Fnet = " + rocket.getSumForceVector());
	}
	
	/** Called when burnMoreGas button is clicked*/
	public void burnMoreGasBtnClick(ActionEvent e) {
		System.out.println("More Gas");
	}
	
	/** Called when burnLessGas button is clicked*/
	public void burnLessGasBtnClick(ActionEvent e) {
		System.out.println("Less Gas");
	}
}
