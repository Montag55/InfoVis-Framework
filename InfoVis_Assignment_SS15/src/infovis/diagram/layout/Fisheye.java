package infovis.diagram.layout;

import infovis.debug.Debug;
import infovis.diagram.Model;
import infovis.diagram.View;
import infovis.diagram.elements.Edge;
import infovis.diagram.elements.Element;
import infovis.diagram.elements.Vertex;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * 
 */

public class Fisheye implements Layout{

	private double mouseX;
	private double mouseY;
	List<Point2D> points = new ArrayList<Point2D>();

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

	public void transform(Model model, View view) {

		for(Vertex element: model.getVertices()){

			points.add( new Point2D.Double(element.getX(), element.getY()) );

			Point2D tmp = ff1( new Point2D.Double(element.getCenterX(), element.getCenterY()), view, 5);
			element.setX(tmp.getX());
			element.setY(tmp.getY());
		}

	}

	public void inv_transform(Model model) {

		Iterator<Element> iter = model.iterator();
		Iterator<Point2D> iter_2 = this.points.iterator();

		while (iter.hasNext()) {
			Point2D point = iter_2.next();
			Element element =  iter.next();
			element.setX(point.getX());
			element.setY(point.getY());
		}

		this.points.clear();

	}

	public Point2D ff1(Point2D e, View view, double d){

		double d_maxX;
		double d_maxY;
		Point2D pE = new Point2D.Double(e.getX(), e.getY());
		Point2D pF = new Point2D.Double(mouseX, mouseY);
		Point2D pS = new Point2D.Double(view.getWidth(), view.getHeight());

		if(pE.getX() > pF.getX())
			d_maxX = pS.getX() - pF.getX();
		else
			d_maxX = 0 - pF.getX();
		if(pE.getY() > pF.getY())
			d_maxY = pS.getY() - pF.getY();
		else
			d_maxY = 0 - pF.getY();


		Point2D d_norm = new Point2D.Double(pE.getX() - pF.getX(), pE.getY() - pF.getY());

		return new Point2D.Double(G(d_norm.getX()/d_maxX, d) * d_maxX + pF.getX(),
								 G(d_norm.getY()/d_maxY, d) * d_maxY + pF.getY());

	}

	public double ff2(Vertex e, View view, double d){

		Point2D pE = new Point2D.Double(e.getCenterX(), e.getCenterY());
		Point2D sN = new Point2D.Double(1, 1);
		Point2D qF = ff1(new Point2D.Double(pE.getX() + sN.getX()/2, pE.getY() + sN.getY()/2), view, d);
		return 2 * Math.min(Math.abs(qF.getX() - ff1(pE, view, d).getX()), Math.abs(qF.getY() - ff1(pE, view, d).getY()));
	}

	private double G(double p, double d){
		return ((d+1)*p)/(d*p+1);
	}

}
