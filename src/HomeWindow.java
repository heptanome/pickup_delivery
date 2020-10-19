import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;

public class HomeWindow extends JFrame {
    protected final static int WIDTH = 1200; // Largeur de la fenêtre
    protected final static int HEIGHT = 800; // Hauteur de la fenêtre
    protected Map loadedMap;
  
    public HomeWindow(String nom, Map map) {
        super(nom);
        this.loadedMap = map;

        setSize(WIDTH,HEIGHT);
        setLocation(0,0);
        setLayout(null);
        setResizable(false);

        //Creation of main container
        JPanel container = new JPanel();
        container.setLayout(null);
        container.setBounds(0,0,WIDTH,HEIGHT);
        container.setBackground(Color.GRAY);
    
        //Graphical view
        //JPanel graphicalView = new JPanel();
        //graphicalView.setBounds(0,0,HEIGHT,HEIGHT);
        //graphicalView.setBackground(Color.gray);
        //repaint();


        GraphicalView gv = new GraphicalView();
        container.add(gv);

        //TextualView

        //Buttons
        

        add(container);
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
      
  }
  