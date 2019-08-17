package gestion.compta;

import javax.persistence.*;

@Entity
@Table(name="supp_obj")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class SuppObj {
	
	@Id
	@Column(name="id")
	protected int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public static Class<?> getSuppObjClass(int classIndex){
		switch (classIndex) {
			case 0:
				return SuppObj.class;
			case 1:
				return Compte.class;
			default:
				return SuppObj.class;
		}
	}
}
