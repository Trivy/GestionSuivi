package gestion.compta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="types")
public class GestionType {
	private String name;
	private int id_type;
	
	public GestionType(int index, String name){
		this.id_type=index;
		this.name = name;
	}
	
	@Id
	@Column(name="id_type")
	public int getIdType(){
		return id_type;
	}
	
	public String toString(){
		return name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name =name;
	}
	
	public void setIdType(int id_type){
		this.id_type =id_type;
	}
	
	public static GestionType[] values(){
		String[] namesTypes = {"ETF","OPCVM","Obligations","Immo"};
		GestionType[] vectTypes = new GestionType[namesTypes.length];
		int i = 0;
		for (String name:namesTypes){
			vectTypes[i]=new GestionType(i,name);
			i++;
		}
		return vectTypes;
	}

	// Valeur par défaut de type, mis à jour par GestionTypesDAO.create.
	public static GestionType defaultEntry() {
		GestionType type = new GestionType(0, "Type par défaut");
		return null;
	}
}
