package gestion.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import gestion.compta.Compte;
import gestion.compta.Cours;
import gestion.compta.Placement;
import gestion.data.dao.CompteDAO;
import gestion.data.dao.CoursDAO;
import gestion.data.dao.DaoException;
import gestion.data.dao.GestionTypesDAO;
import gestion.data.dao.OrdreDAO;
import gestion.data.dao.PlacementDAO;
import gestion.data.dao.SourceQuoteDAO;
import gestion.data.dao.TransacDAO;

// Classe qui centralise les informations (dont placement courant et comptes sélectionnés)
// Cette classe sert de "factory" pour les xxxDAO


public class DataCenter{
   	private static Connection conn;
   	
   	// Placement et comptes sélectionnés
   	protected static Placement placeCourant;
   	protected static Set<Integer> idComptesCourants;
   	
   	// Nbr d'ordres à exécuter :
   	protected static int nbrAchatsAExec = 0;
   	protected static int nbrVentesAExec = 0;
   	
   	// données pour la connexion
    //private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String url ;
    private String user ;
    private String passwd ;
	
	// implementation singleton par "initialisation immédiate".
	private static DataCenter instance = new DataCenter();
	
	// ActionListener sur BoutonCompte
	//private BoutonCompteListener boutonCompteListener = new DataCenter.BoutonCompteListener(); 
	
	private DataCenter(){
		DialogueConnection dialogueConn = new DialogueConnection();
		String[] infoConn = dialogueConn.showDialogueConnection();
		// Pour mémoire : infoConn = user, passwd, host, dataBase
		try {
	        Class.forName("org.postgresql.Driver");
	        url = "jdbc:postgresql://"+infoConn[2]+"/"+infoConn[3];
	        user = infoConn[0];
	        passwd = infoConn[1];
	        conn = DriverManager.getConnection(url, user, passwd);
	        // commits automatiques ou pas
	        conn.setAutoCommit(true);
		} catch (ClassNotFoundException e) {
	        e.printStackTrace();
		} catch (PSQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null , "Mot de passe erroné ?", "DataCenter -- PSQLException", JOptionPane.ERROR_MESSAGE );
			e.printStackTrace();
		} catch (SQLException e){
			e.printStackTrace();
		}
		// Initialisation des comptes sélectionnés
		this.idComptesCourants = new HashSet<Integer>();
	}
	
	public static DataCenter getInstance(){
		return instance;
	}
	
	public static TransacDAO getTransacDAO(){
		return new TransacDAO(instance);
	}
	
	public static OrdreDAO getOrdreDAO(){
		return new OrdreDAO(instance);
	}
	
	public static CompteDAO getCompteDAO(){
		return new CompteDAO(instance);
	}
	
	public static PlacementDAO getPlacementDAO(){
		return new PlacementDAO(instance);
	}
	
	public static GestionTypesDAO getGestionTypesDAO(){
		return new GestionTypesDAO(instance);
	}
	
	public static CoursDAO getCoursDAO(){
		return new CoursDAO(instance);
	}
	
	public static SourceQuoteDAO getSourceQuoteDAO() {
		return new SourceQuoteDAO(instance);
	}
	
	public void dernierCoursMaJ(){
		// pour prendre en compte les dernières modif. de source, en particulier.
		Placement place = this.getPlacementDAO().find(this.getPlaceCourant().getIndex());
		try {
			Cours cours = place.getSource().getCours(place);
			this.getCoursDAO().create(cours);
		} catch (DaoException e){
			System.err.println("Erreur dans DataCenter.dernierCoursMaJ()");
			System.err.println("dernierCoursMaJ() : pas de cours créé pour "+place.getName());
		}
	}
	
	public void dernierCoursMaJ(Placement place){
		// pas de nouvelle recherche du placement à partir de la base de données,
		// on suppose que le placement considéré est à jour !
		try {
			Cours cours = place.getSource().getCours(place);
			this.getCoursDAO().create(cours);
		} catch (DaoException e){
			System.err.println("Erreur dans DataCenter.dernierCoursMaJ()");
			System.err.println("dernierCoursMaJ() : pas de cours créé pour "+place.getName());
		}
	}
	
	public static void setNbrAchatsAExec(int nbrAchats){
		nbrAchatsAExec = nbrAchats;
	}
	
	public static void setNbrVentesAExec(int nbrVentes){
		nbrVentesAExec = nbrVentes;
	}
	
	public static int getNbrAchatsAExec(){
		return nbrAchatsAExec;
	}
	
	public static int getNbrVentesAExec(){
		return nbrVentesAExec;
	}


	public Object[] defaultLineOrdres() {
		// TODO Auto-generated method stub
		// Comme pour defaultLine, on code les comptes par leur indices (qui commence à 0, par opp. à id_compte, qui commence à 1)
		Object[] ligneDefault= {0, new Float(20.0d), new Float(2), new Float(0), new Float(40), new Float(0),"Nouvelle ligne", "-"};
		return null;
	}

	public float[] getDataPanSynthese(){
		// données pour PanSynthese (dans TableauTransaction), dans l'ordre :
		// totalUC (0), totalEUR (1) [total des EUR investis !], dernierCours (2)
		float[] vectData = {0f,0f,0f};
		
		if (placeCourant == null){
			return vectData;
		} else {
			vectData[0] = this.getTransacDAO().totalUC(placeCourant);
			vectData[1] = this.getTransacDAO().totalEUR(placeCourant);
			vectData[2] = this.getCoursDAO().dernierCours(placeCourant);
			return vectData;
		}
	}

	// données pour l'accueil :
	// montant (actualisé) par type.
	public Object[][] accueilData() {
		LinkedList<Object[]> data = new LinkedList<Object[]>();
		Object[] ligne = new Object[3];
		try{
			// SQL statement pour obtenir le total d'UC pour chaque placement
			String totalUC = "SELECT id_placement, SUM(adduc-dimuc) AS totaluc "
					+ " FROM transactions "
					+ " GROUP BY id_placement ";
			
			// SQL statement to get the latest quotations, with all columns
			String fullLatestCours = "SELECT id_cours, cours.id_placement, coursunit, cours_date "
					+ " FROM cours "
					+" JOIN "
					+" ( SELECT id_placement, MAX (cours_date) AS last_date "
					+" FROM cours GROUP BY id_placement ) AS last_cours "
					+" ON last_date = cours.cours_date AND last_cours.id_placement= cours.id_placement";
			
			// Total actualisé :
			String totalEUR = "SELECT quc.id_placement AS id_placement, totaluc * full_last_cours.coursunit AS totaleur "
					+ " FROM ( "+totalUC+" ) AS quc, ( "+fullLatestCours+" ) AS full_last_cours "
					+ " WHERE quc.id_placement = full_last_cours.id_placement ";
			
			// total par types :
			String queryTypes = "SELECT types.id_type, SUM(totaleur) AS totaltype "
					+ " FROM ("+totalEUR+") AS totaleurq "
					+ " , types "
					+ " , placements "
					+ " WHERE types.id_type = placements.id_type "
					+ " AND placements.id_placement = totaleurq.id_placement "
					+ " GROUP BY types.id_type ";
			
			// Final
			String query0 = "SELECT SUM(totaleur) AS totalorum "
					+ " FROM ( "+totalEUR+" ) AS quc ";
			
			String query1 = "SELECT types.name, totaltype "
					+ " FROM ( "+queryTypes+" ) AS querytypes "
					+ " , types "
					+ " WHERE types.id_type = querytypes.id_type ";
			
			PreparedStatement state0 = conn.prepareStatement(query0);
			ResultSet res0 = state0.executeQuery();
			
			PreparedStatement state1 = conn.prepareStatement(query1);
			ResultSet res1 = state1.executeQuery();
			
			res0.next();
			float totalorum = res0.getFloat("totalorum");
			
			int i = 0;
			while(res1.next()){
				ligne = new Object[3];
				ligne[0] = res1.getString("name");
				float totaltype = res1.getFloat("totaltype");
				ligne[1] = totaltype;
				ligne[2] = totaltype/totalorum; // l'affichage se charge de la multiplication par 100 !
				data.add(ligne);
				i+=1;
			}
			System.out.println("DataCenter.accueilData() : "+i+" ligne(s) trouvée(s).");
			res1.close();
			state1.close();
			Object[][] vectData = data.toArray(new Object[i][3]);
			return vectData;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	
	public void updateComptesCourants(Compte compte, Boolean isChecked){
		if (isChecked){
			idComptesCourants.add(compte.getIdCompte());
		} else {
			idComptesCourants.remove(compte.getIdCompte());
		}
		// liste des comptes courants
		System.out.println("=== Var. comptesCourant (id_compte) ===");
		String output = "[";
		for (int cpt : idComptesCourants){
			output =output+String.valueOf(cpt)+",";
		}
		System.out.println(output+"]");
	}
	
	/**
	 * Method used to update *placeCourant*
	 * @param place
	 */
	public void updatePlacement(Placement place){
		this.placeCourant = place;
		System.out.println("=== Var. placeCourant ===");
		System.out.println("Var. placeCourant = "+placeCourant);
	}
	
	public Connection getConn(){
		return this.conn;
	}
	
	public Placement getPlaceCourant(){
		return placeCourant;
	}
	
	public static Compte[] getComptesCourants(){
		Compte[] output = new Compte[idComptesCourants.size()];
		int i = 0;
		for (int cpt : idComptesCourants){
			output[i] = getCompteDAO().find(cpt);
			i++;
		}
		return output;
	}

	// trouve les ordres d'achats à effectuer, actualise nbrAchatsAExec 
	public Object[][] achatsAExecuter(){
		LinkedList<Object[]> data = new LinkedList<Object[]>();
		Object[] ligne = new Object[5];
		try{
			// SQL statement to get the latest quotations, with all columns
			String fullLatestCours = "SELECT id_cours, cours.id_placement, coursunit, cours_date FROM cours"
					+" JOIN "
					+" ( SELECT id_placement, MAX (cours_date) AS last_date "
					+" FROM cours GROUP BY id_placement ) AS last_cours "
					+" ON last_date = cours.cours_date AND last_cours.id_placement= cours.id_placement";
			
			// SQL statement obtenant les "ordres" de volume positif (= ordres d'achats)
			String achats = "SELECT * FROM "
					+ " (SELECT id_ordre, id_placement, coursunit, adduc - dimuc AS volume FROM ordres) AS subq "
					+ " WHERE volume >= 0";
			
			// full SQL statement:
			String query = "SELECT placements.name, volume, achats.coursunit, full_last_cours.coursunit, cours_date FROM"
					+ "("+fullLatestCours+") AS full_last_cours"
					+ ", ("+achats+") AS achats "
					+ ", placements "
					+ " WHERE full_last_cours.id_placement = achats.id_placement "
					+ " AND achats.id_placement = placements.id_placement "
					+ " AND achats.coursunit > full_last_cours.coursunit";
			
			PreparedStatement state = conn.prepareStatement(query);
			ResultSet res = state.executeQuery();
			
			int i = 0;
			while(res.next()){
				ligne = new Object[5];
				ligne[0] = res.getString("name");
				ligne[1] = res.getFloat("volume");
				// les deux suivants sont des cours unitaires, portant le même nom "coursunit"
				ligne[2] = res.getFloat(3);
				ligne[3] = res.getFloat(4);
				ligne[4] = res.getDate("cours_date");
				data.add(ligne);
				i+=1;
			}
			System.out.println("DataCenter.achatsAExecuter() : "+i+" ligne(s) trouvée(s).");
			DataCenter.setNbrAchatsAExec(i);
			res.close();
			state.close();
			Object[][] vectData = data.toArray(new Object[i][5]);
			return vectData;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	// trouve les ordres de ventes à effectuer, actualise nbrVentesAExec 
	public Object[][] ventesAExecuter(){
		LinkedList<Object[]> data = new LinkedList<Object[]>();
		Object[] ligne = new Object[5];
		try{
			// SQL statement to get the latest quotations, with all columns
			String fullLatestCours = "SELECT id_cours, cours.id_placement, coursunit, cours_date FROM cours"
					+" JOIN "
					+" ( SELECT id_placement, MAX (cours_date) AS last_date "
					+" FROM cours GROUP BY id_placement ) AS last_cours "
					+" ON last_date = cours.cours_date AND last_cours.id_placement= cours.id_placement";
			
			// SQL statement obtenant les "ordres" de volume négatif (= ordres de ventes)
			String ventes = "SELECT * FROM "
					+ " (SELECT id_ordre, id_placement, coursunit, adduc - dimuc AS volume FROM ordres) AS subq "
					+ " WHERE volume < 0";
			
			// full SQL statement:
			String query = "SELECT placements.name, volume, ventes.coursunit, full_last_cours.coursunit, cours_date FROM"
					+ "("+fullLatestCours+") AS full_last_cours"
					+ ", ("+ventes+") AS ventes "
					+ ", placements "
					+ " WHERE full_last_cours.id_placement = ventes.id_placement "
					+ " AND ventes.id_placement = placements.id_placement "
					+ " AND ventes.coursunit < full_last_cours.coursunit";
			
			PreparedStatement state = conn.prepareStatement(query);
			ResultSet res = state.executeQuery();
			
			int i = 0;
			while(res.next()){
				ligne = new Object[5];
				ligne[0] = res.getString("name");
				ligne[1] = -res.getFloat("volume"); // pour éviter les volumes négatifs
				// les deux suivants sont des cours unitaires, portant le même nom "coursunit"
				ligne[2] = res.getFloat(3);
				ligne[3] = res.getFloat(4);
				ligne[4] = res.getDate("cours_date");
				data.add(ligne);
				i+=1;
			}
			System.out.println("DataCenter.ventesAExecuter() : "+i+" ligne(s) trouvée(s).");
			DataCenter.setNbrVentesAExec(i);
			res.close();
			state.close();
			Object[][] vectData = data.toArray(new Object[i][5]);
			return vectData;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public float getRepartition(Placement place){
		float repartition;
		switch (place.getIndex()){
			case 3:
				repartition = (20/78f);
				break;
			case 6:
				repartition = (20/48f);
				break;
			default:
				repartition = 0.5f;				
		}
		return repartition;
	}
	
	// comptesCourants comme vecteurs d'entiers (=id_compte)
	public Object[] comptesCourantsArray(){
		int N = idComptesCourants.size();
		Object[] array = new Object[N];
		int i = 0;
		for (int id_compte : idComptesCourants){
			array[i] = id_compte;
			i++;
		}
		return array;
	}
	
	public void addComptesCourants(Compte compte){
		this.idComptesCourants.add(compte.getIdCompte());
	}
}
