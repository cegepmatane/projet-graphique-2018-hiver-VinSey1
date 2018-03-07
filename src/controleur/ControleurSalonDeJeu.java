package controleur;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
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
		
		if (message.equals("Demande vote du village")) {
			vueSalonDeJeu.ajouterTexteAuChat("A vous de voter villageois");
		}
		
		if (message.equals("Loup-garou tué")) {
			vueSalonDeJeu.ajouterTexteAuChat("Un loup-garou a été tué");
			vueSalonDeJeu.modifierIndicationNombreDeLoupGarouVivant(2);
			vueSalonDeJeu.modifierNombreDeJoueur(4);
		}
		
		if (message.equals("Villageois tué")) {
			vueSalonDeJeu.ajouterTexteAuChat("Un villageois a été tué");
			vueSalonDeJeu.modifierNombreDeJoueur(2);
		}
		
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
		
	}

		
	

	public void validerVote() {
		contactServeur.envoyerMessage("<message>"
										+ "<joueurs>"
											+ "<vote>"
												+ "<joueur>"
													+"ValBreton"
												+ "</joueur>"
											+ "</vote>"
										+ "</joueurs>"
									+ "</message>");
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
						
						contactServeur.envoyerMessage("<message>"
								+ "<demandeListe>"
								+ "villageois"
								+ "</demandeListe>"
								+ "</message>");
						
						
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
}
