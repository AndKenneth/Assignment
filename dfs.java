import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;


public class dfs {

	public static void main(String[] args) {
		
		try {
			System.setIn(new FileInputStream("list.txt"));
			System.setOut(new PrintStream(new FileOutputStream("dfs.txt")));
			//System.setErr(new PrintStream(new FileOutputStream("error.out")));
			dfs instance = new dfs();
			instance.start();
		} catch (FileNotFoundException e) {
			System.err.println("list.txt was not found");
		}


	}

	private void start() {
		Kgraph graph = new Kgraph();
		output(graph.doDFS());
		
	}
	
	private void output(ArrayList<Integer> list) {
		Integer[] outputArray = new Integer[list.size()];
		outputArray = list.toArray(outputArray);
		 for (int i = 0; i < outputArray.length; i++) System.out.format("%d,%d%n", i, outputArray[i]);
	}
	
	
	private class Kgraph {
		private Knode[] graph;
		
		public Kgraph() {
			ArrayList<Knode> graphList = new ArrayList<Knode>();
			Scanner in = new Scanner(System.in);
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
		
		public ArrayList<Integer> doDFS(){
			Stack<Integer> stack = new Stack<Integer>();
			ArrayList<Integer> processedList = new ArrayList<Integer>();
			System.err.println("START DO DFS");
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
					if (white) {
						seen(lowest, stack);
					} else {
						process(current, processedList, stack);
					}
				}
				stack.push(-1);
			}
			
			
			return processedList;
		}
		
		private void seen(int index, Stack<Integer> stack) {
			stack.push(index);
			graph[index].setGrey();
			System.err.println("SEEN Index " + index);
		}
		
		private void process(int index, ArrayList<Integer> list, Stack<Integer> stack) {
			stack.pop();
			graph[index].setBlack();
			list.add(index);
			System.err.println("PROC Index " + index);
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
