package gestion.compta;

import javax.persistence.*;

@Entity
@Table(name="comptes")
@AttributeOverrides({
	@AttributeOverride(name="id", column=@Column(name="id_compte"))
})
public class Compte {
	private String name;
	private int id_compte;

	
	public Compte(String name){
		this.name = name;
		this.id_compte = 0;
	}
	
	// collection de "getters"
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setIdCompte(int id) {
		this.id_compte = id;
	}
	
	public int getIdCompte() {
		return this.id_compte;
	}
	
	// utilis� pour d�finir les combos !
	public String toString(){
		return this.getName();
	}
	
	// obtenir toute la collection des diff�rents comptes dispos :
	public static Compte[] values(){
		String[] listeNoms ={"AV Epargn.","Retr. Epargn.","Oddo","Appart. Paturle","Cpt Titre BP"};
		int nbComptes = listeNoms.length;
		Compte[] vectComptes = new Compte[nbComptes];
		for (int i = 0; i< nbComptes; i++){
			vectComptes[i]=new Compte(listeNoms[i]);
			vectComptes[i].setIdCompte(i+1);
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
	
	// Valeur par d�faut de type, mis � jour par CompteDAO.create.
	public static Compte defaultEntry() {
		Compte compte = new Compte("Compte par d�faut");
		return compte;
	}

}


