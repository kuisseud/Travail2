import java.util.List;

public class Test {


    public static void main(String[] args){

        try {

            PersistanceManager pm = new PersistanceManager();

        //Requete SQL qui sélectionne les inscription des étudiants dont etudiantid = 1
            List<Etudiant> et = pm.retrieveSet(Etudiant.class, "SELECT * FROM public.\"Etudiant\" WHERE etudiantid = 1" );

            for (Etudiant e: et) {
                System.out.println("\n" + e + "\n");

                for (Inscription i:e.getInscription()) {
                    System.out.println("\n\t" + i + "\n");

                }

            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
