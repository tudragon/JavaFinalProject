package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import motion.DisplayObject;
import motion.Force;
import motion.Vector2D;
import motion.movingobject.MovingCircle;
import motion.movingobject.MovingObject;
import motion.staticobject.Block;

/**
 * Specific Controller for Scene 1
 * @author Nguyen Minh Tu
 *
 */
public class LawOneController extends LawSceneController{
	/** Start Button and START_PAUSE state design pattern*/
	@FXML
	private Button lawOneStartPauseButton;
	private final int START_STATE = 0;
	private final int PAUSE_STATE = 1;
	private int startPauseButtonState = 0; //current text in button, default is "Start"
	
	/** Speed up and slow down button*/
	@FXML
	private Button lawOneSpeedUpButton;
	
	@FXML
	private Button lawOneSlowDownButton;
	
	/**Canvas with object Pane*/
	@FXML
	private Pane lawOneFirstPane;
	
	@FXML
	private Pane lawOneSecondPane;
	
	/** Two circles in two pane*/
	private MovingObject circle_object1;
	private MovingObject circle_object2;
	
	/** Force acts on circle_object1*/
	private Force f;
	
	/** Display two circles' movement*/
	@FXML
	private Label f1,f2,a1,a2,v1,v2;

	
	public LawOneController() {

	}
	/**
	 * Implement abstract method of parent class. Set up two camera in two panes
	 */
	@Override
	protected void setupCameraPane() {
		setupCameraFirstPane();			
		setupCameraSecondPane();
	}
	
	/** 
	 * Implement abstract method of parent class. Initialize UI elements in two panes
	 */
	@Override
	protected void initPane() {
		initFirstPane();
		initSecondPane();
	}
	
	/**
	 * Set up a simple clip in second Pane (circle 2 will not move)
	 */
	private void setupCameraSecondPane() {
		Rectangle clip = new Rectangle(0,0, 600, 200); 
		lawOneSecondPane.setClip(clip);
	}

	/**
	 * Set up a camera that will follow the circle object in first Pane
	 */
	private void setupCameraFirstPane() {
		Rectangle clip = new Rectangle(0,0, 600, 200); 

		//Bind clip to circle's X
		clip.xProperty().bind(Bindings.add(-2*SIZE_UNIT, circle_object1.getView().layoutXProperty()));
		lawOneFirstPane.setClip(clip);
		
		//pane.translatex = -clip.x
        lawOneFirstPane.translateXProperty().bind(clip.xProperty().multiply(-1));		
	}
	
	/**
	 * This function creates object in the second canvas, including:
	 * 1. circle_object1 with zero movement
	 * 2. 1000 blocks
	 * 3. A force acting on object 1, in order to give it acceleration
	 */
	private void initFirstPane() {
		// create  circle 1
        Vector2D location = new Vector2D(SIZE_UNIT,SIZE_UNIT);
        Vector2D velocity = new Vector2D(0,0);
        Vector2D acceleration = new Vector2D(0,0);
        
        circle_object1 = new MovingCircle(lawOneFirstPane, 
        		location, velocity, acceleration, 1, SIZE_UNIT);
        allDisplayObjects.add(circle_object1);
        
        f = new Force(lawOneFirstPane, 0, 0, circle_object1, "F");
        //Force g = new Force(lawOneFirstPane, 0, 1, circle_object1, "G");
        //Force n = new Force(lawOneFirstPane, 0, -1, circle_object1, "N");
        
        allDisplayObjects.add(f);
        //allDisplayObjects.add(g);
        //allDisplayObjects.add(n);
                        
        //create blocks
        int numBlocks = 1000;
        
        for (int i = 0; i < numBlocks * SIZE_UNIT; i+= SIZE_UNIT) {
			DisplayObject block = new Block(lawOneFirstPane, i, SIZE_UNIT*3, SIZE_UNIT);
			allDisplayObjects.add(block);			
		}
        
	}	
	
	/**
	 * This function creates object in the second canvas, including:
	 * 1. circle_object2 with zero movement
	 * 2. 1000 blocks
	 */
	private void initSecondPane() {
		// create circle 2
        Vector2D location = new Vector2D(SIZE_UNIT,SIZE_UNIT);
        Vector2D velocity = new Vector2D(0,0);
        Vector2D acceleration = new Vector2D( 0,0);
        
        circle_object2 = new MovingCircle(lawOneSecondPane, 
        		location, velocity, acceleration, 1, SIZE_UNIT);
        allDisplayObjects.add(circle_object2);  
                
        //create blocks
        int numBlocks = 1000;
        
        for (int i = 0; i < numBlocks * SIZE_UNIT; i+= SIZE_UNIT) {
			DisplayObject block = new Block(lawOneSecondPane, i, SIZE_UNIT*3, SIZE_UNIT);
			allDisplayObjects.add(block);			
		}		
	}
	
	@Override
	protected void updatePane(double elapsedSeconds) {
		//update all display objects
		super.updatePane(elapsedSeconds);;	
		
		//update force, velocity, acceleration of two objects on screen
		this.f1.setText("F = " + f.getForceVector());
		this.f2.setText("F = (0,0)");
		this.v1.setText("v = " + circle_object1.getVelocity());
		this.v2.setText("v = " + circle_object2.getVelocity());
		this.a1.setText("a = " + circle_object1.getAcceleration());
		this.a2.setText("a = " + circle_object2.getAcceleration());
		
	}


	/**
	 * Click start button. Trigger run method in both canvas
	 * @param e
	 */
	public void lawOneStartStopBtnClick(ActionEvent e){
		System.out.println("Start/Stop Clicked");
		if(startPauseButtonState == START_STATE) {
			//start AnimationTimer
			PaneTimer.start();			
			
			//change to stop state
			startPauseButtonState = PAUSE_STATE;
			lawOneStartPauseButton.setText("Pause");
		} else if (startPauseButtonState == PAUSE_STATE) {
			//stop AnimationTimer
			PaneTimer.stop();
			
			//change to start state
			startPauseButtonState = START_STATE;
			lawOneStartPauseButton.setText("Start");
		}
		
		
	}

	/**
	 * Action listener of "Act Force" button
	 * @param e
	 */
	public void lawOneSpeedUp(ActionEvent e){
		System.out.println("Speed Up Clicked");		
		f.speedUp();	
				
		
	}
	
	/**
	 * Action listener of "Stop Force" button
	 * @param e
	 */
	public void lawOneSlowDown(ActionEvent e){
		System.out.println("Slow Down Clicked");
		f.slowDown();		
	}


}
