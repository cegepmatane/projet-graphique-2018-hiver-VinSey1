package Client;

import javafx.application.Application;
import vue.VueSalonDeJeu;

public class main {
	
	public static void main(String[] args) {
				
		Application.launch(VueSalonDeJeu.class, args);
		
		ContactServeur contactserveur = new ContactServeur();
		ControleurClient controleur = new ControleurClient(client, contactserveur);
		client.setControleurClient(controleur);
		contactserveur.setControleur(controleur);
		
		
		
		
	}

}
