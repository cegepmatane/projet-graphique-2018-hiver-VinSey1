package modele;
import java.io.IOException;
import java.io.StringReader;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import jdk.internal.org.xml.sax.SAXException;
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

	}
	
	public void lancerPartie() {
		serveur.envoyerATous("Il y a assez de joueurs ! La partie va commencer.\nLes cartes vont �tre distribu�es");
		tableauJoueurs = new int[serveur.getNB_JOUEURS_MAX()];
		nbMaxLoupGarou = 1;
		nbMaxVillageois = 2;
		distribuerCartes();
		while(!finDePartie()) {
			deroulementNuit();
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			deroulementJour();
		}
		partieFinie();
	}
	
	private void partieFinie() {
		if (nbVillageois == 0) {
			serveur.envoyerATous("Les loups-garous ont gagn� !");
		} else {
			serveur.envoyerATous("Les villageois ont gagn� !");
		}
	}
	private boolean finDePartie() {
		return (nbVillageois == 0 || nbLoupGarou == 0);
	}
	private void deroulementNuit() {
		serveur.envoyerATous("La nuit tombe sur le village de thiercelieux, vous fermez les yeux en esp�rant passer la nuit");
		//tourLoupGarou();
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
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
				serveur.envoyerIndividuel("C'est � ton tour de jouer. En tant que Loup-Garou, tu dois choisir une cible � manger cette nuit.", iterateur);
				
			}
		}
	}
	
	public void traiter(String message) {
		
		try {
			DocumentBuilder lecteurXML = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(message));
			
			Document doc = lecteurXML.parse(inputSource);
			NodeList contenuMessage = doc.getElementsByTagName("message");		
			
		}catch(org.xml.sax.SAXException e){
			e.printStackTrace();
			
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
			
			
		}
		
		

		
		
		/*
		if(message.equals("requete liste joueur pour vote villageois")) {
			serveur.envoyerIndividuel("test liste joueur", 2);
		}
		*/
	}
}
