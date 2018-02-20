package controleur;

import vue.VueSalonDeJeu;

public class ControleurSalonDeJeu {

	VueSalonDeJeu vueSalonDeJeu;
	
	public ControleurSalonDeJeu (VueSalonDeJeu vueSalonDeJeu) {
		this.vueSalonDeJeu = vueSalonDeJeu;
		
	}
	
	public void traiter(String message) {
		if (message.equals("Demande vote du village")) {
			vueSalonDeJeu.ajouterTexteAuChat("A vous de voter villageois");
		}
		
		if (message.equals("Loup-garou tu�")) {
			vueSalonDeJeu.ajouterTexteAuChat("Un loup-garou a �t� tu�");
			vueSalonDeJeu.modifierIndicationNombreDeLoupGarouVivant(2);
		}
	}
	
	

	
	
	public void validerVote() {
		vueSalonDeJeu.ajouterTexteAuChat("vote effectu�");

	}
}
