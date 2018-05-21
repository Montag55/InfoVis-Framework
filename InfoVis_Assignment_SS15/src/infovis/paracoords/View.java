package infovis.paracoords;

import infovis.debug.Debug;
import infovis.scatterplot.Data;
import infovis.scatterplot.Model;
import infovis.scatterplot.Range;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;

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

		drawRaster(g2D);
		drawLabels(g2D);
		drawData(g2D);
		g2D.setColor(Color.BLACK);
		g2D.draw(getMarkerRectangle());
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public void drawRaster(Graphics2D g2D){
		for(int i = 0; i < model.getDim(); i++){
			Line2D line = new Line2D.Double(i*(getWidth() - 20)/model.getDim() + 20, 20, i*(getWidth() - 20)/model.getDim() + 20, getHeight()-20);
			g2D.setColor(Color.DARK_GRAY);
			g2D.draw(line);
		}
	}
	public void drawLabels(Graphics2D g2D){
		g2D.setFont(new Font("TimesRoman", Font.PLAIN, 9));
		for(int i = 0; i < model.getLabels().size(); i++) {
			g2D.drawString(model.getLabels().get(i), (int)(i*(getWidth() - 20)/model.getDim() + 20), (int) (10));
		}
	}
	public void drawData(Graphics2D g2D){

		double line_height = getHeight() - 40;

		for(int e = 0; e < model.getList().size(); e++) {

			g2D.setColor(model.getList().get(e).getColor());
			double min = model.getRanges().get(0).getMin();
			double max = model.getRanges().get(0).getMax();
			double val_off = (max - model.getList().get(e).getValue(0))/(max - min);

			Point2D old = new Point.Double(20, line_height * val_off + 20);

			for(int i = 1; i < model.getDim(); i++){

				min = model.getRanges().get(i).getMin();
				max = model.getRanges().get(i).getMax();
				val_off = (max - model.getList().get(e).getValue(i))/(max - min);

				Point2D tmp = new Point2D.Double(i*(getWidth() - 20)/model.getDim() + 20, line_height * val_off + 20);
				Line2D line = new Line2D.Double(old, tmp);

				if(line.intersects(getMarkerRectangle()))
					model.getList().get(e).setColor(Color.RED);

				g2D.draw(line);

				old = tmp;
			}
		}
	}
}
