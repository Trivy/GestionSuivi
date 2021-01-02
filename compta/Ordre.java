package gestion.compta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ordres")
public class Ordre {
	private int id_ordre;
	private Placement place;
	private Compte compte;
	private float coursUnit;
	private float addUC;
	private float dimUC;
	private float addEUR;
	private float dimEUR;
	private String note;
	
	public Ordre(Placement place, Compte compte, float coursUnit, float addUC, float dimUC, float addEUR,
			float dimEUR, String note) {
		super();
		this.id_ordre = 0;
		this.place = place;
		this.compte = compte;
		this.coursUnit = coursUnit;
		this.addUC = addUC;
		this.dimUC = dimUC;
		this.addEUR = addEUR;
		this.dimEUR = dimEUR;
		this.note = note;
	}
	
	@Id
	@Column(name="id_ordre")
	public int getIdOrdre(){
		return id_ordre;
	}
	
	// NB : setter à utiliser avec parcimonie !
	public void setIdOrdre(int id_ordre){
		this.id_ordre=id_ordre;
	}

	public Placement getPlace() {
		return place;
	}

	public void setPlace(Placement place) {
		this.place = place;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public float getCoursUnit() {
		return coursUnit;
	}

	public void setCoursUnit(float coursUnit) {
		this.coursUnit = coursUnit;
	}

	public float getAddUC() {
		return addUC;
	}

	public void setAddUC(float addUC) {
		this.addUC = addUC;
	}

	public float getDimUC() {
		return dimUC;
	}

	public void setDimUC(float dimUC) {
		this.dimUC = dimUC;
	}

	public float getAddEUR() {
		return addEUR;
	}

	public void setAddEUR(float addEUR) {
		this.addEUR = addEUR;
	}

	public float getDimEUR() {
		return dimEUR;
	}

	public void setDimEUR(float dimEUR) {
		this.dimEUR = dimEUR;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}	

}
