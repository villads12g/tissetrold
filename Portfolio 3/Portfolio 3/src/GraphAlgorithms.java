import java.io.*;
import java.util.*;

public class GraphAlgorithms {
    // Calculates the length of a path or any other collection of edges
    public static int pathLength(Collection<Edge> edges){
        return edges.stream().mapToInt(e-> e.weight()).sum();
    }

    //checks if a list of edges form a path
    //each edge must connect to the next
    public static boolean isPath(List<Edge> edges){
        for(int i=1;i<edges.size();i++){
            if(edges.get(i-1).to()!=edges.get(i).from())return false;
        }
        return true;
    }

    //calculates the total lenght a path given as a list
    public static Integer pathLength(Graph g,List<Vertex> path){
        int length=0;
        for(int i=1;i<path.size();i++){
            Integer w=g.getWeight(path.get(i-1),path.get(i));
            if(w==null)return null;
            length+=w;
        }
        return length;
    }

    //compares the edges by weight and then by name
    static int cmpEdgeWeight(Edge e1,Edge e2) {

        int w1=e1.weight(),w2=e2.weight();
        if(w1!=w2)return w1-w2;
        if(e1.from()!=e2.from())return e1.from().name().compareTo(e2.from().name());
        return e1.to().name().compareTo(e2.to().name());
    }
    //compares the egdes by their from vertex
    static int cmpEdgeFrom(Edge e1,Edge e2) {

        if(e1.from()!=e2.from())return e1.from().name().compareTo(e2.from().name());
        int w1=e1.weight(),w2=e2.weight();
        if(w1!=w2)return w1-w2;
        return e1.to().name().compareTo(e2.to().name());
    }
    //compares the egdes by their to vertex
    static int cmpEdgeTo(Edge e1,Edge e2) {
        if(e1.to()!=e2.to())return e1.to().name().compareTo(e2.to().name());
        if(e1.from()!=e2.from())return e1.from().name().compareTo(e2.from().name());
        int w1=e1.weight(),w2=e2.weight();
        return w1-w2;
    }
    // sort a collection of edges based on their weights
    static List<Edge> sortEdges(Collection<Edge> edges){
        ArrayList<Edge> list=new ArrayList<>(edges);
        Collections.sort(list,GraphAlgorithms::cmpEdgeWeight);
        return list;
    }
    // sort a collection of edges based on from-vertex
    static List<Edge> sortEdgesFrom(Collection<Edge> edges){
        ArrayList<Edge> list=new ArrayList<>(edges);
        Collections.sort(list,GraphAlgorithms::cmpEdgeFrom);
        return list;
    }
    // sort a collection of edges based on to-vertex
    static List<Edge> sortEdgesTo(Collection<Edge> edges){
        ArrayList<Edge> list=new ArrayList<>(edges);
        Collections.sort(list,GraphAlgorithms::cmpEdgeTo);
        return list;
    }

    // sort a collection of vertices based on their name
    static List<Vertex> sortVertex(Collection<Vertex> vertices){
        ArrayList<Vertex> list=new ArrayList<>(vertices);
        Collections.sort(list,(Vertex v1,Vertex v2)-> v1.name().compareTo(v2.name()));
        return list;
    }


    // traverse a graph depth first from a given vertex
    // return the set of visited vertices
    public static Set<Vertex> visitBreadthFirst(Graph g,Vertex v){

        HashSet<Vertex> thisLevel=new HashSet<>();
        HashSet<Vertex> nextLevel=new HashSet<>();
        HashSet<Vertex> visited=new HashSet<>();
        thisLevel.add(v);
        while(thisLevel.size()>0){
            System.out.println("level "+thisLevel);
            for(Vertex w:thisLevel){
                //System.out.println("visited "+w);
                visited.add(w);
                Collection<Edge> outedge=g.outEdge(w);
                if(outedge==null)continue;
                for(Edge e: outedge){
                    if(visited.contains(e.to()))continue;
                    if(thisLevel.contains(e.to()))continue;
                    nextLevel.add(e.to());
                }
            }
            thisLevel=nextLevel;
            nextLevel=new HashSet<Vertex>();
        }
        return visited;
    }

    //traverse a graph depth first from a given vertex
    //return the set of visited vertices
    public static Set<Vertex> visitDepthFirst(Graph g,Vertex v){
        HashSet<Vertex> visit=new HashSet<>();
        visitDepthFirst(g, v,visit);
        return visit;
    }

    //recursive helper for depth first traversal
    private static void visitDepthFirst(Graph g,Vertex v,Set<Vertex> visited){
        if(visited.contains(v))return;
        //System.out.println("visited "+v);
        visited.add(v);
        for(Edge e: g.outEdge(v))
            visitDepthFirst(g,e.to(),visited);
    }

    // an implementation of Prim's algorithm
    // naive implementation without priority queue
    public static Set<Edge> minimumSpanningTree(Graph g){
        Collection<Edge> edges=g.edges();
        HashSet<Edge> mst=new HashSet<>();
        HashSet<Vertex> frontier=new HashSet<>();
        for(Edge e:edges){frontier.add(e.from());break;}
        while(true) {
            Edge nearest = null;
            for (Edge e : edges) {
                if (!frontier.contains(e.from())) continue;
                if (frontier.contains(e.to())) continue;
                if (nearest == null || nearest.weight() > e.weight())
                    nearest = e;
            }
            if(nearest==null)break;
            mst.add(nearest);
            frontier.add(nearest.to());
        }
        return mst;
    }

    // returns the tree of shortest paths from start to
    // all vertices  in the graph
    // naive implementation without a prorityqueue
    // create table for done, prev and weight from start
    public static Set<Edge> dijkstra(Graph g, Vertex start){
        int maxint =Integer.MAX_VALUE;
        HashSet<Vertex> done=new HashSet<>();
        HashMap<Vertex,Edge> prev=new HashMap<>();
        HashMap<Vertex,Integer> weight=new HashMap<>();
        for(Vertex w:g.vertices())weight.put(w,maxint);
        // start node is done, distance 0 from start
        weight.put(start,0);
        done.add(start);

        while(true){
            // find nearest from a done vertex
            Vertex nearest = null;
            int neardist = maxint;
            Edge done2near=null;
            for(Vertex w1:done){
                for (Edge e : g.outEdge(w1)) {
                    Vertex w2 = e.to();
                    if (done.contains(w2)) continue;
                    if ((weight.get(w1) + e.weight()) < neardist) {
                        nearest = e.to();
                        neardist = weight.get(w1) + e.weight();
                        done2near = e;
                    }
                }
            }
            // System.out.println("find nearest "+done2near);
            // if no more, then we are done
            if (nearest == null) break;
            // update distance from this node to other nodes
            for (Edge e1 : g.outEdge(nearest)) {
                Vertex w3 = e1.to();
                int wght = e1.weight();
                if (weight.get(w3) > (neardist + wght)) {
                    weight.put(w3, neardist + wght);
                }
            }
            done.add(nearest);
            prev.put(nearest,done2near);
            weight.put(nearest,neardist);
        }
        return new HashSet<Edge>(prev.values());
    }


    // read a comma-separated file in the format
    // stores file as bidirectional graph
    // <vertex> , <vertex> , <weight>
    public static void readGraph(Graph g, String file) {

        try{
            BufferedReader in = new BufferedReader(new FileReader(file));
            for(String line=in.readLine(); line!=null; line=in.readLine()) {
                if(line.length()==0) continue;
                String[] arr = line.split(" , ");
                if(arr.length!=3) throw new RuntimeException("CSV file format error: "+line);
                g.insertEdge(arr[0].trim(), arr[1].trim(), Integer.parseInt(arr[2].trim()));
                g.insertEdge(arr[1].trim(), arr[0].trim(), Integer.parseInt(arr[2].trim()));
            }
            in.close();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    static void printGraph(Graph g) {
        for(Vertex v: sortVertex(g.vertices())) {
            System.out.println(v.toString());
            for(Edge e:sortEdgesTo(g.outEdge(v)))
                System.out.println("  "+e.toString());
        }
    }

    public static void storeStrings(List<String> list,String f){
        // store a list of lines as a file
        try{
            PrintWriter out=new PrintWriter(new FileWriter(f));
            for(String s:list){
                out.println(s);
            }
            out.close();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<String> loadStrings(String f){
        //read a file a returns a list of lines
        ArrayList<String> list=new ArrayList<>();
        try{
            BufferedReader in=new BufferedReader(new FileReader(f));
            while(true){
                String s=in.readLine();
                if(s==null)break;
                list.add(s);
            }
            in.close();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        return list;
    }
    //checks if the graph is connected
    //picks a starting vertex and checks if all other vertices are reachable
    public static boolean isConnected(Graph g) {

        Vertex start = g.vertices().iterator().next();
        Set<Vertex> visited = visitBreadthFirst(g, start);
        return visited.size() == g.vertices().size();
    }
    //prints all vertices by number of connections, highest first
    public static void printVerticesByDegree(Graph g) {

        List<Vertex> vertices = new ArrayList<>(g.vertices());

        vertices.sort((v1, v2) ->
                g.outEdge(v2).size() - g.outEdge(v1).size()
        );

        for (Vertex v : vertices) {
            System.out.println(v + " -> " + g.outEdge(v).size());
        }
    }

    //checks if a schedule is valid
    //invalid if two subjects in the same timeslot share students
    public static boolean isValidSchedule(Graph g, List<List<Vertex>> groups) {

        for (List<Vertex> group : groups) {

            for (int i = 0; i < group.size(); i++) {
                for (int j = i + 1; j < group.size(); j++) {

                    Vertex v1 = group.get(i);
                    Vertex v2 = group.get(j);

                    if (g.getWeight(v1, v2) != null) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
    //calculates total number of students with subjects in adjacent slots
    public static int calcAdjacentStudents(Graph g, List<List<Vertex>> groups) {
        int total = 0;
        for (int i = 0; i < groups.size() - 1; i++) {
            int between = studentsBetweenGroups(g, groups.get(i), groups.get(i + 1));
            System.out.println("  Slot " + (i+1) + " -> Slot " + (i+2) + " : " + between + " students");
            total += between;
        }
        return total;
    }
    //counts the number of students shared between two timeslots
    public static int studentsBetweenGroups(Graph g, List<Vertex> g1, List<Vertex> g2) {

        int count = 0;

        for (Vertex v1 : g1) {
            for (Vertex v2 : g2) {

                Integer w = g.getWeight(v1, v2);

                if (w == null) {
                    w = g.getWeight(v2, v1);
                }

                if (w != null) {
                    count += w;
                }
            }
        }

        return count;
    }
}
