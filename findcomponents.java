import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class findcomponents {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.setOut(new PrintStream(new FileOutputStream("components.txt")));
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		}
		
		findcomponents instance = new findcomponents();
		instance.start();
		
	}
	
	private void start() {
		Kgraph reverseGraph =null;
		Kgraph normalGraph = null;
		try {
			reverseGraph = new Kgraph("reverselist.txt");
			normalGraph = new Kgraph("list.txt");
		} catch (FileNotFoundException e) {
			System.err.println("Files not found");
		}
		
		Integer[] DFS = normalGraph.doDFS();
		ArrayList<ArrayList<Integer>> components = reverseGraph.getComponents(DFS);
		for (ArrayList<Integer> i : components) Collections.sort(i);
		System.out.println(buildOutput(components, ","));
	}
	
	private String buildOutput(ArrayList<ArrayList<Integer>> components, String delimiter) {
		StringBuilder sb = new StringBuilder();
		for (ArrayList<Integer> component : components) {
			String delim = "";
			for (Integer i : component) {
				sb.append(delim + i);
				delim = delimiter;
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	
	private class Kgraph {
		private Knode[] graph;
		
		public Kgraph(String file) throws FileNotFoundException {
			ArrayList<Knode> graphList = new ArrayList<Knode>();
			Scanner in = new Scanner(new FileInputStream(file));
			while (in.hasNext()) {
				graphList.add(new Knode(in.next()));
			}
			in.close();
			
			graph = new Knode[graphList.size()];
			graphList.toArray(graph);
		}
		
		private boolean processed() {
			for (int i = 0; i < graph.length; i++) {
				if (graph[i].getColor() != Kcolor.BLACK) {
					return false;
				}
			}
			return true;
		}
		
		public ArrayList<ArrayList<Integer>> getComponents(Integer[] DFS) {
			setUnprocessed();
			ArrayList<ArrayList<Integer>> componentsList = new ArrayList<ArrayList<Integer>>();
			while(!processed()) {
				Stack<Integer> stack = new Stack<Integer>();

				int current = -1;
				for (int i = 0; i < DFS.length; i++) if (graph[DFS[i]].getColor() == Kcolor.WHITE) current = DFS[i];

				//current will represent the highest number that is unprocessed
				ArrayList<Integer> component = new ArrayList<Integer>();

				seen(current, stack);
				//do a dfs on component, then add component to overall list
				while (!stack.isEmpty()) {
					current = stack.peek();
					int lowest = Integer.MAX_VALUE;
					boolean white = false;
					for (Integer i : graph[current]) {
						if (indexIsWhite(i)){
							lowest = Math.min(lowest, i);
							white = true;
						}
					}
					if (white) {
						seen(lowest, stack);
					}
					else {
						process(current, component, stack);
					}
				}
				componentsList.add(component);
			}
			return componentsList;
		}

		
		public Integer[] doDFS(){
			setUnprocessed();
			Stack<Integer> stack = new Stack<Integer>();
			ArrayList<Integer> processedList = new ArrayList<Integer>();
			while (!processed()) {
				//take lowest unprocessed
				int current = -1;
				for (int i = graph.length - 1; i >= 0; i--) {
					if (graph[i].getColor() == Kcolor.WHITE) {
						current = i;
					}
				}
				
				if (current != -1){
					seen(current, stack);
				}
				else break;
				
				while (!stack.empty()){
					current = stack.peek();
					//add lowest unprocessed child to stack
					int lowest = Integer.MAX_VALUE;
					boolean white = false;
					for (Integer i : graph[current]) {
						if (indexIsWhite(i)) {
							lowest = Math.min(lowest, i);
							white = true;
						}
					}
					if (white) seen(lowest, stack);
					else process(current, processedList, stack);
				}
				stack.push(-1);
			}
			
			Integer[] outputArray = new Integer[processedList.size()];
			outputArray = processedList.toArray(outputArray);
			return outputArray;
		}
		
		private void seen(int index, Stack<Integer> stack) {
			stack.push(index);
			graph[index].setGrey();
		}
		
		private void process(int index, ArrayList<Integer> list, Stack<Integer> stack) {
			stack.pop();
			graph[index].setBlack();
			list.add(index);
		}
		
		private void setUnprocessed() {
			for (int i = 0; i < graph.length; i++) {
				graph[i].setWhite();
			}
		}
		
		private boolean indexIsWhite(int i) {
			return graph[i].getColor() == Kcolor.WHITE;
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			int count = 0;
			for (Knode n : Arrays.asList(graph)) {
				sb.append(count);
				sb.append(": ");
				sb.append(n.toString());
				sb.append("\n\n");
				count++;
			}
			return sb.toString();
		}
	}
	
	class Knode extends ArrayList<Integer>{
		private Kcolor color;
		
		public Knode(String connections) {
			List<String> connectionStrings = Arrays.asList(connections.split(","));
			for (String s : connectionStrings) this.add(Integer.parseInt(s));
			
			this.color = Kcolor.WHITE;
		}
		public Kcolor getColor() {
			return color;
		}
		
		public String getColorString() {
			switch (color) {
			case WHITE:
				return "White";
			case GREY:
				return "Grey";
			case BLACK:
				return "Black";
			default:
				return null;
			}
		}
		
		public void setGrey() {
			this.color = Kcolor.GREY;
		}
		
		public void setBlack() {
			this.color = Kcolor.BLACK;
		}
		
		public void setWhite() {
			this.color = Kcolor.WHITE;
		}
		
		public String toString() {
			String s = super.toString();
			s = s + "\n    Color:" + getColorString();
			
			return s;
		}
	}
	
	private enum Kcolor {
		WHITE, GREY, BLACK
	}
}
