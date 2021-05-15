package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		File path;
		File file;
		String strPath;
		String strName = "source.csv";
		
		do {
			System.out.print("Enter the file path: ");
			// D:\workspace\ws-eclipse\EX198_Files1-java
			strPath = sc.nextLine();
			path = new File(strPath);
			file = new File(strPath + "/" + strName);
			if (!file.isFile()) {
				System.out.println("Enter a valid directory with a source.csv...");
				System.out.println();
			}
		}
		while (!file.isFile());
		
		File outPath = new File(path + "\\out\\summary.csv");
		
		if (!outPath.isFile()) {
			boolean createOut = new File(strPath + "//out").mkdir();
			System.out.println("Creating out directory = " + createOut);
		}
		else System.out.println("Using existing folder");
		
		String[] vectorLine = new String[2];
		List<Product> list = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(file));
				BufferedWriter bw = new BufferedWriter(new FileWriter(outPath))) {

			String line = br.readLine();
			
			while (line != null) {
				vectorLine = line.split(",");
				list.add(new Product(vectorLine[0],Double.parseDouble(vectorLine[1]), Integer.parseInt(vectorLine[2])));
				line = br.readLine();
			}
			for (Product l : list) {
				bw.write(l.getName() + "," + String.format("%.2f", l.subTotal()));
				bw.newLine();
			}
		}
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		finally {
			sc.close();
			if (outPath.isFile()) System.out.println("Summary.csv updated = " + new Date(outPath.lastModified()));
		}
	}
}