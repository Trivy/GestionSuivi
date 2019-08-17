package gestion.compta;

import javax.persistence.*;

@Entity
@Table(name="comptes")
@AttributeOverrides({
	@AttributeOverride(name="id", column=@Column(name="id_compte"))
})
public class Compte extends SuppObj{
	private String name;
	
	public Compte(String name){
		this.name = name;
		this.id = 0;
	}
	
	// collection de "getters"
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	// utilisé pour définir les combos !
	public String toString(){
		return this.getName();
	}
	
	// obtenir toute la collection des différents comptes dispos :
	public static Compte[] values(){
		String[] listeNoms ={"AV Epargn.","Retr. Epargn.","Oddo","Appart. Paturle","Cpt Titre BP"};
		int nbComptes = listeNoms.length;
		Compte[] vectComptes = new Compte[nbComptes];
		for (int i = 0; i< nbComptes; i++){
			vectComptes[i]=new Compte(listeNoms[i]);
			vectComptes[i].setId(i+1);
		}
		return vectComptes;
	}
	
	public static String[] getNames(){
		String[] output = new String[Compte.values().length];
		int index = 0;
		for (Compte compte:Compte.values()){
			output[index++]=compte.getName();
		}
		return output;
	}
	
	// Valeur par défaut de type, mis à jour par CompteDAO.create.
	public static Compte defaultEntry() {
		Compte compte = new Compte("Compte par défaut");
		return compte;
	}

}


