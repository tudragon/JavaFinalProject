package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * General Controller for all law Scene
 * @author Nguyen Minh Tu
 *
 */
public abstract class LawSceneController implements Initializable {	
	
	@FXML
	private Button previousLawButton;
	
	@FXML
	private Button nextLawButton;
	
	public void previousLaw(ActionEvent e){
		System.out.println("Previous Law Clicked");
	}
	
	public void nextLaw(ActionEvent e){
		System.out.println("Next Law Clicked");
	}
	


}
