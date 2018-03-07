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
		
		contactServeur.envoyerMessage("<message>"
										+ "<joueurs>"
											+ "<vote>"
												+ "<joueur>"
													+ vueVillageois.getChoixJoueur()
												+ "</joueur>"
											+ "</vote>"
										+ "</joueurs>"
									+ "</message>");
	}
		
	public void traiter(String message) {
				
		List<String> listeJoueurs = new ArrayList<String>();
		
		listeJoueurs.add("Eliott");
		listeJoueurs.add("Vincente");
		listeJoueurs.add("Valentin");

		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				vueVillageois.setChoixJoueur(listeJoueurs);
				
			}
		});	
		
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
	
}
