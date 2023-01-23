package cw.kazi.cst3130;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.util.List;

/*
 * Model class that configures the connection with SQL
 */
public class HibernateConnections {

	private SessionFactory sessionFactory;

	/*
	 * @Param Object of type universityC that maps to table in database
	 */
	public int saveUniversity(universityC uni){
		
        //Get a new Session instance from the session factory
        Session session = sessionFactory.getCurrentSession();

        //Start transaction
        session.beginTransaction();
        
        String queryStr = "from univ where name='" + uni.getName() + "' and location='" + uni.getLoation() + "'";
        List<universityC> uniList = session.createQuery(queryStr).getResultList();
        
        if(uniList.size() == 0)
        {
        	//Add University to database - will not be stored until we commit the transaction
            int uni_id = Integer.parseInt(session.save(uni).toString());

            //Commit transaction to save it to database
            session.getTransaction().commit();

            //Close the session and release database connection
            session.close();
            return uni_id;
        }
        else
        {
        	session.close();
        	return uniList.get(0).getId();
        }
        
    }
	/*
	 * @Param Object of type DegreesC that maps to table in database
	 */
	public int saveDegree(DegreesC degree){
		
		//Get a new Session instance from the session factory
        Session session = sessionFactory.getCurrentSession();

        //Start transaction
        session.beginTransaction();

        //Add Cereal to database - will not be stored until we commit the transaction
        int deg_id = Integer.parseInt(session.save(degree).toString());

        //Commit transaction to save it to database
        session.getTransaction().commit();

        //Close the session and release database connection
        session.close();
        return deg_id;
    }
	
	/*
	 * @Param Object of type db_comp that maps to table in database
	 */
	public void savedb_comp(db_comp dbcomp){
		
        //Get a new Session instance from the session factory
        Session session = sessionFactory.getCurrentSession();

        //Start transaction
        session.beginTransaction();

        //Add Cereal to database - will not be stored until we commit the transaction
        session.save(dbcomp);

        //Commit transaction to save it to database
        session.getTransaction().commit();

        //Close the session and release database connection
        session.close();
    }
	
	/*
	 * Setup the standard service registry builder and create connection Pools
	 * @throws trouble building session factory
	 * @throws failed to create session factory
	 */
	public void init(){
        try {
            //Create a builder for the standard service registry
            StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();

            //Load configuration from hibernate configuration file.
            //Here we are using a configuration file that specifies Java annotations.
            standardServiceRegistryBuilder.configure("hibernate.cfg.xml");

            //Create the registry that will be used to build the session factory
            StandardServiceRegistry registry = standardServiceRegistryBuilder.build();
            try {
                //Create the session factory - this is the goal of the init method.
                sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            }
            catch (Exception e) {
                    /* The registry would be destroyed by the SessionFactory,
                        but we had trouble building the SessionFactory, so destroy it manually */
                System.err.println("Session Factory build failed.");
                e.printStackTrace();
                StandardServiceRegistryBuilder.destroy( registry );
            }

            //Output result
            System.out.println("Session factory built.");

        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("SessionFactory creation failed." + ex);
        }
    }

}
