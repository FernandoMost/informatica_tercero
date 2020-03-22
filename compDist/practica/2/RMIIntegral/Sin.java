import java.io.Serializable;

class Sin implements Evaluatable, Serializable {
	public double evaluate(double val) {
		return(Math.sin(val));
	}

	public String toString() {
		return("Sin");
	}
}
