package infovis.scatterplot;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;

public class MouseController implements MouseListener, MouseMotionListener {

	private Model model = null;
	private View view = null;
	private int x_click;
	private int y_click;

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
		view.getMarkerRectangle().setRect(0,0, 0,0);
		view.repaint();
	}

	public void mousePressed(MouseEvent arg0) {
		this.x_click = arg0.getX();
		this.y_click = arg0.getY();
		Iterator<Data> iter = model.iterator();

		while(iter.hasNext()){
			iter.next().setColor(Color.BLACK);
		}

		view.repaint();
	}

	public void mouseReleased(MouseEvent arg0) {
		view.getMarkerRectangle().setRect(0,0,0,0);
		view.repaint();
	}

	public void mouseDragged(MouseEvent arg0) {
		view.getMarkerRectangle().setRect(this.x_click, this.y_click, arg0.getX() - this.x_click, arg0.getY() - this.y_click);
		view.repaint();
	}

	public void mouseMoved(MouseEvent arg0) {
	}

	public void setModel(Model model) {
		this.model  = model;	
	}

	public void setView(View view) {
		this.view  = view;
	}

}
