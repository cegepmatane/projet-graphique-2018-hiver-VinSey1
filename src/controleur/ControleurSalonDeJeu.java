package controleur;

import javafx.application.Platform;
import reseau.ContactServeur;
import vue.VueRegles;
import vue.VueSalonDeJeu;
import vue.VueVillageois;

public class ControleurSalonDeJeu {

	ContactServeur contactServeur;
	VueSalonDeJeu vueSalonDeJeu;
	VueRegles vueRegles;
	
	
	public ControleurSalonDeJeu (VueSalonDeJeu vueSalonDeJeu, ContactServeur contactServeur, VueRegles vueRegles) {
		this.vueSalonDeJeu = vueSalonDeJeu;
		this.contactServeur = contactServeur;
		this.vueRegles = vueRegles;
		
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
		
			VueVillageois vueVillageois = new VueVillageois();
			
			ControleurVueVillageois controleurVueVillageois = new ControleurVueVillageois(vueVillageois, contactServeur);
			
			@Override
				public void run() {
													
					contactServeur.setControleurVueVillageois(controleurVueVillageois);
					
					try {
						vueVillageois.start(VueVillageois.instanceVueVillageois);
						contactServeur.setControleurVueVillageois(controleurVueVillageois);
						contactServeur.envoyerMessage("requete liste joueur pour vote villageois");
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		});
	}
	
	public void afficherRegles() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				vueRegles.setRetour(1);
				vueSalonDeJeu.hide();
				vueRegles.show();
						
			}
		});
	}
}
