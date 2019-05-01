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
import motion.force.DragForce;
import motion.force.Force;
import motion.movingobject.Truck;
import motion.movingobject.MovingObject;
import motion.staticobject.Block;

public class LawTwoController extends LawSceneController {
	/**Canvas with object Pane*/
	@FXML
	private Pane lawTwoPane;
	
	/** The main object */
	private MovingObject truck;
	
	/** Display truck's movement*/
	@FXML
	private Label f,a,v,m;
	
	/** F_engine buttons*/
	@FXML
	private Button lawTwoForceEngineDown, lawTwoForceEngineUp;
	
	/** F_engine*/
	private Force F_engine;
	
	/** Pane's clip*/
	Rectangle clip;
	
	/** Main formula*/
	@FXML
	private Label lawTwoFormula;
	
	public LawTwoController() {
	}
	
	/** Set up camera in the pane of this scene */
	@Override
	protected void setupCameraPane() {		
		clip = new Rectangle(0,0, 950, 500);
		lawTwoPane.setClip(clip);
		
		//Move camera: pane.translatex = -clip.x 
        lawTwoPane.translateXProperty().bind(clip.xProperty().multiply(-1));
	}
	
	/** Initialize UI elements*/
	@Override
	protected void initPane() {
		//change formula text: a = sum(F)/m with Sigma sign
		lawTwoFormula.setText("a = \u03A3F / m");

		//create truck
		truck = new Truck(lawTwoPane, new Vector2D(Utility.SIZE_UNIT, Utility.SIZE_UNIT*3), 10);
		truck.setVelocity(new Vector2D(10, 0));
		allDisplayObjects.add(truck);
		
		//create blocks
        int numBlocks = 1000;
        
        for (int i = 0; i < numBlocks * Utility.SIZE_UNIT; i+= Utility.SIZE_UNIT) {
			DisplayObject block = new Block(lawTwoPane, i, Utility.SIZE_UNIT*6);
			allDisplayObjects.add(block);			
		}
        
        //Forced created
        Force P = new Force(lawTwoPane, 0, 8, truck, "P");
        Force N = new Force(lawTwoPane, 0, -8, truck, "N");
        //DEBUG: 
        //System.out.println("Truck width: " + truck.getWidth() + " height: " + truck.getHeight());
        N.setRelativePositionOfForceToObject(Utility.SIZE_UNIT * -1, truck.getHeight()/2); //test move N down
        
        F_engine = new Force(lawTwoPane, 10, 0, truck, "F_engine");
        Force F_drag = new DragForce(lawTwoPane, -5, 0, truck, "F_drag"); //drag of atmosphere
        F_drag.setRelativeXofTextToForce(-1); // name is displayed backwards
        F_drag.setRelativePositionOfForceToObject(1 * Utility.SIZE_UNIT, -1 * Utility.SIZE_UNIT);
        
        allDisplayObjects.add(P);
        allDisplayObjects.add(N);
        allDisplayObjects.add(F_drag);
        allDisplayObjects.add(F_engine);
		
	}
	
	/**
	 * Update movement as well as label
	 */
	@Override
	protected void updatePane(double elapsedSeconds) {
		//update all display objects
		super.updatePane(elapsedSeconds);;	
		
		//update force, velocity, acceleration of two objects on screen
		this.f.setText("F = " + truck.getSumForceVector());
		this.v.setText("v = " + truck.getVelocity());		
		this.a.setText("a = " + truck.getAcceleration());
		this.m.setText("m = " + truck.getMass());
		
		//update clip
		double clipX = clip.getX();
		double truckLayoutX = truck.getView().getLayoutX();
		
		if( clipX >= truckLayoutX - clip_min_x *Utility.SIZE_UNIT) { //clip starts to go backwards
			clip.setX(
					(truckLayoutX - clip_min_x *Utility.SIZE_UNIT >= 0)? truckLayoutX - clip_min_x *Utility.SIZE_UNIT : 0
					);
			
		} else if( clipX <= truckLayoutX - clip_max_x *Utility.SIZE_UNIT) { //clip starts to follow
			clip.setX(truckLayoutX - clip_max_x *Utility.SIZE_UNIT);
		}
		
	}
	
	/** Called when F_engine++ button is clicked */
	public void forceEngineUpClick(ActionEvent e) {
		System.out.println("Force engine ++ clicked");
		F_engine.speedUp();
	}
	
	/** Called when F_engine-- button is clicked */
	public void forceEngineDownClick(ActionEvent e) {
		System.out.println("Force engine -- clicked");
		F_engine.slowDown();
	}

}
