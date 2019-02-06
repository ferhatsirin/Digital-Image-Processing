import vpt.algorithms.io.Load;
import vpt.Image;
import vpt.algorithms.display.Display2D;

public class ImageProcessTest {
    
    public static void main(String[] args){
        
        if(args.length != 4){
            System.err.println("Wrong Argument List!!!");
            System.err.println("Argument list should be like that :");
            System.err.println("path of image scaleX scaleY radyan");
            System.err.println("valve.png 2.0 2.0 1.5707");
            System.exit(1);
        }

        Image img =Load.invoke(args[0]);
        System.out.printf("Original image widthxheight : %dx%d\n",img.getXDim(),img.getYDim());
        
        Image scaled =ImageProcess.scale(img, Double.parseDouble(args[1]), Double.parseDouble(args[2]));
        Image rotated =ImageProcess.rotate(scaled,Double.parseDouble(args[3]));
        
        System.out.printf("Scaled and rotated image widthxheight : %dx%d\n",rotated.getXDim(),rotated.getYDim());
        
        Display2D.invoke(rotated, "Scaled rotated image");
        
    }
}
