import Graph.*;

public class GraphTest {

    public static void main(String[] args){

        System.out.println("Graph Q3 Undirected Cyclic Graph Test :");
        Graph graph =new ListGraph(10,false);
        graph.insert(new Edge(0,4));
        graph.insert(new Edge(0,9));
        graph.insert(new Edge(4,7));
        graph.insert(new Edge(4,5));
        graph.insert(new Edge(5,1));
        graph.insert(new Edge(1,3));
        graph.insert(new Edge(3,8));
        graph.insert(new Edge(7,2));
        graph.insert(new Edge(8,2));
        graph.insert(new Edge(2,6));
        graph.insert(new Edge(6,9));
        graph.insert(new Edge(5,3));
        graph.insert(new Edge(5,7));
        graph.insert(new Edge(3,2));
        graph.insert(new Edge(3,6));
        graph.insert(new Edge(1,8));
        graph.insert(new Edge(8,0));
        graph.insert(new Edge(1,0));
        graph.insert(new Edge(0,7));
        graph.insert(new Edge(1,9));

        System.out.println("plot_graph : ");
        AbstractGraph.plot_graph(graph);
        System.out.println("is_undirected : "+AbstractGraph.is_undirected(graph));
        System.out.println("is_acyclic_graph : "+AbstractGraph.is_acyclic_graph(graph));
        AbstractGraph.DepthFirstSearch(graph);
        AbstractGraph.BreadthFirstSearch(graph);


    }
}
