package controleur;

import javafx.application.Platform;
import vue.VueAccueil;
import vue.VueRegles;
import vue.VueSalonDeJeu;

public class ControleurRegles {

	
	VueRegles vueRegles;
	VueAccueil vueAccueil;
	VueSalonDeJeu vueSalonDeJeu;
	
	
	
	public ControleurRegles (VueRegles vueRegles, VueAccueil vueAccueil, VueSalonDeJeu vueSalonDeJeu) {
		this.vueRegles = vueRegles;
		this.vueAccueil = vueAccueil;
		this.vueSalonDeJeu = vueSalonDeJeu;
		
	}
	

	

	
	public void retour() {
		Platform.runLater(new Runnable() {
			
			@Override
				public void run() {
				
					if (VueRegles.getRetour() == 0) {
						vueRegles.hide();
						vueAccueil.show();
						
					}
					else {
						 vueRegles.hide();
						 vueSalonDeJeu.show();
					}
						
			
					
					
			}
		});
	}
	
	
}
