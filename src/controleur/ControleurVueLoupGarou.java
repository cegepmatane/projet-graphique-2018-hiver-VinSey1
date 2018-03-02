package controleur;

import java.util.ArrayList;
import java.util.List;

import reseau.ContactServeur;
import vue.VueLoupGarou;
import vue.VueVillageois;

public class ControleurVueLoupGarou {

	
	private ContactServeur contactServeur;

	private VueLoupGarou vueLoupGarou;

	public ControleurVueLoupGarou(VueLoupGarou vueLoupGarou, ContactServeur contactServeur) {
		
		this.vueLoupGarou = vueLoupGarou;
		this.contactServeur = contactServeur;
	}
	
	public void validerVote() {
		
		String joueur = "";
		
		for ( int iterateurChoixJoueur = 0 ; iterateurChoixJoueur < vueLoupGarou.getChoixJoueur().size() ; iterateurChoixJoueur++) {
			
			if ( vueLoupGarou.getChoixJoueur().get(iterateurChoixJoueur).isSelected()) joueur = vueLoupGarou.getChoixJoueur().get(iterateurChoixJoueur).getText();
			
		}
		
		contactServeur.envoyerMessage(joueur);	
	}
	
	
	public void traiter(String message) {
		
		List<String> listeJoueurs = new ArrayList<String>();
		
		listeJoueurs.add(message);
		
		vueLoupGarou.setChoixJoueur(listeJoueurs);
	
	}
	
	
}
