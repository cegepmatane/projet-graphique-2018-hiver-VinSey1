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
				
				VueAccueil vueAccueil = new VueAccueil();
				vueAccueil.start(VueAccueil.instanceVueAccueil);
				ControleurAccueil controleRegles = new ControleurAccueil(vueAccueil);
				vueAccueil.setControleurAccueil(controleRegles);
				ContactServeur contactServeur = new ContactServeur();

				/**
				ControleurSalonDeJeu controleurSalonDeJeu = new ControleurSalonDeJeu(vueSalonJeu, contactServeur);
				
				contactServeur.setControleur(controleurSalonDeJeu);
				contactServeur.connection();
				vueSalonJeu.setControleurSalonDeJeu(controleurSalonDeJeu);
				*/
				
			}
		});
				
		
		
		
	}

}
