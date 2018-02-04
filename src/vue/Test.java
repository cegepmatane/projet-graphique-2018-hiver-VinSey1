package vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class Test extends Application{
	
	
    public static void main(String[] args) {
        launch(args);
    }
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane fenetreGenerale = new BorderPane();
		
        HBox boxCarte = new HBox();
//        boxCarte.setId("image-loup-garoup");
        boxCarte.setStyle(  "  -fx-background-image: url(\"../illustration/carteLoupGarou.png\"); "   );
		
        
        
        StackPane stackPane = new StackPane();
        
        stackPane.getChildren().add(boxCarte);
		
        fenetreGenerale.setRight(stackPane);
		
        Scene scene = new Scene(fenetreGenerale, 1500,700);        
        scene.getStylesheets().add(VueSalonDeJeu.class.getResource("decoration/lg.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}

	
	
	
}
