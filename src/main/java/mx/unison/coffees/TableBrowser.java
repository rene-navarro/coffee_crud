package mx.unison.coffees;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class TableBrowser extends javax.swing.JInternalFrame {
    private  JTable table = null;
    
    public TableBrowser(String title, TableModel modelo) {
        super(title,true,true,true,true);   
        
        this.initComponents(modelo);
        
     }  
    
    private  void initComponents(TableModel modelo) {
        // Crear la tabla con los datos de la consulta
        table = new JTable(modelo);        
        table.setPreferredScrollableViewportSize(new Dimension(640, 320));
        table.setFillsViewportHeight(true);

        //Crear un panel con scroll y agregar la tabla.
        JScrollPane scrollPane = new JScrollPane(table);

        this.getContentPane().add(scrollPane);
        this.pack();       
    }
}
