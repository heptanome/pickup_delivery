
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;

public class Window extends JFrame {
  protected final static int WIDTH = 800; // Largeur de la fenêtre
  protected final static int HEIGHT = 800; // Hauteur de la fenêtre

  public Window(String nom, String fond) {
    super(nom);
    setSize(WIDTH, HEIGHT);
    setLocation(0, 0);
    setLayout(null);
    setResizable(false);

    // Creation of main container
    JPanel container = new JPanel();
    container.setLayout(null);
    container.setBounds(0, 0, WIDTH, HEIGHT);

    // Logo
    ImageIcon logo = new ImageIcon(fond);
    JLabel background = new JLabel(
        (new ImageIcon(logo.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT))));
    background.setBounds(0, 0, WIDTH, HEIGHT);
    container.add(background);

    add(container);

    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
