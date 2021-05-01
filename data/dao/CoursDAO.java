package gestion.data.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import gestion.compta.Cours;
import gestion.compta.Placement;
import gestion.data.Dao;
import gestion.data.DataCenter;

public class CoursDAO extends Dao<Cours> {
	
	public CoursDAO(DataCenter dataCenter){
		super(dataCenter);
	}

	/**
	 * Create new object 'Cours'
	 * 
	 * NB: no check for duplicate!
	 */
	 public boolean create(Cours obj){
		try{					
			String query="INSERT INTO cours(id_placement,cours_date,coursunit)";
			query+="VALUES(?,?,?)";
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setInt(1, obj.getPlace().getIndex());
			state.setDate(2, obj.getDate());
			state.setFloat(3, obj.getCoursUnit());
			int nb_rows = state.executeUpdate();
			System.out.println("CoursDAO : insertion de "+nb_rows+" ligne(s)");
			
			// MàJ de id_trans (qui doit être à 0 jusque là)
			ResultSet genKey = state.getGeneratedKeys();
			if (genKey.next()){
				obj.setIdCours(genKey.getInt(1));
				System.out.println("CoursDAO.create -- clée : "+genKey.getInt(1));
			};
			state.close();
			return (nb_rows !=0);
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans CoursDAO.create !",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	};
	
	public boolean update(Cours obj){
		try{
			String query="UPDATE cours SET "
					+ " id_placement = ?,"
					+ " cours_date = ?,"
					+ " coursunit = ?"
					+ " WHERE id_cours = ?";
			PreparedStatement state = conn.prepareStatement(query);
			state.setInt(1, obj.getPlace().getIndex());
			state.setDate(2, obj.getDate());
			state.setFloat(3, obj.getCoursUnit());
			state.setFloat(4, obj.getIdCours());
			int nb_rows = state.executeUpdate();
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans CoursDAO.update !",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	};
	
	public boolean delete(Cours obj){
		try{
			String query="DELETE FROM cours WHERE id_cours = ?";
			PreparedStatement state = conn.prepareStatement(query);
			state.setInt(1, obj.getIdCours());
			int nb_rows = state.executeUpdate();
			System.out.println("CoursDAO.delete : suppression de "+nb_rows+" ligne(s)");
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans CoursDAO.delete !",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	};
	
	public Cours find(int index){
		try{
			String query="SELECT id_placement,cours_date,coursunit "
					+ " FROM cours WHERE id_cours = ? ORDER BY id_cours ";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			ResultSet res = state.executeQuery();
			res.first();
			Placement place = DataCenter.getPlacementDAO().find(res.getInt("id_placement"));
			Cours cours = new Cours(
					res.getDate("cours_date"),
					place,
					res.getFloat("coursunit")
					);
			cours.setIdCours(index);
			state.close();
			return cours;
		} catch (SQLException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans CoursDAO.find !",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	};
	
	public Cours find(Date date, Placement placement){
		try{
			String query="SELECT id_cours, cours_date, coursunit "
					+ " FROM cours WHERE cours_date = ? AND id_placement = ? ORDER BY id_cours DESC ";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setDate(1, date);
			state.setInt(2, placement.getIndex());
			ResultSet res = state.executeQuery();
			if (!res.first()) {
				System.out.println("CoursDAO.find -- No result found! Returning null");
				return null;
			};
			Cours cours = new Cours(
					res.getDate("cours_date"),
					placement,
					res.getFloat("coursunit")
					);
			cours.setIdCours(res.getInt("id_cours"));
			state.close();
			return cours;
		} catch (SQLException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans CoursDAO.find !",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	};
	
	// renvoie un élément pleinement initialisé
	public Cours newElement(){
		Cours cours = new Cours(
				new Date(System.currentTimeMillis()),
				dataCenter.getPlaceCourant(), 
				new Float(15.0d)
				);
		this.create(cours);
		return cours;
	}
	
	public LinkedList<Cours> getData() {
		LinkedList<Cours> data = new LinkedList<Cours>();
		if (dataCenter.getPlaceCourant()==null){
			return data;
		}
		try{
			String query=" SELECT id_cours, id_placement, cours_date, coursunit "
					+ " FROM cours "
					+ " WHERE id_placement = ? ORDER BY cours_date DESC ; ";
			PreparedStatement state = conn.prepareStatement(query);
			state.setInt(1, dataCenter.getPlaceCourant().getIndex());
			ResultSet res = state.executeQuery();
			int i = 0;
			while(res.next()){
				i++;
				Cours cours = new Cours(
						res.getDate("cours_date"),
						dataCenter.getPlaceCourant(),
						res.getFloat("coursunit")
						);
				// update of id_ordre, according to the database.
				cours.setIdCours(res.getInt("id_cours"));
				data.add(cours);
			}
			System.out.println("CoursDAO.getData() : "+i+" ligne(s) trouvées.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans CoursDAO.getData() !",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public float dernierCours(Placement place){
		float dernierCours = 0f;
		try{
			String query="SELECT cours_date,coursunit "
					+ " FROM cours "
					+ " WHERE id_placement = ?"
					+ "	ORDER BY cours_date DESC "
					+ " LIMIT 1 ";
			PreparedStatement state = conn.prepareStatement(query);
			state.setInt(1, place.getIndex());
			ResultSet res = state.executeQuery();
			
			if (res.next()){
				dernierCours = res.getFloat("coursunit");
			} else {
				System.err.println("CoursDAO.dernierCours("+place.getName()+") -- pas de dernier cours !");
				dernierCours = 0f;
			}
			state.close();
			return dernierCours;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans CoursDAO.dernierCours("+place.getName()+") !",JOptionPane.ERROR_MESSAGE);
			return dernierCours;
		} catch (Exception e){
			e.printStackTrace();
			return dernierCours;
		}
	}
}
