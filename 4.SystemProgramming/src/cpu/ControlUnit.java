package cpu;

import java.lang.reflect.InvocationTargetException;

import constant.EnumConstants.EInstruction;
import constant.EnumConstants.EInterrupt;
import constant.EnumConstants.EProcessState;
import memory.Memory;
import mornitor.UXManager;
import os.Interrupt;
import os.OperatingSystem;
import os.Process;

public class ControlUnit {

    private EInstruction einstruction;

    // Declare association
    private Register pc,bp, sp, mar, mbr, ir, ac, status;
    private ArithmeticLogicUnit arithmeticLogicUnit;
    private Memory memory;
	private OperatingSystem operatingSystem;
	private Process process;

    // constructor
    public ControlUnit() {
    }

    //basic method
    // association
    public void connect(Register pc,Register bp, Register ir, Register sp, Register mar, Register mbr, Register ac, Register status) {
        this.pc = pc;
        this.bp=bp;
        this.ir = ir;
        this.sp = sp;
        this.mar = mar;
        this.mbr = mbr;
        this.ac = ac;
        this.status = status;
    }

    //connect HW in cpu
    public void connect(ArithmeticLogicUnit arithmeticLogicUnit) {
        this.arithmeticLogicUnit = arithmeticLogicUnit;

    }

    // connect HW
    public void connect(Memory memory) {
        this.memory = memory;
    }

    public void sendOS(OperatingSystem operiongSystem) {
    	this.operatingSystem = operiongSystem;
    }
    //initialize
    public void initialize(OperatingSystem operiongSystem) { //나중에 로더가 올려줄때가 가능해지면 삭제할 것.
    	this.operatingSystem = operiongSystem;
        this.pc.setData(0);
    }

    public void setprocess(Process process) {
    	this.process = process;
    	
    }
    //run
    public void run() {
    	this.operatingSystem.interruptCheckingProcess(process);
		while (true) {
			this.fetch();
			this.showRegister();
			this.decodeAndExecute();
			this.operatingSystem.interruptCheckingProcess(process);
			this.operatingSystem.processManager.showProcessQueue();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

//basic method    
    private void fetch() { 
        this.mar.setData(this.pc.getData());
        this.memory.fetch(); //fetching의 문제
        this.ir.setData(this.mbr.getData());
    }
    
    private void decodeAndExecute() {
        int instruction = this.ir.getData() >>> 16;
        this.einstruction = EInstruction.values()[instruction];
        invokeMethod(this.einstruction.getMethod());
    }
    
    //execute method
    public void cmp() {
    	int beforeVal=this.ac.getData();
    	//lda와 동일?
    	int address = (this.ir.getData() & 0x0000ffff);//상대주소
    	address = address + constant.ValueConstants.CSStartingP;//프로세스 내 주소
    	this.mar.setData(address);
    	this.memory.fetch();
    	this.ac.setData(this.mbr.getData());
    	
    	this.status.setData(this.ac.getData() - beforeVal>=0 ? 1:0);
        this.pc.setData(this.pc.getData() + 1);

	}

    public void igz() {
    	if(this.status.getData()==1) { //안의 문이 팩트라면?
    		this.pc.setData(this.ir.getData()&0x0000ffff);
    	}else {
    		this.pc.setData(this.pc.getData()+1);
    	}
	}

	public void halt() {
        this.process.getPcb().setEstate(EProcessState.terminated);

        Interrupt i = new Interrupt();
        i.seteType(EInterrupt.eProcessTerminated);
        this.operatingSystem.interruptHandler.addInterrupt(i);
    }

    public void prt() {
    	int address = (this.ir.getData() & 0x0000ffff);//상대주소
    	address = address + constant.ValueConstants.CSStartingP;//프로세스 내 주소
    	
    	this.mar.setData(address);
    	this.memory.fetch();
    	this.ac.setData(this.mbr.getData());
    	
    	Interrupt i = new Interrupt();
    	i.seteType(EInterrupt.eIOStarted);
    	this.operatingSystem.interruptHandler.addInterrupt(i);

    	//console에 출력하게 만들기 (ui)
    	this.pc.setData(this.pc.getData() + 1);
    	UXManager.print("prt : "+this.ac.getData());

    }
    public void add() {
    	int address = (this.ir.getData() & 0x0000ffff);//상대주소
        address = address + constant.ValueConstants.CSStartingP;
        
        this.mar.setData(address);
    	this.memory.fetch();
    	
    	this.arithmeticLogicUnit.add(this.mbr.getData());
    	
    	this.pc.setData(this.pc.getData() + 1);
    }
    public void ldi() { //값을 로드해라.
    	int value = this.ir.getData() & 0x0000ffff;//값
    	this.ac.setData(value);
    	this.pc.setData(this.pc.getData() + 1);
    }

    public void lda() { //주소값을 로드해라
    	int address = (this.ir.getData() & 0x0000ffff);//상대주소
    	address = address + constant.ValueConstants.CSStartingP;//프로세스 내 주소
    	
    	this.mar.setData(address);
    	this.memory.fetch();
    	this.ac.setData(this.mbr.getData());
    	this.pc.setData(this.pc.getData() + 1);
    }
    
    public void sta() { //주소값에 저장해라
        int address = this.ir.getData() & 0x0000ffff; //상대주소
        this.mar.setData(address);
        this.mbr.setData(this.ac.getData());
        this.memory.store();
        this.pc.setData(this.pc.getData() + 1);
    }

    //other using method
    private void invokeMethod(String name) {
		try {
			this.getClass().getMethod(name).invoke(this);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e1) {
			e1.printStackTrace();
		}
	}
    
    int i = 0;
    private void showRegister() {
    	System.out.println("------------"+i+++"번째 clock------------");
    	System.out.println("pc :" + pc.getData());
    	System.out.println("mar : "+ mar.getData());
    	System.out.println("mbr : "+ mbr.getData());
    	System.out.println("ac : "+ ac.getData());
    	System.out.println("ir : "+ ir.getData());
    	System.out.println("sp :"+ sp.getData());
    	System.out.println("status : "+ status.getData());
    }

	
}
