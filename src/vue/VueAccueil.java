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


	private Button boutonQuitter;
	private Button boutonRegles;
	private Text titre;
	private Text entreePseudo;
	private TextField creationPseudo;
	private Button boutonDeConnexion;
	private BorderPane miseEnPage;
	private AnchorPane elementCentral;
	private HBox boxImage;
	private Scene scene;
	
	
	public static void main(String[] args) {
        launch(args);
    }
	
	
	
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Accueil");
      
        
        boutonQuitter = new Button();
        boutonQuitter.setText("Quitter");
        boutonQuitter.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		System.out.println("Au revoir AHOOUUUUUU");
        	}
        });
        
        boutonRegles = new Button();
        boutonRegles.setText("Règles du jeu");
        boutonRegles.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		System.out.println("Affichage des règles du jeu");
        	}
        });
        
        titre = new Text("Les loups-garous");
        titre.setId("titrePrincipal");
        
        entreePseudo = new Text("Entrez votre pseudonyme :");
        entreePseudo.setId("pseudo");
        
        creationPseudo = new TextField();
        
        boutonDeConnexion = new Button();
        boutonDeConnexion.setText("Se connecter");
        boutonDeConnexion.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	String pseudo = new String();
            	if ((creationPseudo.getText() != null && !creationPseudo.getText().isEmpty())) {
            		pseudo = creationPseudo.getText();
            		System.out.println(pseudo);
            	}
            	else {
            		System.out.println("Veuillez entrer un pseudo");
            	}
            }
        });
        
        miseEnPage = new BorderPane();
        miseEnPage.setTop(titre);
        miseEnPage.setAlignment(titre, Pos.CENTER);
        
        elementCentral = new AnchorPane();
        miseEnPage.setCenter(elementCentral);
        elementCentral.setMaxSize(1200, 600);
        elementCentral.setMinSize(100, 50);
        
        boxImage = new HBox();
        boxImage.setId("image-accueil");
        
        elementCentral.setTopAnchor(entreePseudo, (double) 300);
        elementCentral.setRightAnchor(entreePseudo, (double) 300);
        elementCentral.setLeftAnchor(entreePseudo, (double) 300);
        elementCentral.setBottomAnchor(entreePseudo, (double) 50);
        
        elementCentral.setTopAnchor(creationPseudo, (double) 375);
        elementCentral.setRightAnchor(creationPseudo, (double) 300);
        elementCentral.setLeftAnchor(creationPseudo, (double) 300);
        elementCentral.setBottomAnchor(creationPseudo, (double) 170);
        
        elementCentral.setTopAnchor(boutonDeConnexion, (double) 455);
        elementCentral.setRightAnchor(boutonDeConnexion, (double) 300);
        elementCentral.setLeftAnchor(boutonDeConnexion, (double) 300);
        elementCentral.setBottomAnchor(boutonDeConnexion, (double) 100);
        
        elementCentral.setTopAnchor(boutonQuitter, (double) 505);
        elementCentral.setRightAnchor(boutonQuitter, (double) 300);
        elementCentral.setLeftAnchor(boutonQuitter, (double) 300);
        elementCentral.setBottomAnchor(boutonQuitter, (double) 50);
        
        elementCentral.setTopAnchor(boutonRegles, (double) 5);
        elementCentral.setRightAnchor(boutonRegles, (double) 5);
        elementCentral.setLeftAnchor(boutonRegles, (double) 1000);
        elementCentral.setBottomAnchor(boutonRegles, (double) 550);
        
        elementCentral.setTopAnchor(boxImage, (double) 10);
        elementCentral.setRightAnchor(boxImage, (double) 200);
        elementCentral.setLeftAnchor(boxImage, (double) 200);
        elementCentral.setBottomAnchor(boxImage, (double) 320);
        
  
        elementCentral.getChildren().addAll(entreePseudo, creationPseudo, boutonDeConnexion, boutonQuitter, boutonRegles, boxImage);
        elementCentral.setId("cadre-accueil");
        
        
        scene = new Scene(miseEnPage, 1500, 750);
        scene.getStylesheets().add(VueAccueil.class.getResource("decoration/lg.css").toExternalForm()); 
        
        primaryStage.setScene(scene);
        primaryStage.show();
              
    }  
    
}