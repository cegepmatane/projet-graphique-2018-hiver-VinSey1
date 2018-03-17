package vue;

import javafx.scene.text.Font;
import java.awt.Image;
import javafx.geometry.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import controleur.ControleurSalonDeJeu;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * Vue du salon de jeu 
 * 
 * @author 1801067
 *
 */
public class VueSalonDeJeu extends Application {
	
	
	public static Stage instanceVueSalonDeJeu = new Stage();
	
	/**
	 * controleur de la vue
	 */
	private ControleurSalonDeJeu controleur;
	
	/**
	 * Nom du role du joueur
	 */
	private Text nomRole;
	

	/**
	 * Image de la carte du joueur
	 */
	private StackPane imageCarte;
	
	/**
	 * Description du role du joueur
	 */
	Text descriptionRole;
	
	/**
	 * Indication du nombre de joueur restant
	 */
	Text indicationNombreDeJoueurs;
	
	/**
	 * Indication du nombre de loup-garoups vivants
	 */
	Text indicationNombreDeLoupGarouVivant;
	
	/**
	 * Indication du nombre d'innocents vivants
	 */
	Text indicationNombreDInnocentVivant;
	
	/**
	 * Liste des joueurs vivants
	 */
	Text listeJoueursVivant;
	
	/**
	 * Liste des joueurs morts
	 */
	Text listeJoueursMort;
	
	/**
	 * Chat du jeu
	 */
	TextArea chatDeJeu;
	
	/**
	 * Contenu du chat de jeu
	 */
	String contenuChatDeJeu = "";
	
	Button boutonVoter;
    
	Text pseudo = new Text("Pseudo");


	@Override
    public void start(Stage primaryStage) {
    	
    	instanceVueSalonDeJeu = primaryStage;   	
    	
        primaryStage.setTitle("Salon de Jeu");
        
        
        // Bouton d'acces aux regles du jeu	
        Button reglesDuJeu = new Button();
        reglesDuJeu.setText("Règles du jeu");
        reglesDuJeu.setStyle("-fx-background-color:#fff224; -fx-font-size:20px; -fx-padding: 10 50 10 50; -fx-font-family:\"Lucida Handwriting\";");
        reglesDuJeu.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	controleur.afficherRegles();
            }
        });
        
        // Bouton quitter
        Button quitter = new Button();
        quitter.setText("Quitter");
        quitter.setStyle("-fx-background-color:#4d0000; -fx-font-size:20px; -fx-padding: 10 50 10 50;-fx-font-family:\"Lucida Handwriting\"; -fx-text-fill:white;");
        quitter.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            }
        });
     
        Button envoyer = new Button();
        envoyer.setText("Envoyer");
        envoyer.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	
            }
        });   
        // Bouton voter
        boutonVoter = new Button();
        boutonVoter.setDisable(true);
        boutonVoter.setText("Voter");
        boutonVoter.setStyle("-fx-background-color:#00cc00; -fx-font-size:20px; -fx-padding: 10 50 10 50;-fx-font-family:\"Lucida Handwriting\"");
        boutonVoter.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	controleur.afficherVoteVillageois();
            }
        });             
        
        // Image de la carte du joueur        
        imageCarte = new StackPane();
        imageCarte.setId("image-loup-garoup");
        
        // Nom du rôle du joueur      
        nomRole = new Text("");
        nomRole.setFont(Font.font("Lucida Handwriting", 30));
        
        
        // StackPane de gauche
        GridPane panelGauche = new GridPane();
        panelGauche.setPadding(new Insets(20,20,20,20));
        panelGauche.setAlignment(Pos.CENTER);
        panelGauche.setHgap(10);
        panelGauche.setVgap(10);
     
        panelGauche.setAlignment(Pos.CENTER);

        descriptionRole = new Text("Description du rôle du joueur");
        descriptionRole.setFont(Font.font("Lucida Handwriting", 17));
        
        panelGauche.setConstraints(pseudo, 0, 0);
        panelGauche.setConstraints(descriptionRole, 0, 1);
        panelGauche.setConstraints(boutonVoter, 0, 2);
        panelGauche.setConstraints(quitter, 0, 3);
        
        panelGauche.getChildren().add(pseudo);
        panelGauche.getChildren().add(descriptionRole);  
        panelGauche.getChildren().add(boutonVoter);
        panelGauche.getChildren().add(quitter);
        
          
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
        hboxHaut.getChildren().add(reglesDuJeu);
        
        //VBox milieu
        VBox panelMilieu = new VBox();
        chatDeJeu = new TextArea();
        chatDeJeu.setMinHeight(500);
        chatDeJeu.setMaxHeight(500);
        chatDeJeu.setStyle("-fx-font-size:12px ; -fx-font-weight: bold; -fx-font-family:\"Lucida Handwriting\";");
        Region blanc3 = new Region();
                
        TextField zoneDeSaisieMessage = new TextField();
        
        panelMilieu.setPadding(new Insets(20,20,100,20));

        
        panelMilieu.getChildren().add(blanc3);
        panelMilieu.getChildren().add(chatDeJeu);
        panelMilieu.getChildren().add(envoyer);
        panelMilieu.getChildren().add(zoneDeSaisieMessage);

        panelMilieu.setVgrow(blanc3, Priority.ALWAYS);
        
        // StackPane de droite
        GridPane panelDroite = new GridPane();
        panelDroite.setHgap(10);
        panelDroite.setVgap(10);
        panelDroite.setPadding(new Insets(20,20,20,20));
        panelDroite.setAlignment(Pos.CENTER);

        indicationNombreDeJoueurs = new Text("Joueurs : ");
        indicationNombreDeJoueurs.setFont(Font.font("Lucida Handwriting", 20));
        
        indicationNombreDeLoupGarouVivant = new Text("Loups-garous restants : ");
        indicationNombreDeLoupGarouVivant.setFont(Font.font("Lucida Handwriting", 20));
        
        indicationNombreDInnocentVivant = new Text("Innocents restants : ");
        indicationNombreDInnocentVivant.setFont(Font.font("Lucida Handwriting", 20));
        
        Text nomListeJoueursVivant = new Text("Vivants");
        nomListeJoueursVivant.setFont(Font.font("Lucida Handwriting", 30));
        
        listeJoueursVivant = new Text("");
        listeJoueursVivant.setFont(Font.font("Lucida Handwriting", 30));
        
        Text nomListeJoueursMort = new Text("Morts");
        nomListeJoueursMort.setFont(Font.font("Lucida Handwriting", 30));
        
        listeJoueursMort = new Text("");       
        listeJoueursMort.setFont(Font.font("Lucida Handwriting", 30));
        
        panelDroite.setConstraints(indicationNombreDeJoueurs, 0,1);
        panelDroite.setConstraints(indicationNombreDeLoupGarouVivant, 0,2);
        panelDroite.setConstraints(indicationNombreDInnocentVivant, 0,3);
        panelDroite.setConstraints(nomListeJoueursVivant, 0,4);
        panelDroite.setConstraints(listeJoueursVivant, 0,5);
        panelDroite.setConstraints(nomListeJoueursMort, 0,6);
        panelDroite.setConstraints(listeJoueursMort, 0,7);
       
        panelDroite.getChildren().add(indicationNombreDeJoueurs);
        panelDroite.getChildren().add(indicationNombreDeLoupGarouVivant);
        panelDroite.getChildren().add(indicationNombreDInnocentVivant);
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

        fenetreGenerale.setTopAnchor(panelDroite, 100.0);
        fenetreGenerale.setRightAnchor(panelDroite, 0.0);

        fenetreGenerale.setTopAnchor(nomRole, 50.0);
        fenetreGenerale.setLeftAnchor(nomRole, 50.0);
        
        fenetreGenerale.setTopAnchor(imageCarte, 0.0);
        fenetreGenerale.setLeftAnchor(imageCarte, 0.0);
        fenetreGenerale.setRightAnchor(imageCarte, 1100.0);
        fenetreGenerale.setBottomAnchor(imageCarte, 140.0);
        

        fenetreGenerale.setLeftAnchor(panelGauche, 10.0);
        fenetreGenerale.setBottomAnchor(panelGauche, 5.0);
        
        fenetreGenerale.getChildren().addAll(hboxHaut, panelMilieu, panelDroite, nomRole, imageCarte, panelGauche);
        
        Scene scene = new Scene(fenetreGenerale, 1500,700);        
        scene.getStylesheets().add(VueSalonDeJeu.class.getResource("decoration/lg.css").toExternalForm());
                
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
    }
    
    /**
     * Ajoute une ligne de texte au chat de jeu
     * @param texte : le texte a ajouter
     */
    public void ajouterTexteAuChat(String texte) {
    	
    	contenuChatDeJeu += texte+"\n";
    	chatDeJeu.setText(contenuChatDeJeu);
    	chatDeJeu.appendText("");
    	chatDeJeu.setScrollTop(50);
    }
    
    /**
     * Modifier l'indication du nombre de joueur dans la partie
     * @param nouveauNombre : le nombre de joueur a afficher
     */
    public void modifierNombreDeJoueur(String nouveauNombreDeJoueur) {
    	
    	indicationNombreDeJoueurs.setText("Joueurs : "+nouveauNombreDeJoueur);
    	
    }
    
    /**
     * Modifier l'indication du nombre de loup garou vivant dans la partie
     * @param nouveauNombreDeLoupGarou : le nombre de loups-garous à afficher
     */
    public void modifierIndicationNombreDeLoupGarouVivant(String nouveauNombreDeLoupGarou) {
    
    	
        indicationNombreDeLoupGarouVivant.setText("Loups-garous restants : "+nouveauNombreDeLoupGarou);
    	
    }
    
    /**
     * Modifier l'indication d'innocents vivants dans la partie
     * @param nouveauNombreDeLoupGarou : le nombre d'innocents à afficher
     */
    public void modifierIndicationInnocentVivant(String nouveauNombreInnocent) {
    
    	
    	indicationNombreDInnocentVivant.setText("Innocents restants : "+nouveauNombreInnocent);
    	
    }
    
    /**
     * Modifier la liste des joueurs vivants
     * @param nouvelleListeJoueurVivant : la nouvelle liste à utiliser
     */
    public void modifierListeJoueurVivant(String listeJoueurs) {
    	/**
    	String nouvelleListe = "";
    	
    	for ( int iterateurListe = 0 ; iterateurListe < nouvelleListeJoueurVivant.size(); iterateurListe++) {
    		nouvelleListe = nouvelleListe + nouvelleListeJoueurVivant.get(iterateurListe) + "\n";
    	}
    	*/
    	
    	listeJoueursVivant.setText(listeJoueurs);
    }
    
    
    /**
     * Modifier la liste des joueurs morts
     * @param nouvelleListeJoueurMort : la nouvelle liste à utiliser
     */
    public void modifierListeJoueurMort(String nouvelleListeJoueurMort) {
    
    	/**
    	String nouvelleListe = "";
    
    	for ( int iterateurListe = 0 ; iterateurListe < nouvelleListeJoueurMort.size(); iterateurListe++) {
    		nouvelleListe = nouvelleListe + nouvelleListeJoueurMort.get(iterateurListe) + "\n";
    	}
    	*/
    	listeJoueursMort.setText(nouvelleListeJoueurMort);
    } 
    
	public void setNomRole(String nomRole) {
		this.nomRole.setText(nomRole);
	}
    
    public void setDescriptionRole(String descriptionDuRole) {
    	descriptionRole.setText(descriptionDuRole);
    }
    
    
    
    public void setControleurSalonDeJeu(ControleurSalonDeJeu controleurSalonDeJeu) {
    	this.controleur = controleurSalonDeJeu;
    }
    
    public void hide() {
    	instanceVueSalonDeJeu.hide();
    }
    
    public void show() {
    	instanceVueSalonDeJeu.show();
    }
    
    public Button getBoutonVoter() {
		return boutonVoter;
	}
    
	public void setPseudo(String pseudo) {
		this.pseudo.setText(pseudo);
	}
}
