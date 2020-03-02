import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import java.awt.Graphics;


public class MapShop extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6854498137050053635L;
	private boolean creating=false;
	private ArrayList<Rectangle> bins=new ArrayList<Rectangle>();
	private ArrayList<Rectangle> selectedBins;
	private boolean highlightBins=false;
	/**
	 * Create the panel.
	 */
	public MapShop() {
		setBackground(Color.white);
		setOpaque(true);
		
		
	}
	public void paintComponent(Graphics g) {
		setBackground(Color.white);
		setOpaque(true);
		super.paintComponent(g);//GodLike line pour avoir le background blanc
		Graphics2D g2d= (Graphics2D) g;
		if(highlightBins) {
			for(Rectangle r: selectedBins) {
				g2d.setColor(Color.green);
				g2d.fill(r);
			}
		}
		g2d.setColor(Color.red);
		g2d.setStroke(new BasicStroke(2));
		for(Rectangle r: bins) {
			if(r==null) {
				continue;
			}
			g2d.draw(r);
		}
	}
	public void endCreateBin(int x, int y) {
		repaint();
		creating=false;
		
	}
	public void drawBins(ArrayList<Rectangle> bins) {
		this.bins=bins;
		repaint();
	}
	public void fillSelectedBins(ArrayList<Rectangle> bins) {
		selectedBins=bins;
		highlightBins=true;
		repaint();
	}

}
