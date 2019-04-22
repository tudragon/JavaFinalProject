package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
	
	@FXML
	private Button previousLawButton;
	
	@FXML
	private Button nextLawButton;
	
	public void previousLaw(ActionEvent e){
		System.out.println("Previous Law Clicked");		
		Parent previousLawScene;
		Scene scene = previousLawButton.getScene();
		Stage primaryStage = (Stage) scene.getWindow();
		
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
	
	public void nextLaw(ActionEvent e){
		System.out.println("Next Law Clicked");
		Parent nextLawScene;
		Scene scene = nextLawButton.getScene();
		Stage primaryStage = (Stage) scene.getWindow();
		
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
	


}
