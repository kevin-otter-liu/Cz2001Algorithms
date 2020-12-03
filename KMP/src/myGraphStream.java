import java.io.*;

import org.graphstream.algorithm.generator.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class myGraphStream {
	private int noOfNodes;
	private int averageDegree;
	private Graph randomGraph;
	private String fileName;
	public myGraphStream(int noOfNodes, int averageDegree) {
		this.noOfNodes = noOfNodes;
		this.averageDegree = averageDegree;
	}
	
	public int getTotalNumOfVertex() {
		return randomGraph.getNodeCount();
	}
	
	public void generateRandomGraph() {
		System.setProperty("org.graphstream.ui", "swing");
		this.randomGraph = new SingleGraph("randomGraph");
		Generator g = new RandomGenerator(this.averageDegree);
		g.addSink(randomGraph);
		g.begin();
		while( randomGraph.getNodeCount()<this.noOfNodes && g.nextEvents());
		g.end();
		randomGraph.display();
		storeToFile("randomfile");
	}
	
	public void storeToFile(String fileName) {
		this.fileName = fileName+".txt";
		int edges;
		edges = (int)randomGraph.edges().count();
		
		try {
			FileWriter w = new FileWriter(this.fileName);
			w.write("# Directed graph (each unordered pair of nodes is saved once): "+ this.fileName +".txt \n");
			w.write("# Random Graph Generated \n");
			w.write("# Nodes "+ randomGraph.getNodeCount() +" Edges: "+randomGraph.getEdgeCount()+"\n");
			w.write("# FromNodeId	ToNodeId \n");
			
			//remember to change
			randomGraph.edges().forEach(e -> {
				String fromNodeId = e.getId().split("_")[0];
				String toNodeId = e.getId().split("_")[1];
				System.out.println(e.getId());
				try {
					w.write(fromNodeId+  "\t" + toNodeId + "\n");
					w.write(toNodeId+  "\t" + fromNodeId + "\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			w.close();
		}catch (IOException e) {
	            e.printStackTrace();
	    }
		
		
		
		}
}
