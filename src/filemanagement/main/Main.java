package filemanagement.main;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.compress.archivers.ArchiveException;

import filemanagement.util.CreateZip;
import filemanagement.util.FileDeletion;
public class Main {

	public static void main(String[] args) throws IOException, ArchiveException {
		Scanner input = new Scanner( System.in );
		CreateZip createZip = new CreateZip();
		while(true) {
			System.out.print( "Enter which task to start: " );
			int task = input.nextInt();
			createZip.ProcessFile(task);
		}
	}
}
