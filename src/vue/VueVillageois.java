package vue;

import java.util.ArrayList;
import java.util.List;
import controleur.ControleurVueVillageois;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VueVillageois extends Application{

	public static Stage instanceVueVillageois= new Stage();
	
	private ControleurVueVillageois controleur;

	private Button valider = new Button("Valider");
	
	private List<RadioButton> choixJoueur = new ArrayList<RadioButton>();
	
	private Button sauverJoueur = new Button(); 

	private ToggleGroup joueurs = new ToggleGroup();
	
	private VBox fenetreChoixVote;
	
	private Text indication;	



	private List<CheckBox> listeCupidon = new ArrayList<CheckBox>();
	
	private boolean fenetreCupidon = false;

	public void fermer() {
		instanceVueVillageois.close();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Actions du jour");;
		
		valider.setStyle("-fx-background-color:#fff224; -fx-font-size:20px; -fx-padding: 10 50 10 50; -fx-font-family:\"Lucida Handwriting\";");
		
		indication = new Text("Votez pour le joueur à éliminer aujourd'hui");
		
		BorderPane fenetrePrincipale = new BorderPane();

		fenetreChoixVote = new VBox();
		fenetreChoixVote.setMinSize(800, 300);
		fenetreChoixVote.setPadding(new Insets(10));
		fenetreChoixVote.setSpacing(8);
		
		sauverJoueur.setVisible(false);
		sauverJoueur.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				controleur.sauver();
			}
		});
		
		fenetrePrincipale.setTop(indication);
		
		fenetrePrincipale.setBottom(valider);

		fenetrePrincipale.setCenter(fenetreChoixVote);
		
		fenetrePrincipale.setRight(sauverJoueur);
		
		
		Scene scene = new Scene(fenetrePrincipale, 1000,500);
				
		valider.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controleur.validerVote();
				
			}
		});
		
		primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	
	public void setChoixJoueur(List<String> listeJoueur) {
		
		listeJoueur.add("Ne rien voter");
		
		for ( int iterateurJoueur = 0 ; iterateurJoueur < listeJoueur.size(); iterateurJoueur++) {
						
			RadioButton joueur = new RadioButton(listeJoueur.get(iterateurJoueur));
			joueur.setToggleGroup(joueurs);
			
			choixJoueur.add(joueur);
			
			fenetreChoixVote.getChildren().add(choixJoueur.get(choixJoueur.size()-1));
			
		}
					
	}
	
	public String getChoixJoueur() {
		for (int i=0;i<choixJoueur.size();i++) {
			if (choixJoueur.get(i).isSelected() ) {
				return choixJoueur.get(i).getText();
			}
		}
		return "Pas de choix";
		
	}
	
	
	public void setControleur(ControleurVueVillageois controleur) {
		this.controleur = controleur;
	}
	
	public Button getSauverJoueur() {
		return sauverJoueur;
	}

	public void setChoixCupidon(List<String> joueurs) {
	
		for ( int iterateurJoueur = 0 ; iterateurJoueur < joueurs.size(); iterateurJoueur++) {
			
			CheckBox joueur = new CheckBox(joueurs.get(iterateurJoueur));
			
			listeCupidon.add(joueur);
			
			fenetreChoixVote.getChildren().add(listeCupidon.get(choixJoueur.size()-1));
			
		}
	}	
	
	public List<CheckBox> getListeCupidon() {
		return listeCupidon;
	}

	public boolean isFenetreCupidon() {
		return fenetreCupidon;
	}

	public void setFenetreCupidon(boolean choixCupidon) {
		this.fenetreCupidon = choixCupidon;
	}
	
	public Text getIndication() {
		return indication;
	}
}

