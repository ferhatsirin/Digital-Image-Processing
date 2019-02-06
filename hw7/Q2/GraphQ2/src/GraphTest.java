import Graph.*;

public class GraphTest {

    public static void main(String[] args){

        System.out.println("Graph Q2 Undirected Acyclic Graph Test :");
        Graph graph =new ListGraph(15,false);
        graph.insert(new Edge(0,14));
        graph.insert(new Edge(14,2));
        graph.insert(new Edge(2,8));
        graph.insert(new Edge(7,8));
        graph.insert(new Edge(3,5));
        graph.insert(new Edge(7,3));
        graph.insert(new Edge(10,5));
        graph.insert(new Edge(6,12));
        graph.insert(new Edge(12,10));
        graph.insert(new Edge(9,13));
        graph.insert(new Edge(4,11));
        graph.insert(new Edge(1,11));
        graph.insert(new Edge(13,4));

        System.out.println("plot_graph : ");
        AbstractGraph.plot_graph(graph);
        System.out.println("is_undirected : "+AbstractGraph.is_undirected(graph));
        System.out.println("is_acyclic_graph : "+AbstractGraph.is_acyclic_graph(graph));

        System.out.println("is_connected from 0 to 11 : " +AbstractGraph.is_connected(graph,0,11));
        System.out.println("is_connected from 2 to 12 :" +AbstractGraph.is_connected(graph,2,12));
        System.out.println("is_connected from 3 to 6 :" +AbstractGraph.is_connected(graph,3,6));
        System.out.println("is_connected from 9 to 10 :" +AbstractGraph.is_connected(graph,9,10));
        System.out.println("is_connected from 1 to 9 :" +AbstractGraph.is_connected(graph,1,9));

    }
}
