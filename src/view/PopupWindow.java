package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PopupWindow {

	public PopupWindow(String text) {
	    final JFrame popup = new JFrame();
		
	    JButton button = new JButton();
	
	    button.setText(text);
	    popup.add(button);
	    popup.pack();
	    popup.setVisible(true);
	
	    /*
	    button.addActionListener(new java.awt.event.ActionListener() {
	        @Override
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	            String name = JOptionPane.showInputDialog(parent,
	                    "What is your name?", null);
	        }
	    });
	    */
	}
}