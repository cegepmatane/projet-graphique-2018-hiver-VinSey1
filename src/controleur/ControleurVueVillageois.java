package controleur;

import vue.VueVillageois;

import java.io.IOException;
import java.io.StringReader;
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
		
		Platform.runLater(new Runnable() {			
			
			@Override
				public void run() {
													
				if (!vueVillageois.getChoixJoueur().equals("Pas de choix") && !vueVillageois.getChoixJoueur().equals("Ne rien voter")) {
										
					contactServeur.envoyerMessage("<message><vote>"+vueVillageois.getChoixJoueur()+"</vote></message>");
					
					contactServeur.getControleur().desactiverVote();
					vueVillageois.fermer();
				}
			}
		});
	}
	
	public void sauver() {			
		contactServeur.envoyerMessage("<message><sauve></sauve></message>");
		vueVillageois.getSauverJoueur().setVisible(false);
		
	}
		
	public void traiter(String message) {
	
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				
				List<String> listeJoueurs = new ArrayList<String>();
				
				try {
					DocumentBuilder lecteurXML = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					
					InputSource inputSource = new InputSource();
					inputSource.setCharacterStream(new StringReader(message));				
					Document doc = lecteurXML.parse(inputSource);
					
					NodeList contenuMessage = doc.getElementsByTagName("joueur");
					
					
					for (int iterateur=0; iterateur < contenuMessage.getLength();iterateur++) {
						
						Node nodeMessage = contenuMessage.item(iterateur);
						
						Element elementMessage = (Element) nodeMessage;
						
						listeJoueurs.add(elementMessage.getTextContent());
								
					}		
				}
				
				catch(org.xml.sax.SAXException e){
					e.printStackTrace();
					
				}
				catch (ParserConfigurationException e) {
					e.printStackTrace();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
				
				vueVillageois.setChoixJoueur(listeJoueurs);
				
			}
		});	
		
		
		
	}
	
	
	public void afficherVoteSorciere(String message) {
		
		
Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
								
				List<String> listeJoueurs = new ArrayList<String>();
				
				vueVillageois.getSauverJoueur().setVisible(true);
				
				try {
					DocumentBuilder lecteurXML = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					
					InputSource inputSource = new InputSource();
					inputSource.setCharacterStream(new StringReader(message));				
					Document doc = lecteurXML.parse(inputSource);
					
					NodeList contenuMessage = doc.getElementsByTagName("joueur");
					
					NodeList joueurDevore = doc.getElementsByTagName("joueurDevore");
					
					if( joueurDevore.getLength() != 0 ) {
						
						Node nodeMessage = joueurDevore.item(0);
						
						Element elementMessage = (Element) nodeMessage;
						
						vueVillageois.getSauverJoueur().setText("Sauver "+elementMessage.getTextContent());
					}
					
					for (int iterateur=0; iterateur < contenuMessage.getLength();iterateur++) {
						
						Node nodeMessage = contenuMessage.item(iterateur);
						
						Element elementMessage = (Element) nodeMessage;
						
						listeJoueurs.add(elementMessage.getTextContent());
								
					}		
					
				}
				
				catch(org.xml.sax.SAXException e){
					e.printStackTrace();					
				}
				catch (ParserConfigurationException e) {
					e.printStackTrace();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
				
				vueVillageois.setChoixJoueur(listeJoueurs);
				
			}
		});			
	}
}
