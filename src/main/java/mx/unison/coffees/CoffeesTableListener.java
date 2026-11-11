package mx.unison.coffees;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import mx.unison.coffees.db.Database;
import mx.unison.coffees.db.JDBCTableAdapter;

import java.sql.SQLException;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author rnavarro
 */
public class CoffeesTableListener implements TableModelListener  {

    private static final String CLASS_NAME = CoffeesTableListener.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
    
    private final Database db;
    
    public CoffeesTableListener(Database d) {
        super();
        db = d;
    }

    @Override
    public void tableChanged(TableModelEvent event) {
        JDBCTableAdapter modelo = (JDBCTableAdapter) event.getSource();
       
        int row = event.getFirstRow();
        int column = event.getColumn();
        int type = event.getType();
        
        //No se puede modificar una clave primaria
        if( column == 0  ) {
            return;
        }      
        
        String colName = modelo.getColumnName(column);
        String colSQLName = modelo.getSQLColumnName(column);        
        String sql = String.format(
                "UPDATE coffees SET %s = %s WHERE COF_NAME = \'%s\'",
                colSQLName,modelo.getValueAt(row, column), modelo.getValueAt(row, 0));
        LOGGER.info( sql );
        
        try {
            db.update(sql);

        } catch (SQLException ex) {
            LOGGER.severe("Error: " + ex.getMessage());
            LOGGER.severe("Codigo : " + ex.getErrorCode());
        }

    }
    
    
}
