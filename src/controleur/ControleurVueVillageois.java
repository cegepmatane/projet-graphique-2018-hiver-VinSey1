package controleur;

import vue.VueVillageois;
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
	
	
}
