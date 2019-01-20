package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.sun.javafx.util.Utils;

import modele.Complex;
import modele.FractalSet;
import modele.TriFractalFunction;

public class FastDrawer implements Runnable {
	private int startx;
	private int endx;
	private int starty;
	private int endy;
	private Fractal fractal;
	private TriFractalFunction f;
	private BufferedImage image;
	/**
	 *	La classe FastDrawer est une classe qui s'occupe de déssiner une fractal donnée sur une BufferedImage. Il est possible d'invoquer
	 * cette classe dans un thread auxiliaire car cette classe implémente l'interface Runnable.
	 * @param startx Ligne de départ
	 * @param endx Ligne de fin
	 * @param starty Colonne de départ
	 * @param endy Colonne de fin
	 * @param fractal
	 * @param f Fonction à appliquer pour afficher la fractal
	 */
	public FastDrawer(int startx, int endx, int starty, int endy, Fractal fractal,TriFractalFunction f,BufferedImage image){
		this.startx = startx;
		this.endx = endx;
		this.starty = starty;
		this.endy= endy;
		this.fractal=fractal;
		this.f=f;
		this.image=  image;
	}

	@Override
	public void run() {
		for (int x = startx; x < endx; x++) {
			for (int y = starty; y < endy; y++) {

				double zx = (1/fractal.actualzoom)*((((double) x - fractal.widthCanva / 2) / fractal.widthCanva * 3)+fractal.posX );
				double zy = (1/fractal.actualzoom)*((((double) y - fractal.heightCanva / 2) / fractal.heightCanva * 3)+fractal.posY);
				Complex z0 = new Complex(zx, zy);
				int itr = f.apply(z0, fractal.c ,fractal.maxItr);

				float brightness = itr < fractal.maxItr ? 1f : 0;
				float hue = FractalSet.affectColor(itr);
				double[] rgb = Utils.HSBtoRGB(hue, 1.0f, brightness);
				image.setRGB(x, y, new Color(
						(int) (rgb[0] * 255),
						(int) (rgb[1] * 255),
						(int) (rgb[2] * 255)
						).getRGB()
						);
			}
		}

	}

}
