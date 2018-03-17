package modele;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
	private List<Integer> joueurTueeDansLaNuit;
	
	/**
	 * 0 -> aucun
	 * 1 -> loup garou
	 * 2 -> villageois
	 */
	private int tourDeJeu = 0;
	
	public Partie(Serveur serveur) {
		this.serveur=serveur;
		
		joueurTueeDansLaNuit = new ArrayList<>();
		
		tableauJoueurs = new Joueur[serveur.NB_JOUEURS_MAX];
		
		for ( int iterateurNomJoueurs = 0; iterateurNomJoueurs <  serveur.getListeJoueurs().size() ; iterateurNomJoueurs++) {
				
			tableauJoueurs[iterateurNomJoueurs] = new Joueur(serveur.getListeJoueurs().get(iterateurNomJoueurs));
			
		}
	}
	
	public void lancerPartie() {
		
		Thread threadPartie = new Thread(new Runnable() {
			
			@Override
			public void run() {
				envoyerMessage("<message><annonce>Il y a assez de joueurs ! La partie va commencer</annonce></message>");
				envoyerMessage("<message><annonce>Les cartes vont être distribués</annonce></message>");

				nbMaxLoupGarou = 1;
				nbMaxVillageois = 2;
				
				serveur.envoyerATous("<message><rafraichissement><nombreJoueurs>"+tableauJoueurs.length+"/"+serveur.NB_JOUEURS_MAX+"</nombreJoueurs></rafraichissement></message>");

				retournerMorts();
				retournerVivants();
				envoyerMessage("<message><rafraichissement><nombreLoupsGarousRestant>"+nbLoupGarou+"</nombreLoupsGarousRestant></rafraichissement></message>");
				envoyerMessage("<message><rafraichissement><nombreInnocentsRestant>"+nbVillageois+"</nombreInnocentsRestant></rafraichissement></message>");
				
				
				try {
					TimeUnit.SECONDS.sleep(3);
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
		});	


		threadPartie.start();
	}
	

	private void deroulementNuit() {
		
		envoyerMessage("<message><annonce>La nuit tombe sur le village de thiercelieux, vous fermez les yeux en espérant passer la nuit</annonce></message>");
		
		tourLoupGarou();
		
		envoyerMessage("<message><annonce>Les loups-garous ont fait leur choix</annonce></message>");
		
		finDeVote();
		
	}
	
	private void deroulementJour() {
		if(!finDePartie()){
			
			if ( joueurTueeDansLaNuit.size() ==0 ) {
				
				envoyerMessage("<message><annonce>Le jour se lève, personne n'a été tué, les villageois peuvent voter pour désigner une personne à éliminer</annonce></message>");

			}
			else {
					envoyerMessage("<message><annonce>Le jour se lève, "+tableauJoueurs[joueurTueeDansLaNuit.get(0)].getNom()+" a été tué, les villageois peuvent voter pour désigner une personne à éliminer</annonce></message>");
					retournerVivants();
					retournerMorts();				
					envoyerMessage("<message><rafraichissement><nombreLoupsGarousRestant>"+nbLoupGarou+"</nombreLoupsGarousRestant></rafraichissement></message>");
					envoyerMessage("<message><rafraichissement><nombreInnocentsRestant>"+nbVillageois+"</nombreInnocentsRestant></rafraichissement></message>");
					
					joueurTueeDansLaNuit.clear();
				}
			
		}
		else {		
			if ( nbVillageois == 0 ) {
				envoyerMessage("<message><annonce>Les loups garous ont gagné, la partie est terminé</annonce></message>");
			}
			else {
				envoyerMessage("<message><annonce>Les villageois ont gagné, la partie est terminé</annonce></message>");
			}
		}

		
				
		try {
			TimeUnit.SECONDS.sleep(4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void retournerVivants() {
		
		String listeJoueurVivant = "<message><rafraichissement><listeVivants>";
		
		for(int iterateur = 0; iterateur < tableauJoueurs.length; iterateur++) {
			if(tableauJoueurs[iterateur].isVivant()) {
				listeJoueurVivant += "<joueur>"+tableauJoueurs[iterateur].getNom()+"</joueur>";
			}
		}	
		listeJoueurVivant += "</listeVivants></rafraichissement></message>";
		envoyerMessage(listeJoueurVivant);
	}
	
	private void retournerMorts() {
		
		String listeJoueurMort = "<message><rafraichissement><listeMort>";
		
		for(int iterateur = 0; iterateur < tableauJoueurs.length; iterateur++) {
			if(!(tableauJoueurs[iterateur].isVivant())) {
				listeJoueurMort += "<joueur>"+tableauJoueurs[iterateur].getNom()+"</joueur>";
			}
		}		
		listeJoueurMort += "</listeMort></rafraichissement></message>";
		envoyerMessage(listeJoueurMort);

	}
	
	private void distribuerCartes() {
		int role;
		for (int iterateur = 0; iterateur<tableauJoueurs.length; iterateur++) {
			role = aleatoire.nextInt(2);
						
			if (role == 0) {
				
				if (nbLoupGarou+1 <= nbMaxLoupGarou) {
										
					tableauJoueurs[iterateur].setRole(1);
					serveur.envoyerIndividuel("<message><annonce>Tu es un Loup-Garou</annonce></message>", iterateur);
					serveur.envoyerIndividuel("<message><rafraichissement><role>Loup-Garou</role></rafraichissement></message>", iterateur);
					serveur.envoyerIndividuel("<message><rafraichissement><descriptionRole>Ton rôle est de manger les innocents la nuit</descriptionRole></rafraichissement></message>", iterateur);
					nbLoupGarou+=1;
				} else {
					tableauJoueurs[iterateur].setRole(0);
					serveur.envoyerIndividuel("<message><annonce>Tu es un Villageois</annonce></message>", iterateur);
					serveur.envoyerIndividuel("<message><rafraichissement><role>Villageois</role></rafraichissement></message>", iterateur);
					serveur.envoyerIndividuel("<message><rafraichissement><descriptionRole>Ton rôle est de tuer les loups-garous le jour</descriptionRole></rafraichissement></message>", iterateur);
					nbVillageois+=1;
				}
			} else {
				if (nbVillageois+1 <= nbMaxVillageois) {
					tableauJoueurs[iterateur].setRole(0);
					serveur.envoyerIndividuel("<message><annonce>Tu es un Villageois</annonce></message>", iterateur);
					serveur.envoyerIndividuel("<message><rafraichissement><role>Villageois</role></rafraichissement></message>", iterateur);
					serveur.envoyerIndividuel("<message><rafraichissement><descriptionRole>Ton rôle est de tuer les loups-garous le jour</descriptionRole></rafraichissement></message>", iterateur);

					nbVillageois+=1;
				} else {
					tableauJoueurs[iterateur].setRole(1);
					serveur.envoyerIndividuel("<message><annonce>Tu es un Loup-Garou</annonce></message>", iterateur);
					serveur.envoyerIndividuel("<message><rafraichissement><role>Loup-Garou</role></rafraichissement></message>", iterateur);
					serveur.envoyerIndividuel("<message><rafraichissement><descriptionRole>Ton rôle est de manger les innocents la nuit</descriptionRole></rafraichissement></message>", iterateur);

					nbLoupGarou+=1;
				}
			}
		}
	}
	
	private void partieFinie() {
		if (nbVillageois == 0) {
			serveur.envoyerATous("<message><annonce>Les loups-garous ont gagné !</annonce></message>");
		} else {
			serveur.envoyerATous("<message><annonce>Les villageois ont gagné !</annonce></message>");
		}
	}

	
	private void tourLoupGarou() {
				
		tourDeJeu = 1;
		
		//activez le vote pour les loups-garou
		
		for (int iterateur = 0; iterateur<tableauJoueurs.length; iterateur++) {
			if(tableauJoueurs[iterateur].getRole() == 1) {
				serveur.envoyerIndividuel("<message><annonce>C'est à ton tour de jouer. En tant que Loup-Garou, </annonce></message>", iterateur);
				serveur.envoyerIndividuel("<message><annonce>tu dois choisir une cible à manger cette nuit en cliquant sur \"voter\"</annonce></message>", iterateur);
				serveur.envoyerIndividuel("<message><rafraichissement><activerVote></activerVote></rafraichissement></message>", iterateur);
			}
		}
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int iterateur = 0; iterateur<tableauJoueurs.length; iterateur++) {
			if(tableauJoueurs[iterateur].getRole() == 1) {
				serveur.envoyerIndividuel("<message><rafraichissement><desactiverVote></desactiverVote></rafraichissement></message>", iterateur);
			}
		}

		//désactivez le vote pour les loups-garou
		tourDeJeu = 0;
	}
	
	public void traiter(String message) {
		
		try {
			
			System.out.println("Partie: "+message);
			
			DocumentBuilder lecteurXML = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(message));
			
			Document doc = lecteurXML.parse(inputSource);
			NodeList contenuMessage = doc.getElementsByTagName("message");
			Node nodeMessage = contenuMessage.item(0);
			Element elementMessage = (Element) nodeMessage;
						
			switch(nodeMessage.getFirstChild().getNodeName()) {
				
				case "demande":
										
					envoyerListeJoueur(); 

					break;
				
				case "vote":
										
					traiterVote(message);
					
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
	}
	
	
	public void envoyerListeJoueur() {
		
			
		switch( tourDeJeu ) {
		
		case 1:
			
			for (int iterateur = 0; iterateur<tableauJoueurs.length; iterateur++) {
				if(tableauJoueurs[iterateur].getRole() == 1) {
					
					String messageAEnvoyer = "<message><liste>";
					
					for ( int iterateurTableauJoueur = 0; iterateurTableauJoueur< tableauJoueurs.length ; iterateurTableauJoueur++) {
						
						if ( tableauJoueurs[iterateurTableauJoueur].getRole() == 0 ) {
							messageAEnvoyer +="<joueur>"+tableauJoueurs[iterateurTableauJoueur].getNom()+"</joueur>";
						}						
					}
					messageAEnvoyer += "</liste></message>";				
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
	
	private void traiterVote(String message) {
		
		DocumentBuilder lecteurXML;
		try {
			
			lecteurXML = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(message));
			
			Document doc = lecteurXML.parse(inputSource);
			
			String nomDuJoueurVote = doc.getElementsByTagName("vote").item(0).getTextContent();
			
			for ( int iterateurTableauJoueur = 0 ; iterateurTableauJoueur < tableauJoueurs.length ; iterateurTableauJoueur++) {
	
				if ( tableauJoueurs[iterateurTableauJoueur].getNom().equals(nomDuJoueurVote) ) {
					tableauJoueurs[iterateurTableauJoueur].setNombreVote(tableauJoueurs[iterateurTableauJoueur].getNombreVote()+1);
				}
				
			}
		
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (org.xml.sax.SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	private void finDeVote() {
		
		int joueurATuer = tableauJoueurs.length;
		int nombreDeVoteMaximum = 0;
		
		for ( int iterateurTableauJoueur = 0; iterateurTableauJoueur < tableauJoueurs.length ; iterateurTableauJoueur++) {
			
			if ( tableauJoueurs[iterateurTableauJoueur].getNombreVote() > nombreDeVoteMaximum ) {
				joueurATuer = iterateurTableauJoueur;
			}
		}
		
		if( joueurATuer != tableauJoueurs.length ) {
			joueurTueeDansLaNuit.add(joueurATuer);
			tableauJoueurs[joueurATuer].setVivant(false);
			nbVillageois-=1;
		}	
		
	}
	
	
	
	private boolean finDePartie() {
		return (nbVillageois == 0 || nbLoupGarou == 0);
	}
		
}
