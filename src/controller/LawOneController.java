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
	//Scene Controller Button
	@FXML
	private Button lawOneStartPauseButton;
	private final int START_STATE = 0;
	private final int PAUSE_STATE = 1;
	private int startPauseButtonState = 0; //current text in button, default is "Start"
	
	@FXML
	private Button lawOneSpeedUpButton;
	
	@FXML
	private Button lawOneSlowDownButton;
	
	//Canvas with object button
	@FXML
	private Pane lawOneFirstPane;
	
	@FXML
	private Pane lawOneSecondPane;
	
	//two circle
	private MovingObject circle_object1;
	private MovingObject circle_object2;
	
	//force
	private Force f;
	
	@FXML
	private Label f1,f2,a1,a2,v1,v2;
	
	private AnimationTimer PaneTimer;
	private List<DisplayObject> allDisplayObjects = new ArrayList<DisplayObject>();
	
	//size unit of objects in panel
	public static final double SIZE_UNIT = 20; //default tile size if 20	
	
	public LawOneController() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//add UI Elements
		initFirstPane();
		initSecondPane();
		
		//create view & add all display objects to its parentPane
		for (DisplayObject displayObject : allDisplayObjects) {
			displayObject.addToPane();
		}
		
		//set up camera
		setupCameraFirstPane();			
		setupCameraSecondPane();	
		        
		//initialize the timer (not start it yet)
		PaneTimer = new AnimationTimer() {
			private long lastUpdate = -1 ;
			
			@Override
				public void handle(long now) {
					//calculate seconds of this frame
					long elapsedNanos = now - lastUpdate ;
					if (lastUpdate < 0) {
						lastUpdate = now ;
						return ;
					}	
					double elapsedSeconds = elapsedNanos / 1_000_000_000.0;
					
					//pass to function
					updatePane(elapsedSeconds);
					
					lastUpdate = now ;					
					}
		};
		
		
	}	
	
	/**
	 * Set up a simple clip (circle 2 will not move)
	 */
	private void setupCameraSecondPane() {
		Rectangle clip = new Rectangle(0,0, 600, 200); 
		lawOneSecondPane.setClip(clip);
	}

	/**
	 * Set up a camera that will follow the circle object
	 */
	private void setupCameraFirstPane() {
		Rectangle clip = new Rectangle(0,0, 600, 200); 

		//Bind clip to circle's X
		clip.xProperty().bind(Bindings.add(-2*SIZE_UNIT, circle_object1.getView().layoutXProperty()));
		lawOneFirstPane.setClip(clip);
		
		//pane.translatex = -clip.x
        lawOneFirstPane.translateXProperty().bind(clip.xProperty().multiply(-1));		
	}

	private void initFirstPane() {
		// create object data
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

	private void initSecondPane() {
		// create object data
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
	
	/**
	 * Update objects every frame
	 * @param elapsedSeconds seconds passed in this particular frame
	 */
	private void updatePane(double elapsedSeconds) {
		//update display objects
		for (DisplayObject displayObject : allDisplayObjects) {
			displayObject.update(elapsedSeconds);
		}		
		
		//update label
		f1.setText("F = " + f.getForceVector());
		f2.setText("F = (0,0)");
		v1.setText("v = " + circle_object1.getVelocity());
		v2.setText("v = " + circle_object2.getVelocity());
		a1.setText("a = " + circle_object1.getAcceleration());
		a2.setText("a = " + circle_object2.getAcceleration());
		
		//update camera
		//lawOneFirstPane.setTranslateX(1d);
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
