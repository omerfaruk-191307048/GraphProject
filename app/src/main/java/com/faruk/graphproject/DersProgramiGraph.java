package com.faruk.graphproject;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class DersProgramiGraph {

    public static Graph<String, DefaultEdge> createGraphFromDatabase() {
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        /*ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String hocaID = resultSet.getString("HocaID");
            String dersID = resultSet.getString("DersID");
            String sinifID = resultSet.getString("SinifID");
            String gunID = resultSet.getString("Gun_id");
            String saatID = resultSet.getString("Saat_id");

            // Grafınıza düğümleri (nodes) ve kenarları (edges) ekleyin
            graph.addVertex(hocaID);
            graph.addVertex(dersID);
            graph.addVertex(sinifID);
            graph.addVertex(gunID);
            graph.addVertex(saatID);

            graph.addEdge(hocaID, dersID);
            graph.addEdge(dersID, sinifID);
            graph.addEdge(sinifID, gunID);
            graph.addEdge(gunID, saatID);


        }*/
        return graph;
    }

}
