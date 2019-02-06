package Graph;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListGraph extends AbstractGraph {
    private List<Edge>[] edges;

    public ListGraph(int v,boolean d){
        super(v,d);
        edges =new List[v];
        for(int i=0;i<v;++i){
            edges[i] =new LinkedList<Edge>();
        }
        System.out.printf("%d verteces ",v);
        if(d){
            System.out.printf("Directed graph created\n");
        }
        else{
            System.out.printf("Undirected graph created\n");
        }


    }

    /**
     * Look if there is edge betweeen source and destination vertex
     * @param source source vertex
     * @param dest destination vertex
     * @return if there is edge between source and edge return true else false
     */

    public boolean isEdge(int source,int dest){
        return edges[source].contains(new Edge(source,dest));
    }

    /**
     * Insert new edge to graph
     * @param edge new edge to be added
     */
    public void insert(Edge edge){
        edges[edge.getSource()].add(edge);
        System.out.println("New edge created "+edge);
        if(!isDirected()){
            edges[edge.getDest()].add(new Edge(edge.getDest(),edge.getSource(),edge.getWeight()));
        }
    }

    /**
     * Iterator to traverse graph
     * @param source start vertex for iterator
     * @return return iterator
     */
    public Iterator<Edge> edgeIterator(int source){
        return edges[source].iterator();
    }

    /**
     * if there is edge between source and destination return edge otherwise null
     * @param source source
     * @param dest destination
     * @return edge if there exists otherwise null
     */
    public Edge getEdge(int source,int dest){
        Edge target =new Edge(source,dest,Double.POSITIVE_INFINITY);
        for(Edge edge:edges[source]){
            if(edge.equals(target)){
                return edge;
            }
        }
        return null;
    }


}