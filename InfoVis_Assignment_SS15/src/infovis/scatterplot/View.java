package infovis.scatterplot;

import infovis.debug.Debug;

import java.awt.*;
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
			drawLabels(g2D);
			System.out.println(model.getDim());

		}
		public void setModel(Model model) {
			this.model = model;
		}
		public void drawRaster(Graphics2D g2D){
		 	for(int i = 0; i < model.getDim(); i++){
				for(int j = 0; j < model.getDim(); j++){
					Rectangle2D rect = new Rectangle2D.Double((i+2)*getHeight()*0.95/model.getDim(),(j+0.4)*getHeight()*0.95/model.getDim(),getHeight()*0.95/model.getDim()-2,getHeight()*0.95/model.getDim()-2);
						g2D.setColor(Color.DARK_GRAY);
						g2D.draw(rect);
				}
			}
		}
		public void drawLabels(Graphics2D g2D){
			g2D.setFont(new Font("TimesRoman", Font.PLAIN, 9));
			for(int i = 0; i < model.getLabels().size(); i++) {
				g2D.drawString(model.getLabels().get(i), (int)((0 + 0.4) * getHeight() * 0.95 / model.getDim()), (int) ((i + 1) * getHeight() * 0.95 / model.getDim()));
				g2D.drawString(model.getLabels().get(i), (int)((i + 2) * getHeight() * 0.95 / model.getDim()), (int) ((0 + 0.3) * getHeight() * 0.95 / model.getDim()));
			}
		}
}
