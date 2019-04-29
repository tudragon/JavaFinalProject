package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


/**
 * This is main application. Its function is to load the scene from fxml file
 * @TODO: Right now this class loads scene manually. Need a Scene-Controller mechanism to change scene via UI elements
 * @author Tu
 *
 */
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {		
		try {
			//1. Load scene
			Parent lawOneScene = FXMLLoader.load(getClass().getResource("../fxScene/law1scene.fxml"));
			
			//2. Set scene manually. Need to change that
			primaryStage.setTitle("Newton First Law");
            primaryStage.setScene(new Scene(lawOneScene));
            
            primaryStage.setMaximized(true);
            
            System.out.println("Max Width: " +primaryStage.getMaxWidth() + " Max height: " + primaryStage.getMaxHeight());
                        
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
