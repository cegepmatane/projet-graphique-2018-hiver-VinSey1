package vue;


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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
        
        
        
        
        
        BorderPane miseEnPage = new BorderPane();
        miseEnPage.setTop(titre);
        miseEnPage.setAlignment(titre, Pos.CENTER);
        
        AnchorPane grille = new AnchorPane();
        miseEnPage.setCenter(grille);
        grille.setMaxSize(1200, 600);
        grille.setMinSize(100, 50);
        grille.setTopAnchor(pseudo, (double) 300);
        grille.setRightAnchor(pseudo, (double) 300);
        grille.setLeftAnchor(pseudo, (double) 300);
        grille.setBottomAnchor(pseudo, (double) 50);
        grille.setTopAnchor(creationPseudo, (double) 375);
        grille.setRightAnchor(creationPseudo, (double) 300);
        grille.setLeftAnchor(creationPseudo, (double) 300);
        grille.setBottomAnchor(creationPseudo, (double) 150);
        grille.setTopAnchor(boutonDeConnexion, (double) 455);
        grille.setRightAnchor(boutonDeConnexion, (double) 300);
        grille.setLeftAnchor(boutonDeConnexion, (double) 300);
        grille.setBottomAnchor(boutonDeConnexion, (double) 100);
        grille.setTopAnchor(boutonQuitter, (double) 505);
        grille.setRightAnchor(boutonQuitter, (double) 300);
        grille.setLeftAnchor(boutonQuitter, (double) 300);
        grille.setBottomAnchor(boutonQuitter, (double) 50);
        grille.setTopAnchor(boutonRegles, (double) 0);
        grille.setRightAnchor(boutonRegles, (double) 0);
        grille.setLeftAnchor(boutonRegles, (double) 1000);
        grille.setBottomAnchor(boutonRegles, (double) 550);
        grille.getChildren().addAll(pseudo, creationPseudo, boutonDeConnexion, boutonQuitter, boutonRegles);
        grille.setStyle("-fx-border-color: black; -fx-border-width: 2px 2px 2px 2px");
        
        primaryStage.setScene(new Scene(miseEnPage, 1500, 750));
        primaryStage.show();
        
        
    }
}
	

