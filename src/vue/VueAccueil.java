package vue;


import javax.print.DocFlavor.URL;

import com.sun.xml.internal.ws.org.objectweb.asm.Label;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class VueAccueil extends Application {

	public static void main(String[] args) {
        launch(args);
    }
    
	
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Accueil");
        Button boutonDeConnexion = new Button();
        boutonDeConnexion.setText("Se connecter");
        boutonDeConnexion.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Connexion ...");
            }
        });
        
        Button boutonQuitter = new Button();
        boutonQuitter.setText("Quitter");
        boutonQuitter.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		System.out.println("Au revoir AHOOUUUUUU");
        	}
        });
        
        Button boutonRegles = new Button();
        boutonRegles.setText("Règles du jeu");
        boutonRegles.setStyle("fx-background-color : yellow");
        boutonRegles.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		System.out.println("Affichage des règles du jeu");
        	}
        });
        
        Text titre = new Text("Les loups-garous");
        titre.setFont(Font.font("Lucida Handwriting", FontWeight.BOLD, 80));
        
        Text pseudo = new Text("Entrez votre pseudonyme :");
        pseudo.setFont(Font.font("Lucida Handwriting", FontWeight.NORMAL, 40));
        
        TextField creationPseudo = new TextField();
        
        Image imageAccueil = new Image("File:img/werefolf.png");
        ImageView imageAccueilView = new ImageView(imageAccueil);
        
        
        BorderPane miseEnPage = new BorderPane();
        miseEnPage.setTop(titre);
        miseEnPage.setAlignment(titre, Pos.CENTER);
        AnchorPane elementCentral = new AnchorPane();
        miseEnPage.setCenter(elementCentral);
        elementCentral.setMaxSize(1200, 600);
        elementCentral.setMinSize(100, 50);
        
        HBox boxImage = new HBox();
        
        elementCentral.setTopAnchor(pseudo, (double) 300);
        elementCentral.setRightAnchor(pseudo, (double) 300);
        elementCentral.setLeftAnchor(pseudo, (double) 300);
        elementCentral.setBottomAnchor(pseudo, (double) 50);
        
        elementCentral.setTopAnchor(creationPseudo, (double) 375);
        elementCentral.setRightAnchor(creationPseudo, (double) 300);
        elementCentral.setLeftAnchor(creationPseudo, (double) 300);
        elementCentral.setBottomAnchor(creationPseudo, (double) 150);
        
        elementCentral.setTopAnchor(boutonDeConnexion, (double) 455);
        elementCentral.setRightAnchor(boutonDeConnexion, (double) 300);
        elementCentral.setLeftAnchor(boutonDeConnexion, (double) 300);
        elementCentral.setBottomAnchor(boutonDeConnexion, (double) 100);
        
        elementCentral.setTopAnchor(boutonQuitter, (double) 505);
        elementCentral.setRightAnchor(boutonQuitter, (double) 300);
        elementCentral.setLeftAnchor(boutonQuitter, (double) 300);
        elementCentral.setBottomAnchor(boutonQuitter, (double) 50);
        
        elementCentral.setTopAnchor(boutonRegles, (double) 0);
        elementCentral.setRightAnchor(boutonRegles, (double) 0);
        elementCentral.setLeftAnchor(boutonRegles, (double) 1000);
        elementCentral.setBottomAnchor(boutonRegles, (double) 550);
        
        elementCentral.setTopAnchor(boxImage, (double) 10);
        elementCentral.setRightAnchor(boxImage, (double) 200);
        elementCentral.setLeftAnchor(boxImage, (double) 200);
        elementCentral.setBottomAnchor(boxImage, (double) 320);
        
        
        boxImage.setStyle("-fx-border-color: red; -fx-border-width: 2px 2px 2px 2px");
        boxImage.getChildren().add(imageAccueilView);
        
       

        elementCentral.getChildren().addAll(pseudo, creationPseudo, boutonDeConnexion, boutonQuitter, boutonRegles, boxImage);
        elementCentral.setStyle("-fx-border-color: black; -fx-border-width: 2px 2px 2px 2px");
        
        
        Scene scene = new Scene(miseEnPage, 1500, 750);
        scene.getStylesheets().add(VueAccueil.class.getResource("../decoration/lg.css").toExternalForm());
       
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
    }
}
	

