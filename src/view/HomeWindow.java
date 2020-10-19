package view;
import javax.swing.*;

import model.Application;
import model.Map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;

public class HomeWindow extends JFrame {
    protected final static int WIDTH = 1400; // Largeur de la fenêtre
    protected final static int HEIGHT = 800; // Hauteur de la fenêtre
    protected Map loadedMap;
    
    private JButton btnLoadRequest = new JButton("Load a request");
  
    public HomeWindow(String nom, Map map) {
        super(nom);
        this.loadedMap = map;

        setSize(WIDTH,HEIGHT);
        setLocation(0,0);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Creation of main container
        JPanel graphicalContainer = new JPanel();
        graphicalContainer.setLayout(null);
        graphicalContainer.setBounds(0,0,HEIGHT,HEIGHT);
        
        JPanel textualContainer = new JPanel();
        textualContainer.setLayout(null);
        textualContainer.setBounds(801,0,400,HEIGHT);
        textualContainer.setBackground(Color.green);
        
        JPanel buttonsContainer = new JPanel();
        buttonsContainer.setLayout(null);
        buttonsContainer.setBounds(1201,0,200,HEIGHT);
        buttonsContainer.setBackground(Color.red);
        

        
        //Graphical view
        //JPanel graphicalView = new JPanel();
        //graphicalView.setBounds(0,0,HEIGHT,HEIGHT);
        //graphicalView.setBackground(Color.gray);
        //repaint();
        GraphicalView gv = new GraphicalView(loadedMap);
		graphicalContainer.add(gv);

        //TextualView
        TextualView tv = new TextualView(loadedMap);
        tv.setBounds(800,0,400,800);
        graphicalContainer.add(tv);

        //Buttons
        btnLoadRequest.setForeground(Color.white);
        btnLoadRequest.setBackground(Color.BLUE);
        btnLoadRequest.setBounds(25,50,150,40);
        btnLoadRequest.addActionListener(new LoadMapListener());
        buttonsContainer.add(btnLoadRequest,BorderLayout.SOUTH);
        
        add(graphicalContainer);
        add(textualContainer);
        add(buttonsContainer);
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public class LoadMapListener implements ActionListener {

    	/**
    	 * 
    	 */
    	public LoadMapListener() {
    		// TODO Auto-generated constructor stub
    	}

    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		Application.loadRequest("chemin"); //TODO: Implémenter la récupération du chemin
    	}

    }

    
      
  }
  