package modele;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
	private boolean finPartie = false;
	
	public Partie(Serveur serveur) {
		this.serveur=serveur;
		lancerPartie();
	}
	
	private void lancerPartie() {
		serveur.envoyerATous("Il y a assez de joueurs ! La partie va commencer.\nLes cartes vont être distribuées");
		tableauJoueurs = new int[serveur.getNB_JOUEURS_MAX()];
		nbMaxLoupGarou = 1;
		nbMaxVillageois = 2;
		distribuerCartes();
		while(!finDePartie()) {
			deroulementNuit();
			deroulementJour();
		}
		partieFinie();
	}
	
	private void partieFinie() {
		if (nbVillageois == 0) {
			serveur.envoyerATous("Les loups-garous ont gagné !");
		} else {
			serveur.envoyerATous("Les villageois ont gagné !");
		}
	}
	private boolean finDePartie() {
		return (nbVillageois == 0 || nbLoupGarou == 0);
	}
	private void deroulementNuit() {
		serveur.envoyerATous("La nuit tombe sur le village de thiercelieux, vous fermez les yeux en espérant passer la nuit");
		tourLoupGarou();
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		serveur.envoyerATous("Les loups-garous ont fait leur choix");
	}
	
	private void deroulementJour() {
		if(!finDePartie()) {
			serveur.envoyerATous("C'est le jour");
			
		}
	}
	private void distribuerCartes() {
		int role;
		for (int iterateur = 0; iterateur<tableauJoueurs.length; iterateur++) {
			role = aleatoire.nextInt(2);
			if (role == 0) {
				if (nbLoupGarou+1 <= nbMaxLoupGarou) {
					tableauJoueurs[iterateur] = 0;
					serveur.envoyerIndividuel("Tu es un Loup-Garou", iterateur);
					nbLoupGarou+=1;
				} else {
					tableauJoueurs[iterateur] = 1;
					serveur.envoyerIndividuel("Tu es un Villageois", iterateur);
					nbVillageois+=1;
				}
			} else {
				if (nbVillageois+1 <= nbMaxVillageois) {
					tableauJoueurs[iterateur] = 1;
					serveur.envoyerIndividuel("Tu es un Villageois", iterateur);
					nbVillageois+=1;
				} else {
					tableauJoueurs[iterateur] = 0;
					serveur.envoyerIndividuel("Tu es un Loup-Garou", iterateur);
					nbLoupGarou+=1;
				}
			}
		}
	}
	
	private void tourLoupGarou() {
		for (int iterateur = 0; iterateur<tableauJoueurs.length; iterateur++) {
			if(tableauJoueurs[iterateur] == 0) {
				serveur.envoyerIndividuel("C'est à ton tour de jouer. En tant que Loup-Garou, tu dois choisir une cible à manger cette nuit.", iterateur);
				
			}
		}
	}
	
	public void traiter(String message) {
		//traitement du message
	}
}
