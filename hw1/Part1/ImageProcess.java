import vpt.Image;
import vpt.ByteImage;

public class ImageProcess {
    
    public static Image scale(Image img,double scaleX, double scaleY){
        if(img == null){
            throw new NullPointerException("Image is null!!!");
        }
        if(scaleX <1.0 || scaleY <1.0 ){
            throw new IllegalArgumentException("scaleX and scaleY should be 1 or bigger!!!");
        }
        
        int newWidth =(int)(img.getXDim()*scaleX);
        int newHeight =(int)(img.getYDim()*scaleY);
        Image scaledImage = new ByteImage(newWidth,newHeight,1);
        double x,y,a,b;
        int Q11,Q12,Q21,Q22,xi,yi,value;
        for(int i =0; i<newWidth;++i){
            for(int j =0; j<newHeight;++j){
                //points in the original image
                x =(double)(i)/newWidth*(img.getXDim()-1);
                y =(double)(j)/newHeight*(img.getYDim()-1);
                xi =(int)x;
                yi =(int)y;
                
                //pixel values in the original image
                Q11=img.getXYByte(xi, yi); //f(m,n) 
                Q12 =img.getXYByte(xi,yi+1); //f(m,n+1)
                Q21 =img.getXYByte(xi+1, yi); //f(m+1,n)
                Q22 =img.getXYByte(xi+1,yi+1); //f(m+1,n+1)
                
                //Bilinear interpolation
                a =x-xi;
                b =y-yi;
                value =(int) ((1-a)*(1-b)*Q11 + (1-a)*b*Q12 + a*(1-b)*Q21 + a*b*Q22);
   
                scaledImage.setXYByte(i, j, value);
            }
        }
        return scaledImage;        
    }
    
    public static Image rotate(Image img,double radyan){
    
        double cos =Math.cos(radyan);
        double sin =Math.sin(radyan);
        double x0 =(img.getXDim()-1)/2.0; // center of image
        double y0 =(img.getYDim()-1)/2.0;
        Image rotated =new ByteImage(img.getXDim(),img.getYDim());
        int x,y;
        for(int i=0;i<img.getXDim();++i){
            for(int j=0; j<img.getYDim();++j){
                x =(int) ((i-x0)*cos-(j-y0)*sin+x0); // corresponsing points
                y =(int) ((i-x0)*sin+(j-y0)*cos+y0); // (i-x0) (j-y0) because rotating w.r.t center
                  
                if ( 0 <=x && x < img.getXDim() && 0 <=y && y < img.getYDim()){
                    rotated.setXYByte(i,j,img.getXYByte(x,y));
                }
            }
        }
        return rotated;
    }
}

