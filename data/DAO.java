package gestion.data;

import java.sql.Connection;
import java.util.LinkedList;

public abstract class Dao<T>{
	protected static Connection conn;
	protected DataCenter dataCenter;
	
	public Dao(DataCenter dataCenter){
		this.dataCenter = dataCenter;
		this.conn=dataCenter.getConn();
	}

	abstract public boolean create(T obj);
	
	abstract public boolean update(T obj);
	
	abstract public boolean delete(T obj);
	
	abstract public T find(int index);
	
	// renvoie un élément pleinement initialisé
	abstract public T newElement();
	
	abstract public LinkedList<T> getData();

}
