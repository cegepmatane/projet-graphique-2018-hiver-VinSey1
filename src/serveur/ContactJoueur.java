package serveur;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.*;
import java.net.*;

public class ContactJoueur implements Runnable  {

	Socket connexion = null;
	BufferedReader lecteur = null;
	PrintWriter imprimante = null;


	private Serveur serveur = null;
	
	public ContactJoueur(Socket connexion, Serveur serveur)
	{
		

		this.serveur = serveur;
		this.connexion = connexion;
		try {
			this.imprimante = new PrintWriter(connexion.getOutputStream(), true);
			this.lecteur = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		if(lecteur == null) return;
		String message;
		try {			
			while((message = lecteur.readLine()) != null)
			{			
					//System.out.println("ContactJoueur reçoit: "+message);
					serveur.traiter(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
		
	public boolean envoiMessage(String message)
	{
		System.out.println("ContactJoueur envoie: "+message);
		imprimante.println(message);
		return true;
	}		
	
}