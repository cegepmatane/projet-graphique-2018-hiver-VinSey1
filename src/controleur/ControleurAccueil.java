package controleur;

import javafx.application.Platform;
import reseau.ContactServeur;
import vue.VueAccueil;
import vue.VueRegles;
import vue.VueSalonDeJeu;

public class ControleurAccueil {

	ContactServeur contactServeur;
	VueAccueil vueAccueil;
	String pseudo ="";
	
	public ControleurAccueil(VueAccueil vueAccueil) {
		this.vueAccueil = vueAccueil;
	}
	
	public void afficherRegles() {
			Platform.runLater(new Runnable() {
			
				@Override
					public void run() {
					
						VueRegles vueRegles = new VueRegles();
						vueRegles.start(VueRegles.instanceVueRegles);
				}
			});
	}
	
	public void afficherSalonDeJeu() {
		Platform.runLater(new Runnable() {
		
			@Override
				public void run() {
					pseudo=vueAccueil.recupererPseudo();
					
					// Est-ce acceptable que le contrôleur de l'acceuil modifie une autre vue que celle dont il a la charge ?
					if (pseudo.equals("")) {
						vueAccueil.setErreur();
					}
					else {
						VueSalonDeJeu vueSalonDeJeu = new VueSalonDeJeu();
						vueSalonDeJeu.start(VueSalonDeJeu.instanceVueSalonDeJeu);
						vueSalonDeJeu.ajouterTexteAuChat("Bonjour "+pseudo);
						ControleurSalonDeJeu controleurSalonDeJeu = new ControleurSalonDeJeu(vueSalonDeJeu, contactServeur);
						ContactServeur contactServeur = new ContactServeur();
						contactServeur.setControleur(controleurSalonDeJeu);
						contactServeur.connection();
						vueSalonDeJeu.setControleurSalonDeJeu(controleurSalonDeJeu);
					}
					
					
			}
		});
}
}
