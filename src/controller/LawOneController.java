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
import motion.movingobject.MovingCircle;
import motion.movingobject.MovingObject;
import motion.staticobject.Block;

/**
 * Specific Controller for Scene 1
 * @author Nguyen Minh Tu
 *
 */
public class LawOneController extends LawSceneController{
	
	
	/** Speed up and slow down button*/
	@FXML
	private Button lawOneSpeedUpButton;
	
	@FXML
	private Button lawOneSlowDownButton;
	
	/**Canvas with object Pane*/
	@FXML
	private Pane lawOneFirstPane;
	
	private Rectangle clipFirstPane;
	
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
		clipFirstPane = new Rectangle(0,0, 600, 200); 

		//Bind clip to circle's X: clip.x = circle.layoutX - 2 * SIZE_UNIT
		//clipFirstPane.xProperty().bind(Bindings.add(-2*SIZE_UNIT, circle_object1.getView().layoutXProperty()));
		lawOneFirstPane.setClip(clipFirstPane);
		
		//pane.translatex = -clip.x
        lawOneFirstPane.translateXProperty().bind(clipFirstPane.xProperty().multiply(-1));		
	}
	
	/**
	 * This function creates object in the second canvas, including:
	 * 1. circle_object1 with zero movement
	 * 2. 1000 blocks
	 * 3. A force acting on object 1, in order to give it acceleration
	 */
	private void initFirstPane() {
		// create  circle 1
        Vector2D location = new Vector2D(Utility.SIZE_UNIT,Utility.SIZE_UNIT);
        Vector2D velocity = new Vector2D(0,0);
        Vector2D acceleration = new Vector2D(0,0);
        
        circle_object1 = new MovingCircle(lawOneFirstPane, 
        		location, velocity, acceleration, 1);
        allDisplayObjects.add(circle_object1);
        
        f = new Force(lawOneFirstPane, 0, 0, circle_object1, "F");
        //Force g = new Force(lawOneFirstPane, 0, 1, circle_object1, "G");
        //Force n = new Force(lawOneFirstPane, 0, -1, circle_object1, "N");
        
        allDisplayObjects.add(f);
        //allDisplayObjects.add(g);
        //allDisplayObjects.add(n);
                        
        //create blocks
        int numBlocks = 1000;
        
        for (int i = 0; i < numBlocks * Utility.SIZE_UNIT; i+= Utility.SIZE_UNIT) {
			DisplayObject block = new Block(lawOneFirstPane, i, Utility.SIZE_UNIT*3);
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
        Vector2D location = new Vector2D(Utility.SIZE_UNIT,Utility.SIZE_UNIT);
        Vector2D velocity = new Vector2D(0,0);
        Vector2D acceleration = new Vector2D( 0,0);
        
        circle_object2 = new MovingCircle(lawOneSecondPane, 
        		location, velocity, acceleration, 1);
        allDisplayObjects.add(circle_object2);  
                
        //create blocks
        int numBlocks = 1000;
        
        for (int i = 0; i < numBlocks * Utility.SIZE_UNIT; i+= Utility.SIZE_UNIT) {
			DisplayObject block = new Block(lawOneSecondPane,  i, Utility.SIZE_UNIT*3);
			allDisplayObjects.add(block);			
		}		
	}
	
	/**
	 * Update movement as well as label
	 */
	@Override
	protected void updatePane(double elapsedSeconds) {
		//update all display objects
		super.updatePane(elapsedSeconds);
		
		//DEBUG: force location relative to circle location
		//System.out.println("Circle: (" + this.circle_object1.getX() + ", " + this.circle_object1.getY() + ")");
		//System.out.println("Circle dimension: (" + this.circle_object1.getWidth() + ", " + this.circle_object1.getHeight() + ")");
		
		//update force, velocity, acceleration of two objects on screen
		this.f1.setText("F = " + f.getForceVector());
		this.f2.setText("F = (0,0)");
		this.v1.setText("v = " + circle_object1.getVelocity());
		this.v2.setText("v = " + circle_object2.getVelocity());
		this.a1.setText("a = " + circle_object1.getAcceleration());
		this.a2.setText("a = " + circle_object2.getAcceleration());
		
		//update camera first pane
		double clipX = clipFirstPane.getX();
		double circleLayoutX = circle_object1.getView().getLayoutX();
				
		if( clipX >= circleLayoutX - clip_min *Utility.SIZE_UNIT) { //clip starts to go backwards
			clipFirstPane.setX(
					(circleLayoutX - clip_min *Utility.SIZE_UNIT >= 0)? circleLayoutX - clip_min *Utility.SIZE_UNIT : 0
					);
					
		} else if( clipX <= circleLayoutX - clip_max *Utility.SIZE_UNIT) { //clip starts to follow
			clipFirstPane.setX(circleLayoutX - clip_max *Utility.SIZE_UNIT);
		}
	}


	/**
	 * Action listener of speed up button
	 * @param e
	 */
	public void lawOneSpeedUp(ActionEvent e){
		System.out.println("Speed Up Clicked");		
		f.speedUp();	
				
		
	}
	
	/**
	 * Action listener of slow down button
	 * @param e
	 */
	public void lawOneSlowDown(ActionEvent e){
		System.out.println("Slow Down Clicked");
		f.slowDown();		
	}


}
