package controller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modele.Complex;
import modele.FractalSet;
import modele.TriFractalFunction;

public class Fractal extends Application {
	protected int maxItr = 200;


	protected final int widthCanva = 1100;
	protected final int heightCanva = 1000;
	protected  final int width=1100;
	protected  final int height=950;

	protected double posX=0;
	protected double posY=0;
	protected double actualzoom=1;
	protected Complex c;
	GraphicsContext gc;
	Group group;

	//Julia
	@FXML protected ScrollPane grapheJulia;
	@FXML protected TextField zoom;
	@FXML protected TextField img;
	@FXML protected TextField reel;
	@FXML protected TextField iterations;

	//Mandelbrot
	@FXML protected ScrollPane grapheMandelbrot;
	@FXML protected TextField zoom1;
	@FXML protected TextField img1;
	@FXML protected TextField reel1;
	@FXML protected TextField iterations1;

	//Other fractals
	@FXML protected ScrollPane grapheFractal;
	@FXML protected TextField zoom2;
	@FXML protected TextField img2;
	@FXML protected TextField reel2;
	@FXML protected TextField iterations2;
	@FXML protected TextField degre;

	public Fractal(){

	}

	public Fractal(Complex c, double zoom, int maxItr){
		this.c = c;
		this.actualzoom=zoom;
		this.maxItr=maxItr;
	}
	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/main.fxml"));
		loader.setController(this);
		Parent root = loader.load();
		primaryStage.setTitle("Fractals");
		primaryStage.setResizable(false);
		primaryStage.setHeight(height);
		primaryStage.setWidth(width);
		group = new Group();
		final Canvas canvas = new Canvas(widthCanva, heightCanva);
		gc = canvas.getGraphicsContext2D();
		group.getChildren().addAll(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

	}

	/**
	 * Cette fonction s'occupe de générer une fractale en fonction de la suite passée en paramètre, et ce en usant 4 threads afin d'accélerer les calculs.
	 * @param f suite à appliquer pour calculer la fractale.
	 * @return La fractale dessinée
	 */
	protected BufferedImage paint(TriFractalFunction f) {
		BufferedImage image = new BufferedImage(widthCanva,heightCanva, BufferedImage.TYPE_INT_ARGB);

		ExecutorService drawService = Executors.newFixedThreadPool(4);

		//1er quart
		FastDrawer t1 = new FastDrawer(0, widthCanva/2, 0, heightCanva/2, this, f, image);
		//2éme quart
		FastDrawer t3 = new FastDrawer(0, widthCanva/2, heightCanva/2 , heightCanva , this , f, image);
		//3éme quart
		FastDrawer t2 = new FastDrawer(widthCanva/2, widthCanva, 0 , heightCanva/2 , this , f, image);
		//4éme quart
		FastDrawer t4 = new FastDrawer(widthCanva/2, widthCanva, heightCanva/2 , heightCanva , this , f, image);
		List<FastDrawer> tasks = new ArrayList<>();
		tasks.add(t1);
		tasks.add(t2);
		tasks.add(t3);
		tasks.add(t4);
		List<Future<?>> futures = tasks.stream().map(drawService::submit).collect(Collectors.toList());
		int received = 0;
		boolean errors = false;

		while(received < 4 && !errors) {
			Future<?> result = futures.get(received); //blocks if none available
			try {
				result.get();
				received ++;
			}
			catch(Exception e) {
				//log
				errors = true;
			}
		}
		return image;
	}

	public void showGraph(TriFractalFunction f){
		BufferedImage image = paint(f);
		Image img = SwingFXUtils.toFXImage(image, null);
		gc.drawImage(img, 0, 0);
	}


	public void afficheMandelbrot(){
		actualzoom=zoom1.getText().equals("")?1:Double.parseDouble(zoom1.getText());
		c= null;
		maxItr = iterations1.getText().equals("")?1:Integer.parseInt(iterations1.getText());
		FractalSet.Mandelbrot mandelbrot = new FractalSet.Mandelbrot();
		grapheMandelbrot.setContent(group);

		showGraph(mandelbrot);
	}

	public void afficheFractal(){
		actualzoom=zoom2.getText().equals("")?1:Double.parseDouble(zoom2.getText());
		c= new Complex(Double.parseDouble(reel2.getText()), Double.parseDouble(img2.getText()));
		maxItr = iterations2.getText().equals("")?1:Integer.parseInt(iterations2.getText());
		int formule = degre.getText().equals("")?2:Integer.parseInt(degre.getText());
		FractalSet fractal= new FractalSet(formule);
		grapheFractal.setContent(group);

		showGraph(fractal);
	}

	public void afficheJulia(){
		actualzoom=zoom.getText().equals("")?1:Double.parseDouble(zoom.getText());
		c= new Complex(Double.parseDouble(reel.getText()), Double.parseDouble(img.getText()));
		maxItr = iterations.getText().equals("")?1:Integer.parseInt(iterations.getText());
		FractalSet.Julia julia = new FractalSet.Julia();
		grapheJulia.setContent(group);
		showGraph(julia);
	}

	//Trouve un nom disponible pour l'image de la fractale


}

