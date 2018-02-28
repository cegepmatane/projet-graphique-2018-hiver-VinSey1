package serveur;
import java.io.*;
import java.net.*;

import modele.Partie;


public class Serveur{
	
	BufferedReader lecture = null;
	private int nombreDeJoueurs = 0;
	private final int NB_JOUEURS_MAX = 3;
	ContactJoueur[] tableauContactJoueur = new ContactJoueur[NB_JOUEURS_MAX];
	Socket socket = null;
	ServerSocket serveur;
	
	@SuppressWarnings("resource")
	public void ecouter() {	     
		try {
			String message = "";
			serveur = new ServerSocket(11);
			while((socket = serveur.accept()) != null) {
			
				tableauContactJoueur[nombreDeJoueurs] = new ContactJoueur(socket, Serveur.this);
				Thread thread = new Thread(tableauContactJoueur[nombreDeJoueurs]);
				nombreDeJoueurs++;
				traiter("MessageNBJoueurs : "+nombreDeJoueurs+"/"+NB_JOUEURS_MAX);
				if(nombreDeJoueurs == tableauContactJoueur.length) {
					Partie partie = new Partie(Serveur.this);
				}
				thread.start();
								
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void traiter(String message) {
		
		for ( int iterateurBoucle = 0 ; iterateurBoucle < nombreDeJoueurs ; iterateurBoucle++) {		

			tableauContactJoueur[iterateurBoucle].envoiMessage(message);			

		}
		
	}
	
	public void fermerServeur() throws IOException {
		serveur.close();
	}
}