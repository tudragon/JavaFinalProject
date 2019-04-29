package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WelcomeSceneController implements Initializable {
	
	@FXML
	private Button firstLaw, secondLaw, thirdLaw;
	

	public WelcomeSceneController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
	
	public void firstLawClick() {
		this.lawClick(1);
	}
	
	public void secondLawClick() {
		this.lawClick(2);
	}

	public void thirdLawClick() {
		this.lawClick(3);
	}
	
	/**
	 * General method for choosing scene
	 * @param scene_number
	 */
	private void lawClick(int scene_number) {
		//set scene number indicator
		LawSceneController.SCENE_NUMBER = scene_number;
		
		Parent lawScene = null;  //scene loader
		Scene scene = firstLaw.getScene(); //current scene
		Stage primaryStage = (Stage) scene.getWindow();  //current stage
		
		//load scene
		switch (scene_number) {
		case 1:	
			try {
				lawScene = FXMLLoader.load(getClass().getResource("../fxScene/law1scene.fxml"));
				primaryStage.setTitle("Newton First Law");				
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
			break;
		case 2: 
			try {
				lawScene = FXMLLoader.load(getClass().getResource("../fxScene/law2scene.fxml"));
				primaryStage.setTitle("Newton Second Law");				
			} catch (IOException e1) {
				e1.printStackTrace();
			}		
			break;
		case 3: 
			try {
				lawScene = FXMLLoader.load(getClass().getResource("../fxScene/law3scene.fxml"));
				primaryStage.setTitle("Newton Third Law");				
			} catch (IOException e1) {
				e1.printStackTrace();
			}		
			break;
		default:
			break;
		}
		scene.setRoot(lawScene);
	}

}
