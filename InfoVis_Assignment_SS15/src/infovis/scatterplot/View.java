package infovis.scatterplot;

import infovis.debug.Debug;

import java.awt.*;
import java.awt.geom.Point2D;
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

			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.clearRect(0, 0, getWidth(), getHeight());

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
			drawData(g2D);
			g2D.setColor(Color.BLACK);
			g2D.draw(getMarkerRectangle());

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
		public void drawData(Graphics2D g2D){

			for(int i = 0; i < model.getDim(); i++){
				for(int j = 0; j < model.getDim(); j++){

					double x = (i+2)*getHeight()*0.95/model.getDim();
					double y = (j+0.4)*getHeight()*0.95/model.getDim();
					double box_size = getHeight()*0.95/model.getDim()-2;

					for(int e = 0; e < model.getList().size(); e++) {

						double min_1 = model.getRanges().get(i).getMin();
						double max_1 = model.getRanges().get(i).getMax();
						double val_1_off = (max_1 - model.getList().get(e).getValue(i))/(max_1 - min_1);

						double min_2 = model.getRanges().get(j).getMin();
						double max_2 = model.getRanges().get(j).getMax();
						double val_2_off = (max_2 - model.getList().get(e).getValue(j))/(max_2 - min_2);

						Rectangle2D rect = new Rectangle2D.Double(x + (box_size - 3) * val_1_off, y + (box_size - 3) * val_2_off, 3, 3);

						if(getMarkerRectangle().contains(rect))
							model.getList().get(e).setColor(Color.RED);

						g2D.setColor(model.getList().get(e).getColor());
						g2D.draw(rect);
					}
				}
			}
		}
}
