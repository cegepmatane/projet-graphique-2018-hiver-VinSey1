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
	
	public ControleurAccueil(VueAccueil vueAccueil, ContactServeur contactServeur, VueRegles vueRegles) {
		this.vueAccueil = vueAccueil;
		this.contactServeur = contactServeur;
		this.vueRegles = vueRegles;
	}
	
	public void afficherRegles() {
			Platform.runLater(new Runnable() {
			
				@Override
					public void run() {
					
						vueAccueil.hide();
						vueRegles.show();
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
						
						vueAccueil.hide();
						VueSalonDeJeu vueSalonDeJeu = new VueSalonDeJeu();											
						ControleurSalonDeJeu controleurSalonDeJeu = new ControleurSalonDeJeu(vueSalonDeJeu, contactServeur, vueRegles);
						
						contactServeur.setControleur(controleurSalonDeJeu);
						vueSalonDeJeu.setControleurSalonDeJeu(controleurSalonDeJeu);
						vueSalonDeJeu.start(VueSalonDeJeu.instanceVueSalonDeJeu);
						contactServeur.connection();
						
					}
			}
		});
	}
}