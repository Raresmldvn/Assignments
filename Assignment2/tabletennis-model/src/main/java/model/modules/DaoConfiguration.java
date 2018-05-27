package model.modules;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import dal.factory.DaoFactory;
import dal.util.HibernateUtil;

public class DaoConfiguration {

	String result = "";
	InputStream inputStream;
	DaoFactory factory;
	public void setFactory() {
		
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			String source= prop.getProperty("source");
 
			if(source.equals("Hibernate"))
				factory = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE);
			else
				factory = DaoFactory.getInstance(DaoFactory.Type.JDBC);
			inputStream.close();
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			
		}
	}
	
	public DaoFactory getFactory() {
		
		return factory;
	}
	
	public static void closeHibernate() {
		
		HibernateUtil.getSessionFactory().close();
	}
	
}
