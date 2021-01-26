package filemanagement.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileDeletion {
	private String targetFile = "Mustang-Shelby-2010_2.jpg";
	private String path = "D:\\tasks_Development\\aG9tZQ==";
	
	
	public void FindFile() {
		String targetPath = "";
		String originalInput = "home";
		String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
		System.out.println(encodedString);
		
		
		
		 try (Stream<Path> walk = Files.walk(Paths.get(path))) {
	            // We want to find only regular files
	            List<String> result = walk.filter(Files::isRegularFile)
	                    .map(x -> x.toString()).collect(Collectors.toList());

	            for (String temp : result) {
	                encodedString = (temp.substring(temp.lastIndexOf('\\') + 1));
	                byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
	        		String decodedString = new String(decodedBytes);
	        		System.out.println(decodedString);
	        		if(decodedString.equals(this.targetFile)) {
	        			System.out.println(1);
	        			targetPath = temp;
	        			break;
	        		}
	            }
	            System.out.println(targetPath);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		 	File mustang = new File(targetPath); 
		    if (mustang.delete()) { 
		      System.out.println("Deleted the file: " + mustang.getName());
		    } else {
		      System.out.println("Failed to delete the file.");
		    } 
		 
	}
}
