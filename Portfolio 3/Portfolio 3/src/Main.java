import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {

        // PART 1
        //create graph and load data from combi.txt
        System.out.println("\n=== PART 1 ===");
        Graph g = new AdjListGraph();
        GraphAlgorithms.readGraph(g, "src/combi.txt");
        GraphAlgorithms.printGraph(g);

        // PART 2 check if all vertices are connected
        System.out.println("\n=== PART 2 ===");
        System.out.println("Connected? " + GraphAlgorithms.isConnected(g));

        // PART 3 sort all vertices by number of connection
        System.out.println("\n=== PART 3 ===");
        GraphAlgorithms.printVerticesByDegree(g);

        // PART 4 define timeslots and check if valid
        //schedule is valid if no subjects are in the same timeslot shares students
        System.out.println("\n=== PART 4 ===");
        List<List<Vertex>> groups = new ArrayList<>();
        groups.add(List.of(g.getVertex("IS"), g.getVertex("KEMI"), g.getVertex("PSYK")));
        groups.add(List.of(g.getVertex("HIST"), g.getVertex("PLANBY")));
        groups.add(List.of(g.getVertex("VIRK")));
        groups.add(List.of(g.getVertex("JOURNALIST"), g.getVertex("PÆD")));
        groups.add(List.of(g.getVertex("ENG"), g.getVertex("GEO"), g.getVertex("MAT"), g.getVertex("SUNDHFR")));
        groups.add(List.of(g.getVertex("PA"), g.getVertex("PERF")));
        groups.add(List.of(g.getVertex("FYSIK"), g.getVertex("INFORMATIK"), g.getVertex("KULTUR"), g.getVertex("MOLBIO")));
        groups.add(List.of(g.getVertex("ARBLIV")));
        groups.add(List.of(g.getVertex("DANSK"), g.getVertex("TEKSAM")));
        groups.add(List.of(g.getVertex("DATA"), g.getVertex("MEDBIO"), g.getVertex("SOC")));
        groups.add(List.of(g.getVertex("FILO")));
        groups.add(List.of(g.getVertex("KOMM"), g.getVertex("MILBIO")));

        System.out.println("Valid schedule: " + GraphAlgorithms.isValidSchedule(g, groups));

        // PART 5 count students have subjects in adjacent slots
        System.out.println("\n=== PART 5 ===");
        System.out.println("ADJACENT TIMESLOTS ");
        int originalScore = GraphAlgorithms.calcAdjacentStudents(g, groups);
        System.out.println("Total students in adjacent timeslots: " + originalScore);

        // Reordered groups manually to reduce conflicts
        List<List<Vertex>> reordered = new ArrayList<>();
       reordered.add(List.of(g.getVertex("KOMM"), g.getVertex("MILBIO")));
        reordered.add(List.of(g.getVertex("IS"), g.getVertex("KEMI"), g.getVertex("PSYK")));
        reordered.add(List.of(g.getVertex("DATA"), g.getVertex("MEDBIO"), g.getVertex("SOC")));
        reordered.add(List.of(g.getVertex("HIST"), g.getVertex("PLANBY")));
        reordered.add(List.of(g.getVertex("FYSIK"), g.getVertex("INFORMATIK"), g.getVertex("KULTUR"), g.getVertex("MOLBIO")));
        reordered.add(List.of(g.getVertex("VIRK")));
        reordered.add(List.of(g.getVertex("ENG"), g.getVertex("GEO"), g.getVertex("MAT"), g.getVertex("SUNDHFR")));
        reordered.add(List.of(g.getVertex("JOURNALIST"), g.getVertex("PÆD")));
        reordered.add(List.of(g.getVertex("DANSK"), g.getVertex("TEKSAM")));
        reordered.add(List.of(g.getVertex("PA"), g.getVertex("PERF")));
        reordered.add(List.of(g.getVertex("FILO")));
        reordered.add(List.of(g.getVertex("ARBLIV")));

System.out.println("Reordered: " + GraphAlgorithms.calcAdjacentStudents(g, reordered));
    }
}

        /* Old code trying to reorder
Collections.shuffle(reordered) randomizes the group order randomly each run,
 which means the result is different every time and cannot be compared
consistently against the original ordering. Instead, we manually define
a specific reordering.

old code for part 5 here:
Collections.shuffle(reordered);
 int score = GraphAlgorithms.calcAdjacentStudents(g, reordered);
  System.out.println(score);
  Collections.shuffle(reordered);
int score = GraphAlgorithms.calcAdjacentStudents(g, reordered);
       System.out.println(score);

         */
