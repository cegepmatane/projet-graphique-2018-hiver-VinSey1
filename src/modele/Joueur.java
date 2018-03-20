package modele;

public class Joueur {

	private String nom;
	/**
	 * 0 : villageois
	 * 1 : loup garou
	 */
	private int role;
	private boolean vivant = true;
	private int nombreVote = 0;
	private int conjoint = -1;

	public Joueur(String nom) {
		this.nom = nom;
	}
	
	public Joueur(String nom, int role) {
		this(nom);
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
	
	public int getNombreVote() {
		return nombreVote;
	}

	public void setNombreVote(int nombreVote) {
		this.nombreVote = nombreVote;
	}
	
	public void reinitialiserNombreDeVote() {
		this.nombreVote = 0;
	}
	
	public int getConjoint() {
		return conjoint;
	}

	public void setConjoint(int conjoint) {
		this.conjoint = conjoint;
	}	
}
