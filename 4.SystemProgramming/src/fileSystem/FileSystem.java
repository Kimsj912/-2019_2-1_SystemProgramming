package fileSystem;

import java.io.File;

import memory.Memory;
import os.OperatingSystem;

public class FileSystem {	
	
	//Drive
	private File CDrive;
	private Memory memory;
	
	public File getCDrive() {return this.CDrive;}

	public FileSystem() {
		this.CDrive = new File(constant.ValueConstants.CDriveAdr);
	}
	public void connect(Memory memory, OperatingSystem operationgSystem) {
		this.memory = memory;
		this.memory.uploadMemory(operationgSystem);
	}
}
