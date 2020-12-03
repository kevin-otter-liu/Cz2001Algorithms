import org.graphstream.graph.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Bfs {
	private String fileName;
	public ArrayList<ArrayList<Integer>> adjacencyList;
	private ArrayList<Integer> hospitalNodes;
	boolean[] hospitalList = new boolean[this.max];
	public int max;
	
	public Bfs(){
		
	}
	
	public void createHospitalList(){
		hospitalList = new boolean[this.max+1];
		for (Integer hospitalVertexNum : hospitalNodes) { 
			hospitalList[hospitalVertexNum] = true;
		}
	}
	
	boolean isHospital(int num) {
		return hospitalList[num];
	}
	
	//read the hospital.txt file
	public void readHospitalFile(String filename) throws FileNotFoundException {
		  hospitalNodes = new ArrayList<Integer>();
	      File myObj = new File(filename+".txt");
	      Scanner myReader = new Scanner(myObj);
	      //skip line
	      myReader.nextLine();
	      while (myReader.hasNextLine()) {
	    	  String data = myReader.nextLine();
	          int num = Integer.valueOf(data);
	          
	          //check if hospital is within node limit
	          if(num<this.max) {
		          this.hospitalNodes.add(num);
	          }
	      }
	      myReader.close();
	}
	
	//converts sourc nodes to adjacencylist
	public void fileToList(String fileName) throws FileNotFoundException{
		this.fileName = fileName+".txt";
		File f = new File(this.fileName);
		Scanner sc = new Scanner(f);
		
		//maximum node
		this.max = 0;
		
		//instantiate adjacency list
		adjacencyList = new ArrayList<ArrayList<Integer>>();
		
		sc.nextLine();
		sc.nextLine();
		sc.nextLine();
		sc.nextLine();
		
		//find max node
		while (sc.hasNextLine()){
			String data = sc.nextLine();
			//split whitespaces to get the node ids
			String[] splitData= data.split("\\s+");
			//starting Node
			int fromNode = Integer.parseInt(splitData[0]);
			//destination node
			int toNode = Integer.parseInt(splitData[1]);
			//get the Max Node
			if(toNode>max) {
				this.max=toNode;
			}
		}
		//making adjacencylist
		for (int i =0;i<=this.max+1;i++){
			this.adjacencyList.add(new ArrayList<Integer>());
		}
		
		File f2 = new File(this.fileName);
		Scanner sc2 = new Scanner(f2);
		sc2.nextLine();
		sc2.nextLine();
		sc2.nextLine();
		sc2.nextLine();
		
		//slotting value into empty adjacencylist
		while (sc2.hasNextLine()) {
			String data = sc2.nextLine();
			String[] splitData= data.split("\\s+");
			//starting Node
			int fromNode = Integer.parseInt(splitData[0]);
			//destination node
			int toNode = Integer.parseInt(splitData[1]);
			adjacencyList.get(fromNode).add(toNode);
			//System.out.println("adding neighbours into adjacencylist");
		}
	}
	
	//Breadth first search method source node (s) and nearest hospital to find
	void BFS(int s,int chooseNumOfHospital) throws IOException 
    { 
        // Mark all the vertices as not visited(By default 
        // set as false) 
		//System.out.println("BFS");
		boolean[] visited = new boolean[this.max+1];
		int[] level = new int[this.max+1];
		int[] parent = new int[this.max+1];
		List<Integer> bfsOutput = new ArrayList<Integer>();
		List<Integer> shortestOutput = new ArrayList<Integer>();
		
		int hospitalFoundCount = 0;
        
        //setting visited for all nodes to be 0
        for(int i=0;i<=this.max;i++) {
        	visited[i] = false;
        	level[i] = 0;
        	parent[i] = -1;
        }
        // Create a queue for BFS 
        LinkedList<Integer> queue = new LinkedList<Integer>(); 
        FileWriter fileWritter = new FileWriter("output.txt",true);
        BufferedWriter bw = new BufferedWriter(fileWritter);
        String newLine = System.getProperty("line.separator");
  
        // Mark the current node as visited and enqueue it 
        visited[s]=true;
        queue.add(s); 
  
        while (queue.size() != 0) 
        { 
            // Dequeue a vertex from queue and print it 
            int u = queue.poll(); 
            bfsOutput.add(u);
            if (hospitalFoundCount == chooseNumOfHospital)
            	break;          
            if (isHospital(u)) {
            	shortestOutput = new ArrayList<Integer>();
            	bw.write("D = " + level[u] + newLine);
            	int shortestDistance = level[u]; //gets the shortest distance needed to reach that specific hospital
            	shortestOutput.add(u); // adds the destination hospital into the arraylist
            	while (shortestDistance > 0) {
            		shortestOutput.add(parent[u]); // get the parent of the hospital and by repetitively calling the parent until source node is reached.
            		u = parent[u];
            		shortestDistance--;              	
            	}
            	Collections.reverse(shortestOutput);
            	bw.write(shortestOutput + newLine);
            	hospitalFoundCount++;
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
        bw.close();
    }
	
	
	/*
	 * Test methods used for empirical analysis
	 */
	
	//test ,method to read hospital
	public void readHospitalFileTest (String filename,int hToSearch) throws FileNotFoundException {
		  hospitalNodes = new ArrayList<Integer>();
	      File myObj = new File(filename+".txt");
	      Scanner myReader = new Scanner(myObj);
	      int count=0;
	      //skip line
	      myReader.nextLine();
	      while (myReader.hasNextLine()) {
	    	  if (count == hToSearch){
	    		  break;
	    	  }
	    	  String data = myReader.nextLine();
	          int num = Integer.valueOf(data);
	          
	          //check if hospital is within node limit
	          if(num<this.max) {
		          this.hospitalNodes.add(num);
		          count++;
	          }
	      }
	      myReader.close();
	}

	void BFS_test(int s,int chooseNumOfHospital) throws IOException 
    { 
        // Mark all the vertices as not visited(By default 
        // set as false) 
		//System.out.println("BFS");
		boolean[] visited = new boolean[this.max+1];
		int[] level = new int[this.max+1];
		int[] parent = new int[this.max+1];
		List<Integer> bfsOutput = new ArrayList<Integer>();
		List<Integer> shortestOutput = new ArrayList<Integer>();
		
		int hospitalFoundCount = 0;
        
        //setting visited for all nodes to be 0
        for(int i=0;i<=this.max;i++) {
        	visited[i] = false;
        	level[i] = 0;
        	parent[i] = -1;
        }
        // Create a queue for BFS 
        LinkedList<Integer> queue = new LinkedList<Integer>(); 
        FileWriter fileWritter = new FileWriter("output.txt",true);
        BufferedWriter bw = new BufferedWriter(fileWritter);
        String newLine = System.getProperty("line.separator");
  
        // Mark the current node as visited and enqueue it 
        visited[s]=true;
        queue.add(s); 
  
        while (queue.size() != 0) 
        { 
            // Dequeue a vertex from queue and print it 
            int u = queue.poll(); 
            bfsOutput.add(u);
            if (hospitalFoundCount == chooseNumOfHospital)
            	break;          
            if (isHospital(u)) {
            	shortestOutput = new ArrayList<Integer>();
            	bw.write("D =  " + level[u] + newLine);
            	int shortestDistance = level[u]; //gets the shortest distance needed to reach that specific hospital
            	shortestOutput.add(u); // adds the destination hospital into the arraylist
            	while (shortestDistance > 0) {
            		shortestOutput.add(parent[u]); // get the parent of the hospital and by repetitively calling the parent until source node is reached.
            		u = parent[u];
            		shortestDistance--;              	
            	}
            	Collections.reverse(shortestOutput);
            	bw.write(shortestOutput + newLine);
            	hospitalFoundCount++;
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
        bw.close();
        this.hospitalNodes= new ArrayList<Integer>();
    }
}