package serveur;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import modele.Partie;


public class Serveur{
	
	private BufferedReader lecture = null;
	private int nombreDeJoueurs = 0;
	public final int NB_JOUEURS_MAX = 3;
	private ContactJoueur[] tableauContactJoueur = new ContactJoueur[NB_JOUEURS_MAX];
	private Socket socket = null;
	private ServerSocket serveur;
	private volatile Partie partie;
	private List<String> listeJoueurs = new ArrayList<String>();


	@SuppressWarnings("resource")
	public void ecouter() {	     
		try {
			String message = "";
			serveur = new ServerSocket(11);
			while((socket = serveur.accept()) != null) {
				
				tableauContactJoueur[nombreDeJoueurs] = new ContactJoueur(socket, this);
				Thread thread = new Thread(tableauContactJoueur[nombreDeJoueurs]);
				nombreDeJoueurs++;
				listeJoueurs.add("Joueur "+nombreDeJoueurs);
				envoyerATous("<annonce>Un Joueur vient de rejoindre la partie<annonce>");
				envoyerATous("<annonce>Nombre de joueurs : "+nombreDeJoueurs+"/"+NB_JOUEURS_MAX+"</annonce>");
				thread.start();
				
				if(nombreDeJoueurs == NB_JOUEURS_MAX) {
					
					this.partie = new Partie(this);
					partie.lancerPartie();
				}

			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void envoyerATous(String message) {
		
		for ( int iterateurBoucle = 0 ; iterateurBoucle < nombreDeJoueurs ; iterateurBoucle++) {		

			tableauContactJoueur[iterateurBoucle].envoiMessage(message);			

		}
		
	}
	
	public void envoyerIndividuel(String message, int numJoueur) {
		tableauContactJoueur[numJoueur].envoiMessage(message);
	}
	
	public void fermerServeur() throws IOException {
		serveur.close();
	}
	
	
	public int getNB_JOUEURS_MAX() {
		return NB_JOUEURS_MAX;
	}
	
	public int getNombreDeJoueurs() {
		return nombreDeJoueurs;
	}
	
	public Partie getPartie() {
		return partie;
	}
	
	
	public List<String> getListeJoueurs() {
		return listeJoueurs;
	}

}