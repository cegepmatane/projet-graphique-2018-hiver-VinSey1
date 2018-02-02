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

    	Text titre = new Text("Les Loups-Garous");
    	titre.setFont(Font.font("Lucida Handwriting", 80));
    	BorderPane.setAlignment(titre, Pos.CENTER);
    	
    	Text sousTitre = new Text("Règles");
    	sousTitre.setFont(Font.font("Lucida Handwriting", 50));
    	
    	Button principe = new Button("Principe");
    	Button deroulementJour = new Button("Déroulement Jour");
    	Button deroulementNuit = new Button("Déroulement Nuit");
    	Button cartes = new Button("Cartes");
    	Button savoirVivre = new Button("Savoir-vivre");
    	Button triche = new Button("Triche");
    	Button retour = new Button("Retour");
    	retour.setMaxWidth(200.0);
    	retour.setMinWidth(200.0);
    	
    	Label contenu = new Label("Test, consectetur adipiscing elit. Praesent ipsum erat, imperdiet non tellus consectetur, lobortis suscipit orci. Duis volutpat quam in interdum vestibulum. Maecenas at nibh aliquam, vulputate nisi quis, pharetra orci. Aliquam quis purus semper tellus pulvinar posuere. Donec in massa porttitor, sodales tortor non, pharetra risus. Praesent sit amet varius erat. Mauris sodales ultrices ullamcorper. Aliquam erat volutpat. Maecenas mollis efficitur purus at euismod. Sed convallis leo vitae ligula blandit vehicula.");
    	contenu.setWrapText(true);
    	//contenu.setMaxWidth(950);

    	VBox categories = new VBox();
    	categories.getChildren().add(principe);
    	categories.getChildren().add(deroulementJour);
    	categories.getChildren().add(deroulementNuit);
    	categories.getChildren().add(cartes);
    	categories.getChildren().add(savoirVivre);
    	categories.getChildren().add(triche);
        
        TitledPane menu = new TitledPane("Catégories", categories);
        menu.setExpanded(false);
    	menu.setMaxSize(170, 250);
    	menu.setMinSize(170, 250);
    	BorderPane.setAlignment(menu, Pos.BASELINE_LEFT);
    	menu.setStyle("-fx-z-index: 1");
    	AnchorPane fenetre = new AnchorPane();
    	AnchorPane zoneTexte = new AnchorPane();
    	zoneTexte.setMaxSize(600,300);
    	zoneTexte.setMinSize(50,25);
    	zoneTexte.setStyle("-fx-border-color: grey; -fx-border-width: 1px 1px 1px 1px; -fx-z-index: 10");
    	AnchorPane.setTopAnchor(zoneTexte, 150.0);
    	AnchorPane.setLeftAnchor(zoneTexte, 100.0);
    	AnchorPane.setRightAnchor(zoneTexte, 100.0);
    	AnchorPane.setBottomAnchor(zoneTexte, 100.0);
    	AnchorPane.setTopAnchor(menu, 120.0);
    	AnchorPane.setLeftAnchor(menu, 100.0);
    	AnchorPane.setRightAnchor(menu, 100.0);
    	AnchorPane.setTopAnchor(sousTitre, 10.0);
    	AnchorPane.setLeftAnchor(sousTitre, 500.0);
    	AnchorPane.setRightAnchor(sousTitre, 50.0);
    	AnchorPane.setBottomAnchor(retour, 20.0);
    	AnchorPane.setLeftAnchor(retour, 500.0);
    	AnchorPane.setTopAnchor(contenu, 10.0);
    	AnchorPane.setLeftAnchor(contenu, 10.0);
    	AnchorPane.setRightAnchor(contenu, 10.0);
    	fenetre.setMaxSize(1200, 600);
    	fenetre.setMinSize(100, 50);
        
        zoneTexte.getChildren().add(contenu);
        fenetre.getChildren().addAll(menu, retour, zoneTexte, sousTitre);
    	BorderPane miseEnPage = new BorderPane();
    	
    	fenetre.setStyle("-fx-border-color: black; -fx-border-width: 2px 2px 2px 2px");
    	miseEnPage.setTop(titre);
    	miseEnPage.setCenter(fenetre);
    	
        Scene scene = new Scene(miseEnPage, 1500, 750);
        scene.getStylesheets().add(VueRegles.class.getResource("../decoration/lg.css").toExternalForm());

        primaryStage.setTitle("Loup-garou");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    
    public static void main(String[] args) {
        launch(args);
    }
}