package prototypeServeurClient;
import java.io.*;
import java.net.*;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.*;

public class Client extends Application {
		
	Socket connexion;
	PrintWriter sortie;
	BufferedReader lecture;
	Text texte;
	
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
	
	public static void main(String[] args){

		launch(args);
	}
	@SuppressWarnings("restriction")
	@Override
	public void start(Stage primaryStage) throws Exception {
		connection();
		recevoirMessage();
		BorderPane pane = new BorderPane();
		texte = new Text();
		Button b1 = new Button("1");
		Button b2 = new Button("2");
		
		b1.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {

				envoyerMessage("1");
			}
		});
		
		b2.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				envoyerMessage("2");
			}
		});
		
		pane.setTop(texte);
		pane.setLeft(b1);
		pane.setRight(b2);
		Scene scene = new Scene(pane, 500, 100);
		primaryStage.setScene(scene);
		primaryStage.show();


	}
	
	public void traiter(String message) throws IOException {

		if ( message.equals("1") || message.equals("2")) {
			modifierTexte("le message est "+message);
		}
		
		
	}
	
	private void envoyerMessage(String message) {

		sortie.println(message);
	}
	
	public void recevoirMessage() {
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				String message = "";
				try {
					while ((message = lecture.readLine()) != null) {
						traiter(message);
					}
				} catch(Exception e) {
				
				}
			}
		});
		thread.start();		
	}
	public Socket getSocket() {
		return connexion;
		
	}
	
	public void fermerConnexion() {
		sortie.close();
	}
	
	public void modifierTexte(String nouveauTexte) {
		
		texte.setText(nouveauTexte);
	}
}
