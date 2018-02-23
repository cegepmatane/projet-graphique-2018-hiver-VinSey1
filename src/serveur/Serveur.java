package serveur;
import java.io.*;
import java.net.*;


public class Serveur{
	
	BufferedReader lecture = null;
	private int nombreDeJoueur = 0;
	ContactJoueur[] tableauContactJoueur = new ContactJoueur[8];
	Socket socket = null;
	ServerSocket serveur;
	
	@SuppressWarnings("resource")
	public void ecouter() {	     
		try {
			String message = "";
			serveur = new ServerSocket(11);
			while((socket = serveur.accept()) != null) {
				
				
				
				tableauContactJoueur[nombreDeJoueur] = new ContactJoueur(socket, Serveur.this);
				Thread thread = new Thread(tableauContactJoueur[nombreDeJoueur]);
				nombreDeJoueur++;
				thread.start();
				
				
				
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void traiter(String message) {


		
		for ( int iterateurBoucle = 0 ; iterateurBoucle < nombreDeJoueur ; iterateurBoucle++) {
			

			
			tableauContactJoueur[iterateurBoucle].envoiMessage(message);			

		}
		
	}
	
	public void fermerServeur() throws IOException {
		serveur.close();
	}
}