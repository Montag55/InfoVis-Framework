package infovis.diagram;

import infovis.diagram.elements.Element;
import infovis.diagram.elements.Vertex;
import infovis.diagram.layout.Fisheye;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.JPanel;



public class View extends JPanel{
	private Model model = null;
	private Color color = Color.BLUE;
	private double scale = 1;
	private double translateX = 0;
	private double translateY = 0;
	private Rectangle2D marker = new Rectangle2D.Double();
	private Rectangle2D overviewRect = new Rectangle2D.Double();
	private boolean fisheyemode = false;
	private Fisheye fisheye = new Fisheye();

	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void set_fisheyemode(boolean b){ this.fisheyemode = b; }
	public boolean get_fisheyemode(){ return this.fisheyemode; };
	public Fisheye getFisheye() { return fisheye; }


	public void paint(Graphics g) {

		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.clearRect(0, 0, getWidth(), getHeight());

		if(get_fisheyemode()) {
			draw_fisheye(g2D);
			paintOverview(g2D);
		}
		else {
			g2D.translate(-getTranslateX() * getScale() * 4, -getTranslateY() * getScale() * 4);
			paintDiagram(g2D, getScale());
			g2D.translate(getTranslateX() * getScale() * 4, getTranslateY() * getScale() * 4);
			paintOverview(g2D);
		}
	}

	private void paintDiagram(Graphics2D g2D, double scale){
		g2D.scale(scale, scale);
		for (Element element: model.getElements()) {
			element.paint(g2D);
		}
		g2D.scale(1/scale, 1/scale);
	}

	private void paintOverview(Graphics2D g2D){
		if(fisheyemode){updateOverview(getWidth() * 0.75, 0);
			g2D.setColor(Color.WHITE);
			g2D.fill(overviewRect);
			g2D.draw(overviewRect);

			g2D.translate(getWidth()*0.75, 0);
			g2D.scale(0.25, 0.25);

			paintDiagram(g2D, 1);
			//draw_fisheye(g2D);

			Rectangle2D frame_rect = new Rectangle2D.Double(0,0, getWidth(), getHeight());
			g2D.setColor(Color.BLACK);
			g2D.draw(frame_rect);

			g2D.scale(4, 4);
			g2D.translate(-getWidth()*0.75, 0);


		}
		else {
			updateOverview(getWidth() * 0.75, 0);
			g2D.setColor(Color.WHITE);
			g2D.fill(overviewRect);
			g2D.draw(overviewRect);

			g2D.translate(getWidth() * 0.75, 0);
			g2D.scale(0.25, 0.25);
			paintDiagram(g2D, 1);
			g2D.scale(4, 4);
			g2D.translate(-getWidth() * 0.75, 0);

			updateMarker(getWidth() * 0.75 + getTranslateX(), getTranslateY());
			g2D.draw(marker);
		}
	}
	
	public void setScale(double scale) {
		this.scale = scale;
	}

	public double getScale(){
		return scale;
	}

	public double getTranslateX() {
		return translateX;
	}

	public void setTranslateX(double translateX) {
		this.translateX = translateX;
	}

	public double getTranslateY() {
		return translateY;
	}

	public void setTranslateY(double tansslateY) {
		this.translateY = tansslateY;
	}

	public void updateTranslation(double x, double y){
		setTranslateX(x);
		setTranslateY(y);
	}

	public void updateMarker(double x, double y) {
		marker.setRect(x, y, 0.25 * getWidth() / getScale(), 0.25 * getHeight() / getScale());
	}

	public void updateOverview(double x, double y){
		this.overviewRect.setRect(x, y, 0.25*getWidth(), 0.25*getHeight());
	}

	public Rectangle2D getMarker(){
		return this.marker;
	}

	public boolean markerContains(int x, int y){
		return this.marker.contains(x, y);

	}

	public boolean overviewContains(Rectangle2D rect){
		return this.overviewRect.contains(rect);
	}

	public void draw_fisheye(Graphics2D g2D){

		Iterator<Vertex> iter = model.iteratorVertices();
		for (Vertex element: model.getVertices_copy()) {

			Vertex orig_point = iter.next();
			double sG = fisheye.ff2( orig_point, this, 5)*0.05;
			Point2D tmp = fisheye.transform(orig_point, this);

			element.setX(tmp.getX());
			element.setY(tmp.getY());

			element.setHeight(orig_point.getHeight()*sG);
			element.setWidth(orig_point.getWidth()*sG);
			element.paint(g2D);

			//element.setHeight(element.getHeight()/sG);
			//element.setWidth(element.getWidth()/sG);
			//fisheye.inv_transform(element);
		}


	}
}
 