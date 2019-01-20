import controller.CLI;
import controller.Fractal;
import javafx.application.Application;

public class Main{
	public static void main(String [] args){
		if (args.length == 1 && "cli".equalsIgnoreCase(args[0])) {
            new CLI().runCLI();
        } else {
            Application.launch(Fractal.class);
        }
	}
}
