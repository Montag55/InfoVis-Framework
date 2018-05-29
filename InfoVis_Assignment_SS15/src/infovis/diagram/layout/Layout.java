package infovis.diagram.layout;

import infovis.diagram.Model;
import infovis.diagram.View;
import infovis.diagram.elements.Edge;
import infovis.diagram.elements.Vertex;

import java.awt.geom.Point2D;

public interface Layout {

	public void setMouseCoords(int x, int y, View view);
	public Point2D transform(Vertex element, View view);
	
}
