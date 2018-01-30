package vue;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.text.html.ImageView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text; 
import javafx.scene.layout.GridPane;

public class VueSalonDeJeu extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("Salon de Jeu");
        
        
        // Bouton d'acces aux relges du jeu
        Button boutonReglesDuJeu = new Button();
        boutonReglesDuJeu.setText("Say 'Hello World'");
        boutonReglesDuJeu.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	System.out.println("clic bouton relges du jeu");
            }
        });
        
        // Bouton Voter
        Button boutonQuitter = new Button();
        boutonQuitter.setText("Say 'Hello World'");
        boutonQuitter.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	System.out.println("clic bouton quitter");
            }
        });
     
        
        // Bouton Quitter
        Button boutonVoter = new Button();
        boutonVoter.setText("Say 'Hello World'");
        boutonVoter.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	System.out.println("clic bouton voter");
            }
        });        

             
        // Description du role
        
        String descriptionRoleLoupGaroup = "Le loup-garoup tue chaque nuit une personne, il doit remporter la partie en tuant tous les innocents";
        
        
        // StackPane de gauche
        StackPane panelGauche = new StackPane();
        Text texteRole = new Text("Rôle");
        panelGauche.getChildren().add(texteRole);
        
        
        try {
			Image imageCarteLoupGarou = ImageIO.read(new File("CarteLoupGarou.png"));
//			ImageView imageViewCarteLoupGaroup = new ImageView(imageCarteLoupGarou);
//	        panelGauche.getChildren().add(imageViewCarteLoupGaroup);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Text texteDescriptionRole = new Text(descriptionRoleLoupGaroup);
        panelGauche.getChildren().add(texteDescriptionRole);  
        panelGauche.getChildren().add(boutonVoter);
        panelGauche.getChildren().add(boutonQuitter);
        
        //StackPane milieu
        
        StackPane panelMilieu = new StackPane();

        
        // StackPane de droite
        StackPane panelDroite = new StackPane();

        
        
        BorderPane fenetreGenerale = new BorderPane();

        //ajouter les 3 gridpane
     //   primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
