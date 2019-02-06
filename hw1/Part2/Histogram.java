import vpt.ByteImage;
import vpt.Image;

public class Histogram {

    /**
     * Adaptive histogram equalization
     * @param img to be equalized
     * @return a new image that applied adaptive histogram equalization
     */
    public static Image equalize(Image img){
        int width =img.getXDim();
        int height =img.getYDim();
        Image output =new ByteImage(width,height,1);
        Image grid;
        int gridSize =50; // as default 50
        int rank;
        for(int i=0;i<width;++i){
            for(int j=0;j<height;++j){
                grid =sliceImage(img,i,j,gridSize);
                rank =0;
                for(int x =0;x <gridSize ;++x){  // calculate histogram for that grid
                    for(int y =0;y<gridSize ;++y){
                        if(img.getXYByte(i,j) >= grid.getXYByte(x,y)){
                            ++rank;
                        }
                    }
                }
                output.setXYByte(i,j,rank*255/(gridSize*gridSize));
            }
        }
        return output;
    }
         
        
    private static Image sliceImage(Image img,int x,int y,int size){
       Image out =new ByteImage(size,size,1);
       int startX =x-(size/2); int startY =y-(size/2);
      
       for(int i=0; i<size && (i+startX) <img.getXDim();++i){
           for(int j=0;j<size && (j+startY) <img.getYDim();++j){
               if(0 <=(i+startX) && 0 <=(j+startY))
               out.setXYByte(i,j,img.getXYByte(i+startX,j+startY));
           }
       }
        return out;
    }
}
