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
	
	@SuppressWarnings("resource")
	private void connection() {
		try {
			connexion = new Socket(InetAddress.getLocalHost(), 11);
			sortie = new PrintWriter(connexion.getOutputStream(), true);
			lecture = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
