package infovis.diagram;

import infovis.diagram.elements.Element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
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

	
	public void paint(Graphics g) {

		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.clearRect(0, 0, getWidth(), getHeight());

		paintDiagram(g2D, getScale());
		paintOverview(g2D);
	}

	private void paintDiagram(Graphics2D g2D, double scale){
		g2D.scale(scale, scale);
		for (Element element: model.getElements()) {
			element.paint(g2D);
		}
		g2D.scale(1/scale, 1/scale);
	}

	private void paintOverview(Graphics2D g2D){

		g2D.translate(getWidth()*0.75, 0);
		g2D.scale(0.25, 0.25);

		this.overviewRect.setRect(0, 0, getWidth()-1, getHeight()-1);
		g2D.setColor(Color.WHITE);
		g2D.fill(overviewRect);

		g2D.draw(overviewRect);
		paintDiagram(g2D, 1);

		g2D.scale(4, 4);
		g2D.translate(-getWidth()*0.75, 0);

		this.marker.setRect(getWidth() * 0.75 + getTranslateX(),
							1 * getTranslateY(),
							0.25 * getWidth()/getScale(),
							0.25 * getHeight()/getScale());

		g2D.draw(marker);
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

	public void updateMarker(int x, int y){
		marker.setRect(x, y, 16, 10);
	}

	public Rectangle2D getMarker(){
		return marker;
	}

	public boolean markerContains(int x, int y){
		/*
		Rectangle2D tmp_rec = new Rectangle2D.Double(marker.getMinX()+getScale()*0.25/getTranslateX(),
													marker.getMinY()+getScale()*0.25/getTranslateY(),
													(marker.getMaxX()+getTranslateX())*getScale()*0.25,
													(marker.getMaxY()+getTranslateY())*getScale()*0.25);
		System.out.println(tmp_rec.getMinX() + "; " + tmp_rec.getMinY() + "; " + tmp_rec.getMaxX() + "; " + tmp_rec.getMaxY());
*/
		return marker.contains(x, y);

	}
}
 