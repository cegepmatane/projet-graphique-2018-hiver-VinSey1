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
		
		try {
			DocumentBuilder lecteurXML = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(message));
			
			Document doc = lecteurXML.parse(inputSource);
			NodeList contenuMessage = doc.getElementsByTagName("rafraichissement");
			Node nodeMessage = contenuMessage.item(0);
			
			Element elementMessage = (Element) nodeMessage;

			System.out.println("ControleurSalonDeJeu reçoit:"+message);
			
			switch(nodeMessage.getFirstChild().getNodeName()) {
			
				case "descriptionRole":
					vueSalonDeJeu.setDescriptionRole(elementMessage.getTextContent());
					break;
				
				case "listeVivants":
					
					modifierListeJoueurVivant(message);
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
					
				case "role":
					modifierRole(elementMessage.getTextContent());
					break;
					
				case "activerVote":
					activerVote();
					break;

				case "desactiverVote":
					desactiverVote();
					break;
				case "nomJoueur":
					modifierNomJoueur(elementMessage.getTextContent());
					break;
					
				default:
					System.out.println("Message non traité : ControleurSalonDeJeu");
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
		contactServeur.envoyerMessage("<message><demande></demande></message>");
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
						vueVillageois.setControleur(controleurVueVillageois);
						contactServeur.setControleurVueVillageois(controleurVueVillageois);
						
						demandeVote();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		});
	}
	
	public void test() {
	Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				vueSalonDeJeu.modifierListeJoueurVivant("Test");
			}
		});		
	}
	
	public void modifierListeJoueurVivant(String message) {
		Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					
					try {
												
						String listeJoueurVivant = "";
						DocumentBuilder lecteurXML;
						lecteurXML = DocumentBuilderFactory.newInstance().newDocumentBuilder();

						
						InputSource inputSource = new InputSource();
						inputSource.setCharacterStream(new StringReader(message));
						
						Document doc = lecteurXML.parse(inputSource);
						
						NodeList contenuMessage = doc.getElementsByTagName("joueur");
												
						for (int iterateur=0; iterateur < contenuMessage.getLength();iterateur++) {
														
							Node nodeMessage = contenuMessage.item(iterateur);
							
							Element elementMessage = (Element) nodeMessage;
							
							listeJoueurVivant+= elementMessage.getTextContent()+"\n";						
						}
						vueSalonDeJeu.modifierListeJoueurVivant(listeJoueurVivant);
						
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
			});	
	}
	
	public void modifierListeJoueurMort(String message) {
		Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					
					try {
												
						String listeJoueurMort = "";
						DocumentBuilder lecteurXML;
						lecteurXML = DocumentBuilderFactory.newInstance().newDocumentBuilder();

						
						InputSource inputSource = new InputSource();
						inputSource.setCharacterStream(new StringReader(message));
						
						Document doc = lecteurXML.parse(inputSource);
						
						NodeList contenuMessage = doc.getElementsByTagName("joueur");
												
						for (int iterateur=0; iterateur < contenuMessage.getLength();iterateur++) {
														
							Node nodeMessage = contenuMessage.item(iterateur);
							
							Element elementMessage = (Element) nodeMessage;
							
							listeJoueurMort+= elementMessage.getTextContent()+"\n";						
						}
						vueSalonDeJeu.modifierListeJoueurMort(listeJoueurMort);
						
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
	
	public void ajouterTexteAuChat(String message) {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				vueSalonDeJeu.ajouterTexteAuChat(message);			
			}
		});		
	}
	
	public void modifierRole(String role) {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				vueSalonDeJeu.setNomRole(role);
			}
		});		
	}
	
	public void activerVote() {		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				vueSalonDeJeu.getBoutonVoter().setDisable(false);
			}
		});	
	}
	
	public void desactiverVote() {		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				vueSalonDeJeu.getBoutonVoter().setDisable(true);
			}
		});	
	}
	
	public void modifierNomJoueur(String nomJoueur) {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {	
				vueSalonDeJeu.setPseudo(nomJoueur);
			}
		});	
	}
	
	public VueSalonDeJeu getSalonDeJeu() {
		return vueSalonDeJeu;
	}
	
	
	
}
