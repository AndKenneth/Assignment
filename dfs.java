import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class dfs {

	public static void main(String[] args) {
		
		try {
			System.setIn(new FileInputStream("list.txt"));
			//System.setOut(new PrintStream(new FileOutputStream("dfs.txt")));
			dfs instance = new dfs();
			instance.start();
		} catch (FileNotFoundException e) {
			System.err.println("list.txt was not found");
		}


	}

	private void start() {
		Kgraph graph = new Kgraph();
		System.out.print(graph.toString());
	}
	
	
	private class Kgraph extends ArrayList<Knode>{

		public Kgraph() {
			Scanner in = new Scanner(System.in);
			while (in.hasNext()) {
				this.add(new Knode(in.next()));
			}
			in.close();
		}
		
		public void doDFS(){
			ArrayList<Knode> unprocessed = new ArrayList<Knode>();
			for 
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			int count = 0;
			for (Knode n : this) {
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
