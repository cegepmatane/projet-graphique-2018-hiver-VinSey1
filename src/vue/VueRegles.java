package vue;

import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;
 
public class VueRegles extends Application {
    
    @Override
    public void start(Stage primaryStage) {
    	BorderPane pane = new BorderPane();
    	Label titre = new Label("Règles");
    	titre.setFont(Font.font("Arial", FontWeight.BOLD, 80));
    	titre.setAlignment(Pos.CENTER);
    	titre.setMaxWidth(Double.POSITIVE_INFINITY);
    	pane.setTop(titre);
        Scene scene = new Scene(pane, 1500, 750);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    
    public static void main(String[] args) {
        launch(args);
    }
}