package controleur;

import javafx.application.Platform;
import vue.VueAccueil;
import vue.VueRegles;

public class ControleurAccueil {

	VueAccueil vueAccueil;
	
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
}
