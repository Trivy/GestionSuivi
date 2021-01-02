package gestion.data.dao.hibernateDao;

import java.util.LinkedList;
import java.util.List;

import org.centenaire.dao.abstractDao.AbstractItemDao;
import org.centenaire.entity.Item;
import org.centenaire.entity.util.LabelEntity;

public class HibernateItemDao extends AbstractItemDao {

	public HibernateItemDao() {
		super();
	}

	@Override
	public List<Item> find(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean create(Item obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Item obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Item obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Item find(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Item> findAll() {
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
	public List<Item> findAll(int startIndex, int endIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
