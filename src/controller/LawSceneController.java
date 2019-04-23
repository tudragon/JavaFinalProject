package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import motion.DisplayObject;

/**
 * General Controller for all law Scene
 * @author Nguyen Minh Tu
 *
 */
public abstract class LawSceneController implements Initializable {	
	/** Indicate the current scene:
	 * 1: law one
	 * 2: law two
	 * 3: law three */ 
	private static int SCENE_NUMBER = 1;
	
	/**size unit of objects in panel. Default is 20 pixels*/
	public static final double SIZE_UNIT = 20;
	
	/** Keep track of all objects on scene*/
	protected List<DisplayObject> allDisplayObjects = new ArrayList<DisplayObject>();
	
	/** Main animation loop*/
	protected AnimationTimer PaneTimer;
	
	/** Previous and next law button*/
	@FXML
	private Button previousLawButton, nextLawButton;
	
	/** Start Button and START_PAUSE state design pattern*/
	@FXML
	protected Button startPauseButton;
	protected final int START_STATE = 0;
	protected final int PAUSE_STATE = 1;
	protected int startPauseButtonState = 0; //current text in button, default is "Start"
	
	/**
	 * This function is called first when the controller is called by JavaFx. It will:
	 * 1. Initialize every UI elements in the scene
	 * 2. Create view of all {@link DisplayObject}, add them to corresponding pane
	 * 3. Set up camera to follow main object on screen
	 * 4. Initialize the timer (not start it yet)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//1. add UI Elements
		initPane();

		//2. create view & add all display objects to its parentPane
		for (DisplayObject displayObject : allDisplayObjects) {
			displayObject.addToPane();
		}
				
		//3. set up camera
		setupCameraPane();			

				        
		//4. initialize the timer (not start it yet)
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
	
	/** Set up camera in every pane the scene has*/
	protected abstract void setupCameraPane();
	
	/** Initialize UI elements*/
	protected abstract void initPane();

	/**
	 * Called when the previous law button in scene 1/2/3 is clicked
	 */
	public void previousLaw(ActionEvent e){
		System.out.println("Previous Law Clicked");		
		Parent previousLawScene; //scene loader
		Scene scene = previousLawButton.getScene(); //current scene
		Stage primaryStage = (Stage) scene.getWindow(); //current stage
		
		switch (SCENE_NUMBER) {
		case 2:	 //previous law button in scene 2
			try {
				previousLawScene = FXMLLoader.load(getClass().getResource("../fxScene/law1scene.fxml"));

				primaryStage.setTitle("Newton First Law");
				scene.setRoot(previousLawScene);
	            SCENE_NUMBER--;
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
			break;
		case 3: //previous law button in scene 3
			try {
				previousLawScene = FXMLLoader.load(getClass().getResource("../fxScene/law2scene.fxml"));

				primaryStage.setTitle("Newton Second Law");
				scene.setRoot(previousLawScene);
	            SCENE_NUMBER--;
			} catch (IOException e1) {
				e1.printStackTrace();
			}		
			break;
		default:
			break;
		}
	}
	
	/**
	 * Called when the next law button in scene 1/2/3 is clicked
	 */
	public void nextLaw(ActionEvent e){
		System.out.println("Next Law Clicked");
		Parent nextLawScene;  //scene loader
		Scene scene = nextLawButton.getScene(); //current scene
		Stage primaryStage = (Stage) scene.getWindow();  //current stage
		
		switch (SCENE_NUMBER) {
		case 1:	 //next law button in scene 1
			try {
				nextLawScene = FXMLLoader.load(getClass().getResource("../fxScene/law2scene.fxml"));

				primaryStage.setTitle("Newton Second Law");
				scene.setRoot(nextLawScene);
	            SCENE_NUMBER++;
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
			break;
		case 2: //next law button in scene 2
			try {
				nextLawScene = FXMLLoader.load(getClass().getResource("../fxScene/law3scene.fxml"));

				primaryStage.setTitle("Newton Third Law");
				scene.setRoot(nextLawScene);
	            SCENE_NUMBER++;
			} catch (IOException e1) {
				e1.printStackTrace();
			}		
			break;
		default:
			break;
		}
	}
	
	/**
	 * Update objects every frame
	 * @param elapsedSeconds seconds passed in this particular frame
	 */
	protected void updatePane(double elapsedSeconds) {
		//update all display objects
		for (DisplayObject displayObject : allDisplayObjects) {
			displayObject.update(elapsedSeconds);
		}		
	}
	
	/**
	 * Click start/pause button. Trigger run method in pane
	 */
	public void startPauseBtnClick(ActionEvent e){
		System.out.println("Start/Stop Clicked");
		if(startPauseButtonState == START_STATE) {
			//start AnimationTimer
			PaneTimer.start();			
			
			//change to stop state
			startPauseButtonState = PAUSE_STATE;
			startPauseButton.setText("PAUSE");
		} else if (startPauseButtonState == PAUSE_STATE) {
			//stop AnimationTimer
			PaneTimer.stop();
			
			//change to start state
			startPauseButtonState = START_STATE;
			startPauseButton.setText("START");
		}
		
		
	}

}
