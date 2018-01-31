package vue;

import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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
    	sousTitre.setFont(Font.font("Lucida Handwriting", 80));
    	
    	Button principe = new Button("Principe");
    	Button deroulementJour = new Button("Déroulement Jour");
    	Button deroulementNuit = new Button("Déroulement Nuit");
    	Button cartes = new Button("Cartes");
    	Button savoirVivre = new Button("Savoir-vivre");
    	Button triche = new Button("Triche");
    	Button retour = new Button("Retour");
    	retour.setMaxWidth(200.0);
    	retour.setMinWidth(200.0);
    	
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
    	BorderPane.setAlignment(menu, Pos.BASELINE_LEFT);
    	
    	AnchorPane grille = new AnchorPane();
    	
    	AnchorPane.setTopAnchor(menu, 120.0);
    	AnchorPane.setLeftAnchor(menu, 100.0);
    	AnchorPane.setRightAnchor(menu, 100.0);
    	AnchorPane.setTopAnchor(sousTitre, 0.0);
    	AnchorPane.setLeftAnchor(sousTitre, 450.0);
    	AnchorPane.setRightAnchor(sousTitre, 50.0);
    	AnchorPane.setBottomAnchor(retour, 20.0);
    	AnchorPane.setLeftAnchor(retour, 500.0);
    	grille.getChildren().addAll(menu, sousTitre, retour);
    	BorderPane miseEnPage = new BorderPane();
    	grille.setMaxSize(1200, 600);
        grille.setMinSize(100, 50);
        grille.setStyle("-fx-border-color: black; -fx-border-width: 2px 2px 2px 2px");
    	miseEnPage.setTop(titre);
    	miseEnPage.setCenter(grille);
    	
        Scene scene = new Scene(miseEnPage, 1500, 750);

        primaryStage.setTitle("Loup-garou");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    
    public static void main(String[] args) {
        launch(args);
    }
}