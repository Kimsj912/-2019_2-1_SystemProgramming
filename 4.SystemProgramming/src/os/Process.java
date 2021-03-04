package os;

import java.util.Vector;

import constant.EnumConstants.*;
import cpu.CentralProcessingUnit;
import cpu.Register;

public class Process {
	// components
	private PCB pcb;
	private Segment codeSegment;
	private Segment StackSegment;
	private CentralProcessingUnit centralProcessingUnit;

	public Segment getCodeSegment() {
		return codeSegment;
	}

	public void setCodeSegment(Segment codeSegment) {
		this.codeSegment = codeSegment;
	}

	public Segment getStackSegment() {
		return StackSegment;
	}

	public void setStackSegment(Segment stackSegment) {
		StackSegment = stackSegment;
	}

	// working variable -> get set필요없음, 이 클래스에서 계산할 때 쓰는 값이다.

	public PCB getPcb() {
		return pcb;
	}

	public void setPcb(PCB pcb) {
		this.pcb = pcb;
	}

	public Process(int stackSegmentSize, int[] codes) {
		this.pcb = new PCB();
		this.codeSegment = new Segment(codes);
		this.StackSegment = new Segment(stackSegmentSize);
	}
	public void connect(CentralProcessingUnit centralProcessingUnit){
		this.centralProcessingUnit = centralProcessingUnit;
	}

	public void initialize(int timeslice) {
		this.pcb.setEstate(EProcessState.running);
	}

	public EInterrupt interruptCheck(EInterrupt einterrupt) {//process의 인터럽트 체크 iostarted, terminated,
		int instruction = this.centralProcessingUnit.ir.getData()>>>16;

		if (EInstruction.prt.ordinal()==instruction) {
			// io started interrupt
			this.getPcb().setEstate(EProcessState.wait);
			return EInterrupt.eIOStarted;
		}

		else if (EInstruction.halt.ordinal()==instruction) {
			// process terminated interrupt
			this.getPcb().setEstate(EProcessState.terminated);
			return EInterrupt.eProcessTerminated;
		}

		return einterrupt;
	}

	// pcb
	public class PCB { // loader에서 가져온거를 pcb 에 채우고 segment에 채우고 ??에 채워야지.
		private int id;
		private EProcessState estate;
		private Register[] registers;
		
		//constructor
		public PCB() {
			//ePC, eCS , eDS , eSS, eHS, eMAR, eMBR, eIR, eStatus, eAC 
			this.registers = new Register[ERegister.values().length];
			for (ERegister eregister : ERegister.values()) {
				Register register = new Register();
				int adr = eregister == ERegister.eSP ? constant.ValueConstants.DSStartingP :0;
				adr = eregister == ERegister.eBP ? constant.ValueConstants.CSStartingP : adr;
				register.setData(adr);
				this.registers[eregister.ordinal()]=register;
			}
			this.estate = EProcessState.ready;
		}

		public int getId() {return id;}
		public void setId(int id) {this.id = id;}
		
		public EProcessState getEstate() {return estate;}
		public void setEstate(EProcessState estate) {this.estate = estate;}
		
		public Register[] getRegisters() {return registers;}
		public void setRegisters(Register[] registers) {this.registers = registers;}
		
		public void setEachRegister(ERegister eregister, int val) {
			for (ERegister erv : ERegister.values()) {
				if (erv.equals(eregister))
					this.registers[erv.ordinal()].setData(val);
			}
		}
	}

	// segment
	public class Segment {
		private int[] memory;
		private int size;
		public int[] getMemory() {return memory;}
		public void setMemory(int[] memory) {this.memory = memory;}
		public void setSize(int size) {this.size = size;}
		public int getSize() {return this.size;}
		
		//constructor
		public Segment(int[] memory) {
			this.memory = memory;
			this.size = memory.length;
		}
		public Segment(int size) {
			this.size = size;
		}
	}
}
