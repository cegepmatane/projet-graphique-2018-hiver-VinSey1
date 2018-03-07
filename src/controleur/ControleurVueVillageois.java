package controleur;

import vue.VueVillageois;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import reseau.ContactServeur;
public class ControleurVueVillageois {

	private VueVillageois vueVillageois;
	private ContactServeur contactServeur;
	
	public ControleurVueVillageois(VueVillageois vueVillageois, ContactServeur contactServeur) {
		
		this.vueVillageois = vueVillageois;
		this.contactServeur = contactServeur;
	}
	
	public void validerVote() {
		
		contactServeur.envoyerMessage("<message>"
										+ "<joueurs>"
											+ "<vote>"
												+ "<joueur>"
													+ vueVillageois.getChoixJoueur();
												+ "</joueur>"
											+ "</vote>"
										+ "</joueurs>"
									+ "</message>");
	}
		
	public void traiter(String message) {
				
		List<String> listeJoueurs = new ArrayList<String>();
		
		listeJoueurs.add("Eliott");
		listeJoueurs.add("Vincente");
		listeJoueurs.add("Valentin");

		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				vueVillageois.setChoixJoueur(listeJoueurs);
				
			}
		});		
	}
}
