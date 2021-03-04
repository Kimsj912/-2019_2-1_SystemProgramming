package board;

import cpu.CentralProcessingUnit;
import fileSystem.FileSystem;
import memory.Memory;
import os.OperatingSystem;

public class Board {
	// Component
	private OperatingSystem operatingSystem;
	private CentralProcessingUnit centralProcessingUnit;
	private Memory memory;
	private FileSystem fileSystem;

	// Constructor
	public Board() {
		this.fileSystem = new FileSystem();
		this.memory = new Memory();
		this.centralProcessingUnit = new CentralProcessingUnit();
		this.operatingSystem = new OperatingSystem(); 
	}

	public void initialize() {
		this.connect();
		this.setOperatingSystem();
		this.memory.initialize();
		this.centralProcessingUnit.initialize();
	}

	private void connect() {
		this.fileSystem.connect(this.memory, this.operatingSystem);
		this.memory.connect(this.fileSystem, centralProcessingUnit);
		this.centralProcessingUnit.connect(this.memory);
	}

	private void setOperatingSystem() {
		this.operatingSystem.associate();
		this.operatingSystem.connect(this.centralProcessingUnit, this.memory, this.fileSystem);
		this.operatingSystem.initialize();
	}
}