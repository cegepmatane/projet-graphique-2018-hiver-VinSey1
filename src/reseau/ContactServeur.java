package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
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
										
					if ( message.equals("test liste joueur")) {
						

						
						controleurVueVillageois.traiter("rcgr");
					}
					
					if ( message.equals("vote villageois")) {
						
						controleurVueVillageois.traiter(message);
					}
					else {
							
						controleurSalonDeJeu.traiter(message);
						
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

	public void setControleur(ControleurSalonDeJeu controleur) {
		this.controleurSalonDeJeu = controleur;
	}
	
	public boolean envoyerMessage(String message) {
		sortie.println(message);
		return true;
	}

}
