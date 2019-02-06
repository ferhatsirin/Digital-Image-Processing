
import java.io.IOException;
import vpt.Image;
import vpt.algorithms.display.Display2D;
import vpt.algorithms.io.Load;

public class ComponentTest {

    public static void main(String[] args) throws IOException {
    
        if(args.length != 2 && args.length != 3){
        
            System.err.println("Wrong Argument List!!!");
            System.err.println("Argument list should be like that :");
            System.err.println("path of image, connectivity, structure size (optional default as 7)");
            System.exit(1);
        }
               
        Image img = Load.invoke(args[0]);
        int connectivity =Integer.parseInt(args[1]);
        int structureSize =7;
        if(args.length == 3){
            structureSize =Integer.parseInt(args[2]);
        } 
        img =Component.removeArtifact(img,structureSize);
        System.out.println("Structuring size = "+structureSize);
        System.out.println("Number of component with respect to "+connectivity+ " connectivity ="+Component.findNumberOfComponent(img,connectivity));
        Display2D.invoke(img,"Image without artifacts");
    }
}
