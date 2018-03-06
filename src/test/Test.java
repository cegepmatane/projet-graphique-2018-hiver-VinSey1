package test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Test extends Application{

	private ToggleGroup joueurs = new ToggleGroup();
	
	private StackPane fenetreChoixVote;
	
	private Text indication;
	
	private Button valider = new Button("Valider");
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Actions du jour");;
		
		valider.setStyle("-fx-background-color:#fff224; -fx-font-size:20px; -fx-padding: 10 50 10 50; -fx-font-family:\"Lucida Handwriting\";");
		
		indication = new Text("Votez pour le joueur à éliminer aujourd'hui");
		
		BorderPane fenetrePrincipale = new BorderPane();

		fenetreChoixVote = new StackPane();
		
		fenetreChoixVote.setMinSize(400, 400);
		
		fenetrePrincipale.setTop(indication);
		
		fenetrePrincipale.setBottom(valider);

		fenetrePrincipale.setCenter(fenetreChoixVote);	
		
		RadioButton test = new RadioButton("ttesttesttesttesttesttest");
		
		test.setToggleGroup(joueurs);
		
		fenetreChoixVote.getChildren().add(test);
		
		Scene scene = new Scene(fenetrePrincipale, 800,600);
		
		primaryStage.setScene(scene);
        primaryStage.show();
	}

	
	public static void main(String[] args) {
		
		launch(args);
	}
	
}
