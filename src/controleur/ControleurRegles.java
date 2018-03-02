package controleur;

import javafx.application.Platform;
import vue.VueAccueil;
import vue.VueRegles;
import vue.VueSalonDeJeu;

public class ControleurRegles {

	
	VueRegles vueRegles;
	VueAccueil vueAccueil;
	VueSalonDeJeu vueSalonDeJeu;
	
	
	public ControleurRegles (VueRegles vueRegles, VueAccueil vueAccueil) {
		this.vueRegles = vueRegles;
		this.vueAccueil = vueAccueil;
		this.vueSalonDeJeu = vueSalonDeJeu;
	}
	
	public void retour() {
		Platform.runLater(new Runnable() {
			
			@Override
				public void run() {
					
					if (vueAccueil==null) {
						vueRegles.hide();
						vueSalonDeJeu.show();
						
					}
					else {
						vueRegles.hide();
						vueAccueil.show();
					}
					
					
			}
		});
	}
	
	
}
