package com.faruk.graphproject;

import org.jgrapht.Graph;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.Map;

public class GreedyColoringExample {

    public static Boolean function() {

        return true;
    }
    public static void main() {
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        graph.addVertex("A"); //ders
        graph.addVertex("B"); //hoca
        graph.addVertex("C"); //sinif
        graph.addVertex("D"); //gun
        graph.addVertex("E"); //saat
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("A", "D");
        graph.addEdge("B", "C");
        graph.addEdge("B", "E");
        graph.addEdge("D", "E");

        GreedyColoring<String, DefaultEdge> coloring = new GreedyColoring<>(graph);

        Map<String, Integer> colorMap = coloring.getColoring().getColors();

        for (String vertex : graph.vertexSet()) {
            System.out.println("Vertex " + vertex + " has color " + colorMap.get(vertex));
        }
    }
}
