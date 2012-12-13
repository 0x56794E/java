/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

/**
 * QWallet Project - Glasgow Caledonian University  Summer Internship
 * BoxListener
 * @Date: August 10th, 2011
 * @author Vy Thuy Nguyen
 */
    public class BoxListener extends MouseAdapter
    {

        private JComboBox bx;

        public BoxListener(JComboBox b)
        {
            bx = b;
        }

        public void mouseClicked(MouseEvent e)
        {
            bx.setBackground(Color.WHITE);
            bx.repaint();
        }
    }
