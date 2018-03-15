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
	private Joueur[] tableauJoueurs;
	private boolean finPartie = false;
	private boolean nuitEnCours = true;
	private boolean jourEnCours = false;
	
	/**
	 * 0 -> aucun
	 * 1 -> loup garou
	 * 2 -> villageois
	 */
	private int tourDeJeu = 0;
	
	public Partie(Serveur serveur) {
		this.serveur=serveur;
		
		tableauJoueurs = new Joueur[serveur.NB_JOUEURS_MAX];
		
		for ( int iterateurNomJoueurs = 0; iterateurNomJoueurs <  serveur.getListeJoueurs().size() ; iterateurNomJoueurs++) {
			
			tableauJoueurs[iterateurNomJoueurs] = new Joueur(serveur.getListeJoueurs().get(iterateurNomJoueurs));
			
		}
	}
	
	public void lancerPartie() {
		
		envoyerMessage("Il y a assez de joueurs ! La partie va commencer.\nLes cartes vont être distribuées");
		tableauJoueurs = new Joueur[serveur.getNB_JOUEURS_MAX()];
		nbMaxLoupGarou = 1;
		nbMaxVillageois = 2;
		
		try {
			TimeUnit.SECONDS.sleep(4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		distribuerCartes();
		
		try {
			TimeUnit.SECONDS.sleep(4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		while(!finDePartie()) {
			deroulementNuit();
			deroulementJour();
		}
		partieFinie();
	}
	

	private void deroulementNuit() {
		
		envoyerMessage("<annonce>La nuit tombe sur le village de thiercelieux, vous fermez les yeux en espérant passer la nuit</annonce>");
		
		tourLoupGarou();
		
		serveur.envoyerATous("<annonce>Les loups-garous ont fait leur choix</annonce>");
	}
	
	private void deroulementJour() {
		if(!finDePartie()) {
			envoyerMessage("<annonce>Le jour se lève, les villageois peuvent voter pour désigner une</annonce>");
			
		}
		else {		
			if ( nbVillageois == 0 ) {
				envoyerMessage("<annonce>Les loups garous ont gagné, la partie est terminé</annonce>");
			}
			else {
				envoyerMessage("<annonce>Les villageois ont gagné, la partie est terminé</annonce>");
			}
		}
	}
	private void distribuerCartes() {
		int role;
		for (int iterateur = 0; iterateur<tableauJoueurs.length; iterateur++) {
			role = aleatoire.nextInt(2);
			if (role == 0) {
				
				if (nbLoupGarou+1 <= nbMaxLoupGarou) {
					tableauJoueurs[iterateur].setRole(1);
					serveur.envoyerIndividuel("<annonce>Tu es un Loup-Garou</annonce>", iterateur);
					nbLoupGarou+=1;
				} else {
					tableauJoueurs[iterateur].setRole(0);
					serveur.envoyerIndividuel("<annonce>Tu es un Villageois</annonce>", iterateur);
					nbVillageois+=1;
				}
			} else {
				if (nbVillageois+1 <= nbMaxVillageois) {
					tableauJoueurs[iterateur].setRole(0);
					serveur.envoyerIndividuel("<annonce>Tu es un Villageois</annonce>", iterateur);
					nbVillageois+=1;
				} else {
					tableauJoueurs[iterateur].setRole(1);
					serveur.envoyerIndividuel("<annonce>Tu es un Loup-Garou</annonce>", iterateur);
					nbLoupGarou+=1;
				}
			}
		}
	}
	
	private void partieFinie() {
		if (nbVillageois == 0) {
			serveur.envoyerATous("Les loups-garous ont gagné !");
		} else {
			serveur.envoyerATous("Les villageois ont gagné !");
		}
	}

	
	private void tourLoupGarou() {
				
		tourDeJeu = 1;
		
		for (int iterateur = 0; iterateur<tableauJoueurs.length; iterateur++) {
			if(tableauJoueurs[iterateur].getRole() == 1) {
				serveur.envoyerIndividuel("<annonce>C'est à ton tour de jouer. "
						+ "En tant que Loup-Garou, tu dois choisir une "
						+ "cible à manger cette nuit en cliquant sur \"voter\"</annonce>", iterateur);
				
			}
		}
				
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		tourDeJeu = 1;
	}
	
	public void traiter(String message) {
		
		try {
			DocumentBuilder lecteurXML = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(message));
			
			Document doc = lecteurXML.parse(inputSource);
			
			switch( doc.getDocumentElement().getNodeName() ) {
				
				case "demande":
					
					break;
					
				default:
					
					System.out.println("message non interprété: Partie.traiter");
					
					break;
					
			}
						
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
	
	public void envoyerListeJoueur(int joueur) {
		
			
		switch( tourDeJeu ) {
		
		case 1:
			
			for (int iterateur = 0; iterateur<tableauJoueurs.length; iterateur++) {
				if(tableauJoueurs[iterateur].getRole() == 1) {
					
					String messageAEnvoyer = "<liste>";
					
					for ( int iterateurTableauJoueur = 0; iterateurTableauJoueur< tableauJoueurs.length ; iterateurTableauJoueur++) {
						
						if ( tableauJoueurs[iterateurTableauJoueur].getRole() == 0 ) {
							messageAEnvoyer +="<joueur>"+tableauJoueurs[iterateurTableauJoueur].getNom()+"</joueur>";
						}
						
					}
					
					messageAEnvoyer += "</liste>";				
					serveur.envoyerIndividuel(messageAEnvoyer, iterateur);
				}
			}		
			
			break;
			
		case 2:
			
			break;
		
		default:
			break;
			
		}
		
	}
	
	
	public void envoyerMessage(String message) {
		
		
		serveur.envoyerATous(message);
	}
	
	
	
	private boolean finDePartie() {
		return (nbVillageois == 0 || nbLoupGarou == 0);
	}
	
}
