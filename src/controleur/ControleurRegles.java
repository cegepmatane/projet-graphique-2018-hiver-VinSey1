package controleur;

import javafx.application.Platform;
import vue.VueAccueil;
import vue.VueRegles;

public class ControleurRegles {

	
	VueRegles vueRegles;
	
	
	public ControleurRegles (VueRegles vueRegles) {
		this.vueRegles = vueRegles;
	}
	
	public void retourAccueil() {
		Platform.runLater(new Runnable() {
			
			@Override
				public void run() {
				
					vueRegles.close();
					VueAccueil vueAccueil = new VueAccueil();
					vueAccueil.start(VueAccueil.instanceVueAccueil);
					ControleurAccueil controleAccueil = new ControleurAccueil(vueAccueil);
					vueAccueil.setControleurAccueil(controleAccueil);
					
			}
		});
	}
	
	
}
