/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author prelius
 */
public class MyCellRenderer extends JLabel implements ListCellRenderer<Object> {
    ImageIcon icon = new ImageIcon(getClass().getResource("icon.png"));
    Color bck_sel_col = new Color(224, 224, 255);
    Color brd_col = new Color(128, 128, 164);
    Connection con = null;
    
    public MyCellRenderer(Connection c){
        this.con = c;
    }

    // This is the only method defined by ListCellRenderer.
    // We just reconfigure the JLabel each time we're called.

    @Override
    public Component getListCellRendererComponent(
        JList<?> list,           // the list
        Object value,            // value to display
        int index,               // cell index
        boolean isSelected,      // is the cell selected
        boolean cellHasFocus)    // does the cell have focus
      {
        String s = value.toString();
        setText(s);
        ImageIcon ic = null;
        ic = icon;
        try {
            Statement stm = con.createStatement();
            String sql = "SELECT * FROM \"public\".\"gamelist\" WHERE NAME LIKE '" + s + "'";
            ResultSet rs = stm.executeQuery(sql);
            
            if(rs.next()){
                ic = new ImageIcon(getClass().getResource("images\\" + rs.getString("img")));
            }
            rs.close();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(MyCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        setIcon(ic);
        setIconTextGap(15);
        
        if (isSelected) {
           setBackground(bck_sel_col);
           setForeground(Color.black);
           setBorder(BorderFactory.createLineBorder(brd_col));
       } else {
           setBackground(Color.white);
           setForeground(Color.black);
           setBorder(BorderFactory.createEmptyBorder());
       }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        return this;
    }
 }
