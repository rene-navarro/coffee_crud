package mx.unison.coffees.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private Connection con;

    //URL que identifica a la base de datos que nos queremos conectar
    //private final String DB_URL = "jdbc:mysql://148.225.64.69:3306/db210215739";
    //private final String DB_URL = "jdbc:mysql://localhost:3306/COFFEES";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/coffeesdb";

    //Driver de JDBC que vamos a usar para conectarnos a la base de datos
    private static final String DRIVER = "org.postgresql.Driver";

   // Objeto singleton
    private static Database DB = null;

    // Constructor privado para implementar Singleton
    private Database() {
        super();
    }

    private Properties properties ;
    private Database(String user, String password) {
        super();
        con = null;

        properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("coffee_crud.properties")));
            System.out.printf("Properties file loaded.\n");

        } catch (FileNotFoundException e) {
            LOGGER.severe(e.getMessage() );
        } catch (IOException e) {
            LOGGER.severe(e.getMessage() );
        }

        try {

            // Cargar el driver
            Class.forName(DRIVER);
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            // Abrir una conexion a la base de datos
            con = DriverManager.getConnection(DB_URL, props);

        } catch (ClassNotFoundException ex) {
            LOGGER.severe(ex.getMessage() );

        } catch (SQLException ex) {
            LOGGER.severe("Error: " + ex.getMessage());
            LOGGER.severe("Codigo : " + ex.getErrorCode());
        }
    }

    // Abrir la conexión y regresar objeto mx.unison.coffees.db.Database
    public static Database getDatabase(String user, String pass) {
        if (DB == null) {
            DB = new Database(user, pass);
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
