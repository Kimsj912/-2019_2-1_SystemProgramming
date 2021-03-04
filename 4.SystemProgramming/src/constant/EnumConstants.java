package constant;

import cpu.Register;

public class EnumConstants {
	//나중에 편리성을 위해 모아둘 것.
	
	public enum eFileSystem {
		eFileSystem
	}
	public enum ERegister{
		ePC(new Register()),
		eAC(new Register()),

		eMAR(new Register()),
		eMBR(new Register()), 
		
		eBP(new Register()),
		eSP(new Register()),
		eIR(new Register()),
		eStatus(new Register()), 
		;
		private Register register;
		public Register getRegister() {return register;}
		public void setRegister(Register register) {this.register = register;}
		
		private ERegister (Register r) {
			this.register = r;
		}
	}
	
	public enum EInstruction {
		halt("halt"), 
		lda("lda"),
		ldi("ldi"),
		sta("sta"),
		add("add"),
		igz("igz"), 
		cmp("cmp"),
		prt("prt");
		
		private String method;
		public String getMethod() {return method;}
		public void setMethod(String method) {this.method = method;}

		EInstruction(String method) {
			this.method = method;
		}
	}

	public enum EProcessState {
		nnew, running, wait, ready, terminated
	}

	public enum EInterrupt {
		eNone, eProcessTerminated, eIOStarted, eIOFinished, eProcessStart, eTimeFinished, eTimeStart,
	}
	
	public enum EDevice{
		mouse("halt"), 
		keyboard("lda"),
		ldi("ldi"),
		sta("sta"),
		add("add"),
		igz("igz"), 
		cmp("cmp"),
		prt("prt");
		
		private String method;
		public String getMethod() {return method;}
		public void setMethod(String method) {this.method = method;}

		EDevice(String method) {
			this.method = method;
		}
	}

}
