package modele;

/**
 * Created by mac on 04/12/2018.
 */


public class Complex {
	private final double re;   // the real part
	private final double im;   // the imaginary part


	/**
	 * create a new object with the given real and imaginary parts
	 * @param real
	 * @param imag
	 */
	public Complex(double real, double imag) {
		re = real;
		im = imag;
	}

	/**
	 *  return a string representation of the invoking Complex object
	 */
	public String toString() {
		if (im == 0) return re + "";
		if (re == 0) return im + "i";
		if (im <  0) return re + " - " + (-im) + "i";
		return re + " + " + im + "i";
	}

	/**
	 *  return abs/modulus/magnitude
	 * @return
	 */
	public double modulus() {
		return Math.hypot(re, im);
	}

	/**
	 *  return a new Complex object whose value is (this + b)
	 * @param b
	 * @return
	 */
	public Complex plus(Complex b) {
		Complex a = this;             // invoking object
		double real = a.re + b.re;
		double imag = a.im + b.im;
		return new Complex(real, imag);
	}

	/**
	 *  return a new Complex object whose value is (this * b)
	 * @param b
	 * @return
	 */
	public Complex times(Complex b) {
		Complex a = this;
		double real = a.re * b.re - a.im * b.im;
		double imag = a.re * b.im + a.im * b.re;
		return new Complex(real, imag);
	}

	public double getReal(){
		return this.re;
	}

	public double getImg(){
		return this.im;
	}
}


