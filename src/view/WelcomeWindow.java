package view;

import javax.swing.*;

import model.Application;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class WelcomeWindow extends JFrame implements ActionListener {
  protected final static int WIDTH = 1200; // Largeur de la fenêtre
  protected final static int HEIGHT = 800; // Hauteur de la fenêtre
  
  private JButton load = new JButton("Load a map");

	public WelcomeWindow(String nom, String fond) {
		    super(nom);
        setSize(WIDTH,HEIGHT);
        setLocation(0,0);
        setLayout(null);
        setResizable(false);
      
        //Creation of main container
        JPanel container = new JPanel();
        container.setLayout(null);
        container.setBounds(0,0,WIDTH,HEIGHT);
        
        //Logo
        ImageIcon logo = new ImageIcon(fond);
        JLabel background = new JLabel((new ImageIcon(logo.getImage().getScaledInstance(HEIGHT,HEIGHT,Image.SCALE_DEFAULT))));
		    background.setBounds(0,0,HEIGHT,HEIGHT);
        container.add(background);
        
        //Description Text
        JPanel rightContainer = new JPanel();
        rightContainer.setBounds(HEIGHT+1,0,400,400);
        JLabel description = new JLabel("Want to optimize your delivery tour?", JLabel.CENTER);
        description.setBounds(0,30,340,20);
        JLabel description2 = new JLabel("<html>Pickup & Delivery <br> is made for you ! <br> You simply have to <br> load a map first.</html>", JLabel.CENTER);
        description2.setBounds(0,200,340,90);
        rightContainer.add(description);
        rightContainer.add(description2);
        container.add(rightContainer);
        
        //Button
       
        load.setForeground(Color.white);
		    load.setBackground(Color.BLUE);
		    load.setBounds(HEIGHT+100,500,200,40);
		    load.addActionListener(this); //todo
		    container.add(load);
		    
		
        
        add(container);
        
        setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e)
    {
		System.out.println("Un evenement a été detecté");
		
		Object source =e.getSource();
		
		if (source==load) {
			File repertoireCourant = null;
			try {
	            repertoireCourant = new File(".").getCanonicalFile();
	            System.out.println("Répertoire courant : " + repertoireCourant);
	        } catch(IOException err) {}
			JFileChooser dialogue = new JFileChooser(repertoireCourant);
			dialogue.showOpenDialog(null);
			String cheminMap = dialogue.getSelectedFile().getAbsolutePath();
			System.out.println("Fichier choisi : " + cheminMap);
			
			Application.loadMap(cheminMap);
		} else {
			System.out.println("Cet evenement n'a pas d'action associée");
		}
    }
    
}
