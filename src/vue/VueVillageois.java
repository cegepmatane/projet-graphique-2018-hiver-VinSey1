package vue;

import java.util.ArrayList;
import java.util.List;
import controleur.ControleurVueVillageois;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VueVillageois extends Application{

	public static Stage instanceVueVillageois= new Stage();
	
	private ControleurVueVillageois controleur;
	
	private Button valider = new Button("Valider");
	
	private List<RadioButton> choixJoueur = new ArrayList<RadioButton>();

	private ToggleGroup joueurs = new ToggleGroup();
	
	private BorderPane fenetreChoixVote;
	
	private Text indication;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Actions du jour");;
		
		valider.setStyle("-fx-background-color:#fff224; -fx-font-size:20px; -fx-padding: 10 50 10 50; -fx-font-family:\"Lucida Handwriting\";");
		
		indication = new Text("Votez pour le joueur à éliminer aujourd'hui");
		
		BorderPane fenetrePrincipale = new BorderPane();

		fenetreChoixVote = new BorderPane();
		
		fenetrePrincipale.setTop(indication);
		
		fenetrePrincipale.setBottom(valider);

		fenetrePrincipale.setCenter(fenetreChoixVote);
		
		Scene scene = new Scene(fenetrePrincipale, 800,600);
		
		RadioButton test = new RadioButton("testtesttesttesttesttesttesttesttesttesttest");
		test.setToggleGroup(joueurs);
		
		choixJoueur.add(test);
		fenetreChoixVote.getChildren().add(test);
		
		valider.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				VueVillageois.this.controleur.validerVote();
				
			}
		});
		
		primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	
	public void setChoixJoueur(List<String> listeJoueur) {
		/*
		for ( int iterateurJoueur = 0 ; iterateurJoueur < listeJoueur.size(); iterateurJoueur++) {
									
			choixJoueur.add(new RadioButton(listeJoueur.get(iterateurJoueur)));
			fenetreChoixVote.getChildren().add(choixJoueur.get(choixJoueur.size()-1));
			
		}
		
		choixJoueur.add(new RadioButton(listeJoueur.get(0)));
		fenetreChoixVote.getChildren().add(choixJoueur.get(0));*/
		
	}
	
	public List<RadioButton> getChoixJoueur() {
		return choixJoueur;
	}
}
