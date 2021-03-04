package cpu;

import constant.EnumConstants.EInterrupt;
import constant.EnumConstants.EProcessState;
import constant.EnumConstants.ERegister;
import memory.Memory;
import os.Interrupt;
import os.OperatingSystem;
import os.Process;

public class CentralProcessingUnit {
	
	// component
	private ControlUnit controlUnit;
	private ArithmeticLogicUnit arithmeticLogicUnit;
	public Register pc,bp, sp, mar, mbr, ir, ac, status; // ?? ????.

	// association
	private Memory memory;
//	private Vector<Register> registersVect;
	private OperatingSystem operatingSystem;
	private Process process;
	public CentralProcessingUnit() {
		// component
		this.controlUnit = new ControlUnit();
		this.arithmeticLogicUnit = new ArithmeticLogicUnit();
		
		this.pc = ERegister.ePC.getRegister();
		this.bp = ERegister.eBP.getRegister();
		this.sp = ERegister.eSP.getRegister();
		this.mar = ERegister.eMAR.getRegister();
		this.mbr = ERegister.eMBR.getRegister();
		this.ir = ERegister.eIR.getRegister();
		this.ac = ERegister.eAC.getRegister();
		this.status = ERegister.eStatus.getRegister();

		// component association
		this.controlUnit.connect(this.pc, this.bp, this.ir, this.sp, this.mar, this.mbr, this.ac, this.status);
		this.arithmeticLogicUnit.connect(this.mbr, this.ac, this.status);
		
		this.controlUnit.connect(this.arithmeticLogicUnit);

	}
	
	//basic method
	public void connect(Memory memory) { 
		this.memory = memory;
		
		this.memory.connect(this.mar, this.mbr);
		this.controlUnit.connect(this.memory);
		
	}
	public void sendOS(OperatingSystem operationSystem) {
		this.operatingSystem = operationSystem;
		this.controlUnit.sendOS(this.operatingSystem);
		
	}
	public void initialize() {
		this.controlUnit.initialize(this.operatingSystem);
	}

	public void run() { 
		//하던 거 저장
		if(this.process!=null) {//프로세스가 있을떄,
			this.memory.addProcess(this.process);
		}
		
		//스타트 인터럽트 추가
		Interrupt i = new Interrupt();
		i.seteType(EInterrupt.eProcessStart);
		this.operatingSystem.interruptHandler.addInterrupt(i);

		//process뺴와
		this.process = this.operatingSystem.getProcessFromRQ();

		//상태 복구
		this.setRegisters(process.getPcb().getRegisters());
		this.controlUnit.setprocess(process);
		this.memory.setProcess(process);
		
		//실행
		this.controlUnit.run();
	}
	
	public void setProcess (Process process) {
		this.process = process;
		setRegisters(this.process.getPcb().getRegisters());
		this.process.getPcb().setEstate(EProcessState.running);
		Interrupt i = new Interrupt();
		i.seteType(EInterrupt.eProcessStart);
		this.operatingSystem.interruptHandler.addInterrupt(i);
		System.out.println("cpu setprocess");
		System.exit(0);
	}

	public void setRegisters(Register[] registers) {
		//ePC, eCS , eDS , eSS, eHS, eMAR, eMBR, eIR, eStatus, eAC
		this.pc = registers[ERegister.ePC.ordinal()];
		this.bp = registers[ERegister.eBP.ordinal()];
		this.sp = registers[ERegister.eSP.ordinal()];
		this.mar = registers[ERegister.eMAR.ordinal()];
		this.mbr = registers[ERegister.eMBR.ordinal()];
		this.ir = registers[ERegister.eIR.ordinal()];
		this.ac = registers[ERegister.eAC.ordinal()];
		this.status = registers[ERegister.eStatus.ordinal()];
	} 
	public Register[] getRegister() { 
		Register[] registers=new Register[ERegister.values().length];
 		registers[ERegister.ePC.ordinal()]= this.pc;
		registers[ERegister.eBP.ordinal()]= this.bp;
		registers[ERegister.eSP.ordinal()]= this.sp;
		registers[ERegister.eMAR.ordinal()]= this.mar;
		registers[ERegister.eMBR.ordinal()]= this.mbr;
		registers[ERegister.eIR.ordinal()]= this.ir;
		registers[ERegister.eAC.ordinal()]= this.ac;
		registers[ERegister.eStatus.ordinal()]= this.status;
	
		return registers; 
	} 
 }
