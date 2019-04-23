package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import motion.DisplayObject;
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
	
	public LawTwoController() {
	}
	
	/** Set up camera in the pane of this scene */
	@Override
	protected void setupCameraPane() {		
		Rectangle clip = new Rectangle(0,0, 600, 200); 
		lawTwoPane.setClip(clip);
	}
	/** Initialize UI elements*/
	@Override
	protected void initPane() {
		//create truck
		truck = new Truck(lawTwoPane, new Vector2D(SIZE_UNIT, SIZE_UNIT*3), 50);
		truck.setVelocity(new Vector2D(10, 0));
		allDisplayObjects.add(truck);
		
		//create blocks
        int numBlocks = 1000;
        
        for (int i = 0; i < numBlocks * SIZE_UNIT; i+= SIZE_UNIT) {
			DisplayObject block = new Block(lawTwoPane, i, SIZE_UNIT*6, SIZE_UNIT);
			allDisplayObjects.add(block);			
		}
        
        //Forced created
        Force P = new Force(lawTwoPane, 0, 4, truck, "P");
        Force N = new Force(lawTwoPane, 0, -4, truck, "N");
        F_engine = new Force(lawTwoPane, 10, 0, truck, "F_engine");
        Force F_drag = new DragForce(lawTwoPane, -5, 0, truck, "F_drag"); //drag of atmosphere
        F_drag.setRelativeXofTextToForce(-3);; // name is displayed backwards 3 * SIZE_UNIT
        
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
