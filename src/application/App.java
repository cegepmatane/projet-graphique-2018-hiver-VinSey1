package application;


import javafx.application.Platform;
import vue.VueSalonDeJeu;
import javafx.embed.swing.JFXPanel;

public class App {
	
	public static void main(String[] parametres) {
		
		new JFXPanel();
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				VueSalonDeJeu vueSalonJeu = new VueSalonDeJeu();
				vueSalonJeu.start(VueSalonDeJeu.instanceVueSalonDeJeu);
				
			}
		});
		
		
		
		
		
		
	}

}
