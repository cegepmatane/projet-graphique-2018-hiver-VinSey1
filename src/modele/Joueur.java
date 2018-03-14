package modele;

public class Joueur {

	private String nom;
	/**
	 * 0 : villageois
	 * 1 : loup garou
	 */
	private int role;
	private boolean vivant = true;
	
	public Joueur(String nom, int role) {
		
		this.nom = nom;
		this.role = role;
	}

	public boolean isVivant() {
		return vivant;
	}

	public void setVivant(boolean vivant) {
		this.vivant = vivant;
	}

	public String getNom() {
		return nom;
	}
	
	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
}
