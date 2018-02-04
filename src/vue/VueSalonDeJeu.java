package vue;

import javafx.scene.text.Font;
import java.awt.Image;
import javafx.geometry.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text; 
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;

public class VueSalonDeJeu extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("Salon de Jeu");
        
        
        // Bouton d'acces aux relges du jeu	
        Button boutonReglesDuJeu = new Button();
        boutonReglesDuJeu.setText("Règles du jeu");
        boutonReglesDuJeu.setStyle("-fx-background-color:#fff224; -fx-font-size:20px; -fx-padding: 10 50 10 50; -fx-font-family:\"Lucida Handwriting\";");
        boutonReglesDuJeu.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	System.out.println("clic bouton relges du jeu");
            }
        });
        
        // Bouton Voter
        Button boutonQuitter = new Button();
        boutonQuitter.setText("Quitter");
        boutonQuitter.setStyle("-fx-background-color:#4d0000; -fx-font-size:20px; -fx-padding: 10 50 10 50;-fx-font-family:\"Lucida Handwriting\"; -fx-text-fill:white;");
        boutonQuitter.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	System.out.println("clic bouton quitter");
            }
        });
     
        
        // Bouton Quitter
        Button boutonVoter = new Button();
        boutonVoter.setText("Voter");
        boutonVoter.setStyle("-fx-background-color:#00cc00; -fx-font-size:20px; -fx-padding: 10 50 10 50;-fx-font-family:\"Lucida Handwriting\"");
        boutonVoter.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	System.out.println("clic bouton voter");
            }
        });        

             
        // Description du role
        
        String descriptionRoleLoupGaroup = "Le loup-garoup tue chaque\nnuit une personne,\nil doit remporter la partie\nen tuant tous les innocents";
        
        
        
        // Image de la carte du joueur        
        StackPane boxCarte = new StackPane();
        boxCarte.setId("image-loup-garoup");
        
        
        // Nom du rôle du joueur      
        Text texteRole = new Text("Loup-Garou");
        texteRole.setFont(Font.font("Lucida Handwriting", 30));
        
        
        // StackPane de gauche
        GridPane panelGauche = new GridPane();
        panelGauche.setPadding(new Insets(20,20,20,20));
        panelGauche.setAlignment(Pos.CENTER);
        panelGauche.setHgap(10);
        panelGauche.setVgap(10);
     
        panelGauche.setAlignment(Pos.CENTER);

        Text texteDescriptionRole = new Text(descriptionRoleLoupGaroup);
        texteDescriptionRole.setFont(Font.font("Lucida Handwriting", 17));
        
        panelGauche.setConstraints(texteDescriptionRole, 0, 0);
        panelGauche.setConstraints(boutonVoter, 0, 1);
        panelGauche.setConstraints(boutonQuitter, 0, 2);
        
        panelGauche.getChildren().add(texteDescriptionRole);  
        panelGauche.getChildren().add(boutonVoter);
        panelGauche.getChildren().add(boutonQuitter);
        
          
        // HBox du haut
        
        Text nomDeLaPage = new Text("Salon de jeu");
        nomDeLaPage.setStyle("-fx-font-family:\"Lucida Handwriting\"; -fx-font-size: 50");
        
     
        HBox hboxHaut = new HBox();
        
        hboxHaut.setAlignment(Pos.CENTER);
        
        Region blanc1 = new Region();
        Region blanc2 = new Region();
        
        hboxHaut.setHgrow(blanc1, Priority.ALWAYS);
        hboxHaut.setHgrow(blanc2, Priority.ALWAYS);

        
        hboxHaut.getChildren().add(blanc1);
        hboxHaut.getChildren().add(nomDeLaPage);
        hboxHaut.getChildren().add(blanc2);
        hboxHaut.getChildren().add(boutonReglesDuJeu);

        
        
        
        //VBox milieu
        
        VBox panelMilieu = new VBox();
        TextField chatDeJeu = new TextField("chat de jeu");
        Region blanc3 = new Region();
        chatDeJeu.setEditable(false);
        
        chatDeJeu.setMinHeight(400);
        
        TextField zoneDeSaisieMessage = new TextField();
        
        panelMilieu.setPadding(new Insets(20,20,100,20));

        
        panelMilieu.getChildren().add(blanc3);
        panelMilieu.getChildren().add(chatDeJeu);
        panelMilieu.getChildren().add(zoneDeSaisieMessage);

        panelMilieu.setVgrow(blanc3, Priority.ALWAYS);
        
        // StackPane de droite
        GridPane panelDroite = new GridPane();
        panelDroite.setHgap(10);
        panelDroite.setVgap(10);
        panelDroite.setPadding(new Insets(20,20,20,20));
        panelDroite.setAlignment(Pos.CENTER);

        Text nombreDeJoueurs = new Text("Joueurs : 10");
        nombreDeJoueurs.setFont(Font.font("Lucida Handwriting", 20));
        
        Text nombreDeLoupGarouVivant = new Text("2 loup(s)-garoup(s) restant(s)");
        nombreDeLoupGarouVivant.setFont(Font.font("Lucida Handwriting", 20));
        
        Text nombreDInnocentVivant = new Text("3 innocent(s) restant(s)");
        nombreDInnocentVivant.setFont(Font.font("Lucida Handwriting", 20));
        
        Text nomListeJoueursVivant = new Text("Vivants");
        nomListeJoueursVivant.setFont(Font.font("Lucida Handwriting", 30));
        
        Text listeJoueursVivant = new Text("Vincent\n Eliott \n Thomas \n");
        listeJoueursVivant.setStyle(" -fx-font-family:\"Lucida Handwriting\"; -fx-font-size:20; -fx-border-style:solid; -fx-border-width: 5px; ");
        
        Text nomListeJoueursMort = new Text("Morts");
        nomListeJoueursMort.setFont(Font.font("Lucida Handwriting", 30));
        
        Text listeJoueursMort = new Text("Patate2f\nDamien\nThéo");       
        listeJoueursMort.setFont(Font.font("Lucida Handwriting", 20));
        
        panelDroite.setConstraints(nombreDeJoueurs, 0,1);
        panelDroite.setConstraints(nombreDeLoupGarouVivant, 0,2);
        panelDroite.setConstraints(nombreDInnocentVivant, 0,3);
        panelDroite.setConstraints(nomListeJoueursVivant, 0,4);
        panelDroite.setConstraints(listeJoueursVivant, 0,5);
        panelDroite.setConstraints(nomListeJoueursMort, 0,6);
        panelDroite.setConstraints(listeJoueursMort, 0,7);
       
        panelDroite.getChildren().add(nombreDeJoueurs);
        panelDroite.getChildren().add(nombreDeLoupGarouVivant);
        panelDroite.getChildren().add(nombreDInnocentVivant);
        panelDroite.getChildren().add(nomListeJoueursVivant);
        panelDroite.getChildren().add(listeJoueursVivant);
        panelDroite.getChildren().add(nomListeJoueursMort);
        panelDroite.getChildren().add(listeJoueursMort);

       
        // Fenetre Generale
        AnchorPane fenetreGenerale = new AnchorPane();
        
        fenetreGenerale.setPadding(new Insets(20,20,20,20));

        fenetreGenerale.setTopAnchor(hboxHaut, 0.0);
        fenetreGenerale.setRightAnchor(hboxHaut, 50.0);
        fenetreGenerale.setLeftAnchor(hboxHaut, 50.0);
        
        fenetreGenerale.setTopAnchor(panelMilieu, 50.0);
        fenetreGenerale.setRightAnchor(panelMilieu, 400.0);
        fenetreGenerale.setLeftAnchor(panelMilieu, 400.0);

        fenetreGenerale.setTopAnchor(panelDroite, 50.0);
        fenetreGenerale.setRightAnchor(panelDroite, 0.0);

        fenetreGenerale.setTopAnchor(texteRole, 50.0);
        fenetreGenerale.setLeftAnchor(texteRole, 0.0);
        
        fenetreGenerale.setTopAnchor(boxCarte, 70.0);
        fenetreGenerale.setLeftAnchor(boxCarte, 0.0);
        fenetreGenerale.setRightAnchor(boxCarte, 50.0);
        fenetreGenerale.setBottomAnchor(boxCarte, 50.0);
        
        fenetreGenerale.setTopAnchor(panelGauche, 150.0);
        fenetreGenerale.setLeftAnchor(panelGauche, 10.0);
        fenetreGenerale.setBottomAnchor(panelGauche, 50.0);
        
        fenetreGenerale.getChildren().addAll(hboxHaut, panelMilieu, panelDroite, texteRole, boxCarte, panelGauche);
        
        Scene scene = new Scene(fenetreGenerale, 1500,700);        
        scene.getStylesheets().add(VueSalonDeJeu.class.getResource("decoration/lg.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
}
