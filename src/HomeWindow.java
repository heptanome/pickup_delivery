import javax.swing.*;

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
    protected LoadMapBtn loadMapBtnListener;
    
    private JButton btnLoadRequest=new JButton("Load a request");
  
    public HomeWindow(String nom, Map map) {
        super(nom);
        this.loadedMap = map;
        this.loadMapBtnListener = new LoadMapBtn();

        setSize(WIDTH,HEIGHT);
        setLocation(0,0);
        setLayout(null);
        setResizable(false);

        //Creation of main container
        JPanel graphicalContainer = new JPanel();
        graphicalContainer.setLayout(null);
        graphicalContainer.setBounds(0,0,HEIGHT,HEIGHT);
        
        JPanel textualContainer = new JPanel();
        textualContainer.setLayout(null);
        textualContainer.setBounds(801,0,400,HEIGHT);
        
        JPanel buttonsContainer = new JPanel();
        buttonsContainer.setLayout(null);
        buttonsContainer.setBounds(1001,0,200,HEIGHT);
    
        add(graphicalContainer);
        add(textualContainer);
        add(buttonsContainer);
        
        //Graphical view
        //JPanel graphicalView = new JPanel();
        //graphicalView.setBounds(0,0,HEIGHT,HEIGHT);
        //graphicalView.setBackground(Color.gray);
        //repaint();


        GraphicalView gv = new GraphicalView(loadedMap);
        graphicalContainer.add(gv);

        //TextualView

        //Buttons
        
        btnLoadRequest.addActionListener(loadMapBtnListener);
        buttonsContainer.add(btnLoadRequest,BorderLayout.SOUTH);

        //Label
        
  
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
      
  }
  