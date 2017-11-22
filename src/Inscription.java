

// Annotation sur la table inscription et la clé primaire
@Table(name = "Inscription", primaryKey = "inscriptionid")
public class Inscription {

	@Join(eK = "coursid", iK = "coursid" )
	private Cours cours;

	@DBExclude
	private Etudiant etudiant;
	
	public Inscription(){ }

	public Cours getCours() {
		return cours;
	}

	public void setCours(Cours cours) {
		this.cours = cours;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public String toString(){
		return "Inscription[" + "\t" +"cours=" + cours ;

	    }
     }
