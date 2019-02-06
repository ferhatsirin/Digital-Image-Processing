package Graph;

import java.util.*;

public abstract class AbstractGraph implements Graph {
    private int numV;
    private boolean directed;

    /**
     * if there is a path from vertex1 to vertex 2 then return true otherwise false
     * @param graph graph object
     * @param vertex1 start vertex
     * @param vertex2 end vertex
     * @return true if there is a path between vertices
     */
    public static boolean is_connected(Graph graph,int vertex1,int vertex2) {
        if (vertex1 < 0 || vertex1 >= graph.getNumV() || vertex2 < 0 || vertex2 >= graph.getNumV()) {
            throw new IllegalArgumentException();
        }
        if(vertex1 == vertex2){
            return true;
        }

        Queue<Integer> queue =new LinkedList<Integer>();
        boolean[] identified =new boolean[graph.getNumV()];
        identified[vertex1] =true;
        queue.offer(vertex1);
        while(!queue.isEmpty()){
            int current =queue.remove();
            Iterator iter =graph.edgeIterator(current);
            while(iter.hasNext()){
                Edge edge = (Edge) iter.next();
                int neighbor =edge.getDest();
                if(neighbor == vertex2){
                    return true;
                }
                if(!identified[neighbor]){
                    identified[neighbor] =true;
                    queue.offer(neighbor);
                }
            }
        }
        return false;
    }

    /**
     * if there is no cycle in graph return true otherwise false
     * @param graph graph object
     * @return true if graph is acyclic, false if there is one cycle
     */
    public static boolean is_acyclic_graph(Graph graph){

        for(int i=0;i<graph.getNumV();++i) {
            boolean[] visited =new boolean[graph.getNumV()];
            visited[i] = true;
            if(traverseTree(graph,i,i,visited,1)){
                return false;
            }
        }

        return true;
    }

    private static boolean traverseTree(Graph graph,int vertex,int current,boolean[] visited,int count){
        visited[current] =true;
        Iterator<Edge> iter =graph.edgeIterator(current);
        while(iter.hasNext()){
            int neighbor =iter.next().getDest();
            if(2 < count && neighbor == vertex){
                return true;
            }
            if(!visited[neighbor]){
                ++count;
                return false || traverseTree(graph,vertex,neighbor,visited,count);
            }
        }
        return false;
    }

    /**
     * if graph can be traversed from all the way then it is undirected otherwise directed
     * @param graph graph object
     * @return true if graph is undirected otherwise false
     */
    public static boolean is_undirected(Graph graph){
        for(int i=0;i<graph.getNumV();++i){
            Iterator<Edge> iter =graph.edgeIterator(i);
            while(iter.hasNext()){
                if(graph.getEdge(iter.next().getDest(),i) == null){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Plot graph showing the vertices and their adjacent
     * @param graph graph object
     */
    public static void plot_graph(Graph graph){
        for(int i=0;i<graph.getNumV();++i) {
            Iterator<Edge> iter = graph.edgeIterator(i);
            System.out.printf("%d --> ",i);
            StringBuilder neighbor =new StringBuilder();
            while(iter.hasNext()){
                Edge temp =iter.next();
                neighbor.append(temp.getDest()+" :"+temp.getWeight()+" - ");
            }
            if(neighbor.lastIndexOf(" - ") !=-1){
                neighbor.delete(neighbor.lastIndexOf(" - "),neighbor.length());
            }
            System.out.println(neighbor.toString());
        }
    }

    /**
     * Look the shortest path from vertex1 to vertex 2
     * @param graph graph object
     * @param vertex1 start vertex
     * @param vertex2 end vertex
     * @return vector element including path from vertex1 to vertex 2
     */
    public static Vector<Integer> shortest_path(Graph graph,int vertex1,int vertex2){
        if (vertex1 < 0 || vertex1 >= graph.getNumV() || vertex2 < 0 || vertex2 >= graph.getNumV()) {
            throw new IllegalArgumentException();
        }
        int[] pred =new int[graph.getNumV()];
        double[] dist =new double[graph.getNumV()];
        Vector<Integer> path =new Vector<>();
        if(!is_connected(graph,vertex1,vertex2)){
            return path;
        }
        dijkstrasAlgorithm(graph,vertex1,pred,dist);
        path.add(vertex2);
        while(vertex1 != vertex2){
            path.add(0,pred[vertex2]);
            vertex2 =pred[vertex2];
        }

        return path;
    }

    private static void dijkstrasAlgorithm(Graph graph,int start, int[] pred, double[] dist){

        int numV = graph.getNumV();
        HashSet<Integer> vMinusS =new HashSet<Integer>(numV);
        for(int i=0;i<numV;++i){
            if(i !=start && is_connected(graph,start,i)){
                vMinusS.add(i);
            }
        }
        for(int v :vMinusS){
            Edge edge =graph.getEdge(start,v);
            if(edge != null){
                pred[v] =start;
                dist[v] =edge.getWeight();
            }
            else {
                pred[v] =0;
                dist[v] =Double.POSITIVE_INFINITY;
            }
        }

        while(vMinusS.size() !=0){
            double minDist =Double.POSITIVE_INFINITY;
            int u =-1;
            for(int v : vMinusS){
                if(dist[v] < minDist){
                    minDist =dist[v];
                    u =v;
                }
            }
            vMinusS.remove(u);
            for(int v : vMinusS){
                if(graph.isEdge(u,v)){
                    double weight =graph.getEdge(u,v).getWeight();
                    if(dist[u]+weight < dist[v]){
                        dist[v] =dist[u]+weight;
                        pred[v] =u;
                    }
                }
            }
        }
    }



    public AbstractGraph(int v,boolean d){
        numV =v;
        directed =d;
    }

    public int getNumV(){
        return numV;
    }

    public boolean isDirected(){
        return directed;
    }

    public static Graph createGraph(Scanner scan, boolean isDirected, String type){

        int numV =scan.nextInt();
        scan.nextLine();
        AbstractGraph returnValue =null;
        if(type.equalsIgnoreCase("List")){
            returnValue =new ListGraph(numV,isDirected);
        }
        else{
            throw new IllegalArgumentException();
        }
        returnValue.loadEdgesFromFile(scan);
        return returnValue;
    }

    private void loadEdgesFromFile(Scanner scan){

        String[] line;
        while(scan.hasNext()){
            line =scan.nextLine().split(" ");
            new Edge(Integer.parseInt(line[0]),
                    Integer.parseInt(line[1]),
                    line.length ==3  ? Integer.parseInt(line[2]) : 1.0);
        }
    }

    public static int[] breadthFirstSearch(AbstractGraph graph,int start){

        Queue<Integer> queue =new LinkedList<Integer>();
        int[] parent =new int[graph.getNumV()];
        for(int i=0;i<graph.getNumV();++i){
            parent[i] =-1;
        }
        boolean[] identified =new boolean[graph.getNumV()];
        identified[start] =true;
        queue.offer(start);
        while(!queue.isEmpty()){
            int current =queue.remove();
            Iterator<Edge> itr =graph.edgeIterator(current);
            while(itr.hasNext()){
                Edge edge =itr.next();
                int neighbor =edge.getDest();
                if(!identified[neighbor]){
                    identified[neighbor] =true;
                    queue.offer(neighbor);
                    parent[neighbor] =current;
                }
            }
        }
        return parent;
    }
}