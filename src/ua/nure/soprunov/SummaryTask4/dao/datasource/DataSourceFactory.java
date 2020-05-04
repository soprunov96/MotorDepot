package ua.nure.soprunov.SummaryTask4.dao.datasource;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DataSourceFactory use to  get datasource from DB with or without JNDI
 * If use JNDI configure context.xml or server.xml
 *
 * @author Igor Soprunov
 *
 */

public abstract class DataSourceFactory {

    private final static Logger LOG = Logger.getLogger(DataSourceFactory.class);

    /**
     * Get dataSource from db with JNDI or without
     *
     * @param type  type of dataSource

     *
     * @return dataSource
     */

    public static DataSource getDataSource(DataSourceType type) {

        switch (type) {

            case MY_SQL_DATASOURCE:
                Context initContext;
                try {
//                    Properties props = new Properties();
//                    props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.camel.util.jndi.CamelInitialContextFactory");
//                    System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
//                            "org.apache.naming.java.javaURLContextFactory");
//                    System.setProperty(Context.URL_PKG_PREFIXES,
//                            "org.apache.naming");

//                    initContext = new InitialContext(props);

                    initContext = new InitialContext();

                    return (DataSource) initContext
                            .lookup("java:/comp/env/jdbc/MYDB");
                } catch (NamingException e) {
                    LOG.error("Cannot get JNDI DataSource", e);
                }
            case MY_SQL_DATASOURCE_WITH_OUT_JNDI:
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
                    dataSource
                            .setURL("jdbc:mysql://localhost:3306/mydb");
                    dataSource.setUser("root");
                    dataSource.setPassword("root");
                    dataSource.setServerTimezone("UTC");
                    return dataSource;
                } catch (ClassNotFoundException | SQLException e) {
                    LOG.error("Cannot get DataSource without JNDI", e);
                }

            default:
                throw new UnsupportedOperationException("No such DataSource: "
                        + type);
        }
    }
}
