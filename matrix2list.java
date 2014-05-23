import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;


public class matrix2list {
	public static void main(String[] args) {
		try {
			System.setIn(new FileInputStream("matrix.txt"));
			System.setOut(new PrintStream(new FileOutputStream("list.txt")));
			matrix2list instance = new matrix2list();
			instance.start();
		} catch (FileNotFoundException e) {
			System.err.println("matrix.txt was not found");
		}
	}
	
	private void start() {
		LinkedList<String> nodeStrings = getNodes();
		for (String n : nodeStrings) System.out.println(convert2list(n));
		
	}
	
	private LinkedList<String> getNodes() {
		LinkedList<String> nodeStrings = new LinkedList<String>();
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			nodeStrings.add(in.nextLine());
		}
		in.close();
		return nodeStrings;
	}
	
	private String convert2list(String s) {
		String[] split = s.split(",");
		String newString = "";
		boolean first = true;
		for (int i = 0; i < split.length; i++) {
			if (split[i].equals("1")) {
				if (!first) newString = newString + ",";
				newString = newString + i;
				first = false;
			}
		}
		return newString;
	}
}