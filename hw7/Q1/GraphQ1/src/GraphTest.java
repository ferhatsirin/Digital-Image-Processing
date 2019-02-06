import Graph.*;

import java.util.Random;
import java.util.Vector;

public class GraphTest {

    public static void main(String[] args){

        System.out.println("Graph Q1 Directed Acyclic Graph Test :");
        Graph graph =new ListGraph(10,true);
        Random rand =new Random();
        graph.insert(new Edge(0,3,rand.nextInt(50)+1));
        graph.insert(new Edge(0,5,rand.nextInt(50)+1));
        graph.insert(new Edge(0,2,rand.nextInt(50)+1));
        graph.insert(new Edge(1,4,rand.nextInt(50)+1));
        graph.insert(new Edge(1,8,rand.nextInt(50)+1));
        graph.insert(new Edge(2,8,rand.nextInt(50)+1));
        graph.insert(new Edge(2,9,rand.nextInt(50)+1));
        graph.insert(new Edge(3,2,rand.nextInt(50)+1));
        graph.insert(new Edge(3,8,rand.nextInt(50)+1));
        graph.insert(new Edge(4,6,rand.nextInt(50)+1));
        graph.insert(new Edge(5,9,rand.nextInt(50)+1));
        graph.insert(new Edge(5,2,rand.nextInt(50)+1));
        graph.insert(new Edge(7,4,rand.nextInt(50)+1));
        graph.insert(new Edge(7,9,rand.nextInt(50)+1));
        graph.insert(new Edge(8,7,rand.nextInt(50)+1));
        graph.insert(new Edge(9,6,rand.nextInt(50)+1));
        graph.insert(new Edge(9,2,rand.nextInt(50)+1));
        graph.insert(new Edge(1,7,rand.nextInt(50)+1));
        graph.insert(new Edge(7,6,rand.nextInt(50)+1));
        graph.insert(new Edge(2,7,rand.nextInt(50)+1));

        System.out.println("plot_graph : ");
        AbstractGraph.plot_graph(graph);
        System.out.println("is_undirected : "+AbstractGraph.is_undirected(graph));
        System.out.println("is_acyclic_graph : "+AbstractGraph.is_acyclic_graph(graph));

        Vector<Integer> path =AbstractGraph.shortest_path(graph,0,4);
        System.out.println("Shortest path from 0 to 4 :" +path);
        path =AbstractGraph.shortest_path(graph,3,9);
        System.out.println("Shortest path from 3 to 9 :" +path);
        path =AbstractGraph.shortest_path(graph,1,9);
        System.out.println("Shortest path from 1 to 9 :" +path);
        path =AbstractGraph.shortest_path(graph,0,8);
        System.out.println("Shortest path from 0 to 8 :" +path);

    }

}
