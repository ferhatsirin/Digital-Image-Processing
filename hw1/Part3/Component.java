import java.awt.Point;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import vpt.Image;

public class Component {
    
    /**
     * Find number of component using union find method.
     * @param img 
     * @param connectivity Connectivity can be 4 or 8 
     * @return The number of component
     */
     public static int findNumberOfComponent(Image img,int connectivity){
        if(connectivity != 4 && connectivity !=8){
            throw new IllegalArgumentException("Connecitivity can only be 4 or 8 !!!");
        }
        int width =img.getXDim();
        int height =img.getYDim();
        int[] parent =new int[width*height];
        int[][] labeled =new int[height][width];
        Vector<Point> neighbors;
        HashSet<Integer> set =new HashSet<>();
        int label =1; int value;
        
        for(int i=0;i<height;++i){
            for (int j =0; j < width;++j){
                if(0<img.getXYByte(j,i)){
                    neighbors =priorNeighbors(img,j,i,connectivity);
                    if(neighbors.isEmpty()){
                        value =label;
                        ++label;
                    }
                    else{
                        value =Collections.min(labels(labeled,neighbors));
                    }
                    labeled[i][j] =value;
                    Iterator iter =labels(labeled,neighbors).iterator();
                    while(iter.hasNext()){
                        union(parent,value,(int)iter.next());
                    }
          
                }
            }
        }
        for(int i=0;i<height;++i){
            for(int j=0;j<width;++j){
                if(0 <img.getXYByte(j,i)){
                    value =find(parent,labeled[i][j]);
                    labeled[i][j] =value;
                    set.add(value);
                }
            }
        }
        return set.size();
     }
     // type= 4 for 4 connectivity type= 8 for 8 connectivity
     private static Vector<Point> priorNeighbors(Image img,int x,int y,int type){
         Vector<Point> set =new Vector<>();
         // Points will correspond to 2 dimensional array so change the x and y places
         if(type == 4){
             if ( 0 <=(x-1) && 0 < img.getXYByte(x-1,y)){ // right neighbor
                 set.add(new Point(y,x-1));
             }
             if(0 <=(y-1) && 0 <img.getXYByte(x,y-1)){  // north neighbor
                 set.add(new Point(y-1,x));
             }
         }
         if(type == 8){
              
             if ( 0 <=(x-1) && 0 < img.getXYByte(x-1,y)){ // right neighbor
                 set.add(new Point(y,x-1));
             }
             if(0 <=(y-1) && 0 <img.getXYByte(x,y-1)){  // north neighbor
                 set.add(new Point(y-1,x));
             }
             if(0 <=(x-1) && 0 <=(y-1) && 0 <img.getXYByte(x-1,y-1)){ // northwest neighbor
                 set.add(new Point(y-1,x-1));
             }
             if(0 <= (y-1) && (x+1) <img.getXDim() && 0 <img.getXYByte(x+1,y-1)){ //northeast neighbor
                 set.add(new Point(y-1,x+1));
             }
         }
     
         return set;
     }
     // returns labels related to pixels
     private static Vector<Integer> labels(int[][] labeled,Vector<Point> pixels){
     
         Vector<Integer> list =new Vector<>();
         for(int i=0;i<pixels.size();++i){
             list.add(labeled[pixels.get(i).x][pixels.get(i).y]);
         }
         
         return list;
     }
     
     private static void union(int[] parent,int x, int y){
     
         int parentX =find(parent,x);
         int parentY =find(parent,y);
         if(parentX != parentY){
             parent[parentY] =parentX;
         }
     }
     //finds the parent of that label
     private static int find(int[] parent, int label){
         while(0 <parent[label]){
             label =parent[label];
         }
         return label;
     }
     
     /**
      * Apply dilation operation for the image 
      * @param img
      * @param structureSize Structure size for structuring element
      * @return 
      */
    public static Image dilation(Image img,int structureSize){
        Image dilated =img.newInstance(true);
        boolean done;
        for(int i=0;i<img.getXDim();++i){
            for(int j=0;j<img.getYDim();++j){
                if((i+structureSize) <img.getXDim() && (j+structureSize) <img.getYDim()){
                    done =false;
                    for(int x=i;x<(i+structureSize) && !done;++x){
                        for(int y=j ;y<(j+structureSize) && !done;++y){
                            if(0 <img.getXYByte(x,y)){
                                if(img.getXYByte(i+(structureSize/2),j+(structureSize/2)) <=0){
                                    dilated.setXYByte(i+(structureSize/2),j+(structureSize/2),img.getXYByte(x,y)); 
                                }
                                done =true;
                            }
                        }
                    }
                }
            }
        }
        return dilated;
    }
    
    /**
     * Apply erosion operation for the image 
     * @param img
     * @param structureSize Structure size for structuring element
     * @return 
     */
    public static Image erosion(Image img,int structureSize){
        Image dilated =img.newInstance(true);
        boolean done;
        for(int i=0;i<img.getXDim();++i){
            for(int j=0;j<img.getYDim();++j){
                if((i+structureSize) <img.getXDim() && (j+structureSize) <img.getYDim()){
                    done =false;
                    for(int x=i;x<(i+structureSize) && !done;++x){
                        for(int y=j ;y<(j+structureSize) && !done;++y){
                            if(img.getXYByte(x,y) <=0){
                                if(0 <img.getXYByte(i+(structureSize/2),j+(structureSize/2))){
                                    dilated.setXYByte(i+(structureSize/2),j+(structureSize/2),img.getXYByte(x,y)); 
                                }
                                done =true;
                            }
                        }
                    }
                }
            }
        }
        return dilated;
    }
    /**
     * For opening operation first erosion then dilation operation are performed on the image
     * @param img
     * @param structureSize Structure size for dilation and erosion operations
     * @return Image 
     */
    public static Image opening(Image img,int structureSize){
        img =erosion(img,structureSize);
        return dilation(img,structureSize);
    }
    
    /**
     * For closing operation first dilation then erosion operation are performed on the image
     * @param img
     * @param structureSize Structure size for dilation and erosion operations
     * @return Image
     */
    public static Image closing(Image img,int structureSize){
        img =dilation(img,structureSize);
        return erosion(img,structureSize);
    }

    /**
     * To remove artifacts first opening then closing operations are performed on the image
     * @param img
     * @param structureSize Structure size for opening and closing operations
     * @return The image without artifacts
     */
    public static Image removeArtifact(Image img,int structureSize){
        img =opening(img,structureSize);
        return closing(img,structureSize);
    }
}
