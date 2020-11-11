package view.graphical;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import view.GraphicalView;

/**
 * Our implementation of the zoom. for the graphical view
 */
public class ZoomBox extends JPanel {
	private static final long serialVersionUID = 11L;
	private final int BOX_WIDTH = 200;
	private final int BOX_HEIGHT = 200;
	private final int OFFSET = -105;
	private final int ZOOM = 2;

	private int x, y, imgWidth, imgHeight;
	private BufferedImage img;
	private GraphicalView gv;

	/**
	 * Constructor
	 * 
	 * @param graphical the GraphicalView i.e. the map we want to zoom on
	 */
	public ZoomBox(GraphicalView graphical) {
		this.gv = graphical;
		img = createImage(gv);
		x = 0;
		y = 0;

		setLayout(null);
		setBounds(0, 0, BOX_WIDTH, BOX_HEIGHT);
		setBorder(BorderFactory.createLineBorder(Color.white, 2));
		setVisible(true);

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, -x * ZOOM + OFFSET, -y * ZOOM + OFFSET, imgWidth, imgHeight, this);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(1f));

		g2d.setColor(Color.white);
		g2d.drawLine(75, 100, 125, 100);
		g2d.drawLine(100, 75, 100, 125);

	}

	/**
	 * Updates the location of the lao
	 * 
	 * @param xMouse the new x coordinate of the mouse
	 * @param yMouse the new y coordinate of the mouse
	 */
	public void updateLocation(int xMouse, int yMouse) {
		x = xMouse + OFFSET;
		y = yMouse + OFFSET;
		repaint();
	}

	/**
	 * Updates the image used for the zoom
	 */
	public void updateImage() {
		img = createImage(gv);
	}

	// from https://stackoverflow.com/a/1349264
	public BufferedImage createImage(JPanel panel) {

		int w = panel.getWidth() * ZOOM;
		int h = panel.getHeight() * ZOOM;
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		g.scale(ZOOM, ZOOM);
		panel.paint(g);
		g.dispose();

		imgWidth = bi.getWidth();
		imgHeight = bi.getHeight();
		return bi;
	}

}
