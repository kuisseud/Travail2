
import java.util.ArrayList;
import java.util.List;

//Annotation sur la table 
@Table( name = "Etudiant", primaryKey ="etudiantid")
public class Etudiant{
	

	private Long etudiantid;
	private String fname;
	private String iname;
	private int age;

	// Annotation de jointure d clé primaire
	@Join(eK = "etudiantid", iK = "etudiantid")
	private List<Inscription> inscriptions;
	
	// Constructors
	public Etudiant(){}
	
	public List<Inscription> getInscription() {
		return inscriptions;
	}

	public void setInscription(ArrayList<Inscription> inscription) {
		this.inscriptions = inscription;
	}
		
	/*
	 * Getters and setters
	 */

	
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getIname() {
		return iname;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
	public long getEtudiantid(){
		return etudiantid;
	}
	
	public void setEtudiantid(Long etudiantid) {
		this.etudiantid = etudiantid;
	}

	/*
	 * Red�finition de la methode toString
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){


		return "Etudiant" +"\t" + "[etudiantid =" + etudiantid + "\t" +
				"fname = " + fname + "\t" +
				"iname = " + iname + "\t" +
				"age = " +age +  "]";
	}
	

	
}
