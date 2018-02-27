package controleur;

import javafx.application.Platform;
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
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				if (message.equals("Start")) {
					vueSalonDeJeu.ajouterTexteAuChat("Nombre maximum de joueurs atteint, la partie commence !");
				}
				if (message.equals("VoteJoueur"))
				vueSalonDeJeu.ajouterTexteAuChat("Un joueur a vot�");
				
			}
		});
		
		
		

		
		if (message.equals("Demande vote du village")) {
			vueSalonDeJeu.ajouterTexteAuChat("A vous de voter villageois");
		}
		
		if (message.equals("Loup-garou tu�")) {
			vueSalonDeJeu.ajouterTexteAuChat("Un loup-garou a �t� tu�");
			vueSalonDeJeu.modifierIndicationNombreDeLoupGarouVivant(2);
			vueSalonDeJeu.modifierNombreDeJoueur(4);
		}
		
		if (message.equals("Villageois tu�")) {
			vueSalonDeJeu.ajouterTexteAuChat("Un villageois a �t� tu�");
			vueSalonDeJeu.modifierNombreDeJoueur(2);
		}
		
		
	}
	

	public void validerVote() {
		contactServeur.envoyerMessage("VoteJoueur");
	}
}
