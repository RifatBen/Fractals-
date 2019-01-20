package modele;

@FunctionalInterface
public interface TriFractalFunction {
	/**
	 * Applique la suite itrMax fois sur le complex z0, et retourne le nombre d'itération faite pour atteindre la divergence de la suite.
	 * @param z0 Le premier membre de la suite
	 * @param c	Nombre complexe
	 * @param itrMax Le nombre d'itération max avant d'assurer la convergence de la suite
	 * @return
	 */
	Integer apply(Complex z0,Complex c,Integer itrMax);
}
