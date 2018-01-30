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
import javafx.stage.*;
 
public class VueRegles extends Application {
    
    @Override
    public void start(Stage primaryStage) {
    	Pane pane = new Pane();
    	Label titre = new Label("Règles");
    	titre.setFont(Font.font("Arial", FontWeight.BOLD, 80));
        /*Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");

            }
        });*/
        
        //grid.add(btn, 0, 1);
       
    	pane.getChildren().add(titre);
        Scene scene = new Scene(pane, 1500, 750);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    
    public static void main(String[] args) {
        launch(args);
    }
}