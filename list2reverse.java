import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class list2reverse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.setIn(new FileInputStream("list.txt"));
			System.setOut(new PrintStream(new FileOutputStream("reverselist.txt")));
			list2reverse instance = new list2reverse();
			instance.start();
		} catch (FileNotFoundException e) {
			System.err.println("list.txt was not found");
		}
		

	}

	private void start() {
		ArrayList<ArrayList<Integer>> normalGraph = getArrayList();
		ArrayList<ArrayList<Integer>> inverseGraph = getInverse(normalGraph);
		printGraph(inverseGraph);
	}
	
	private void printGraph(ArrayList<ArrayList<Integer>> graph) {
		for (ArrayList<Integer> node : graph) {
			System.out.println(join(node, ","));
		}
	}
	
	private String join(ArrayList<Integer> list, String delimiter) {
		StringBuilder string = new StringBuilder();
		String delim = "";
		
		for (Integer i : list) {
			String s = i.toString();
			string.append(delim);
			string.append(s);
			delim = delimiter;
		}
		
		return string.toString();
	}
	
	
	private ArrayList<ArrayList<Integer>> getInverse(ArrayList<ArrayList<Integer>> normal) {
		ArrayList<ArrayList<Integer>> inverse = new ArrayList<ArrayList<Integer>>(normal.size());
		for (int i = 0;  i < normal.size(); i++) inverse.add(i,new ArrayList<Integer>());
		for(int i = 0; i < normal.size(); i++) {
			ArrayList<Integer> node = normal.get(i);
			for (Integer otherNode : node){
				inverse.get(otherNode).add(i);
			}
		}
		
		return inverse;
	}
	
	
	private ArrayList<ArrayList<Integer>> getArrayList() {
		ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
		ArrayList<String> tempStrings = new ArrayList<String>();
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			tempStrings.add(in.nextLine());
		}
		in.close();
		
		for (String s : tempStrings) {
			ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(s.split(",")));
			ArrayList<Integer> integerList = new ArrayList<Integer>();
			for (String number : stringList) integerList.add(Integer.parseInt(number));
			graph.add(integerList);
		}
		
		return graph;
	}
}
