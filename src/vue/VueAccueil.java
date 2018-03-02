package vue;


import javax.print.DocFlavor.URL;

import com.sun.xml.internal.ws.org.objectweb.asm.Label;

import controleur.ControleurAccueil;
import controleur.ControleurSalonDeJeu;
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

/**
 * Classe VueAccueil représentant la vue de l'accueil de notre jeu
 * 
 * Version : 1.0
 * 
 * @author BALTZ Eliott
 */
public class VueAccueil extends Application {


	private Button actionQuitter;
	private Button actionRegles;
	private Text titre;
	private Text entreePseudo;
	private Text erreur;
	private TextField creationPseudo;
	private Button actionDeConnexion;
	private BorderPane miseEnPage;
	private AnchorPane elementCentral;
	private HBox image;
	private Scene scene;
	public static Stage instanceVueAccueil = new Stage();
	private ControleurAccueil controle;
	
	/**
	 * @return un string qui est le pseudo qu'a entrée le joueur pour se connecter à la partie
	 */
	public String recupererPseudo() {
		return(creationPseudo.getText());
	}
	
	public void setControleurAccueil(ControleurAccueil controleAccueil) {
    	this.controle = controleAccueil;
    }
	
	public void setErreur() {
		erreur.setText("Veuillez entrer un pseudonyme");
		
	}
	
	public void close() {
		instanceVueAccueil.close();
	}
	
	private Scene getScene() {
		return scene;
	}
	
	


	
	
	
    @Override
    public void start(Stage primaryStage) {
    	
    	instanceVueAccueil = primaryStage; 
        primaryStage.setTitle("Accueil");
      
        
        actionQuitter = new Button();
        actionQuitter.setText("Quitter");
        actionQuitter.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		close();
        	}
        });
        
        actionRegles = new Button();
        actionRegles.setText("Règles du jeu");
        actionRegles.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		controle.afficherRegles();
        	}
        });
        
        titre = new Text("Les loups-garous");
        titre.setId("titre-principal");
        
        entreePseudo = new Text("Entrez votre pseudonyme :");
        entreePseudo.setId("pseudo");
        
        erreur = new Text("");
        erreur.setId("erreur");
        
        creationPseudo = new TextField();
        
        actionDeConnexion = new Button();
        actionDeConnexion.setText("Se connecter");
        actionDeConnexion.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	//String pseudo = new String();
            	//if ((creationPseudo.getText() != null && !creationPseudo.getText().isEmpty())) {
            		//pseudo = creationPseudo.getText();
            		//System.out.println(pseudo);
            	//}
            	//else {
            		//System.out.println("Veuillez entrer un pseudo");
            	//}
            	controle.afficherSalonDeJeu();
            	
            }
        });
        
        miseEnPage = new BorderPane();
        miseEnPage.setTop(titre);
        miseEnPage.setAlignment(titre, Pos.CENTER);
        
        elementCentral = new AnchorPane();
        miseEnPage.setCenter(elementCentral);
        elementCentral.setMaxSize(1200, 600);
        elementCentral.setMinSize(100, 50);
        
        image = new HBox();
        image.setId("image-accueil");
        
        elementCentral.setTopAnchor(entreePseudo, (double) 300);
        elementCentral.setRightAnchor(entreePseudo, (double) 300);
        elementCentral.setLeftAnchor(entreePseudo, (double) 300);
        elementCentral.setBottomAnchor(entreePseudo, (double) 50);
        
        elementCentral.setTopAnchor(creationPseudo, (double) 375);
        elementCentral.setRightAnchor(creationPseudo, (double) 300);
        elementCentral.setLeftAnchor(creationPseudo, (double) 300);
        elementCentral.setBottomAnchor(creationPseudo, (double) 170);
        
        elementCentral.setTopAnchor(erreur, (double) 430);
        elementCentral.setRightAnchor(erreur, (double) 300);
        elementCentral.setLeftAnchor(erreur, (double) 300);
        elementCentral.setBottomAnchor(erreur, (double) 110);
        
        elementCentral.setTopAnchor(actionDeConnexion, (double) 455);
        elementCentral.setRightAnchor(actionDeConnexion, (double) 300);
        elementCentral.setLeftAnchor(actionDeConnexion, (double) 300);
        elementCentral.setBottomAnchor(actionDeConnexion, (double) 100);
        
        elementCentral.setTopAnchor(actionQuitter, (double) 505);
        elementCentral.setRightAnchor(actionQuitter, (double) 300);
        elementCentral.setLeftAnchor(actionQuitter, (double) 300);
        elementCentral.setBottomAnchor(actionQuitter, (double) 50);
        
        elementCentral.setTopAnchor(actionRegles, (double) 5);
        elementCentral.setRightAnchor(actionRegles, (double) 5);
        elementCentral.setLeftAnchor(actionRegles, (double) 1000);
        elementCentral.setBottomAnchor(actionRegles, (double) 550);
        
        elementCentral.setTopAnchor(image, (double) 10);
        elementCentral.setRightAnchor(image, (double) 200);
        elementCentral.setLeftAnchor(image, (double) 200);
        elementCentral.setBottomAnchor(image, (double) 320);
        
  
        elementCentral.getChildren().addAll(entreePseudo, creationPseudo, erreur, actionDeConnexion, actionQuitter, actionRegles, image);
        elementCentral.setId("cadre-accueil");
        
        
        scene = new Scene(miseEnPage, 1500, 750);
        scene.getStylesheets().add(VueAccueil.class.getResource("decoration/lg.css").toExternalForm()); 
        
        primaryStage.setScene(scene);
        primaryStage.show();
              
    }  
    
}