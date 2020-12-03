import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Map.Entry;

public class App {
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("Input number of total nodes desired: ");
		Scanner sc = new Scanner(System.in);
		Integer numOfNodes = sc.nextInt();
		myGraphStream g = new myGraphStream(numOfNodes,3);
		g.generateRandomGraph();
		System.out.println("How many nearest hospital would you like to find?");
		Integer chooseNumOfHospital = sc.nextInt();
		
		Bfs obj = new Bfs(numOfNodes);
		obj.fileToList("randomfile");
		obj.readHospitalFile();
		
			for (int i = 0; i < numOfNodes; i++) {
				try {
					System.out.println("Source Node: " + i);
					obj.BFS(i,chooseNumOfHospital);
					System.out.println("--------------------------------------");
				}
				catch(NullPointerException e) {
					System.out.println("No reachable hospital found. ");
					System.out.println("--------------------------------------");
					continue;
			}
		}
			
		
//		System.out.println(obj.readHospitalFile());
//		System.out.println(obj.fileToList("randomfile"));
//		for (Map.Entry entry : (obj.fileToList("randomfile").entrySet())) {
//		    Object key = entry.getKey();
//		    System.out.println(key);
//		    Object value = entry.getValue();
//		    System.out.println(value);
//		}
//		Hashtable<Integer, ArrayList<Integer>> ht = obj.fileToList("randomfile");
//		Set<Integer> setOfCountries = ht.keySet();
//		 
//        // for-each loop
//        for(Integer key : setOfCountries) {
// 
//            System.out.println("Node : "  + key 
//                    + "\t\t Linked Nodes : "  + ht.get(key));
//        }
    }

		
}
	
	
	
