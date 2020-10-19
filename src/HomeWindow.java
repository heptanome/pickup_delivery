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
    protected LoadMapBtn loadMapBtnListener;
    
    private JButton btnLoadRequest=new JButton("Load a request");
  
    public HomeWindow(String nom, Map map) {
        super(nom);
        this.loadedMap = map;

        setSize(WIDTH,HEIGHT);
        setLocation(0,0);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Creation of main container
        JPanel container = new JPanel();
        container.setLayout(null);
        container.setBackground(Color.black);
        container.setBounds(0,0,WIDTH,HEIGHT);
   
        GraphicalView gv = new GraphicalView(loadedMap);
        container.add(gv);

        //TextualView
        TextualView tv = new TextualView(loadedMap);
        tv.setBounds(800,0,400,800);
        container.add(tv);

        //Buttons
        

        add(container);
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
      
  }
  