package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import modele.Complex;
import modele.FractalSet;
import modele.TriFractalFunction;

public class CLI {
	private Scanner scan;
	/**
	 * S'execute en invite de commande, s'occupe de demander la fractale à générer, et transmet cette information à la methode generateFractal
	 */
	public void runCLI(){
		scan = new Scanner(System.in);

		System.out.print("*******************************FRACTALS*******************************\n");
		System.out.println("1.Ensemble de Julia\n2.Ensemble de Mandelbrot\n3.Autre");
		System.out.print("Quel ensemble souhaitez vous générer? ");
		int choix = scan.nextInt();
		switch(choix){
		case 1 :
			System.out.println("Vous avez choisis l'ensemble de Julia");
			break;
		case 2 :
			System.out.println("Vous avez choisis l'ensemble de Mandelbrot!");
			break;
		default :
			System.out.println("Vous souhaitez générer un autre ensemble");
			break;
		}
		generateFractal(choix);
		System.exit(1);
	}

	/**
	 * Fonction qui s'occupe de générer la fractale choisis en fonction du choix donné, et la sauvegarde en tant qu'image .png dans le répértoire racine du projet
	 * @param choix
	 */
	public void generateFractal(int choix){
		System.out.print("Entrez le nombre d'itération : ");
		int itr = scan.nextInt();
		System.out.print("Entrez le zoom : ");
		double zoom = scan.nextDouble();
		Complex c;
		if(choix!=2){
			System.out.print("Entrez la partie réel du C : ");
			double re = scan.nextDouble();
			System.out.print("Entrez la partie imaginaire du C : ");
			double im = scan.nextDouble();
			c = new Complex(re,im);
		}
		else
			c=null;

		Fractal fractal = new Fractal(c,zoom,itr);
		TriFractalFunction f;
		switch(choix){
		case 1 : f=new FractalSet.Julia(); break;
		case 2 : f=new FractalSet.Mandelbrot(); break;
		default :
			System.out.print("Entrez le plus haut degré du polynome : ");
			int degre = scan.nextInt();
			f=new FractalSet(degre);
			break;
		}
		System.out.println("Génération en cours...");
		BufferedImage image = fractal.paint(f);
		File output = fractalPicture(f);
		try {
			ImageIO.write(image, "png", output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Fractale générée dans : " + output.getAbsolutePath());

	}

	/**
	 * Cette fonction génére un fichier .png ayant le nom de la fractale passée en paramètre
	 * @param f La fractale souhaitant être générée
	 * @return Une référence vers le fichier créé
	 */
	public File fractalPicture(TriFractalFunction f){
		File output = new File(f.getClass().getSimpleName() + ".png");
		int i=0;
		while(output.exists()){
			output = new File(f.getClass().getSimpleName() + i + ".png");
			i++;
		}
		return output;
	}
}
