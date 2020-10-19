import javax.swing.*;
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
<<<<<<< HEAD
        JPanel container = new JPanel();
        container.setLayout(null);
        container.setBackground(Color.black);
        container.setBounds(0,0,WIDTH,HEIGHT);
   
=======
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
        
        add(graphicalContainer);
        add(textualContainer);
        add(buttonsContainer);
        
        //Graphical view
        //JPanel graphicalView = new JPanel();
        //graphicalView.setBounds(0,0,HEIGHT,HEIGHT);
        //graphicalView.setBackground(Color.gray);
        //repaint();


>>>>>>> branch 'develop_ihm' of https://github.com/heptanome/pickup_delivery.git
        GraphicalView gv = new GraphicalView(loadedMap);
        container.add(gv);

        //TextualView
        TextualView tv = new TextualView(loadedMap);
        tv.setBounds(800,0,400,800);
        container.add(tv);

        //Buttons
<<<<<<< HEAD
        
=======
        btnLoadRequest.setForeground(Color.white);
        btnLoadRequest.setBackground(Color.BLUE);
        btnLoadRequest.setBounds(25,50,150,40);
        btnLoadRequest.addActionListener(loadMapBtnListener);
        buttonsContainer.add(btnLoadRequest,BorderLayout.SOUTH);
>>>>>>> branch 'develop_ihm' of https://github.com/heptanome/pickup_delivery.git

        add(container);
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
      
  }
  