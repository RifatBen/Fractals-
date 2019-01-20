package modele;

public class FractalSet implements TriFractalFunction{
	private int degre;

	public FractalSet(int degre){
		this.degre=degre;
	}



	@Override
	public Integer apply(Complex x0, Complex c, Integer maxIteration) {
		int ite = 0;
		Complex xn = x0;
		// sortie de boucle si divergence

		while (ite++ < maxIteration && xn.modulus() <= 2.){
			Complex current = new Complex(xn.getReal(),xn.getImg());
			for(int i=1;i<degre;i++)
				xn=xn.times(current);
			xn=c.plus(xn);
		}
		return ite;
	}

	public static float affectColor(int itr){
		return (itr*10)%255f;
	}


	public final static class Julia implements TriFractalFunction{
		@Override
		public Integer apply(Complex x0, Complex c, Integer maxIteration) {
			int ite = 0;
			Complex xn = x0;
			// sortie de boucle si divergence

			while (ite++ < maxIteration && xn.modulus() <= 2.){
				xn = c.plus(xn.times(xn));
			}
			return ite;
		}
	}

	public final static class Mandelbrot implements TriFractalFunction{

		@Override
		public Integer apply(Complex xn, Complex c0, Integer maxIteration) {
			int ite = 0;
			Complex cn = new Complex(xn.getReal(),xn.getImg());
			xn = new Complex(0,0);
			// sortie de boucle si divergence

			while (ite++ < maxIteration && xn.modulus() <= 2.){
				xn = cn.plus(xn.times(xn));
			}
			return ite;
		}
	}
}