package controleur;

import javafx.application.Platform;
import reseau.ContactServeur;
import vue.VueAccueil;
import vue.VueRegles;
import vue.VueSalonDeJeu;

public class ControleurAccueil {

	VueAccueil vueAccueil;
	String pseudo ="";
	ContactServeur contactServeur;
	
	public ControleurAccueil(VueAccueil vueAccueil, ContactServeur contactServeur) {
		this.vueAccueil = vueAccueil;
		this.contactServeur = contactServeur;
	}
	
	public void afficherRegles() {
			Platform.runLater(new Runnable() {
			
				@Override
					public void run() {
					
						vueAccueil.close();
						VueRegles vueRegles = new VueRegles();
						vueRegles.start(VueRegles.instanceVueRegles);
						ControleurRegles controleRegles = new ControleurRegles(vueRegles);
						vueRegles.setControleurRegles(controleRegles);
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
						vueAccueil.close();
						VueSalonDeJeu vueSalonDeJeu = new VueSalonDeJeu();											
						ControleurSalonDeJeu controleurSalonDeJeu = new ControleurSalonDeJeu(vueSalonDeJeu, contactServeur);
						
						contactServeur.setControleur(controleurSalonDeJeu);
						vueSalonDeJeu.setControleurSalonDeJeu(controleurSalonDeJeu);
						vueSalonDeJeu.start(VueSalonDeJeu.instanceVueSalonDeJeu);
						
						
					}
					
					
			}
		});
	}
}