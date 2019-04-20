package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent lawOneScene = FXMLLoader.load(getClass().getResource("../fxScene/law1scene.fxml"));
			Parent lawTwoScene = FXMLLoader.load(getClass().getResource("../fxScene/law2scene.fxml"));
			Parent lawThreeScene = FXMLLoader.load(getClass().getResource("../fxScene/law3scene.fxml"));
			
			primaryStage.setTitle("Newton First Law");
            primaryStage.setScene(new Scene(lawThreeScene));
            
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
