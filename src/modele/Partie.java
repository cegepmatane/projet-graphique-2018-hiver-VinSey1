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

import com.sun.corba.se.spi.ior.Identifiable;

import jdk.internal.org.xml.sax.SAXException;
import serveur.Serveur;

public class Partie {

	private Random aleatoire = new Random();
	private int nbMaxLoupGarou;
	private int nbMaxVillageois;
	private int nbMaxChasseur;
	private int nbMaxSorcière;
	
	/**
	 *  0 : innocents 
	 *  1 : loup garou
	 */
	private int[] nbJoueurVivantParCamp = new int[2];
	private final String[] numeroRoles = {"Villageois", "Loup-Garou", "Chasseur", "Sorcière", "Cupidon"};
	private Serveur serveur;
	private Joueur[] tableauJoueurs;
	private boolean finPartie = false;
	private boolean nuitEnCours = true;
	private boolean jourEnCours = false;
	private List<Integer> joueurTueeParVote;
	private boolean egalite = false;
	private List<Integer> cartes = new ArrayList<Integer>();
	private List<Integer> joueurEmpoisonne = new ArrayList<Integer>();
	private boolean sorciereASauve = false;
	private boolean sorciereAEmmpoisonne = false;
	private boolean tourCupidonPasse = false;
	
	/**
	 * 0 -> aucun
	 * 1 -> loup garou
	 * 2 -> villageois
	 * 3 -> chasseur
	 * 4 -> sorcière
	 * 5 -> cupidon
	 */
	private int tourDeJeu = 0;
	
	public Partie(Serveur serveur) {
		this.serveur=serveur;
		
		joueurTueeParVote = new ArrayList<>();
		
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
				nbMaxVillageois = 1;
				
				nbJoueurVivantParCamp[0] = serveur.NB_JOUEURS_MAX - nbMaxLoupGarou;
				nbJoueurVivantParCamp[1] = nbMaxLoupGarou;
				
				for ( int iterateurCarte = 0; iterateurCarte < nbMaxVillageois ; iterateurCarte++ ) {
					cartes.add(0);
				}
				
				for ( int iterateurCarte = 0; iterateurCarte < nbMaxLoupGarou ; iterateurCarte++ ) {
					cartes.add(1);
				}
				
				cartes.add(2);		
				//cartes.add(3);
				cartes.add(4);
				
				
				serveur.envoyerATous("<message><rafraichissement><nombreJoueurs>"+tableauJoueurs.length+"/"+serveur.NB_JOUEURS_MAX+"</nombreJoueurs></rafraichissement></message>");

				retournerMorts();
				retournerVivants();

				
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				distribuerCartes();
				envoyerMessage("<message><rafraichissement><nombreLoupsGarousRestant>"+nbJoueurVivantParCamp[1]+"</nombreLoupsGarousRestant></rafraichissement></message>");
				envoyerMessage("<message><rafraichissement><nombreInnocentsRestant>"+nbJoueurVivantParCamp[0]+"</nombreInnocentsRestant></rafraichissement></message>");
				
				try {
					TimeUnit.SECONDS.sleep(4);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				while(!finDePartie()) {
					deroulementNuit();
					deroulementJour();
				}				
			}
		});	


		threadPartie.start();
	}
	

	private void deroulementNuit() {
		
		envoyerMessage("<message><annonce>La nuit tombe sur le village de thiercelieux, vous fermez les yeux en espérant passer la nuit</annonce></message>");
		
		if ( !tourCupidonPasse ) tourCupidon();
		
		tourLoupGarou();
		
		if ( sorciereAEmmpoisonne != true && sorciereASauve != true) tourSorciere();
		
	}
	
	private void deroulementJour() {
			
			if ( joueurTueeParVote.size() == 0  && joueurEmpoisonne.size() == 0) {
				envoyerMessage("<message><annonce>Le jour se lève, personne n'a été tué, les villageois peuvent voter pour désigner une personne à éliminer</annonce></message>");
			}
			else{
					envoyerMessage("<message><annonce>Le jour se lève</annonce></message>");
					if ( joueurTueeParVote.size() == 1 ) {
						
						envoyerMessage("<message><annonce>"+tableauJoueurs[joueurTueeParVote.get(0)].getNom()+" a été tué, il était"+numeroRoles[tableauJoueurs[joueurTueeParVote.get(0)].getRole()]+"</annonce></message>");
						nbJoueurVivantParCamp[0]-=1;

						int indexJoueurMort = joueurTueeParVote.get(0);
						reinitialiserVote();
						if ( finDePartie() ) return;
						if (tableauJoueurs[indexJoueurMort].getRole() == 2 ) {										
							tourChasseur();						
						}
						if (tableauJoueurs[indexJoueurMort].getConjoint() != -1 ) {										
							faireMourirConjoint(indexJoueurMort);						
						}
						
					}
					if ( joueurEmpoisonne.size() == 1) {
						
						envoyerMessage("<message><annonce>"+tableauJoueurs[joueurEmpoisonne.get(0)].getNom()+" a été tué, il était"+numeroRoles[tableauJoueurs[joueurEmpoisonne.get(0)].getRole()]+"</annonce></message>");

						if ( tableauJoueurs[joueurEmpoisonne.get(0)].getRole() == 1 ) {
							nbJoueurVivantParCamp[1] -= 1;
						}
						else {
							nbJoueurVivantParCamp[0] -=1;
						}

						int indexJoueurMort = joueurEmpoisonne.get(0);
						reinitialiserEmpoissonnement();
						if ( finDePartie() ) return;
						if (tableauJoueurs[indexJoueurMort].getRole() == 2 ) {										
							tourChasseur();						
						}
						if (tableauJoueurs[indexJoueurMort].getConjoint() != -1 ) {										
							faireMourirConjoint(indexJoueurMort);						
						}					
					}		
					
					envoyerMessage("<message><rafraichissement><nombreLoupsGarousRestant>"+nbJoueurVivantParCamp[1]+"</nombreLoupsGarousRestant></rafraichissement></message>");
					envoyerMessage("<message><rafraichissement><nombreInnocentsRestant>"+nbJoueurVivantParCamp[0]+"</nombreInnocentsRestant></rafraichissement></message>");
					retournerMorts();
					retournerVivants();
			}
				
			envoyerMessage("<message><annonce>Les villageois peuvent voter pour désigner une personne à éliminer</annonce></message>");
			
			tourDeJeu = 2;
			activerVoteVillageois();
			try {
				TimeUnit.SECONDS.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tourDeJeu = 0;
			
			envoyerMessage("<message><annonce>Les villageois ont fait leur choix</annonce></message>");
			finDeVote();
			desactiverVoteVillageois();
				
			if ( joueurTueeParVote.size() !=0 && egalite == false) {
				
				envoyerMessage("<message><annonce>"+tableauJoueurs[joueurTueeParVote.get(0)].getNom()+" a été tué, il était"+numeroRoles[tableauJoueurs[joueurTueeParVote.get(0)].getRole()] +"</annonce></message>");

				if ( tableauJoueurs[joueurTueeParVote.get(0)].getRole() == 1 ) {
					nbJoueurVivantParCamp[1] -= 1;
				}
				else {
					nbJoueurVivantParCamp[0] -=1;
				}
				envoyerMessage("<message><rafraichissement><nombreLoupsGarousRestant>"+nbJoueurVivantParCamp[1]+"</nombreLoupsGarousRestant></rafraichissement></message>");
				envoyerMessage("<message><rafraichissement><nombreInnocentsRestant>"+nbJoueurVivantParCamp[0]+"</nombreInnocentsRestant></rafraichissement></message>");
				retournerMorts();
				retournerVivants();
				
				int indexJoueurMort = joueurTueeParVote.get(0);
				reinitialiserVote();
				if (tableauJoueurs[indexJoueurMort].getRole() == 2 ) {										
					tourChasseur();						
				}			
			}
			else if (egalite == true ) {
				
				envoyerMessage("<message><annonce>Il y a eu une égalité lors des votes, personne de sera lynché</annonce></message>");
				reinitialiserVote();
			}		
			else {			
				envoyerMessage("<message><annonce>Personne n'a été lynché par la population</annonce></message>");
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
			role = aleatoire.nextInt(cartes.size());						
			tableauJoueurs[iterateur].setRole(cartes.get(role));
			cartes.remove(role);
			serveur.envoyerIndividuel("<message><annonce>Tu es "+numeroRoles[tableauJoueurs[iterateur].getRole()]+" </annonce></message>", iterateur);
			serveur.envoyerIndividuel("<message><rafraichissement><role>"+numeroRoles[tableauJoueurs[iterateur].getRole()]+"</role></rafraichissement></message>", iterateur);			
		}
	}
		
	public void traiter(String message) {
		
		try {
			
			//System.out.println("Partie reçoit: "+message);
			
			DocumentBuilder lecteurXML = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(message));
			
			Document doc = lecteurXML.parse(inputSource);
			NodeList contenuMessage = doc.getElementsByTagName("message");
			Node nodeMessage = contenuMessage.item(0);
			Element elementMessage = (Element) nodeMessage;
						
			switch(nodeMessage.getFirstChild().getNodeName()) {
				
				case "demande":
										
					NodeList joueur = doc.getElementsByTagName("joueur");
					nodeMessage = contenuMessage.item(0);
					elementMessage = (Element) nodeMessage;
					envoyerListeJoueur(elementMessage.getTextContent()); 

					break;
				
				case "vote":
										
					traiterVote(message);
					
					break;
					
				case "chat":
					
					gererChat(message);
					
					break;
				
				case "sauve":
					
					sauverJoueur();
					
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
	
	
	public void envoyerListeJoueur(String joueur) {
					
		switch( tourDeJeu ) {
		
		case 1:
			
			envoyerListeJoueurTourLoupGarou(joueur);
			
			break;
			
		case 2:
			
			envoyerListeVillageois(joueur);
							
			break;
			
		case 3:
			
			envoyerListeVillageois(joueur);
			break;
			
		case 4:
			
			envoyerListeSorciere(joueur);
			
			break;
		case 5:
			envoyerListeCupidon(joueur);
			break;
			
		default:
			break;
			
		}	
	}
	
	private void envoyerListeCupidon(String joueur) {
		
		int indexJoueur = -1;
		
		for (int iterateurJoueur = 0; iterateurJoueur < tableauJoueurs.length ; iterateurJoueur++) {
			
			if ( tableauJoueurs[iterateurJoueur].getNom().equals(joueur) ) {			
				indexJoueur = iterateurJoueur;		
			}
		}
		
		String messageAEnvoyer = "<message><listeCupidon>";
		
		for ( int iterateurTableauJoueur = 0; iterateurTableauJoueur< tableauJoueurs.length ; iterateurTableauJoueur++) {						
			messageAEnvoyer +="<joueur>"+tableauJoueurs[iterateurTableauJoueur].getNom()+"</joueur>";					
		}
		
		messageAEnvoyer += "</listeCupidon></message>";
		serveur.envoyerIndividuel(messageAEnvoyer, indexJoueur);
		
	}
	
	private void envoyerListeJoueurTourLoupGarou(String joueur) {
		
		int indexJoueur = -1;
		for (int iterateurJoueur = 0; iterateurJoueur < tableauJoueurs.length ; iterateurJoueur++) {
			
			if ( tableauJoueurs[iterateurJoueur].getNom().equals(joueur) ) {
				
				indexJoueur = iterateurJoueur;
				
			}
		}
		
		String messageAEnvoyer = "<message><liste>";
		
		for ( int iterateurTableauJoueur = 0; iterateurTableauJoueur< tableauJoueurs.length ; iterateurTableauJoueur++) {						
			if ( tableauJoueurs[iterateurTableauJoueur].getRole() != 1 && tableauJoueurs[iterateurTableauJoueur].isVivant()) {
				messageAEnvoyer +="<joueur>"+tableauJoueurs[iterateurTableauJoueur].getNom()+"</joueur>";
			}						
		}
		messageAEnvoyer += "</liste></message>";				
		serveur.envoyerIndividuel(messageAEnvoyer, indexJoueur);
		
	}
	
	
	private void envoyerListeVillageois(String joueur) {
		
		int indexJoueur = -1;
		for (int iterateurJoueur = 0; iterateurJoueur < tableauJoueurs.length ; iterateurJoueur++) {
			
			if ( tableauJoueurs[iterateurJoueur].getNom().equals(joueur) ) {
				
				indexJoueur = iterateurJoueur;
				
			}
		}
		String messageAEnvoyer = "<message><liste>";
		
		for ( int iterateurTableauJoueur = 0; iterateurTableauJoueur< tableauJoueurs.length ; iterateurTableauJoueur++) {						
			if ( tableauJoueurs[iterateurTableauJoueur].isVivant() && !tableauJoueurs[iterateurTableauJoueur].getNom().equals(joueur)) {
				messageAEnvoyer +="<joueur>"+tableauJoueurs[iterateurTableauJoueur].getNom()+"</joueur>";
			}						
		}
		messageAEnvoyer += "</liste></message>";
		serveur.envoyerIndividuel(messageAEnvoyer, indexJoueur);
			
	}
	
	private void envoyerListeSorciere(String joueur) {
		
		int indexJoueur = -1;
		for (int iterateurJoueur = 0; iterateurJoueur < tableauJoueurs.length ; iterateurJoueur++) {
			
			if ( tableauJoueurs[iterateurJoueur].getNom().equals(joueur) ) {
				
				indexJoueur = iterateurJoueur;
				
			}
		}
		
		String messageAEnvoyer = "<message><listeSorciere>";
		
		if ( joueurTueeParVote.size() != 0 && sorciereASauve == false) {
		
			messageAEnvoyer += "<joueurDevore>"+tableauJoueurs[joueurTueeParVote.get(0)].getNom()+"</joueurDevore>";
			
		}	
		
		for ( int iterateurTableauJoueur = 0; iterateurTableauJoueur< tableauJoueurs.length ; iterateurTableauJoueur++) {						
			if ( tableauJoueurs[iterateurTableauJoueur].isVivant() && !tableauJoueurs[iterateurTableauJoueur].getNom().equals(joueur) && sorciereAEmmpoisonne == false) {
				messageAEnvoyer +="<joueur>"+tableauJoueurs[iterateurTableauJoueur].getNom()+"</joueur>";
			}						
		}	
		messageAEnvoyer += "</listeSorciere></message>";
		serveur.envoyerIndividuel(messageAEnvoyer, indexJoueur);

		
	}
	
	private void faireMourirConjoint(int indexJoueur) {
		
		envoyerMessage(tableauJoueurs[tableauJoueurs[indexJoueur].getConjoint()].getNom()+" était son conjoint, il le suit donc dans la tombe et meurt");
		
		tableauJoueurs[tableauJoueurs[indexJoueur].getConjoint()].setVivant(false);
		
		if (tableauJoueurs[tableauJoueurs[indexJoueur].getConjoint()].getRole() == 2 ) {										
			tourChasseur();						
		}	
	}
	
	private void sauverJoueur() {

		tableauJoueurs[joueurTueeParVote.get(0)].setVivant(true);
		joueurTueeParVote.remove(0);
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
			
			switch (tourDeJeu) {
			
				case 4:
				
					empoisonnerJoueur(nomDuJoueurVote);
					
					break;
						
				case 5:
					
					lierJoueur(message);
					
					break;
					
					
				default:
										
					ajouterVoteAuJoueur(nomDuJoueurVote);
					
					break;		
			}

		
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (org.xml.sax.SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	private void lierJoueur(String message) {
		
		try {
			DocumentBuilder lecteurXML;
			lecteurXML = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(message));
			Document doc = lecteurXML.parse(inputSource);
			
			String joueur1 = doc.getElementsByTagName("joueur").item(0).getTextContent();
			String joueur2 = doc.getElementsByTagName("joueur").item(1).getTextContent();

			int indexJoueur1 = -1;
			int indexJoueur2 = -1;
			
			for ( int iterateurTableauJoueur = 0 ; iterateurTableauJoueur < tableauJoueurs.length ; iterateurTableauJoueur++) {
				
				if ( tableauJoueurs[iterateurTableauJoueur].getNom().equals(joueur1) ) {
					indexJoueur1 = iterateurTableauJoueur;
				}
				if ( tableauJoueurs[iterateurTableauJoueur].getNom().equals(joueur2) ) {
					indexJoueur2 = iterateurTableauJoueur;
				}
				
			}	 
			
			tableauJoueurs[indexJoueur1].setConjoint(indexJoueur2);
			tableauJoueurs[indexJoueur2].setConjoint(indexJoueur1);
			
		} catch (ParserConfigurationException | org.xml.sax.SAXException | IOException e) {
			e.printStackTrace();
		}

		
		
		
		
		
	}
	
	private  void ajouterVoteAuJoueur(String joueur) {
				
		for ( int iterateurTableauJoueur = 0 ; iterateurTableauJoueur < tableauJoueurs.length ; iterateurTableauJoueur++) {
			
			if ( tableauJoueurs[iterateurTableauJoueur].getNom().equals(joueur) ) {
				tableauJoueurs[iterateurTableauJoueur].setNombreVote(tableauJoueurs[iterateurTableauJoueur].getNombreVote()+1);
			}
			
		}	
	}
	
	private void empoisonnerJoueur(String joueur) {
				
		for ( int iterateurTableauJoueur = 0 ; iterateurTableauJoueur < tableauJoueurs.length ; iterateurTableauJoueur++) {
			
			if ( tableauJoueurs[iterateurTableauJoueur].getNom().equals(joueur) ) {
				joueurEmpoisonne.add(iterateurTableauJoueur);
				tableauJoueurs[iterateurTableauJoueur].setVivant(false);
			}
		}
	}
	
	private void finDeVote() {
		
		int joueurATuer = tableauJoueurs.length;
		int nombreDeVoteMaximum = 0;
		
		for ( int iterateurTableauJoueur = 0; iterateurTableauJoueur < tableauJoueurs.length ; iterateurTableauJoueur++) {
			
			if ( tableauJoueurs[iterateurTableauJoueur].getNombreVote() == nombreDeVoteMaximum && nombreDeVoteMaximum != 0) egalite = true;
						
			if ( tableauJoueurs[iterateurTableauJoueur].getNombreVote() > nombreDeVoteMaximum ) {
				joueurATuer = iterateurTableauJoueur;
				nombreDeVoteMaximum = tableauJoueurs[iterateurTableauJoueur].getNombreVote();
			}
		}
		
		if( joueurATuer != tableauJoueurs.length && egalite == false ) {
			joueurTueeParVote.add(joueurATuer);
			tableauJoueurs[joueurATuer].setVivant(false);
			
			if (tableauJoueurs[joueurATuer].getConjoint() != -1 ) {										
				faireMourirConjoint(joueurATuer);						
			}	
		}	
	}
	
	private boolean finDePartie() {
		
		if ( (nbJoueurVivantParCamp[0] == 0 || nbJoueurVivantParCamp[1] == 0) ) {
			if ( nbJoueurVivantParCamp[0] == 0 ) {
				envoyerMessage("<message><annonce>Les loups garous ont gagné, la partie est terminé</annonce></message>");
			}
			else {
				envoyerMessage("<message><annonce>Les villageois ont gagné, la partie est terminé</annonce></message>");
			}
			return true;
		}
		
		return false;
	}
		

	private void reinitialiserVote() {
		joueurTueeParVote.clear();		
		for ( int iterateurTableauJoueur = 0; iterateurTableauJoueur < tableauJoueurs.length ; iterateurTableauJoueur++) {
			tableauJoueurs[iterateurTableauJoueur].reinitialiserNombreDeVote();
		}
		egalite = false;
	}
	
	private void reinitialiserEmpoissonnement() {
		
		joueurEmpoisonne.remove(0);
	}
	
	private void activerVoteVillageois() {
			
		for ( int iterateurTableauJoueur = 0; iterateurTableauJoueur < tableauJoueurs.length ; iterateurTableauJoueur++) {
			if ( tableauJoueurs[iterateurTableauJoueur].isVivant() ) serveur.envoyerIndividuel("<message><rafraichissement><activerVote></activerVote></rafraichissement></message>", iterateurTableauJoueur);
		}
	}
	
	private void desactiverVoteVillageois() {
		
		envoyerMessage("<message><rafraichissement><desactiverVote></desactiverVote></rafraichissement></message>");
		
	}
	
	private void gererChat(String message) {
				
		try {
					
			DocumentBuilder lecteurXML;

			lecteurXML = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(message));
			
			Document doc = lecteurXML.parse(inputSource);
			NodeList contenuMessage = doc.getElementsByTagName("texte");
			Node nodeMessage = contenuMessage.item(0);
			Element elementMessage = (Element) nodeMessage;
			
			NodeList emmetteur = doc.getElementsByTagName("joueur");
			Node nodeEmmetteur = emmetteur.item(0);
			Element elementEmmetteur = (Element) nodeEmmetteur;
			
			String messageAEnvoyer = elementEmmetteur.getTextContent()+": "+elementMessage.getTextContent();
			
			for ( int iterateurJoueur = 0; iterateurJoueur < tableauJoueurs.length ; iterateurJoueur++) {
					
				if ( tableauJoueurs[iterateurJoueur].isVivant() && tableauJoueurs[iterateurJoueur].getNom().equals(elementEmmetteur.getTextContent() )){
					envoyerMessage("<message><chat>"+messageAEnvoyer+"</chat></message>");
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
	
	
	public void tourChasseur() {
		
		int indexChasseur= -1;
		
		for ( int iterateurJoueur = 0 ; iterateurJoueur < tableauJoueurs.length ; iterateurJoueur++) {
			
			if ( tableauJoueurs[iterateurJoueur].getRole() == 2 ) indexChasseur = iterateurJoueur;
		}
		envoyerMessage("<message><annonce>Le chasseur peut désigner quelqu'un a tuer</annonce></message>");
		serveur.envoyerIndividuel("<message><annonce>Votre role de chasseur vous donne le droit de tuer quelqu'un</annonce></message>", indexChasseur);
		serveur.envoyerIndividuel("<message><rafraichissement><activerVote></activerVote></rafraichissement></message>", indexChasseur);
		tourDeJeu = 3;
		try {
			TimeUnit.SECONDS.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		tourDeJeu = 0;
		serveur.envoyerIndividuel("<message><rafraichissement><desactiverVote></desactiverVote></rafraichissement></message>", indexChasseur);

		finDeVote();
		
		envoyerMessage("<message><annonce>"+tableauJoueurs[joueurTueeParVote.get(0)].getNom()+" a été tué par le chasseur, il était "+numeroRoles[tableauJoueurs[joueurTueeParVote.get(0)].getRole()]+"</annonce></message>");
		if ( tableauJoueurs[joueurTueeParVote.get(0)].getRole() == 1 ) {
			nbJoueurVivantParCamp[1] -= 1;
		}
		else {
			nbJoueurVivantParCamp[0] -=1;
		}
		retournerVivants();
		retournerMorts();
		
		envoyerMessage("<message><rafraichissement><nombreLoupsGarousRestant>"+nbJoueurVivantParCamp[1]+"</nombreLoupsGarousRestant></rafraichissement></message>");
		envoyerMessage("<message><rafraichissement><nombreInnocentsRestant>"+nbJoueurVivantParCamp[0]+"</nombreInnocentsRestant></rafraichissement></message>");
		
		
	}
	
	private void tourLoupGarou() {
		
		tourDeJeu = 1;
		envoyerMessage("<message><annonce>Les loups-garous se réveillent et choisissent quelqun à dévorer</annonce></message>");
		//activez le vote pour les loups-garou
		
		for (int iterateur = 0; iterateur<tableauJoueurs.length; iterateur++) {
			if(tableauJoueurs[iterateur].getRole() == 1) {
				serveur.envoyerIndividuel("<message><annonce>C'est à ton tour de jouer. En tant que Loup-Garou, tu dois choisir une cible à manger cette nuit en cliquant sur \"voter\"</annonce></message>", iterateur);
				serveur.envoyerIndividuel("<message><rafraichissement><activerVote></activerVote></rafraichissement></message>", iterateur);
			}
		}
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int iterateur = 0; iterateur<tableauJoueurs.length; iterateur++) {
			if(tableauJoueurs[iterateur].getRole() == 1) {
				serveur.envoyerIndividuel("<message><rafraichissement><desactiverVote></desactiverVote></rafraichissement></message>", iterateur);
			}
		}
		envoyerMessage("<message><annonce>Les loups-garous ont fait leur choix et se rendorment</annonce></message>");
		//désactivez le vote pour les loups-garou
		tourDeJeu = 0;
		
		finDeVote();
	}
	
	
	private void tourSorciere() {
		tourDeJeu = 4;
		
		
		envoyerMessage("<message><annonce>La sorcière se réveille...</annonce></message>");

		for (int iterateur = 0; iterateur<tableauJoueurs.length; iterateur++) {
			
			if(tableauJoueurs[iterateur].getRole() == 3) {
				serveur.envoyerIndividuel("<message><annonce>C'est à ton tour de jouer</annonce></message>", iterateur);
				if ( joueurTueeParVote.size() != 0 && sorciereASauve == false) serveur.envoyerIndividuel("<message><annonce>Tu peux décider de sauver"+tableauJoueurs[joueurTueeParVote.get(0)].getNom()+" qui sera dévoré par les loups </annonce></message>", iterateur);
				if ( sorciereAEmmpoisonne == false ) serveur.envoyerIndividuel("<message><annonce>Tu peux choisir d'empoisonner quelqu'un</annonce></message>", iterateur);
				serveur.envoyerIndividuel("<message><rafraichissement><activerVote></activerVote></rafraichissement></message>", iterateur);
			}
		}
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int iterateur = 0; iterateur<tableauJoueurs.length; iterateur++) {
			if(tableauJoueurs[iterateur].getRole() == 3) {
				serveur.envoyerIndividuel("<message><rafraichissement><desactiverVote></desactiverVote></rafraichissement></message>", iterateur);
			}
		}
		

		tourDeJeu = 0;
	}
	
	private void tourCupidon() {
		tourDeJeu = 5;
		envoyerMessage("<message><annonce>Cupidon se réveille...</annonce></message>");

		for (int iterateur = 0; iterateur<tableauJoueurs.length; iterateur++) {
			
			if(tableauJoueurs[iterateur].getRole() == 4) {
				
				serveur.envoyerIndividuel("<message><annonce>C'est à ton tour de jouer, choisit deux joueurs pour les liers jusque dans la mort</annonce></message>", iterateur);
				serveur.envoyerIndividuel("<message><rafraichissement><activerVote></activerVote></rafraichissement></message>", iterateur);
			}
		}		
		try {
			TimeUnit.SECONDS.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		envoyerMessage("<message><annonce>Cupidon a lié deux personnnes</annonce></message>");
		tourDeJeu = 0;
		tourCupidonPasse = true;
	}
}
