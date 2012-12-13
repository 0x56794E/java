/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * QWallet Project - Glasgow Caledonian University  Summer Internship
 * TextFieldListener
 * @Date: August 10th, 2011
 * @author Vy Thuy Nguyen
 */
public class TextFieldListener extends MouseAdapter
    {

        private JTextField tx;

        public TextFieldListener(JTextField tf)
        {
            tx = tf;
        }

        public void mouseClicked(MouseEvent e)
        {
            tx.setBackground(Color.WHITE);
            tx.selectAll();
        }
    }

