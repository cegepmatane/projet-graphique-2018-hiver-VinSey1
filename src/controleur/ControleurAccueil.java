package controleur;

import javafx.application.Platform;
import reseau.ContactServeur;
import vue.VueAccueil;
import vue.VueRegles;
import vue.VueSalonDeJeu;

public class ControleurAccueil {

	VueRegles vueRegles;
	VueAccueil vueAccueil;
	String pseudo ="";
	ContactServeur contactServeur;
	VueSalonDeJeu vueSalonDeJeu;
	
	
	
	public ControleurAccueil(VueAccueil vueAccueil, ContactServeur contactServeur, VueRegles vueRegles, VueSalonDeJeu vueSalonDeJeu) {
		this.vueAccueil = vueAccueil;
		this.contactServeur = contactServeur;
		this.vueRegles = vueRegles;
		this.vueSalonDeJeu = vueSalonDeJeu;
	}
	
	public void afficherRegles() {
			Platform.runLater(new Runnable() {
			
				@Override
					public void run() {					
						vueAccueil.hide();
						vueRegles.show();
						vueRegles.setRetour(0);
						
				}
			});
	}
	
	public void afficherSalonDeJeu() {
		Platform.runLater(new Runnable() {
		
			@Override
				public void run() {
					pseudo=vueAccueil.recupererPseudo();
					if (pseudo.equals("")) {
						vueAccueil.setErreur();
					}
					else {
						contactServeur.envoyerMessage("<message>"
														+ "<authentification>"
															+ "<joueur>"
																+vueAccueil.recupererPseudo()
															+"</joueur>"
														+ "</authentification>"
													+ "</message>");
						vueAccueil.close();
						vueSalonDeJeu.show();
						
					}
			}
		});
	}
}