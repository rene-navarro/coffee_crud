package mx.unison.coffees.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author Rene Navarro
 */
public class Database {

    private static final String CLASS_NAME = Database.class.getSimpleName();
    private static final Logger LOG = Logger.getLogger(CLASS_NAME);

    private Connection con;

     //Driver de JDBC que vamos a usar para conectarnos a la base de datos
    private static final String DRIVER = "org.postgresql.Driver";

   // Objeto singleton
    private static Database DB = null;

    private Properties configuration;

    // Constructor privado para implementar Singleton
    private Database() {

        super();
        con = null;

        configuration = new Properties();

        String dbUrl = null;
        String user = "nobody";
        String password = "nobody";
        String driver = null;
        try {
            configuration.load(new FileInputStream("coffee_crud.properties"));
            LOG.info("Properties file loaded.");
            dbUrl = configuration.getProperty("DB_URL");
            user = configuration.getProperty("DB_USER");
            password = configuration.getProperty("DB_PASSWORD");
            driver = configuration.getProperty("DRIVER");
        } catch (IOException e) {
            LOG.severe( e.getMessage() );
        }

        try {
            // Cargar el driver
            Class.forName(driver);
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            // Abrir una conexion a la base de datos
            con = DriverManager.getConnection(dbUrl, props);

        } catch (ClassNotFoundException ex) {
            LOG.severe(ex.getMessage() );

        } catch (SQLException ex) {
            LOG.severe("Error: " + ex.getMessage());
            LOG.severe("Codigo : " + ex.getErrorCode());
        }
    }


    private Database(String user, String password) {
        super();
        con = null;

        configuration = new Properties();

        String dbUrl = null;
        try {
            configuration.load(new FileInputStream("coffee_crud.properties"));
            LOG.info("Properties file loaded.");

            dbUrl = configuration.getProperty("DB_URL");

        } catch (IOException e) {
            LOG.severe( e.getMessage() );
        }

        try {
            // Cargar el driver
            Class.forName(DRIVER);
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            // Abrir una conexion a la base de datos
            con = DriverManager.getConnection(dbUrl, props);

        } catch (ClassNotFoundException ex) {
            LOG.severe(ex.getMessage() );

        } catch (SQLException ex) {
            LOG.severe("Error: " + ex.getMessage());
            LOG.severe("Codigo : " + ex.getErrorCode());
        }
    }

    // Abrir la conexión y regresar objeto mx.unison.coffees.db.Database
    public static Database getDatabase(String user, String pass) {
        if (DB == null) {
            DB = new Database(user, pass);
        }
        return DB;
    }

    public static Database getDatabase() {
        if (DB == null) {
            DB = new Database();
        }
        return DB;
    }

    public ResultSet query(String sql) throws SQLException {

        ResultSet rs = null;
        Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        rs = statement.executeQuery(sql);

        return rs;
    }

    public ResultSet query(String sql, int scroll, int concur) throws SQLException {

        ResultSet rs = null;

        Statement statement = con.createStatement(scroll, concur);
        rs = statement.executeQuery(sql);

        return rs;
    }

    public int update(String sql) throws SQLException {
        int result = -1;

        Statement statement = con.createStatement();
        result = statement.executeUpdate(sql);
        return result;
    }
}
