package modele;
import java.util.Random;

import serveur.Serveur;

public class Partie {

	private Random aleatoire = new Random();
	private int nbLoupGarou;
	private int nbMaxLoupGarou;
	private int nbVillageois;
	private int nbMaxVillageois;
	private final String[] numeroRoles = {"Loups-Garous", "Villageois"};
	private Serveur serveur;
	private int[] tableauJoueurs;
	
	public Partie(Serveur serveur) {
		this.serveur=serveur;
		lancerPartie();
	}
	
	private void lancerPartie() {
		serveur.envoyerATous("Il y a assez de joueurs ! La partie va commencer. \n Les cartes vont être distribuées");
		tableauJoueurs = new int[serveur.getNB_JOUEURS_MAX()];
		nbMaxLoupGarou = 1;
		nbMaxVillageois = 2;
		distribuerCartes();
	}
	
	private void distribuerCartes() {
		int role;
		for (int iterateur = 0; iterateur<serveur.getNB_JOUEURS_MAX(); iterateur++) {
			role = aleatoire.nextInt(2);
			if (role == 0) {
				if (nbLoupGarou+1 <= nbMaxLoupGarou) {
					serveur.envoyerIndividuel("Tu es un Loup-Garou", iterateur);
				} else {
					serveur.envoyerIndividuel("Tu es un Villageois", iterateur);
				}
			} else {
				if (nbVillageois+1 <= nbMaxVillageois) {
					serveur.envoyerIndividuel("Tu es un Villageois", iterateur);
				} else {
					serveur.envoyerIndividuel("Tu es un Loup-Garou", iterateur);
				}
			}
		}
	}
}
