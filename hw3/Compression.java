import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import vpt.ByteImage;
import vpt.Image;

public class Compression {

    private String compressedY;
    private String compressedU;
    private String compressedV;
    private HuffmanNode treeForY;
    private HuffmanNode treeForU;
    private HuffmanNode treeForV;
    private int imgWidth;
    private int imgHeight;
    private int paddedWidth;
    private int paddedHeight;
    private static int N = 8;
    private int[][] YQuan = new int[N][N];
    private int[][] UVQuan = new int[N][N];
    private static double[][] DCTMatrix = new double[N][N];
    private static int[][] lumaQ
            = {{16, 11, 10, 16, 24, 40, 51, 61},
            {12, 12, 14, 19, 26, 58, 60, 55},
            {14, 13, 16, 24, 40, 57, 69, 56},
            {14, 17, 22, 29, 51, 87, 80, 62},
            {18, 22, 37, 56, 68, 109, 103, 77},
            {24, 35, 55, 64, 81, 104, 113, 92},
            {49, 64, 78, 87, 103, 121, 120, 101},
            {72, 92, 95, 98, 112, 100, 103, 99}};

    private static int[][] chromaQ
            = {{17, 18, 24, 47, 99, 99, 99, 99},
            {18, 21, 26, 66, 99, 99, 99, 99},
            {24, 26, 56, 99, 99, 99, 99, 99},
            {47, 66, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99}};

    static {
        createDCTMatrix();
    }
    private String searchTreeForCodeResult;
    private Integer searchTreeForValueResult;
    
    /**
     * Compress the image, hold the data inside
     * @param img to be compressed
     * @param ratio compression ratio should be between 1 and 100. at 100 image quality change drastically, 1 for best quality 
     */
    public void jpegCompression(Image img, int ratio) {
        imgWidth =img.getXDim();
        imgHeight =img.getYDim();
        paddedWidth = paddingSize(img.getXDim());
        paddedHeight = paddingSize(img.getYDim());

        int[][] Y = new int[paddedHeight][paddedWidth];
        int[][] U = new int[paddedHeight][paddedWidth];
        int[][] V = new int[paddedHeight][paddedWidth];

        //RGB to YUV conversion
        RGBToYUV(img, Y, U, V);

        //prepare quantization matrices according to ratio
        prepareQuantization(YQuan,UVQuan,ratio);

        // DCT forward and Quantization
        for (int i = 0; i < paddedHeight; i += N) {
            for (int j = 0; j < paddedWidth; j += N) {
                DCTandQuantization(Y,U,V, i, j, 1);
            }
        }

        // run length compression
        int[] runY =runLengthCoding(Y);
        int[] runU =runLengthCoding(U);
        int[] runV =runLengthCoding(V);
       
        // create tree for YUV individually for huffman coding
        treeForY =createHuffmanTree(runY);
        treeForU =createHuffmanTree(runU);
        treeForV =createHuffmanTree(runV);
          
        //huffman coding 
        compressedY =huffmanCoding(runY,treeForY);
        compressedU =huffmanCoding(runU,treeForU);
        compressedV =huffmanCoding(runV,treeForV);
    }
    /**
     * Decompress the compressed image.  
     * @return The image decompressed
     */
    public Image jpegDecompression(){
 
        //decoding huffman code first
        int[] runY =huffmanDecoding(compressedY,treeForY);
        int[] runU =huffmanDecoding(compressedU,treeForU);
        int[] runV =huffmanDecoding(compressedV,treeForV);

        //decoding run length code
        int[][] Y =runLengthDecoding(runY);
        int[][] U =runLengthDecoding(runU);
        int[][] V =runLengthDecoding(runV);

        // DCT backward and Dequantization
        for (int i = 0; i < paddedHeight; i += N) {
            for (int j= 0; j < paddedWidth; j += N) {
                DCTandQuantization(Y,U,V, i, j, 0);
            }
        }
        //returns YUV values to RGB and assign them to the created image
        Image img =new ByteImage(imgWidth,imgHeight,3);
        for(int i=0;i<imgWidth;++i){
            for(int j=0;j<imgHeight;++j){
                                
                int R =(int) Math.round(1.0004216998181619*Y[j][i]+0.00030204801942090853*U[j][i]
                        +1.4046592342077235*V[j][i]-0.00030204801942090853*128-1.4046592342077235*128);
                int G =(int) Math.round(0.9994403623704784*Y[j][i]-0.3449324661155515*U[j][i]
                        -0.7156838780006247*V[j][i]+0.3449324661155515*128+0.7156838780006247*128);
                int B =(int) Math.round(1.0017756056391183*Y[j][i]+1.7753074144910936*U[j][i]
                        +0.0009940820899769348*V[j][i]-1.7753074144910936*128-0.0009940820899769348*128);

                if(R < 0)
                    R =0;
                else if(255<R)
                    R =255;
                if(G <0)
                    G =0;
                else if(255<G)
                    G =255;
                if(B < 0)
                    B =0;
                else if(255<B)
                    B =255;

                img.setXYCByte(i, j, 0, R);
                img.setXYCByte(i, j, 1, G);
                img.setXYCByte(i, j, 2, B);
            }
        }
        return img;
    }
    /**
     * Measures the change between original and compressed image
     * @param orgin the original image
     * @param decoded the image that first compressed then decompressed
     * @return the mean squared error value
     */
    public double meanSquaredError(Image orgin,Image decoded){
        int sum =0;
        for(int i=0;i<orgin.getXDim();++i){
            for(int j=0;j<orgin.getYDim();++j){
                for(int c=0;c<3;++c){
                    sum +=Math.pow((orgin.getXYCByte(i, j, c)-decoded.getXYCByte(i, j, c)),2.0);
                }
            }
        }
        double result =sum/(3.0*orgin.getXDim()*orgin.getYDim());
        return result;
    }
    
    /**
     * Apply 8*8 DCT matrix for Y U V matrices and quantization operation 
     * @param Y For Y matrix
     * @param U For U matrix
     * @param V For V matrix
     * @param startX Where to start index for YUV
     * @param startY Where to start index for YUV
     * @param dir 1 means forwards, otherwise backwards 
     */
    private void DCTandQuantization(int[][] Y,int[][] U,int[][] V, int startX, int startY, int dir) {

        double[][] multY = new double[N][N];
        double[][] multU =new double[N][N];
        double[][] multV =new double[N][N];
        if (dir == 1) {
            for (int i = 0; i < N; ++i) {
                for (int j = 0; j < N; ++j) {
                    for (int t = 0; t < N; ++t) {
                        multY[i][j] += DCTMatrix[i][t] * Y[startX+t][startY+j];
                        multU[i][j] += DCTMatrix[i][t] * U[startX+t][startY+j];
                        multV[i][j] += DCTMatrix[i][t] * V[startX+t][startY+j];
                    }
                }
            }
            double[][] multY2 =new double[N][N];
            double[][] multU2 =new double[N][N];
            double[][] multV2 =new double[N][N];
            for (int i = 0; i < N; ++i) {
                for (int j = 0; j < N; ++j) {
                    for (int t = 0; t < N; ++t) {
                        multY2[i][j] += multY[i][t] * DCTMatrix[j][t];
                        multU2[i][j] += multU[i][t] * DCTMatrix[j][t];
                        multV2[i][j] += multV[i][t] * DCTMatrix[j][t];
                    }
                    // Quantization
                    Y[startX+i][startY+j] =(int)Math.round(multY2[i][j]/YQuan[i][j]);
                    U[startX+i][startY+j] =(int)Math.round(multU2[i][j]/UVQuan[i][j]);
                    V[startX+i][startY+j] =(int)Math.round(multV2[i][j]/UVQuan[i][j]);
                }
            }
        } else {
            // dct backward and dequantization
            for (int i = 0; i < N; ++i) {
                for (int j = 0; j < N; ++j) {
                    for (int t = 0; t < N; ++t) {
                        multY[i][j] += DCTMatrix[t][i] * Math.round(Y[startX+t][startY+j]*YQuan[t][j]);
                        multU[i][j] += DCTMatrix[t][i] * Math.round(U[startX+t][startY+j]*UVQuan[t][j]);
                        multV[i][j] += DCTMatrix[t][i] * Math.round(V[startX+t][startY+j]*UVQuan[t][j]);
                    }
                }
            }

            for (int i = 0; i < N; ++i) {
                for (int j = 0; j < N; ++j) {
                    Y[startX+i][startY+j] = 0;
                    U[startX+i][startY+j] =0;
                    V[startX+i][startY+j] =0;
                    for (int t = 0; t < N; ++t) {
                        Y[startX+i][startY+j] = (int)Math.round(Y[startX+i][startY+j]+multY[i][t] * DCTMatrix[t][j]);
                        U[startX+i][startY+j] = (int)Math.round(U[startX+i][startY+j]+multU[i][t] * DCTMatrix[t][j]);
                        V[startX+i][startY+j] = (int)Math.round(V[startX+i][startY+j]+multV[i][t] * DCTMatrix[t][j]);
                    }
                }
            }
        }
    }
    /**
     * Apply run length coding for given matrix
     * @param B 2 dimensional matrix
     * @return 1 dimensional matrix containing run length coded values
     */
    private int[] runLengthCoding(int[][] B) {
        int height =B.length;
        int width =B[0].length;
        int[] arr = new int[width*height];
        int k = 0, i = 0, j = 0;
        
        //follows the zig zag path
        while (k < height*width) {
            if (j == width-1) {
                for (int count = 0; count< 2 && i< height && j< width && k< width*height; ++count,++k,++i) {
                    arr[k] = B[i][j];
                }
                --i;
            } else {
                for (int count = 0; count< 2 && i< height && j< width && k< width*height; ++count,++k,++j) {
                    arr[k] = B[i][j];
                }
                --j;
            }

            for (i = i+1, j=j-1; i< height-1 && 0<j && k< width*height; ++i,--j,++k) {
                arr[k] = B[i][j];
            }
            if (i == height-1) {
                for (int count =0; count< 2 && i< height && j< width && k< width*height; ++k,++count,++j) {
                    arr[k] = B[i][j];
                }
                --j;
            }
            else{
                for (int count = 0; count < 2 && i<height && j<width && k< width*height; ++k,++count,++i) {
                    arr[k] = B[i][j];
                }
                --i;
            }
            for (i = i-1, j = j+1; 0 < i && j < width-1 && k < width*height; --i,++j,++k) {
                arr[k] = B[i][j];
            }
        }
        //code the values
        ArrayList<Integer> list =new ArrayList<>();
        int count =1;
        int last =arr[0]; int current;
        for(int x=1;x<width*height;++x){
            current =arr[x];
            if(last == current){
                ++count;
            }
            else{
                list.add(count);
                list.add(last);
                count =1;
                last =current;
            }
        }
        list.add(count);
        list.add(last);

        return list.stream().mapToInt(p -> p).toArray();
    }
    
    /**
     * Decodes the run length code
     * @param code 1 dimensional run length coded matrix
     * @return 2 dimensional decoded matrix
     */
    private int[][] runLengthDecoding(int[] code){
        int width =paddedWidth;
        int height =paddedHeight;
        int[][] arr =new int[height][width];
        int[] values =new int[height*width];
        
        //amplifies the coded matrix  
        for(int i=0,k=0;i<code.length;i +=2){
            int count =code[i];
            for(int j=0;j<count;++j,++k){
                values[k] =code[i+1];
            }
        }
        //follows the zig zag path
        int k = 0, i = 0, j = 0;
        while (k < values.length) {
            if (j == width-1) {
                for (int count = 0; count< 2 && i< height && j< width && k< width*height; ++count,++k,++i) {
                    arr[i][j] = values[k];
                }
                --i;
            } else {
                for (int count = 0; count< 2 && i< height && j< width && k< width*height; ++count,++k,++j) {
                    arr[i][j] = values[k];
                }
                --j;
            }

            for (i = i+1, j=j-1; i< height-1 && 0<j && k< width*height; ++i,--j,++k) {
                arr[i][j] = values[k];
            }
            if (i == height-1) {
                for (int count =0; count< 2 && i< height && j< width && k< width*height; ++k,++count,++j) {
                    arr[i][j] = values[k];
                }
                --j;
            }
            else{
                for (int count = 0; count < 2 && i<height && j<width && k< width*height; ++k,++count,++i) {
                    arr[i][j] = values[k];
                }
                --i;
            }
            for (i = i-1, j = j+1; 0 < i && j < width-1 && k < width*height; --i,++j,++k) {
                arr[i][j] = values[k];
            }
        }

        return arr;
    }
    
    /**
     * Code the array according to huffman tree
     * @param arr the array to be coded
     * @param root the huffman tree
     * @return String containing bits patterns 
     */
    private String huffmanCoding(int[] arr,HuffmanNode root){
    
        StringBuilder code =new StringBuilder("");
        for(int i=0;i<arr.length;++i){
            searchTreeForCodeResult =null;
            searchTreeForCode(root,arr[i],"");
            if(searchTreeForCodeResult !=null){
                code.append(searchTreeForCodeResult);
            }
        }
        return code.toString();
    }
    /**
     * Decode the huffman code according to the huffman tree
     * @param code The coded bit patterns
     * @param root The huffman tree
     * @return The decoded array
     */
    private int[] huffmanDecoding(String code,HuffmanNode root){
    
        ArrayList<Integer> values =new ArrayList<>();
        
        while(!code.isEmpty()){
            searchTreeForValueResult =null;
            searchTreeForValue(root,code);
            if(searchTreeForValueResult !=null){
                values.add(searchTreeForValueResult);
                code =searchTreeForCodeResult;
            }
        }
        return values.stream().mapToInt(p -> p).toArray();
    }
    
    /**
     * Looks the path to find a value
     * @param root the huffman tree
     * @param path bit patterns
     */
    private void searchTreeForValue(HuffmanNode root,String path){
        
        if(root != null){
            if(root.left ==null && root.right == null){
                searchTreeForValueResult =root.value;
                searchTreeForCodeResult =path.toString();
                return;
            }
            if(path.charAt(0) == '0'){
                searchTreeForValue(root.left,path.substring(1));
            }
            else if(path.charAt(0) == '1'){
                searchTreeForValue(root.right,path.substring(1));
            }
        }
    }
     
    /**
     * Looks the tree to find a path for the given value
     * @param root the huffman tree
     * @param value the value to be found
     * @param path the path of value
     */
    private void searchTreeForCode(HuffmanNode root,int value,String path){
        
        if(root != null){
            if(root.left ==null && root.right == null){
                if(root.value ==value){
                    searchTreeForCodeResult =path;
                    return;
                }
            }
            searchTreeForCode(root.left,value,path +"0");
            searchTreeForCode(root.right,value,path +"1");
        }
    }
    
    /**
     * Creates a huffman tree for given array
     * @param arr 1 dimensional array
     * @return the huffman tree for that array
     */
    private HuffmanNode createHuffmanTree(int[] arr){
    
        PriorityQueue<HuffmanNode> heap =new PriorityQueue<>(arr.length,new Comparator<HuffmanNode>(){
            @Override
            public int compare(HuffmanNode o1, HuffmanNode o2) {
                return o1.freq-o2.freq;
            }
        });

        //values and their frequencies
        Set<Integer> set =new HashSet<Integer>();
        for(int i=0;i<arr.length;++i){
            set.add(arr[i]);
        }
        int[] values =set.stream().mapToInt(p -> p).toArray();
        int[] freq =new int[values.length];
        for(int i=0;i<values.length;++i){
            int count =0;
            for(int j=0;j<arr.length;++j){
                if(values[i] == arr[j]){
                    ++count;
                }
            }
            freq[i] =count;
        }
        
        for(int i=0;i<values.length;++i){
            HuffmanNode temp =new HuffmanNode(freq[i],values[i]);
            heap.add(temp);
        }

        //creates tree
        HuffmanNode root = null;
        while(heap.size()>1){
        
            HuffmanNode first =heap.poll();
            HuffmanNode second =heap.poll();
            HuffmanNode temp =new HuffmanNode(first.freq+second.freq,Byte.MIN_VALUE);
            temp.left =first;
            temp.right =second;
            root =temp;
            heap.add(temp);
        }
        return root;
    }
    
    private class HuffmanNode {
        private int value;
        private int freq;
        private HuffmanNode left;
        private HuffmanNode right;
        
        public HuffmanNode(int freq,int value){
            this.value =value;
            this.freq =freq;
            left =null;
            right =null;
        }
        public String toString(){
            return String.format("%d %d",freq, value);
        }
    }
    
    /**
     * Converts RGB codes to YUV
     * @param img the images containing RGB values
     * @param Y for luminance 
     * @param U for chrominance blue -yellow
     * @param V for chrominance red -cyan
     */
    private void RGBToYUV(Image img, int[][] Y, int[][] U, int[][] V) {
        int R, G, B;
        for (int i = 0; i < img.getYDim(); ++i) {
            for (int j = 0; j < img.getXDim(); ++j) {
                R = img.getXYCByte(j, i, 0);
                G = img.getXYCByte(j, i, 1);
                B = img.getXYCByte(j, i, 2);

                Y[i][j] = (int) Math.round(0.299 * R + 0.587 * G + 0.114 * B);
                U[i][j] = (int) Math.round(-0.169*R-0.331*G+0.499*B+128);
                V[i][j] = (int) Math.round(0.499*R-0.418*G-0.0813*B+128);
            }
        }
    }

    /**
     * Prepares the quantization matrix according to ratio
     * @param YQuan for Y luminance
     * @param UVQuan for UV chrominance
     * @param ratio ratio
     */
    private void prepareQuantization(int[][] YQuan,int[][] UVQuan, int ratio) {

        int q =100-ratio;
        if (q < 1) {
            q = 1;
        }
        if (100 < q) {
            q = 100;
        }
        if (q <= 50) {
            q = 5000 / q;
        } else {
            q = 200 - 2 * q;
        }
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                YQuan[i][j] = (int) Math.floor((q * lumaQ[i][j] + 50.0) / 100.0);
                UVQuan[i][j] = (int) Math.floor((q * chromaQ[i][j] + 50.0) / 100.0);
                if (YQuan[i][j] < 1) {
                    YQuan[i][j] = 1;
                } else if (255 < YQuan[i][j]) {
                    YQuan[i][j] = 255;
                }
                if(UVQuan[i][j] <1){
                    UVQuan[i][j] =1;
                }
                else if(255 <UVQuan[i][j]){
                    UVQuan[i][j] =255;
                }
            }
        }
    }

    /**
     * calculate the size that can be divided by 8.
     * @param size
     * @return Size that can be divided by 8.
     */
    private int paddingSize(int size) {
        while ((size % 8) != 0) {
            ++size;
        }
        return size;
    }

    private static void createDCTMatrix() {

        double value = Math.sqrt(1.0 / N);
        for (int i = 0; i < N; ++i) {
            DCTMatrix[0][i] = value;
        }
        value = Math.sqrt(2.0 / N);
        for (int i = 1; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                DCTMatrix[i][j] = value * Math.cos((2 * j + 1) * i * Math.PI / (2.0 * N));
            }
        }
    }
}

