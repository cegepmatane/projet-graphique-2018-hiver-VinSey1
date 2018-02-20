package application;


import vue.VueSalonDeJeu;

public class App {
	
	public static void main(String[] parametres) {
			
		VueSalonDeJeu vueSalonJeu = new VueSalonDeJeu();

		vueSalonJeu.launch(VueSalonDeJeu.class, parametres);
	
		
	}

}
