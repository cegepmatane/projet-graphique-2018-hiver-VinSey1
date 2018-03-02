package application;


import controleur.ControleurAccueil;
import controleur.ControleurSalonDeJeu;
import javafx.application.Platform;
import vue.VueAccueil;
import vue.VueSalonDeJeu;
import javafx.embed.swing.JFXPanel;
import reseau.*;

public class App {
	
	public static void main(String[] parametres) {
		
		new JFXPanel();
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				ContactServeur contactServeur = new ContactServeur();
				VueAccueil vueAccueil = new VueAccueil();
				vueAccueil.start(VueAccueil.instanceVueAccueil);
				ControleurAccueil controle = new ControleurAccueil(vueAccueil, contactServeur);
				vueAccueil.setControleurAccueil(controle);
				

				
				
			}
		});
				
		
		
		
	}

}
