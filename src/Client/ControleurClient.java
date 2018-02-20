package Client;

import vue.VueSalonDeJeu;

public class ControleurClient {

	VueSalonDeJeu vueSalonDeJeu;
	ContactServeur contactServeur;
	
	public ControleurClient (VueSalonDeJeu vueSalonDeJeu, ContactServeur contactServeur) {
		this.vueSalonDeJeu = vueSalonDeJeu;
		this.contactServeur = contactServeur;
		
	}
	
	public void traiter(String message) {
		if (message.equals("Demande vote du village")) {
			vueSalonDeJeu.ajouterTexteAuChat("A vous de voter villageois");
		}
		
		if (message.equals("Loup-garou tué")) {
			vueSalonDeJeu.ajouterTexteAuChat("Un loup-garou a été tué");
			//ueSalonDeJeu.modifierIndicationNombreDeLoupGarouVivant(nouveauNombreDeLoupGarou);
		}
	}
	
	public void validerVote() {
		contactServeur.envoyerMessage("Un joueur a cliqué sur voter");
	}
}
