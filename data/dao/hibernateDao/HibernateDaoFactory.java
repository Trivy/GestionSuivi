package gestion.data.dao.hibernateDao;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import gestion.compta.Compte;
import gestion.compta.Cours;
import gestion.compta.Ordre;
import gestion.compta.Placement;
import gestion.compta.SourceQuote;
import gestion.compta.Transaction;
import gestion.data.Dao;

public class HibernateDaoFactory {
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

	public Dao<Compte> getCompteDao() {
		// TODO Auto-generated method stub
		return null;
	}

	public Dao<Cours> getCoursDao() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Dao<Ordre> getOrdreDao(){
		return null;
	}
	
	public Dao<Placement> getPlacementDao(){
		return null;
	}
	
	public Dao<SourceQuote> getSourceQuoteDao(){
		return null;
	}
	
	public static Dao<Transaction> getTransacDAO(){
		return null;
	}

}
