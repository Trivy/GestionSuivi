package gestion.compta;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cours")
public class Cours {
	private int id_cours;
	private Placement place;
	private Date date;
	private float coursUnit;
	
	// initialisation de id_cours à 0
	// à renseigner dès que possible !
	public Cours(Date date, Placement place, float coursUnit) {
		super();
		this.id_cours = 0;
		this.date = date;
		this.place = place;
		this.coursUnit = coursUnit;
	}

	@Id
	@Column(name="id_cours")
	public int getIdCours() {
		return id_cours;
	}

	// setIdCours, à utiliser avec parcimonie...
	public void setIdCours(int id_cours) {
		this.id_cours=id_cours;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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
	
	public String toString(){
		String string = "Cours n°"+this.getIdCours()+", date = "+this.getDate().toString()
				+" , prix = "+this.getCoursUnit();
		return string;
	}
}
