package vue;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VueVillageois extends Application{

	public static Stage instanceVueVillageois= new Stage();
	
	private Button valider = new Button("Valider");
	
	private RadioButton choixJoueur = new RadioButton();
	
	private ToggleGroup joueurs = new ToggleGroup();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Actions du jour");;
		
		valider.setStyle("-fx-background-color:#fff224; -fx-font-size:20px; -fx-padding: 10 50 10 50; -fx-font-family:\"Lucida Handwriting\";");
		
		Text indication = new Text("Votez pour le joueur à éliminer aujourd'hui");
		
		StackPane fenetrePrincipale = new StackPane();
		
		fenetrePrincipale.getChildren().add(indication);
		
		StackPane fenetreChoixVote = new StackPane();
		
		
		
		
	}
	
	
	public void setChoixJoueur() {
		
		
		
		
	}
	
	

}
