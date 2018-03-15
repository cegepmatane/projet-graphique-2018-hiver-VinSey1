package controleur;

import java.io.IOException;
import java.io.StringReader;

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
import vue.VueRegles;
import vue.VueSalonDeJeu;
import vue.VueVillageois;

public class ControleurSalonDeJeu {

	ContactServeur contactServeur;
	VueSalonDeJeu vueSalonDeJeu;
	VueRegles vueRegles;
	
	
	public ControleurSalonDeJeu (VueSalonDeJeu vueSalonDeJeu, ContactServeur contactServeur, VueRegles vueRegles) {
		this.vueSalonDeJeu = vueSalonDeJeu;
		this.contactServeur = contactServeur;
		this.vueRegles = vueRegles;
		
	}
	
	public void traiter(String message) {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				vueSalonDeJeu.ajouterTexteAuChat(message);
				
			}
		});
		
		
		try {
			DocumentBuilder lecteurXML = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(message));
			
			Document doc = lecteurXML.parse(inputSource);
			NodeList contenuMessage = doc.getElementsByTagName("rafraichissement");
			Node nodeMessage = contenuMessage.item(0);
			
			Element elementMessage = (Element) nodeMessage;
			
			switch(nodeMessage.getFirstChild().getNodeName()) {
			
			
				case "descriptionRole":
					vueSalonDeJeu.setDescriptionRole(elementMessage.getTextContent());
					break;
				
				case "listeVivant":
					vueSalonDeJeu.modifierListeJoueurVivant(elementMessage.getTextContent());
					break;
				
				case "listeMort":
					vueSalonDeJeu.modifierListeJoueurMort(elementMessage.getTextContent());
					break;
				
				case "nombreJoueurs":
					vueSalonDeJeu.modifierNombreDeJoueur(elementMessage.getTextContent());
					break;
					
				case "nombreLoupsGarousRestant":
					vueSalonDeJeu.modifierIndicationNombreDeLoupGarouVivant(elementMessage.getTextContent());
					break;
				
				case "nombreInnocentsRestant":
					vueSalonDeJeu.modifierIndicationInnocentVivant(elementMessage.getTextContent());
					break;
			
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
		
	}
	
	public void demandeVote() {
		contactServeur.envoyerMessage("<demande>"
										+"</demande>");
	}

		
	
	public void afficherVoteVillageois() {
		
		Platform.runLater(new Runnable() {
		
			VueVillageois vueVillageois = new VueVillageois();
			
			ControleurVueVillageois controleurVueVillageois = new ControleurVueVillageois(vueVillageois, contactServeur);
			
			@Override
				public void run() {
													
					contactServeur.setControleurVueVillageois(controleurVueVillageois);
					
					try {
						vueVillageois.start(VueVillageois.instanceVueVillageois);
						contactServeur.setControleurVueVillageois(controleurVueVillageois);
						
						demandeVote();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		});
	}
	
	public void afficherRegles() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				vueRegles.setRetour(1);
				vueSalonDeJeu.hide();
				vueRegles.show();
						
			}
		});
	}
	
	public VueSalonDeJeu getSalonDeJeu() {
		return vueSalonDeJeu;
	}
}
