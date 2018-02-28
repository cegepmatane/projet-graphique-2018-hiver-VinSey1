package vue;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VueVillageois extends Application{

	public static Stage instanceVueVillageois= new Stage();
	
	private Button valider = new Button("Valider");
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Actions du jour");;
		
		valider.setStyle("-fx-background-color:#fff224; -fx-font-size:20px; -fx-padding: 10 50 10 50; -fx-font-family:\"Lucida Handwriting\";");
		
		
	}
	
	
	

}
