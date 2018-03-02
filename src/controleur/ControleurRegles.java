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
		
	}
	
	public void retour() {
		Platform.runLater(new Runnable() {
			
			@Override
				public void run() {
				
						vueRegles.hide();
						vueAccueil.show();
			
					
					
			}
		});
	}
	
	
}
