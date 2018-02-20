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
		
		if (message.equals("Loup-garou tu�")) {
			vueSalonDeJeu.ajouterTexteAuChat("Un loup-garou a �t� tu�");
			//ueSalonDeJeu.modifierIndicationNombreDeLoupGarouVivant(nouveauNombreDeLoupGarou);
		}
	}
	
	public void validerVote() {
		contactServeur.envoyerMessage("Un joueur a cliqu� sur voter");
	}
}
