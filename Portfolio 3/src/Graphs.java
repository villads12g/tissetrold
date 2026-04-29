import java.util.*;
public class Graphs {}

class Vertex{
    private String name;
    public String name(){return name;}
    public Vertex(String s){name=s;}
    public String toString(){return name;}
}
class Edge{
    private Vertex from,to;
    private int weight;
    public Vertex from(){return from;}
    public Vertex to(){return to;}
    public int weight(){return weight;}
    Edge(Vertex from,Vertex to,int w){this.from=from; this.to=to; weight=w;}
    public String toString(){return from.name()+" - "+weight+" -> "+to.name(); }
}

interface Graph {
    void insertEdge(String v, String u, int w);
    Vertex vertex(String s);
    Collection<Vertex> vertices();
    Collection<Edge> edges();
    Collection<Edge> outEdge(Vertex v);
    Integer getWeight(Vertex v1, Vertex v2);
    Vertex getVertex(String name);

}

abstract class AbstractGraph implements Graph{
    private HashMap<String,Vertex> vertexMap=new HashMap<>();
    private HashSet<Vertex> vertexSet=new HashSet<>();
    public Vertex vertex(String s){
        if(vertexMap.containsKey(s)) return vertexMap.get(s);
        Vertex v = new Vertex(s);
        vertexMap.put(s, v);
        vertexSet.add(v);
        return v;
    }                             // ← this closing brace was missing

    @Override
    public Vertex getVertex(String name) {
        for (Vertex v : vertices()) {
            if (v.name().equals(name)) {
                return v;
            }
        }
        return null;
    }

    public void insertEdge(String v, String u, int w){
        insertEdge(vertex(v), vertex(u), w);
    }

    public Collection<Vertex> vertices() { return vertexSet; }
    //
    abstract public void insertEdge(Vertex v1, Vertex v2, int w);
    abstract public Collection<Edge> edges();
    abstract public Collection<Edge> outEdge(Vertex v);
    abstract public Integer getWeight(Vertex v1, Vertex v2);
}

//--------------------------------------------------------
//  EdgeGraph - One big set of all edges in the graph

class EdgeGraph extends AbstractGraph {
    Set<Edge> edges=new HashSet<>();
    public void insertEdge(Vertex v1,Vertex v2,int w){
        edges.add(new Edge(v1,v2,w));
    }
    public Collection<Edge> edges(){return edges;}
    public Collection<Edge> outEdge(Vertex v){
        ArrayList<Edge> outEdge=new ArrayList<>();
        for(Edge e:edges)if(e.from()==v)outEdge.add(e);
        return outEdge;
    }
    public Integer getWeight(Vertex v1,Vertex v2){
        // linear in number of edges in the graph
        for(Edge e:edges){
            if(e.from()==v1 && e.to()==v2)return e.weight();
        }
        return null;

    }
}

//--------------------------------------------------------
//  Adjecency List Graph - A map from vertices to set of outedges from the vertex

class AdjListGraph extends AbstractGraph {
    private Map<Vertex,Set<Edge>> outEdge= new HashMap<>();
    public void insertEdge(Vertex v1,Vertex v2,int w){
        Edge e=new Edge(v1,v2,w);
        if(!outEdge.containsKey(e.from()))
            outEdge.put(e.from(),new HashSet<Edge>());
        outEdge.get(e.from()).add(e);
    }
    public Collection<Edge> edges(){
        Set<Edge> edges=new HashSet<>();
        for(Vertex v:outEdge.keySet())edges.addAll(outEdge.get(v));
        return edges;
    }
    public Collection<Edge> outEdge(Vertex v){
        if(!outEdge.containsKey(v))
            return new HashSet<Edge>();
        return outEdge.get(v);
    }
    public Integer getWeight(Vertex v1,Vertex v2){
        // linear in number of outedges from vertices
        if(!outEdge.containsKey(v1))return null;
        for(Edge e:outEdge.get(v1)){
            if(e.to()==v2)return e.weight();
        }
        return null;
    }
}

//--------------------------------------------------------
//  Adjecency Map Graph - A map from vertices to map of target vertex to edge

class AdjMapGraph extends AbstractGraph {
    private Map<Vertex, Map<Vertex, Edge>> outEdge = new HashMap<>();

    public void insertEdge(Vertex v1, Vertex v2, int w) {
        Edge e = new Edge(v1,v2, w);
        if (!outEdge.containsKey(e.from()))
            outEdge.put(e.from(), new HashMap<Vertex, Edge>());
        outEdge.get(e.from()).put(e.to(), e);
    }
    public Collection<Edge> edges() {
        Set<Edge> edges = new HashSet<>();
        for (Vertex v : outEdge.keySet())
            for (Vertex w : outEdge.get(v).keySet())
                edges.add(outEdge.get(v).get(w));
        return edges;
    }
    public Collection<Edge> outEdge(Vertex v) {
        return outEdge.get(v).values();
    }
    public Integer getWeight(Vertex v1, Vertex v2) {
        // constant time operation
        if(!outEdge.containsKey(v1))return null;
        if(!outEdge.get(v1).containsKey(v2))return null;
        return outEdge.get(v1).get(v2).weight();
    }
}

//--------------------------------------------------------
//  Matrix Graph:  weights are stored in a twodimensional array

class MatrixGraph extends AbstractGraph {
    private Integer[][] matrix=null; // made in constructor
    // We must be able to map vertices to index in matrix and back again
    private Vertex[] index2vertex; // made in constructor
    private Map<Vertex,Integer> vertex2index=new HashMap<>();
    private int numVertex; // maximum number of vertices
    MatrixGraph(int numVertex){ // maximum number of vertices allowed
        this.numVertex=numVertex;
        matrix =new Integer[numVertex][numVertex];
        index2vertex=new Vertex[numVertex];
    }
    private int getIndex(Vertex v){
        if(vertex2index.containsKey(v)) return vertex2index.get(v);
        int index=vertex2index.size();
        if(index>=index2vertex.length)throw new RuntimeException("Too many vertices in graph");
        vertex2index.put(v,index);
        index2vertex[index]=v;
        return index;
    }
    public void insertEdge(Vertex v1,Vertex v2,int w){
        matrix[getIndex(v1)][getIndex(v2)] = w;
    }
    public Collection<Edge> edges(){
        HashSet<Edge> edges=new HashSet<>();
        for(int i=0;i<numVertex;i++){
            for(int j=0;j<numVertex;j++){
                Integer weight=matrix[i][j]; // may be null
                if(weight==null)continue;
                edges.add(new Edge(index2vertex[i],index2vertex[j],weight));
            }
        }
        return edges;
    }
    public Collection<Edge> outEdge(Vertex v1){
        HashSet<Edge> edges=new HashSet<>();
        int i=vertex2index.get(v1);
        for(int j=0;j<numVertex;j++){
            Integer weight=matrix[i][j]; // may be null
            if(weight==null)continue;
            edges.add(new Edge(v1,index2vertex[j],weight));
        }
        return edges;
    }
    public Integer getWeight(Vertex v1,Vertex v2){
        // constant time operation
        return matrix[vertex2index.get(v1)][vertex2index.get(v2)];}
}
