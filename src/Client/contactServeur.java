package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class contactServeur {
	
	Socket connexion;
	PrintWriter sortie;
	BufferedReader lecture;
	ControleurClient controleur;
	
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
					controleur.traiter(message);
				}
			} catch(Exception e) {}
		}
	});
	thread.start();
	}

	public ControleurClient getControleur() {
		return controleur;
	}

	public void setControleur(ControleurClient controleur) {
		this.controleur = controleur;
	}
	
	public void envoyerMessage(String message) {
		
	}

}
