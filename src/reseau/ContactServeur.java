package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import controleur.*;


public class ContactServeur {
	
	Socket connexion;
	PrintWriter sortie;
	BufferedReader lecture;
	String pseudo;
	
	ControleurSalonDeJeu controleurSalonDeJeu;
	ControleurVueVillageois controleurVueVillageois;
	ControleurVueLoupGarou controleurVueLoupGarou;
	
	@SuppressWarnings("resource")
	public void connection() {
		try {
			connexion = new Socket(InetAddress.getLocalHost(), 11);
			sortie = new PrintWriter(connexion.getOutputStream(), true);
			lecture = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
			attendreMessage();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void attendreMessage() {
	Thread thread = new Thread(new Runnable() {			
		@Override
		public void run() {
			String message = "";
			try {
				while ((message = lecture.readLine()) != null) {
												
					DocumentBuilder lecteurXML = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					
					InputSource inputSource = new InputSource();
					inputSource.setCharacterStream(new StringReader(message));
					
					Document doc = lecteurXML.parse(inputSource);
					NodeList contenuMessage = doc.getElementsByTagName("message");
					Node nodeMessage = contenuMessage.item(0);
					Element elementMessage = (Element) nodeMessage;
					
					switch (nodeMessage.getFirstChild().getNodeName()) {					

					case "liste":						
						controleurVueVillageois.traiter(message);
						break;
						
					case "annonce":
						controleurSalonDeJeu.ajouterTexteAuChat(elementMessage.getTextContent());
						break;
						
					case "rafraichissement":
						controleurSalonDeJeu.traiter(message);
						break;
						
					default:
						System.out.println("Message non traité : ContactServeur");
						break;
					}					
					
				}
			} catch(Exception e) {}
		}
	});
	thread.start();
	}

	public ControleurSalonDeJeu getControleur() {
		return controleurSalonDeJeu;
	}

	public void setControleurSalonDeJeu(ControleurSalonDeJeu controleur) {
		this.controleurSalonDeJeu = controleur;
	}
	
	public void setControleurVueVillageois(ControleurVueVillageois controleur) {
		this.controleurVueVillageois = controleur;
	}
	
	public boolean envoyerMessage(String message) {
		sortie.println(message);
		return true;
	}

}
