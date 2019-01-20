package controller;

import javafx.scene.paint.Color;
import modele.Complex;
import modele.FractalSet;

public final class Mandelbrot extends Fractal {
	protected void paint(){

		Color color;
		for (int x = 0; x <= widthCanva; x++) {
			for (int y = 0; y <= heightCanva; y++) {

				double cx = (1/actualzoom)*((((double) x - widthCanva / 2) / widthCanva * 3)+posX );
				double cy = (1/actualzoom)*((((double) y - heightCanva / 2) / heightCanva * 3)+posY);
				Complex z=new Complex(0,0);
				Complex c0 = new Complex(cx, cy);
				int itr = new FractalSet.Mandelbrot().apply(z, c0, maxItr);

				float Brightness = itr < maxItr ? 1f : 0;
				float Hue = FractalSet.affectColor(itr);

				color = Color.hsb(Hue, 1f, Brightness);
				gc.getPixelWriter().setColor(x, y, color);
			}
		}
	}

}
