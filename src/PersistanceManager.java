import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class PersistanceManager {
	static Connection conn;

	//Constantes de connexion
	String url = "jdbc:postgresql://localhost:5432/Persistance";
	String user = "postgres";
	String passwd = "kisslovekuiss97";


	public PersistanceManager() throws ClassNotFoundException{
		try {

			Class.forName("org.postgresql.Driver");
			System.out.println("Driver O.K.");
			conn = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}




    public<T> List<T> retrieveSet(Class<T> c, String sql){
		
		//Arraylist contenant la liste des objets
		List<T> res = new ArrayList<T>();
		try {
			
			Statement state = conn.createStatement();

			ResultSet result = state.executeQuery(sql);
			// Avoir le nombre de colonne de chaque table

			while(result.next()){

				// création d'une nouvelle instance de bean
				T bean = c.newInstance();
				// récupération des champs de l'objet
				ArrayList<Field> fields = getFields(c);

				for(Field f: fields){
					
					//recherche du type des champs
					Class<?> fieldType = f.getType();

					// Si le bean est une liste, on vérifie que le premier élément soit un bean

					if(fieldType == List.class && getTypeOfList(f).getAnnotation(Table.class) != null){
						
						//récupération de l'annotation
						Table t = getTypeOfList(f).getAnnotation(Table.class);

						//récupération du nom de l'entité
						String tableName = t.name();
						
						Join j = f.getAnnotation(Join.class);
						String externalKey = j.eK();
						String internalKey = j.iK();
						
						String innerSql = "SELECT * FROM public.\"" + tableName + "\" WHERE " + externalKey + "="  + result.getObject(result.findColumn(internalKey)) ;
						//System.out.println(innerSql);

						try {
                            f.setAccessible(true);
							f.set(bean, retrieveSet(Class.forName(tableName), innerSql));
							
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}

					// Si c'est un bean
					else  if(fieldType.getAnnotation(Table.class) != null){
						
						Table t = fieldType.getAnnotation(Table.class);

						//récupération du nom de l'entité
						String tableName = t.name();
						
						Join j = f.getAnnotation(Join.class);
						String externalKey = j.eK();
						String internalKey = j.iK();
						String innerSql = "SELECT * FROM public.\"" + tableName + "\" WHERE " + externalKey + "="  + result.getObject(result.findColumn(internalKey)) ;
						
						try {
                            List list = retrieveSet(Class.forName(tableName), innerSql);
							f.setAccessible(true);
							f.set(bean, list.get(0));


							
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}						

					}	
					// Type de donnée simple (int, double, char, float, boolean)
					else{
						// récupération de l'objet dans le champ
						Object  o = result.getObject(result.findColumn(f.getName())); 
						f.setAccessible(true);

						// insertion de l'objet dans le bean
						f.set(bean, o );
					}

				}

				//Ajout de l'objet dans la liste
				res.add(bean);

			}
		} catch (SQLException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}     
		// retourne tous les objets de la liste
		return res;
	}

	private  Class<?> getTypeOfList(Field field){
		ParameterizedType pt = (ParameterizedType)field.getGenericType();
		return (Class<?>)pt.getActualTypeArguments()[0];
	}
	
	
	// Localisation des champs de la classe
	private <T> ArrayList<Field> getFields(Class<T> c) {

			Field[] fields = c.getDeclaredFields();

			ArrayList<Field> tab = new ArrayList();

		for(Field f: fields){
			// ajoute des éléments qui ne porte pas d'Annotation @DBExclude
				if(f.getAnnotation(DBExclude.class) == null){
					tab.add(f);
				}

			}
			return tab;
	}

}


