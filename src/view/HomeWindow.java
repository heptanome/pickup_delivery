package view;

import javax.swing.*;

import controller.Application;
import model.Map;
import model.SetOfRequests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class HomeWindow extends JFrame {
    protected final static int WIDTH = 1400; // Largeur de la fenêtre
    protected final static int HEIGHT = 800; // Hauteur de la fenêtre
    protected Map loadedMap;
    
    private JButton btnLoadRequest = new JButton("Load a request");
    private JButton btnLoadMap= new JButton("Load a map");
    private JButton btnAddRequest= new JButton("Add a request");
    private JButton btnDeleteRequest= new JButton("Delete a request");
    private JButton btnComputeTour = new JButton("Compute a Tour");

    public GraphicalView gv;
  
    public HomeWindow(String nom) {
        super(nom);

        setSize(WIDTH,HEIGHT);
        setLocation(0,0);
        setLayout(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public void setMap(Map map) {
    	this.loadedMap = map;
    	
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
        
        //Ajout containers
        add(graphicalContainer);
        add(textualContainer);
        add(buttonsContainer);
        //Graphical view
        //JPanel graphicalView = new JPanel();
        //graphicalView.setBounds(0,0,HEIGHT,HEIGHT);
        //graphicalView.setBackground(Color.gray);
        //repaint();
        gv = new GraphicalView(loadedMap);
		graphicalContainer.add(gv);

        //TextualView
        TextualView tv = new TextualView(loadedMap);
        tv.setBounds(800,0,400,800);
        textualContainer.add(tv);

        //Buttons
        btnLoadRequest.setForeground(Color.white);
        btnLoadRequest.setBackground(Color.BLUE);
        btnLoadRequest.setBounds(25,50,150,40);
        btnLoadRequest.addActionListener(new LoadRequestListener());
        buttonsContainer.add(btnLoadRequest,BorderLayout.SOUTH);
        
        btnLoadMap.setForeground(Color.white);
        btnLoadMap.setBackground(Color.BLUE);
        btnLoadMap.setBounds(25,80,150,40);
        btnLoadMap.addActionListener(new LoadMapListener());
        buttonsContainer.add(btnLoadMap,BorderLayout.SOUTH);

        btnAddRequest.setForeground(Color.white);
        btnAddRequest.setBackground(Color.BLUE);
        btnAddRequest.setBounds(25,110,150,40);
        btnAddRequest.addActionListener(new AddRequestListener());
        buttonsContainer.add(btnAddRequest,BorderLayout.SOUTH);
        
        btnDeleteRequest.setForeground(Color.white);
        btnDeleteRequest.setBackground(Color.BLUE);
        btnDeleteRequest.setBounds(25,140,150,40);
        btnDeleteRequest.addActionListener(new DeleteRequestListener());
        buttonsContainer.add(btnDeleteRequest,BorderLayout.SOUTH);
        
        btnComputeTour.setForeground(Color.white);
        btnComputeTour.setBackground(Color.BLUE);
        btnComputeTour.setBounds(25,170,150,40);
        btnComputeTour.addActionListener(new ComputeTourListener());
        buttonsContainer.add(btnComputeTour,BorderLayout.SOUTH);
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public class LoadRequestListener implements ActionListener {

    	/**
    	 * 
    	 */
    	public LoadRequestListener() {
    		// TODO Auto-generated constructor stub
    	}

    	@Override
    	public void actionPerformed(ActionEvent e) {
            System.out.println("Un evenement a été detecté");
            Object source =e.getSource();
            
            if (source==btnLoadRequest) {
                File repertoireCourant = null;
                try {
                    repertoireCourant = new File(".").getCanonicalFile();
                    System.out.println("Répertoire courant : " + repertoireCourant);
                } catch(IOException err) {}
                JFileChooser dialogue = new JFileChooser(repertoireCourant);
                dialogue.showOpenDialog(null);
                String requestPath = dialogue.getSelectedFile().getAbsolutePath();
                System.out.println("Fichier choisi : " + requestPath);
                
                SetOfRequests sr = Application.loadRequest(requestPath); 
                gv.displayRequests(sr);
            } else {
                System.out.println("Cet evenement n'a pas d'action associée");
            }
    	}

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
    		File repertoireCourant = null;
			try {
	            repertoireCourant = new File(".").getCanonicalFile();
	            System.out.println("Répertoire courant : " + repertoireCourant);
	        } catch(IOException err) {}
			JFileChooser dialogue = new JFileChooser(repertoireCourant);
			dialogue.showOpenDialog(null);
			String mapPath = dialogue.getSelectedFile().getAbsolutePath();
			System.out.println("Fichier choisi : " + mapPath);
    		//Application.loadMap(mapPath);
    	}

    }
    
    public class AddRequestListener implements ActionListener {

    	/**
    	 * 
    	 */
    	public AddRequestListener() {
    		// TODO Auto-generated constructor stub
    	}

    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		Application.addRequest(); 
    	}

    }
    
    public class DeleteRequestListener implements ActionListener {

    	/**
    	 * 
    	 */
    	public DeleteRequestListener() {
    		// TODO Auto-generated constructor stub
    	}

    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		Application.deleteRequest();
    	}

    }
    
    public class ComputeTourListener implements ActionListener {

    	/**
    	 * 
    	 */
    	public ComputeTourListener() {
    		// TODO Auto-generated constructor stub
    	}

    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		Application.computeTour();
    	}

    }
    

    
      
  }
  