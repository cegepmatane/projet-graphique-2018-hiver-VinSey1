package controleur;

import javafx.application.Platform;
import reseau.ContactServeur;
import vue.VueSalonDeJeu;
import vue.VueVillageois;

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
				
				vueSalonDeJeu.ajouterTexteAuChat(message);
				
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
	
	public void afficherVoteVillageois() {
		Platform.runLater(new Runnable() {
		
			@Override
				public void run() {
				
					VueVillageois vueVillageois = new VueVillageois();
					
					ControleurVueVillageois controleurVueVillageois = new ControleurVueVillageois(vueVillageois, contactServeur);
				
					try {
						vueVillageois.start(VueVillageois.instanceVueVillageois);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
	}
}
