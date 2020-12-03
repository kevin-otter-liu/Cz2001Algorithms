import org.graphstream.graph.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Queue;
import java.util.Set;

public class Bfs {
	private String fileName;
	public ArrayList<ArrayList<Integer>> adjacencyList;
	private int totalNoOfNodes;
	private ArrayList<Integer> hospitalNodes;
	
	public Bfs(int totalNoOfNodes){
		this.totalNoOfNodes = totalNoOfNodes;
	}
	/*
	 * readsHospitalFile
	 */
	public void readHospitalFile() throws FileNotFoundException {
		  hospitalNodes = new ArrayList<Integer>();
	      File myObj = new File("hospital.txt");
	      Scanner myReader = new Scanner(myObj);
	      String hospitalString = myReader.nextLine();
	      String hospitalStrCount = hospitalString.replaceAll("[^0-9]", "");       
	      Integer hospitalCount = Integer.valueOf(hospitalStrCount);  //hospitalCount is the total number of hospitals
//	      hospitalNode.add(hospitalCount);
	      while (myReader.hasNextLine()) {
	    	  String data = myReader.nextLine();
	          int num = Integer.valueOf(data);
	          this.hospitalNodes.add(num);                      // hospitalNode is an array that stores all the value of hospital nodes.
	      }
	      myReader.close();

	}
	
	
	public void fileToList(String fileName) throws FileNotFoundException{
		this.fileName = fileName+".txt";
		File f = new File(this.fileName);
		Scanner sc = new Scanner(f);
		int i;
		//instantiate adjacency list
		adjacencyList = new ArrayList<ArrayList<Integer>>();
		
		sc.nextLine();
		//System.out.println(sc.nextLine().replaceAll("[^0-9]", ""));
		sc.nextLine();
		sc.nextLine();
		sc.nextLine();
		
		//instantiating the arraylist
		for(i=0; i <this.totalNoOfNodes ; i++) {
			adjacencyList.add(new ArrayList<Integer>());

		}

		while (sc.hasNextLine()) {
			String data = sc.nextLine();
			//split whitespaces to get the node ids
			String[] splitData= data.split("\\s+");
			//starting Node
			
			int fromNode = Integer.parseInt(splitData[0]);
			//destination node
			int toNode = Integer.parseInt(splitData[1]);
			//add into the array[fromNode].append(toNode)
			adjacencyList.get(fromNode).add(toNode);
		}
		System.out.println(adjacencyList);
		sc.close();
	}
	void BFS(int s,int chooseNumOfHospital) throws FileNotFoundException 
    { 
        // Mark all the vertices as not visited(By default 
        // set as false) 
		boolean[] visited = new boolean[this.totalNoOfNodes];
		int[] level = new int[totalNoOfNodes];
		int[] parent = new int[totalNoOfNodes];
		List<Integer> bfsOutput = new ArrayList<Integer>();
		List<Integer> shortestOutput = new ArrayList<Integer>();
		
		int hospitalFoundCount = 0;
        
        //setting visited for all nodes to be 0
        for(int i=0;i<totalNoOfNodes;i++) {
        	visited[i] = false;
        	level[i] = 0;
        	parent[i] = -1;
        }
        // Create a queue for BFS 
        LinkedList<Integer> queue = new LinkedList<Integer>(); 
  
        // Mark the current node as visited and enqueue it 
        visited[s]=true;
        queue.add(s); 
  
        while (queue.size() != 0) 
        { 
            // Dequeue a vertex from queue and print it 
            int u = queue.poll(); 
            bfsOutput.add(u);
            for (Integer hospitalVertexNum : this.hospitalNodes) { 		      //check if source node == hospital node
            	if (hospitalFoundCount == chooseNumOfHospital)
                	break;
                if (hospitalVertexNum == u) {
                	shortestOutput = new ArrayList<Integer>();
                	System.out.println("Distance of Nearest Hospital: " + level[u]);
                	int shortestDistance = level[u]; //gets the shortest distance needed to reach that specific hospital
                	shortestOutput.add(hospitalVertexNum); // adds the destination hospital into the arraylist
                	while (shortestDistance > 0) {
                		shortestOutput.add(parent[u]); // get the parent of the hospital and by repetitively calling the parent until source node is reached.
                		u = parent[u];
                		shortestDistance--;              	
                	}
                	Collections.reverse(shortestOutput);
                	System.out.println(shortestOutput);
                	hospitalFoundCount++;
                }        
           }
            // Get all adjacent vertices of the dequeued vertex s 
            // If a adjacent has not been visited, then mark it 
            // visited and enqueue it 
            for (Integer v : this.adjacencyList.get(u)) { 		      // loop all the adjacent vertex v in node U    
            	if (visited[v] == false) {
            		visited[v]=true;
            		parent[v] =u;
            		int newlevel = level[u] + 1;    // adds 1 level(distance)
            		level[v] =newlevel;
            		queue.add(v);
            	}
 	        }
         
                  	
        }
//        System.out.println("parent: " + parent);
//        System.out.println("distance: " + level);
    } 
}