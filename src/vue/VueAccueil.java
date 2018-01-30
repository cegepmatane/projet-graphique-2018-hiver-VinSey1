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
        Button bouttonDeConnexion = new Button();
        bouttonDeConnexion.setText("Se connecter");
        bouttonDeConnexion.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Connexion ...");
            }
        });
        Button bouttonQuitter = new Button();
        bouttonQuitter.setText("Quitter");
        bouttonQuitter.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		System.out.println("Au revoir AHOOUUUUUU");
        	}
        });
        
        Button bouttonRegles = new Button();
        bouttonRegles.setText("Règles du jeu");
        bouttonRegles.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		System.out.println("Affichage des règles du jeu");
        	}
        });
        
        Text titre = new Text("Les loups-garous");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 80));
        Text pseudo = new Text("Entrez votre pseudonyme :");
        pseudo.setFont(Font.font("Arial", FontWeight.NORMAL, 60));
        TextField creationPseudo = new TextField();
        
        
        
        BorderPane miseEnPage = new BorderPane();
        miseEnPage.setTop(titre);
        miseEnPage.setAlignment(titre, Pos.CENTER);
        
        GridPane grille = new GridPane();
        grille.setAlignment(Pos.CENTER);
        grille.setHgap(10);
        grille.setVgap(50);
        grille.setPadding(new Insets(25,25,25,25));
        grille.add(pseudo, 1, 3);
        grille.add(creationPseudo, 1, 4);
        grille.add(bouttonDeConnexion, 1, 5);
        grille.add(bouttonQuitter, 1, 6);
        miseEnPage.setCenter(grille);
        
        primaryStage.setScene(new Scene(miseEnPage, 1500, 750));
        primaryStage.show();
        
        
    }
}
	

