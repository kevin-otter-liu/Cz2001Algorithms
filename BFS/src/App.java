import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;
import java.util.Map.Entry;

public class App {
	
	public static void main(String[] args) throws Exception {
		
		File f = new File("output.txt");
		f.delete();
		System.out.println(" Choose: \n 1)randomgenerator \n 2)test \n 3)empirical analysis for nearest hospital k \n "
				+ "4)empirical analysis for nearest hospital h\n 5)exit\n");
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
		if(choice ==1) {
			System.out.println("Choose the number of Nodes\n");
			Scanner sc1 = new Scanner(System.in);
			Integer numOfNodes = sc.nextInt();
			myGraphStream g = new myGraphStream(numOfNodes,3);
			g.generateRandomGraph();
			System.out.println("How many nearest hospital would you like to find?");
			Integer chooseNumOfHospital = sc.nextInt();
			
			Bfs obj = new Bfs();
			//enter file to convert to adjacencylist
			obj.fileToList("randomFile");
			
			//enter hospital file to convert to hospital array
			obj.readHospitalFile("hospital");
			obj.createHospitalList();
			
			//loop through each source node
			for (int i = 0; i <= obj.max; i++) {
				try {
					obj.BFS(i,chooseNumOfHospital);
				}
				catch(NullPointerException e) {
					continue;
				}
			}
				
	    }else if(choice == 2) {
	    	System.out.println("Enter file to read: \n");
	    	Scanner sc1 = new Scanner(System.in);
	    	String SourceFile = sc.next();
	    	String hospitalFile;

			System.out.println("Choose hospital file to read: \n");
			hospitalFile = sc.next();
			
			System.out.println("How many nearest hospital would you like to find?");
			Integer chooseNumOfHospital = sc.nextInt();
			
			Bfs obj = new Bfs();
			
			//enter file to convert to adjacencylist
			obj.fileToList(SourceFile);
			
			//enter hospital file to convert to hospital array
			obj.readHospitalFile(hospitalFile);
			obj.createHospitalList();
			
			//loop through each source node
			for (int i = 0; i <= obj.max; i++) {
				try {
					obj.BFS(i,chooseNumOfHospital);
				}
				catch(NullPointerException e) {
					continue;
				}
			}
		}else if(choice ==3) {
			System.out.println("Enter file to read: \n");
	    	Scanner sc1 = new Scanner(System.in);
	    	String SourceFile = sc.next();
			Hashtable<Integer,Long> graph = new Hashtable<Integer,Long>();
			
			Bfs obj = new Bfs();
			//enter file to convert to adjacencylist
			obj.fileToList(SourceFile);
			
			//enter hospital file to convert to hospital array
			obj.readHospitalFile("hospital_test_graph");
			obj.createHospitalList();
			
			//loop through each source node
			for (int k = 1; k < 400; k+=5) {
				long start = System.nanoTime();
				for (int i = 0; i <= obj.max; i++) {
					try {
						obj.BFS(i,k);
					}
					catch(NullPointerException e) {
						continue;
					}
				}
				long stop = System.nanoTime();
				long timeElapsed = stop-start;
				graph.put(k, timeElapsed);
				
		    }
			System.out.println(graph);
		}else if(choice ==4) {
		System.out.println("Enter file to read: \n");
    	Scanner sc1 = new Scanner(System.in);
    	String SourceFile = sc1.next();
//		Integer numOfNodes = sc.nextInt();
//		myGraphStream g = new myGraphStream(numOfNodes,3);
//		g.generateRandomGraph();

		Hashtable<Integer,Long> graph = new Hashtable<Integer,Long>();
		
		Bfs obj = new Bfs();
		//enter file to convert to adjacencylist
		obj.fileToList(SourceFile);
		
		
		//loop through each source node
		for(int j=200;j<=2000;j+=50) {
		
			obj.readHospitalFileTest("hospital_test_graph",j);
			obj.createHospitalList();
			

			long start = System.nanoTime();
			for (int i = 0; i <= obj.max; i++) {

				
				try {
					obj.BFS_test(i,1);
				}
				catch(NullPointerException e) {
					continue;
				}
			}

			long stop = System.nanoTime();
			long timeElapsed = stop-start;

			graph.put(j, timeElapsed);
		
		}

		System.out.println(graph);
    }else {
    	return;
    }
		
}

		
}
	
	
	
