package Graph;

public class Edge {

    private int source;
    private int dest;
    private double weight;

    /**
     * create edge with values given
     * @param s source
     * @param d destination
     * @param w weight
     */
    public Edge(int s,int d,double w){
        source =s;
        dest =d;
        weight =w;
    }

    /**
     * create edge with values given at weight 1.0
     * @param s source
     * @param d destination
     */
    public Edge(int s,int d){
        this(s,d,1.0);
    }

    /**
     *
     * @param o other edge
     * @return true if edge is equal
     */
    public boolean equals(Object o){
        Edge obj =(Edge)o;
        return source ==obj.source && dest == obj.dest;
    }

    /**
     * Return destination
     * @return destination
     */
    public int getDest(){
        return dest;
    }


    /**
     * return source
     * @return source
     */
    public int getSource(){
        return source;
    }
    /**
     * return weight
     * @return weight
     */
    public double getWeight(){
        return weight;
    }

    /**
     * print edge
     * @return string to print edge
     */
    public String toString(){
        return String.format(source+" --> "+dest+ " : "+weight);
    }

}