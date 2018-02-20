package Client;

import vue.VueSalonDeJeu;

public class ControleurClient {

	VueSalonDeJeu vueSalonDeJeu;
	
	public ControleurClient (VueSalonDeJeu vueSalonDeJeu) {
		this.vueSalonDeJeu = vueSalonDeJeu;
	}
	
	public void traiter(String message) {
		if (message.equals("Demande vote du village")) {
			vueSalonDeJeu.ajouterTexteAuChat("A vous de voter villageois");
		}
	}
	
	
}
