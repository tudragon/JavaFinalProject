package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import motion.DisplayObject;
import motion.Force;
import motion.Vector2D;
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
		truck = new Truck(lawTwoPane, new Vector2D(SIZE_UNIT, SIZE_UNIT*2), 50);
		truck.setVelocity(new Vector2D(10, 0));
		allDisplayObjects.add(truck);
		
		//create blocks
        int numBlocks = 1000;
        
        for (int i = 0; i < numBlocks * SIZE_UNIT; i+= SIZE_UNIT) {
			DisplayObject block = new Block(lawTwoPane, i, SIZE_UNIT*5, SIZE_UNIT);
			allDisplayObjects.add(block);			
		}
        
        //Forced created
        Force P = new Force(lawTwoPane, 0, 4, truck, "P");
        //Force N = new Force(lawTwoPane, 0, -4, truck, "N");
        allDisplayObjects.add(P);
        //allDisplayObjects.add(N);
		
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


}
