package controleur;

import javafx.application.Platform;
import vue.VueAccueil;
import vue.VueRegles;
import vue.VueSalonDeJeu;

public class ControleurAccueil {

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
					VueSalonDeJeu vueSalonDeJeu = new VueSalonDeJeu();
					vueSalonDeJeu.start(VueSalonDeJeu.instanceVueSalonDeJeu);
					
					// Est-ce acceptable que le contrôleur de l'acceuil modifie une autre vue que celle dont il a la charge ?
					if (pseudo.equals("")) {
						vueSalonDeJeu.ajouterTexteAuChat("Bonjour Anonyme");
					}
					else {
						vueSalonDeJeu.ajouterTexteAuChat("Bonjour " + pseudo);
					}
					
			}
		});
}
}
