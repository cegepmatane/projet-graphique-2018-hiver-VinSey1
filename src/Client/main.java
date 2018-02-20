package Client;

import vue.VueSalonDeJeu;

public class main {
	
	public static void main(String[] args) {
		VueSalonDeJeu client = new VueSalonDeJeu();
		ContactServeur contactserveur = new ContactServeur();
		ControleurClient controleur = new ControleurClient(client);
		
		contactserveur.setControleur(controleur);
	}

}
