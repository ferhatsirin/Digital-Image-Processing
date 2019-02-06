import java.io.IOException;
import vpt.Image;
import vpt.algorithms.display.Display2D;
import vpt.algorithms.io.Load;

public class CompressionTest {

    public static void main(String[] args) throws IOException {
        
        if(args.length != 2){
        
            System.err.println("Wrong Argument List!!");
            System.err.println("Argument list should be like this");
            System.err.println("./exe path of image ratio of compression (1-100)");
        }
        int ratio =Integer.parseInt(args[1]);
        
        Image img =Load.invoke(args[0]);
        Display2D.invoke(img, "Original Image",true);
        
        Compression comp =new Compression();
        comp.jpegCompression(img, ratio);
        Image decompressed=comp.jpegDecompression();
        Display2D.invoke(decompressed, "Decompressed Image",true);
        
        System.out.printf("Mean squared error: %.2f at compression ratio: %d \n", comp.meanSquaredError(img, decompressed),ratio);
    }
}

