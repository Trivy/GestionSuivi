package gestion.compta;

import java.sql.Date;

import gestion.data.DataCenter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transactions")
public class Transaction {
	private int id_trans;
	private Date date;
	private Placement place;
	private Compte compte;
	private float coursUnit;
	private float addUC;
	private float dimUC;
	private float addEUR;
	private float dimEUR;
	
	public Transaction(
			Date date, 
			Placement place, 
			Compte compte, 
			float coursUnit, 
			float addUC, 
			float dimUC,
			float addEUR,
			float dimEUR){
		this.id_trans = 0;
		this.date = date;
		this.place = place;
		this.compte = compte;
		this.coursUnit = coursUnit;
		this.addUC = addUC;
		this.dimUC = dimUC;
		this.addEUR = addEUR;
		this.dimEUR = dimEUR;
	}
	
	@Id
	@Column(name="id_transac")
	public int getIdTrans(){
		return id_trans;
	}
	
	// setter pour id_trans : ne doit apparaître que dans TransDao ! (et ne doit pas être modifié après création)
	
	public void setIdTrans(int id_trans){
		this.id_trans = id_trans;
	}
	
	// Getters et setters autres :
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date){
		this.date = date;
	}

	public Placement getPlace() {
		return place;
	}

	public void setPlace(Placement place) {
		this.place = place;
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

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte){
		this.compte = compte;
	}
	
	public String toString(){
		return "Transaction "+this.getIdTrans()+", "+this.getPlace().toString()+" totUC ="+(addUC-dimUC)+" totEUR ="+(addEUR-dimEUR);
	}
	
	// entrée par défaut, initialisée à index = 0
	// avec comme placement, le placement courant
	public static Transaction defaultEntry(){
		Transaction transac = new Transaction(
				new Date(System.currentTimeMillis()),
				DataCenter.getInstance().getPlaceCourant(), 
				Compte.values()[0],
				new Float(0),
				new Float(0), 
				new Float(0), 
				new Float(0), 
				new Float(0));
		return transac;
	}
}
