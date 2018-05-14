package infovis.scatterplot;

import infovis.debug.Debug;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class View extends JPanel {
	     private Model model = null;
	     private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);

		 public Rectangle2D getMarkerRectangle() {
			return markerRectangle;
		}
		 
		@Override
		public void paint(Graphics g) {
			Graphics2D g2D = (Graphics2D)g;




			for (String l : model.getLabels()) {
				Debug.print(l);
				Debug.print(",  ");
				Debug.println("");
			}
			for (Range range : model.getRanges()) {
				Debug.print(range.toString());
				Debug.print(",  ");
				Debug.println("");
			}
			for (Data d : model.getList()) {
				Debug.print(d.toString());
				Debug.println("");
			}

			drawRaster(g2D);
			System.out.println(model.getDim());

		}
		public void setModel(Model model) {
			this.model = model;
		}

		public void drawRaster(Graphics2D g2D){
		 	for(int i = 0; i < model.getDim(); i++){
				for(int j = 0; j < model.getDim(); j++){
					Rectangle2D rect = new Rectangle2D.Double((i+0.2)*getHeight()*0.95/model.getDim(),(j+0.2)*getHeight()*0.95/model.getDim(),getHeight()*0.95/model.getDim()-2,getHeight()*0.95/model.getDim()-2);
						g2D.setColor(Color.DARK_GRAY);
						g2D.draw(rect);
				}
			}
		}
}
