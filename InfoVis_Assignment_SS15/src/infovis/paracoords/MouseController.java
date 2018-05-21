package infovis.paracoords;

import infovis.scatterplot.Data;
import infovis.scatterplot.Model;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;

public class MouseController implements MouseListener, MouseMotionListener {
	private View view = null;
	private Model model = null;
	Shape currentShape = null;
	private int x_click;
	private int y_click;


	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		this.x_click = e.getX();
		this.y_click = e.getY();
		Iterator<Data> iter = model.iterator();

		while(iter.hasNext()){
			iter.next().setColor(Color.BLACK);
		}

		view.repaint();
	}

	public void mouseReleased(MouseEvent e) {
		view.getMarkerRectangle().setRect(0,0,0,0);
		view.repaint();
	}

	public void mouseDragged(MouseEvent e) {
		view.getMarkerRectangle().setRect(this.x_click, this.y_click, e.getX() - this.x_click, e.getY() - this.y_click);
		view.repaint();
	}

	public void mouseMoved(MouseEvent e) {

	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

}
