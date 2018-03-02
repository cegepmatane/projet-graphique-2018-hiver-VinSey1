package controleur;

import javafx.application.Platform;
import vue.VueAccueil;
import vue.VueRegles;

public class ControleurRegles {

	
	VueRegles vueRegles;
	VueAccueil vueAccueil;
	
	
	public ControleurRegles (VueRegles vueRegles, VueAccueil vueAccueil) {
		this.vueRegles = vueRegles;
		this.vueAccueil = vueAccueil;
	}
	
	public void retourAccueil() {
		Platform.runLater(new Runnable() {
			
			@Override
				public void run() {
				
					vueRegles.hide();
					vueAccueil.show();
					
			}
		});
	}
	
	
}
