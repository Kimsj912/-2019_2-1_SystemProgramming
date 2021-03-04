package memory;

import cpu.CentralProcessingUnit;
import cpu.Register;
import fileSystem.FileSystem;
import os.OperatingSystem;
import os.Process;

public class Memory {
	// constant
	int memorysize = constant.ValueConstants.MemorySize;
	int codepoint = constant.ValueConstants.CSStartingP;
	int stackpoint = constant.ValueConstants.DSStartingP;
	int ProcessSize = constant.ValueConstants.ProcessSize;
	int ProcessCount = constant.ValueConstants.ProcessCount;

	private Process table[];
	
	// Declare association
	private CentralProcessingUnit centralProcessingUnit;
	private FileSystem fileSystem;

	private Register mar, mbr;
	private OperatingSystem operatingSystem;
	private int pidCount;
	private Process currentprocss;
	
	// constructor & setting
	public Memory() {
		this.table = new Process[ProcessCount];
	}
	// basic method
	// connect with cpu register
	public void connect(Register mar, Register mbr) {
		this.mar = mar; // 주소버스 연결
		this.mbr = mbr; // data bus연결
	}
	public void connect(FileSystem fileSystem, CentralProcessingUnit centralProcessingUnit) {
		this.fileSystem = fileSystem;
		this.centralProcessingUnit=centralProcessingUnit;
	}
	public void uploadMemory(OperatingSystem operationgSystem) {
		this.operatingSystem = operationgSystem;
	}
	public void initialize() {this.centralProcessingUnit.sendOS(this.operatingSystem);}
	//method
	public void fetch() {
		int adrVal = this.mar.getData();
		int dataVal = this.table[this.pidCount].getCodeSegment().getMemory()[this.codepoint+adrVal];//메모리내 주소로 변경
		this.mbr.setData(dataVal);
	}
	public void store() {
		this.table[this.pidCount].getCodeSegment().getMemory()[this.mar.getData()] = this.mbr.getData();
	}
	public void addProcess(Process process) {
		this.table[process.getPcb().getId()] = process;
	}
	public void setProcess(Process process) {
		this.currentprocss = process;
	}
}
