package gestion.data.dao.hibernateDao;

import java.util.LinkedList;
import java.util.List;

import org.centenaire.dao.Dao;
import org.centenaire.entity.Entity;
import org.centenaire.entity.util.LabelEntity;
import org.hibernate.Session;

public class HibernateDao extends Dao<Entity> {
	Session session;

	@Override
	public boolean create(Entity obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Entity obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Entity obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Entity find(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Entity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LabelEntity> findAllLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Entity> findAll(int startIndex, int endIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
