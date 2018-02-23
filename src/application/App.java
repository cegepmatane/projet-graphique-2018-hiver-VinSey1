package application;


import controleur.ControleurSalonDeJeu;
import javafx.application.Platform;
import vue.VueSalonDeJeu;
import javafx.embed.swing.JFXPanel;
import reseau.*;

public class App {
	
	public static void main(String[] parametres) {
		
		new JFXPanel();
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				VueSalonDeJeu vueSalonJeu = new VueSalonDeJeu();
				vueSalonJeu.start(VueSalonDeJeu.instanceVueSalonDeJeu);
				
				ContactServeur contactServeur = new ContactServeur();

				ControleurSalonDeJeu controleurSalonDeJeu = new ControleurSalonDeJeu(vueSalonJeu, contactServeur);
				
				contactServeur.setControleur(controleurSalonDeJeu);
				vueSalonJeu.setControleurSalonDeJeu(controleurSalonDeJeu);
				
			}
		});
				
		
		
		
	}

}
