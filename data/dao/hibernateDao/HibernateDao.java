package gestion.data.dao.hibernateDao;

import java.util.LinkedList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import gestion.compta.SuppObj;

public class HibernateDao<T extends SuppObj>{
	private SessionFactory sessionFactory;
	
	private final int classIndex;
	
	public HibernateDao(SessionFactory sessionFactory, int classIndex) {
		this.sessionFactory = sessionFactory;
		this.classIndex = classIndex;
	}

	public boolean create(T obj) {
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		session.save(obj);
		
		session.getTransaction().commit();
		session.close();
		
		return true;
	}

	
	public boolean update(T obj) {
	    Session session = sessionFactory.openSession();
	    session.beginTransaction();
	 
	    session.update(obj);
	 
	    session.getTransaction().commit();
	    session.close();
	    
	    return true;
	}

	
	public boolean delete(T obj) {
	    Session session = sessionFactory.openSession();
	    session.beginTransaction();
	 
	    session.delete(obj);
	 
	    session.getTransaction().commit();
	    session.close();
	    
		return true;
	}
	
	public T find(int index) {
	    Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    
	    T obj = (T) session.get(SuppObj.getSuppObjClass(classIndex), index);
	 
	    session.close();
		
		return obj;
	};
	
	// renvoie un élément pleinement initialisé
	public T newElement() {
		return null;
	};
	
	public LinkedList<T> getData(){
		return null;
	};

}
