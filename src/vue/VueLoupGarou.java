package vue;

import java.util.ArrayList;
import java.util.List;

import controleur.ControleurVueLoupGarou;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VueLoupGarou extends Application{

	public static Stage instanceVueLoupGarou= new Stage();
	
	private ControleurVueLoupGarou controleur;
	
	private Button valider = new Button("Valider");
	
	private List<RadioButton> choixJoueur = new ArrayList<RadioButton>();

	private ToggleGroup joueurs = new ToggleGroup();
	
	private StackPane fenetreChoixVote;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Actions du jour");;
		
		valider.setStyle("-fx-background-color:#fff224; -fx-font-size:20px; -fx-padding: 10 50 10 50; -fx-font-family:\"Lucida Handwriting\";");
		
		Text indication = new Text("Votez pour le joueur à éliminer aujourd'hui");
		
		StackPane fenetrePrincipale = new StackPane();
		
		fenetrePrincipale.getChildren().add(indication);
		
		fenetreChoixVote = new StackPane();
		
		Scene scene = new Scene(fenetrePrincipale, 800,600);
		
		valider.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				VueLoupGarou.this.controleur.validerVote();
				
			}
		});
		
		primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	
	public void setChoixJoueur(List<String> listeJoueur) {
		
		for ( int iterateurJoueur = 0 ; iterateurJoueur < listeJoueur.size(); iterateurJoueur++) {
			
			choixJoueur.add(new RadioButton(listeJoueur.get(iterateurJoueur)));
			fenetreChoixVote.getChildren().add(choixJoueur.get(choixJoueur.size()-1));
			
		}
		
		fenetreChoixVote.getChildren().add(fenetreChoixVote);
		fenetreChoixVote.getChildren().add(valider);
	}
	
	public List<RadioButton> getChoixJoueur() {
		return choixJoueur;
	}
	
}
