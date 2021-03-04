package os;

import cpu.CentralProcessingUnit;
import os.FileManager;
import fileSystem.FileSystem;
import memory.Memory;
import os.MemoryManager;
import mornitor.UXManager;

public class OperatingSystem {

	// child
	public ProcessManager processManager;
	public ProcessManager getProcessManager() {return processManager;}
	public void setProcessManager(ProcessManager processManager) {this.processManager = processManager;}
	
	private MemoryManager memoryManager;
	private FileManager fileManager;
	private UXManager uxManager;

	// connect
	private CentralProcessingUnit centralProcessingUnit;
	private FileSystem fileSystem;
	private Memory memory;
	public InterruptHandler interruptHandler;

	public OperatingSystem() {
		this.processManager = new ProcessManager();
		this.memoryManager = new MemoryManager();
		this.fileManager = new FileManager();
		this.uxManager = new UXManager();
		this.interruptHandler = new InterruptHandler();
	}

	public void associate() {
		this.memoryManager.associate(this.processManager, this.fileManager);
		this.uxManager.associate(this.fileManager, this.processManager,this.memoryManager);
		this.interruptHandler.associate(this.processManager);
	}

	public void connect(CentralProcessingUnit centralProcessingUnit, Memory memory, FileSystem fileSystem) {
		this.centralProcessingUnit = centralProcessingUnit;
		this.memory = memory;
		this.fileSystem = fileSystem;

		// child connection with hw
		this.memoryManager.connect(this.memory, this.centralProcessingUnit);
		this.fileManager.connect(this.fileSystem);
		this.uxManager.connect(this.centralProcessingUnit, this.memory, this.fileSystem);
		this.interruptHandler.connect(this.centralProcessingUnit);
	}
	public void initialize() {
		this.memoryManager.initialize();
		this.uxManager.initialize();
	}
	public Process getProcessFromRQ() {
		return this.processManager.deReadyQueue();
	}
	public void interruptCheckingProcess(Process process){
		this.interruptHandler.interruptChecking(process);
	}

	
}
