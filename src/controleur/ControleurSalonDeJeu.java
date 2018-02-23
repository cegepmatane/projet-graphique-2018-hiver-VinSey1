package controleur;

import reseau.ContactServeur;
import vue.VueSalonDeJeu;

public class ControleurSalonDeJeu {

	ContactServeur contactServeur;
	VueSalonDeJeu vueSalonDeJeu;
	
	public ControleurSalonDeJeu (VueSalonDeJeu vueSalonDeJeu, ContactServeur contactServeur) {
		this.vueSalonDeJeu = vueSalonDeJeu;
		this.contactServeur = contactServeur;
		
	}
	
	public void traiter(String message) {
		if (message.equals("Demande vote du village")) {
			vueSalonDeJeu.ajouterTexteAuChat("A vous de voter villageois");
		}
		
		if (message.equals("Loup-garou tué")) {
			vueSalonDeJeu.ajouterTexteAuChat("Un loup-garou a été tué");
			vueSalonDeJeu.modifierIndicationNombreDeLoupGarouVivant(2);
			vueSalonDeJeu.modifierNombreDeJoueur(4);
		}
		
		if (message.equals("Villageois tué")) {
			vueSalonDeJeu.ajouterTexteAuChat("Un villageois a été tué");
			vueSalonDeJeu.modifierNombreDeJoueur(2);
		}
	}
	
	

	
	
	public void validerVote() {
		vueSalonDeJeu.ajouterTexteAuChat("vote effectué");
		contactServeur.envoyerMessage("Un joueur a cliqué sur valider");

	}
}
