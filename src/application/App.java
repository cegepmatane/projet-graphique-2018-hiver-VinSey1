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
				VueSalonDeJeu vueSalonDeJeu = new VueSalonDeJeu();											
				ControleurSalonDeJeu controleurSalonDeJeu = new ControleurSalonDeJeu(vueSalonDeJeu, contactServeur, vueRegles);
				contactServeur.setControleurSalonDeJeu(controleurSalonDeJeu);
				vueSalonDeJeu.setControleurSalonDeJeu(controleurSalonDeJeu);
				vueSalonDeJeu.start(VueSalonDeJeu.instanceVueSalonDeJeu);
				contactServeur.connection();
				vueSalonDeJeu.hide();
				ControleurRegles controleRegles = new ControleurRegles(vueRegles, vueAccueil,vueSalonDeJeu);
				vueRegles.setControleurRegles(controleRegles);
				vueRegles.hide();
				ControleurAccueil controle = new ControleurAccueil(vueAccueil, contactServeur, vueRegles, vueSalonDeJeu);
				vueAccueil.setControleurAccueil(controle);
								
			}
		});
	}
}
