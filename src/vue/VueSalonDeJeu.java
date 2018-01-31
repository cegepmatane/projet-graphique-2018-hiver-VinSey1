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
        boutonReglesDuJeu.setStyle("-fx-background-color:#fff224; -fx-font-size:20px");
        boutonReglesDuJeu.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	System.out.println("clic bouton relges du jeu");
            }
        });
        
        // Bouton Voter
        Button boutonQuitter = new Button();
        boutonQuitter.setText("Quitter");
        boutonQuitter.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	System.out.println("clic bouton quitter");
            }
        });
     
        
        // Bouton Quitter
        Button boutonVoter = new Button();
        boutonVoter.setText("Voter");
        boutonVoter.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	System.out.println("clic bouton voter");
            }
        });        

             
        // Description du role
        
        String descriptionRoleLoupGaroup = "Le loup-garoup tue chaque nuit une personne,\n il doit remporter la partie en tuant tous les innocents";
        
        
        // StackPane de gauche
        GridPane panelGauche = new GridPane();
        panelGauche.setPadding(new Insets(20,20,20,20));
        panelGauche.setAlignment(Pos.CENTER);
        panelGauche.setHgap(10);
        panelGauche.setVgap(10);
        
        Text texteRole = new Text("Rôle");
        texteRole.setFont(Font.font("Lucida Handwriting", 30));
        
        panelGauche.setAlignment(Pos.CENTER);
        
        try {
			Image imageCarteLoupGarou = ImageIO.read(new File("CarteLoupGarou.jpg"));
//			ImageView imageViewCarteLoupGaroup = new ImageView(imageCarteLoupGarou);
//	        panelGauche.getChildren().add(imageViewCarteLoupGaroup);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Text texteDescriptionRole = new Text(descriptionRoleLoupGaroup);
      
        panelGauche.setConstraints(texteRole, 0, 0); 
        panelGauche.setConstraints(texteDescriptionRole, 0, 1);
        panelGauche.setConstraints(boutonVoter, 0, 2);
        panelGauche.setConstraints(boutonQuitter, 0, 3);
        
        panelGauche.getChildren().add(texteRole);
        panelGauche.getChildren().add(texteDescriptionRole);  
        panelGauche.getChildren().add(boutonVoter);
        panelGauche.getChildren().add(boutonQuitter);
        
        
        // AnchorPane du haut
        // NON UTILISE
//        AnchorPane anchorPaneHaut = new AnchorPane();
//        
//        Text nomDeLaPage = new Text("Salon de jeu");
//        nomDeLaPage.setFont(Font.font("Lucida Handwriting", 50));
//        TextField chatDeJeu = new TextField("chat de jeu");
//        
//        anchorPaneHaut.setPadding(new Insets(10,10,10,10));
//
//        anchorPaneHaut.getChildren().add(nomDeLaPage);
//        anchorPaneHaut.getChildren().add(boutonReglesDuJeu);
//        
//        anchorPaneHaut.setTopAnchor(nomDeLaPage, 10.0);
//        anchorPaneHaut.setRightAnchor(boutonReglesDuJeu, 10.0);
        
        
        // HBox du haut
        
        HBox hboxHaut = new HBox();
        
        hboxHaut.setAlignment(Pos.CENTER);
//        nomDeLaPage.setPadding(new java.awt.Insets(0, 0, 0, 400));
        
        Region blanc1 = new Region();
        Region blanc2 = new Region();
        
        hboxHaut.setHgrow(blanc1, Priority.ALWAYS);
        hboxHaut.setHgrow(blanc2, Priority.ALWAYS);

        
        hboxHaut.getChildren().add(blanc1);
        hboxHaut.getChildren().add(nomDeLaPage);
        hboxHaut.getChildren().add(blanc2);
        hboxHaut.getChildren().add(boutonReglesDuJeu);

        
        
        
        //StackPane milieu
        
        GridPane panelMilieu = new GridPane();
        panelMilieu.setPadding(new Insets(20,20,20,20));
        panelMilieu.setHgap(10);
        panelMilieu.setVgap(10);
        panelMilieu.setAlignment(Pos.CENTER);
        
        panelMilieu.getChildren().add(chatDeJeu);
        
        // StackPane de droite
        GridPane panelDroite = new GridPane();
        panelDroite.setHgap(10);
        panelDroite.setVgap(10);
        panelDroite.setPadding(new Insets(20,20,20,20));
        panelDroite.setAlignment(Pos.CENTER);

        Text nombreDeJoueurs = new Text("Joueurs : 10");
        Text nombreDeLoupGarouVivant = new Text("2 loup(s)-garoup(s) restant(s)");
        Text nomListeJoueursVivant = new Text("Vivants");
        Text listeJoueursVivant = new Text("Vincent\n Eliott \n Thomas \n");
        Text nomListeJoueursMort = new Text("Morts");
        Text listeJoueursMort = new Text("Denis\nDamien\nThéo");       
        
        panelDroite.setConstraints(nombreDeJoueurs, 0,1);
        panelDroite.setConstraints(nombreDeLoupGarouVivant, 0,2);
        panelDroite.setConstraints(nomListeJoueursVivant, 0,3);
        panelDroite.setConstraints(listeJoueursVivant, 0,4);
        panelDroite.setConstraints(nomListeJoueursMort, 0,5);
        panelDroite.setConstraints(listeJoueursMort, 0,6);
       
       

        panelDroite.getChildren().add(nombreDeJoueurs);
        panelDroite.getChildren().add(nombreDeLoupGarouVivant);
        panelDroite.getChildren().add(nomListeJoueursVivant);
        panelDroite.getChildren().add(listeJoueursVivant);
        panelDroite.getChildren().add(nomListeJoueursMort);
        panelDroite.getChildren().add(listeJoueursMort);

       
        // Fenetre Generale
        BorderPane fenetreGenerale = new BorderPane();
        
//        fenetreGenerale.setHgap(50);
//        fenetreGenerale.setVgap(50);
        
        fenetreGenerale.setPadding(new Insets(20,20,20,20));

        
//        fenetreGenerale.setConstraints(panelGauche, 0,0);
//        fenetreGenerale.setConstraints(panelMilieu, 1,0);
//        fenetreGenerale.setConstraints(panelDroite, 2,0);
        
//        fenetreGenerale.getChildren().add(panelGauche);
//        fenetreGenerale.getChildren().add(panelMilieu);
//        fenetreGenerale.getChildren().add(panelDroite);
        

        fenetreGenerale.setRight(panelDroite);
        fenetreGenerale.setLeft(panelGauche);
        fenetreGenerale.setTop(hboxHaut);
        fenetreGenerale.setCenter(panelMilieu);

        //ajouter les 3 gridpane
        primaryStage.setScene(new Scene(fenetreGenerale, 1600, 900));
        primaryStage.show();
    }
}
