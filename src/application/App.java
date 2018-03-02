package application;


import controleur.ControleurAccueil;
import controleur.ControleurRegles;
import controleur.ControleurSalonDeJeu;
import javafx.application.Platform;
import vue.VueAccueil;
import vue.VueRegles;
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
				VueRegles vueRegles = new VueRegles();
				vueRegles.start(VueRegles.instanceVueRegles);
				ControleurRegles controleRegles = new ControleurRegles(vueRegles, vueAccueil);
				vueRegles.setControleurRegles(controleRegles);
				vueRegles.hide();
				ControleurAccueil controle = new ControleurAccueil(vueAccueil, contactServeur, vueRegles);
				vueAccueil.setControleurAccueil(controle);
				

				
				
			}
		});
				
		
		
		
	}

}
