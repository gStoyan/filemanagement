package filemanagement.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ar.ArArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;



public class CreateZip {
	
	private String APath = "D:\\tasks_Development\\folder A";
	private String BPath = "D:\\tasks_Development\\folder B";
	private String CPath = "D:\\tasks_Development\\folder C\\folder C.zip";
	private String DPath = "D:\\tasks_Development\\folder D\\unknown.unknown";
	private String destinationFolder = "D:\\destination\\New folder";
	
	
	
	public void ProcessFile(int task) throws IOException, ArchiveException{		
		String destination = this.destinationFolder;
		String source = "";
		if(task == 1) {			
			source = this.APath;		
			String fileName = source.substring(source.lastIndexOf('\\') + 1);
			File outputZipFile = new File(destination+ "\\" + fileName);
			try( ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(outputZipFile)){;
			createZip(zipArchiveOutputStream,  source);
		}
		System.out.println(">>>>>>>>>Zip File Created Successfully>>>>>>>>>");
		
		} else if(task == 2) {		
			source = this.BPath;
			String fileName = source.substring(source.lastIndexOf('\\') + 1);
			createSevenZip(source,fileName,destination);
			
		} else if(task ==3) {
			source = this.CPath; 
			unZipFile(source,destination);
		}
	}
	
	
	public static void createZip(ZipArchiveOutputStream zipArchiveOutputStream, String source) throws IOException, ArchiveException {
		try (Stream<Path> walk = Files.walk(Paths.get(source))) {

			List<File> fileList = walk.filter(Files::isRegularFile).map(x -> x.toFile()).collect(Collectors.toList());

			for(File file : fileList) {
				
				int index = file.getAbsoluteFile().getParent().lastIndexOf(File.separator);
				String parentDirectory = file.getAbsoluteFile().getParent().substring(index+1); 
				ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(parentDirectory+File.separator+file.getName());
				zipArchiveEntry.setMethod(ZipEntry.DEFLATED);
				zipArchiveOutputStream.putArchiveEntry(zipArchiveEntry); IOUtils.copy(new FileInputStream(file), zipArchiveOutputStream);
				zipArchiveOutputStream.closeArchiveEntry();
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void createSevenZip(String source,String fileName,String destination)throws IOException, ArchiveException {

		try (SevenZOutputFile sevenZOutput = new SevenZOutputFile(new File(destination+ "\\" + fileName))) {
			 
			File folderToZip = new File(source);
	 
			// Walk through files, folders & sub-folders.
			Files.walk(folderToZip.toPath()).forEach(p -> {
				File file = p.toFile();
	 
				// Directory is not streamed, but its files are streamed into 7z file with
				// folder in it's path
				if (!file.isDirectory()) {
					System.out.println("Seven Zipping file - " + file);
					try (FileInputStream fis = new FileInputStream(file)) {
						SevenZArchiveEntry entry_1 = sevenZOutput.createArchiveEntry(file, file.toString());
						sevenZOutput.putArchiveEntry(entry_1);
						sevenZOutput.write(Files.readAllBytes(file.toPath()));
						sevenZOutput.closeArchiveEntry();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
	 
			// Complete archive entry addition.
			sevenZOutput.finish();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void unZipFile(String source,String destination)throws IOException, ArchiveException {
		 InputStream inputStream = null;//Source file input stream, used to build ZipArchiveInputStream
         OutputStream outputStream = null;//Decompressed file output stream
         ZipArchiveInputStream zipArchiveInputStream = null;//zip file input stream
         ArchiveEntry archiveEntry = null;//Compressed file entity.
try {
                 inputStream = new FileInputStream(source);//Create an input stream, and then transfer the compressed file input stream
    zipArchiveInputStream = new ZipArchiveInputStream(inputStream, "UTF-8");
                 //Traverse and decompress each file.
    while (null != (archiveEntry = zipArchiveInputStream.getNextEntry())) {
                         String archiveEntryFileName = archiveEntry.getName();//Get the file name
                         File entryFile = new File(destination, archiveEntryFileName);//Write the decompressed file to the specified path
        byte[] buffer = new byte[1024 * 5];
        outputStream = new FileOutputStream(entryFile);
        int length = -1;
        while ((length = zipArchiveInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
    		}
		}
		catch (IOException e) {
		    e.printStackTrace();
		}
	}

	public static File changeExtension(File f, String newExtension) {
		  int i = f.getName().lastIndexOf('.');
		  String name = f.getName().substring(0,i);
		  return new File(f.getParent(), name + newExtension);
		}
}
