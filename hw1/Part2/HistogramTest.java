import vpt.Image;
import vpt.algorithms.display.Display2D;
import vpt.algorithms.io.Load;

public class HistogramTest {
    
    public static void main(String[] args){
              
        if(args.length != 1){
            System.err.println("Wrong Argument List!!!");
            System.err.println("Argument list should include only image path");
            System.exit(1);
        }
      
        Image img =Load.invoke(args[0]);
        
        Image hist =Histogram.equalize(img);
        
        Display2D.invoke(hist, "Adaptive Histogram Equilazed Image");
    
    }
    
}

