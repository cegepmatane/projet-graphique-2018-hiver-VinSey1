package controleur;

import vue.VueVillageois;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import reseau.ContactServeur;
public class ControleurVueVillageois {

	private VueVillageois vueVillageois;
	private ContactServeur contactServeur;
	
	public ControleurVueVillageois(VueVillageois vueVillageois, ContactServeur contactServeur) {
		
		this.vueVillageois = vueVillageois;
		this.contactServeur = contactServeur;
	}
	
	public void validerVote() {
		
		String joueur = "";
		
		for ( int iterateurChoixJoueur = 0 ; iterateurChoixJoueur < vueVillageois.getChoixJoueur().size() ; iterateurChoixJoueur++) {
			
			if ( vueVillageois.getChoixJoueur().get(iterateurChoixJoueur).isSelected()) joueur = vueVillageois.getChoixJoueur().get(iterateurChoixJoueur).getText();
			
		}
		
		contactServeur.envoyerMessage(joueur);	
	}
	
	
	public void traiter(String message) {
		List<String> listeJoueurs = new ArrayList<String>();
		
		listeJoueurs.add(message);
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				vueVillageois.setChoixJoueur(listeJoueurs);
				
			}
		});
		
		
		
	}
	
}
