package view.graphical;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

import view.GraphicalView;

public class ZoomBox extends JPanel {
	private static final long serialVersionUID = 11L;
	private final int BOX_WIDTH = 200;
	private final int BOX_HEIGHT = 200;
	private final int OFFSET = -105;
	private final int ZOOM = 2;

	private Timer timer;
	private int x, y, imgWidth, imgHeight;
	private BufferedImage img;
	private GraphicalView gv;

	public ZoomBox(GraphicalView graphical) {
		this.gv = graphical;
		img = createImage(gv);
		x = 0;
		y = 0;

		setLayout(null);
		setBounds(0, 0, BOX_WIDTH, BOX_HEIGHT);
		setBorder(BorderFactory.createLineBorder(Color.black, 2));
		setVisible(true);

		timer = new Timer(1, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}
		});
		timer.start();

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, -x * ZOOM + OFFSET, -y * ZOOM + OFFSET, imgWidth, imgHeight, this);
		setLocation(x, y);

	}

	public void updateLocation(int xMouse, int yMouse) {
		x = xMouse + OFFSET;
		y = yMouse + OFFSET;
	}

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
