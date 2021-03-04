package os;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

import fileSystem.FileSystem;
import mornitor.UXManager;
import os.Process;
import os.ProcessManager;

public class FileManager {
	//child
	////Drive
	private File CDrive;
	//Declare connect HW
	private FileSystem fileSystem;
	//constructor
	public FileManager() { }
	// connect HW
	public void connect(FileSystem fileSystem) { this.fileSystem = fileSystem; }
	// method
	public File getFirstFile() { 
		this.CDrive = this.fileSystem.getCDrive(); 
		return this.CDrive;
	}

}
