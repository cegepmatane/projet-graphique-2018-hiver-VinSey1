package serveur;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import modele.Partie;


public class Serveur{
	
	private BufferedReader lecture = null;
	private int nombreDeJoueurs = 0;
	public final int NB_JOUEURS_MAX = 4;
	private ContactJoueur[] tableauContactJoueur = new ContactJoueur[NB_JOUEURS_MAX];
	private Socket socket = null;
	private ServerSocket serveur;
	private volatile Partie partie;
	private List<String> listeJoueurs = new ArrayList<String>();


	@SuppressWarnings("resource")
	public void ecouter() {	     
		try {
			String message = "";
			serveur = new ServerSocket(11);
			while((socket = serveur.accept()) != null) {
									
					tableauContactJoueur[nombreDeJoueurs] = new ContactJoueur(socket, this);
					Thread thread = new Thread(tableauContactJoueur[nombreDeJoueurs]);
					nombreDeJoueurs++;
					thread.start();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void envoyerATous(String message) {
		
		for ( int iterateurBoucle = 0 ; iterateurBoucle < nombreDeJoueurs ; iterateurBoucle++) {		

			tableauContactJoueur[iterateurBoucle].envoiMessage(message);			

		}
		
	}
	
	public void traiter(String message) {
		
		try {
			
			//System.out.println("Serveur: "+message);
			
			DocumentBuilder lecteurXML = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(message));
			
			Document doc = lecteurXML.parse(inputSource);
			NodeList contenuMessage = doc.getElementsByTagName("message");
			Node nodeMessage = contenuMessage.item(0);
			Element elementMessage = (Element) nodeMessage;
						
			switch(nodeMessage.getFirstChild().getNodeName()) {
			
				case "connexion":
					
					ajoutJoueur(elementMessage.getTextContent());
					envoyerATous("<message><rafraichissement><nombreJoueurs>"+listeJoueurs.size()+"/"+NB_JOUEURS_MAX+"</nombreJoueurs></rafraichissement></message>");

					break;

				default:
					
					if ( partie != null ) partie.traiter(message);
					
					break;
					
			}
		}
		 catch (org.xml.sax.SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}	
	}
		
	private void ajoutJoueur(String nomJoueur) {
		
		if ( listeJoueurs.size() < NB_JOUEURS_MAX ) {
			listeJoueurs.add(nomJoueur);
			envoyerATous("<message><annonce>"+nomJoueur+" vient de rejoindre la partie</annonce></message>");
			envoyerIndividuel("<message><rafraichissement><nomJoueur>"+nomJoueur+"</nomJoueur></rafraichissement></message>", listeJoueurs.size()-1);
			
			if(listeJoueurs.size() == NB_JOUEURS_MAX) {			
				this.partie = new Partie(this);
				partie.lancerPartie();
			}
			
			
		}
		else {
			envoyerIndividuel( "<message><annonce>La partie est déjà complète</message></annonce>"  , tableauContactJoueur.length-1);
		}
	}
	
	public void envoyerIndividuel(String message, int numJoueur) {
		tableauContactJoueur[numJoueur].envoiMessage(message);
	}
	
	public void fermerServeur() throws IOException {
		serveur.close();
	}
	
	
	public int getNB_JOUEURS_MAX() {
		return NB_JOUEURS_MAX;
	}
	
	public int getNombreDeJoueurs() {
		return nombreDeJoueurs;
	}
	
	public Partie getPartie() {
		return partie;
	}
	
	
	public List<String> getListeJoueurs() {
		return listeJoueurs;
	}

}