package vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class Test extends Application{
	
	
    public static void main(String[] args) {
        launch(args);
    }
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		
		AnchorPane elementCentral = new AnchorPane();
		
		elementCentral.setMaxSize(1200, 600);
		elementCentral.setMinSize(100, 50);
			
        StackPane boxCarte = new StackPane();
        boxCarte.setId("image-loup-garoup");
        
        elementCentral.setTopAnchor(boxCarte, (double) 10);
        elementCentral.setRightAnchor(boxCarte, (double) 200);
        elementCentral.setLeftAnchor(boxCarte, (double) 200);
        elementCentral.setBottomAnchor(boxCarte, (double) 320);       
        
        elementCentral.getChildren().addAll(boxCarte);
              		
        Scene scene = new Scene(elementCentral, 1500,700);        
        scene.getStylesheets().add(VueSalonDeJeu.class.getResource("decoration/lg.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}

	
	
	
}
