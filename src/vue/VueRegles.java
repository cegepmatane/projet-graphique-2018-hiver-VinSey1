package vue;

import com.sun.prism.paint.Color;

import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;
 
public class VueRegles extends Application {
    
    @Override
    public void start(Stage primaryStage) {

    	//Cr�ation du titre avec sa police
    	Text titre = new Text("Les Loups-Garous");
    	titre.setFont(Font.font("Lucida Handwriting", 80));
    	//Centre le Titre
    	BorderPane.setAlignment(titre, Pos.CENTER);
    	
    	//Cr�ation du sous-titre avec sa police
    	Text sousTitre = new Text("R�gles");
    	sousTitre.setFont(Font.font("Lucida Handwriting", 50));

    	//Cr�ation des boutons du menu d�roulant
    	Button principe = new Button("Principe");
    	Button deroulementJour = new Button("D�roulement Jour");
    	Button deroulementNuit = new Button("D�roulement Nuit");
    	Button cartes = new Button("Cartes");
    	Button savoirVivre = new Button("Savoir-vivre");
    	Button triche = new Button("Triche");
    	
    	//Cr�ation du bouton "Retour"
    	Button retour = new Button("Retour");
    	//Param�tres de taille du bouton "Retour"
    	retour.setMaxWidth(200.0);
    	retour.setMinWidth(200.0);
    	
    	//Cr�ation de l'explication des r�gles
    	Label contenu = new Label("Test, consectetur adipiscing elit. Praesent ipsum erat, imperdiet non tellus consectetur, "
    			+ "lobortis suscipit orci. Duis volutpat quam in interdum vestibulum. Maecenas at nibh aliquam, vulputate nisi quis, "
    			+ "pharetra orci. Aliquam quis purus semper tellus pulvinar posuere. Donec in massa porttitor, sodales tortor non, "
    			+ "pharetra risus. Praesent sit amet varius erat. Mauris sodales ultrices ullamcorper. Aliquam erat volutpat. Maecenas "
    			+ "mollis efficitur purus at euismod. Sed convallis leo vitae ligula blandit vehicula.");
    	//Gestion de retour � la ligne du contenu
    	contenu.setWrapText(true);
    	//Gestion de la taille du contenu
    	//contenu.setMaxWidth(950);
    	contenu.setMaxSize(1000,400);
    	contenu.setMinSize(1000,400);
    	contenu.setAlignment(Pos.TOP_CENTER);
    	contenu.setTranslateY(35.0);
    	//Cr�ation de la zone de texte contenant le texte
    	AnchorPane zoneTexte = new AnchorPane();
    	//Gestion de la taille de la zone de texte
    	zoneTexte.setMaxSize(1000,400);
    	zoneTexte.setMinSize(1000,400);
    	//Gestion du style de la zone de texte
    	zoneTexte.setId("zonetexte-regles");
    	//Ajoute le contenu � la zone de texte
    	zoneTexte.getChildren().add(contenu);
    	
    	//Cr�ation des cat�gories du menu
    	VBox categories = new VBox();
    	categories.getChildren().add(principe);
    	categories.getChildren().add(deroulementJour);
    	categories.getChildren().add(deroulementNuit);
    	categories.getChildren().add(cartes);
    	categories.getChildren().add(savoirVivre);
    	categories.getChildren().add(triche);
        
    	//Cr�ation du menu d�roulant
    	TitledPane menu = new TitledPane("Cat�gories", categories);
    	//D�finit le menu comme �tant ferm� au lancement de l'application
        menu.setExpanded(false);
        //Gestion de la taille du menu
    	menu.setMinSize(1000, 250);
    	
    	/*BorderPane.setAlignment(menu, Pos.BASELINE_LEFT);
    	menu.setStyle("-fx-z-index: 1; -fx-width:170px");*/
    	
        //Cr�ation du groupe contenant le menu et la zone de texte
    	Group groupe = new Group();
    	//Ajoute la zone de texte et le menu au groupe
    	groupe.getChildren().addAll(zoneTexte, menu);
    	//D�finit la zone de texte � l'arri�re dans le groupe
    	zoneTexte.toBack();
    	//D�finit le menu � l'avant dans le groupe
    	menu.toFront();
    	
    	//Cr�ation de la fen�tre contenant le sous texte et le groupe
    	AnchorPane fenetre = new AnchorPane();
    	//Gestion de la taille de la fenetre
    	fenetre.setMaxSize(1200, 600);
    	fenetre.setMinSize(100, 50);
    	//Gestion du style de la fenetre
    	fenetre.setId("fenetre-regles");
    	//Ajoute le groupe, le bouton retour et le sous titre � la fenetre
        fenetre.getChildren().addAll(groupe, retour, sousTitre);
        
    	//Gestion de tous les placements des diff�rents objets dans les AnchorPanels
    	AnchorPane.setTopAnchor(groupe, 120.0);
    	AnchorPane.setLeftAnchor(groupe, 100.0);
    	AnchorPane.setTopAnchor(sousTitre, 10.0);
    	AnchorPane.setLeftAnchor(sousTitre, 500.0);
    	AnchorPane.setBottomAnchor(retour, 20.0);
    	AnchorPane.setLeftAnchor(retour, 500.0);
    	AnchorPane.setLeftAnchor(contenu, 10.0);
    	AnchorPane.setRightAnchor(contenu, 10.0);

        //Cr�ation de la mise en page
    	BorderPane miseEnPage = new BorderPane();
    	//Ajoute le titre en haut de la mise en page
    	miseEnPage.setTop(titre);
    	//Ajoute la fenetre au centre de la mise en page
    	miseEnPage.setCenter(fenetre);
    	
    	//Cr�ation de la sc�ne contenant la mise en page et d�finition de sa taille
        Scene scene = new Scene(miseEnPage, 1500, 750);
        //Importation du fichier CSS pour la gestion de styles
        scene.getStylesheets().add(VueRegles.class.getResource("decoration/lg.css").toExternalForm());
        //Gestion du titre de la scene
        primaryStage.setTitle("Loup-garou");
        //D�finit la scene
        primaryStage.setScene(scene);
        //Affiche la scene
        primaryStage.show();

    }
    
    public static void main(String[] args) {
        launch(args);
    }
}