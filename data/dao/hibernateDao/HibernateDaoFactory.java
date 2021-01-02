package gestion.data.dao.hibernateDao;

import java.util.List;
import java.util.Properties;

import org.centenaire.dao.Dao;
import org.centenaire.dao.abstractDao.AbstractDaoFactory;
import org.centenaire.dao.abstractDao.AbstractIndividualDao;
import org.centenaire.dao.abstractDao.AbstractInstitutionDao;
import org.centenaire.dao.abstractDao.AbstractItemDao;
import org.centenaire.dao.abstractDao.AbstractLocationDao;
import org.centenaire.entity.Event;
import org.centenaire.entity.taglike.Country;
import org.centenaire.entity.taglike.Departement;
import org.centenaire.entity.taglike.Discipline;
import org.centenaire.entity.taglike.InstitStatus;
import org.centenaire.entity.taglike.InstitutionType;
import org.centenaire.entity.taglike.LocalType;
import org.centenaire.entity.taglike.Tag;
import org.centenaire.entity.taglike.TaxChrono;
import org.centenaire.entity.taglike.TaxGeo;
import org.centenaire.entity.taglike.TaxTheme;
import org.centenaire.entity.typelike.CatEnum;
import org.centenaire.entity.typelike.EventType;
import org.centenaire.entity.typelike.ItemType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

public class HibernateDaoFactory extends AbstractDaoFactory {
	// Try using a single 'session' for all transactions
	// NB: according to Hibernate documentation: "the Hibernate Session wraps a JDBC java.sql.Connection"
	private Session session;

	public HibernateDaoFactory(String[] infoConn) {
		// Extract connection information
        String url = "jdbc:postgresql://"+infoConn[2]+"/"+infoConn[3];
        String user = infoConn[0];
        String passwd = infoConn[1];

		// Create new configuration
		Configuration config = new Configuration();
		
		// Hibernate settings equivalent to properties in hibernate.cfg.xml
		Properties settings = new Properties();
		
		// No comments!
		settings.put(Environment.DRIVER, "org.postgresql.Driver");
		settings.put(Environment.URL, url);
		settings.put(Environment.USER, user);
		settings.put(Environment.PASS, passwd);
		settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
		
		settings.put(Environment.SHOW_SQL, "true");
		
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.applySettings(config.getProperties()) // configures settings from hibernate.cfg.xml
				.build();
		try {
			SessionFactory sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
			
			// Get a session for 'sessionFactory'
			session = sessionFactory.openSession();
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy( registry );
		}
	}

	@Override
	public AbstractIndividualDao getIndividualDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractItemDao getItemDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractItemDao getItemDao(List<CatEnum> categories) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dao<Event> getEventDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractInstitutionDao getInstitutionDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractLocationDao getLocationDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dao<ItemType> getItemTypeDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dao<EventType> getEventTypeDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dao<InstitutionType> getInstitTypeDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dao<Tag> getTagDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dao<Discipline> getDisciplineDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dao<InstitStatus> getInstitStatusDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dao<LocalType> getLocalTypeDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dao<TaxChrono> getTaxChronoDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dao<TaxGeo> getTaxGeoDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dao<TaxTheme> getTaxThemeDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dao<Departement> getDeptDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dao<Country> getCountryDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
