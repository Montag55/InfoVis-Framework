package infovis.diagram.layout;


import infovis.diagram.Model;
import infovis.diagram.View;
import infovis.diagram.elements.Vertex;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Fisheye implements Layout{

	private double mouseX;
	private double mouseY;
	private Point2D point;
	private List<Point2D> points  = new ArrayList<Point2D>();

	public Fisheye(){}

	public void setMouseX(double mouseX) {
		this.mouseX = mouseX;
	}

	public void setMouseY(double mouseY) {
		this.mouseY = mouseY;
	}

	public void setMouseCoords(int x, int y, View view) {
		// TODO Auto-generated method stub
	}

	public Point2D transform(Vertex element, View view) {
		this.point = new Point2D.Double(element.getX(), element.getY());
		Point2D tmp = ff1(element, view, 5);
		//element.setX(tmp.getX());
		//element.setY(tmp.getY());
		return tmp;
	}

	public void inv_transform(Vertex element) {
		element.setX(this.point.getX());
		element.setY(this.point.getY());
	}

	public Point2D ff1(Vertex element, View view, double d){

		double d_maxX;
		double d_maxY;
		Point2D pN = new Point2D.Double(element.getCenterX(), element.getCenterY());
		Point2D pF = new Point2D.Double(mouseX-element.getWidth()/2, mouseY-element.getHeight()/2);
		Point2D pS = new Point2D.Double(view.getWidth(), view.getHeight());

		if(pN.getX() > pF.getX())
			d_maxX = pS.getX() - pF.getX();
		else
			d_maxX = 0 - pF.getX();

		if(pN.getY() > pF.getY())
			d_maxY = pS.getY() - pF.getY();
		else
			d_maxY = 0 - pF.getY();

		Point2D d_norm = new Point2D.Double(pN.getX() - pF.getX(), pN.getY() - pF.getY());

		return new Point2D.Double(G(d_norm.getX()/d_maxX, d) * d_maxX + pF.getX(),
								 G(d_norm.getY()/d_maxY, d) * d_maxY + pF.getY());

	}

	public double ff2(Vertex element, View view, double d){

		Point2D pN = new Point2D.Double(element.getX(), element.getY());
		Point2D sN = new Point2D.Double(element.getWidth(), element.getHeight());
		Point2D qN = new Point2D.Double(pN.getX() + sN.getX()/2, pN.getY() + sN.getY()/2);
		Point2D qF = ff1(new Vertex(qN.getX(), qN.getY(), element.getWidth(), element.getHeight()), view, d);

		return (2 * Math.min(Math.abs(qF.getX() - ff1(element, view, d).getX()), Math.abs(qF.getY() - ff1(element, view, d).getY())));
	}

	private double G(double p, double d){
		return ((d+1)*p)/(d*p+1);
	}

}
