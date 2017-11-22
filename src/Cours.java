
import java.util.List;

// Annotation sur la table et la cl� primaire sur cours  Cours

@Table (name ="Cours", primaryKey = "coursid")
public class Cours {


	private String name;
	private String sigle;
	private String description;
	private Long coursid;

	// Annotation ignore pour ignorer
	@DBExclude
	private List<Inscription> inscription;
	
	//Constructor
	public Cours(){}
	
	
	public List<Inscription> getInscription() {
		return inscription;
	}

	public void setInscription(List<Inscription> inscription) {
		this.inscription = inscription;
	}

	public long getCoursid() {
		return coursid;
	}

	public void setCoursid(Long coursid) {
		this.coursid = coursid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSigle() {
		return sigle;
	}

	public void setSigle(String sigle) {
		this.sigle = sigle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	/*
	 *R�d�finition de la methode toString 
	 *(non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return "["+coursid +"\t" + name + "\t" + sigle + "\t" + description+ "]";
	}
	
}
