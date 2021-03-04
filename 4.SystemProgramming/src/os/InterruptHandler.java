package os;

import java.util.Vector;

import constant.EnumConstants.EInterrupt;
import constant.EnumConstants.EProcessState;
import cpu.CentralProcessingUnit;
import ioDevice.IODevice;
import ioDevice.Timer;


public class InterruptHandler {
	private Interrupt[] interrupts;
	private int front;
	private int rear;
	private int length;
	private int maxLength;
	private Timer resetTimer;
	private ProcessManager processManager;
	private CentralProcessingUnit centralProcessingUnit;
	
	public InterruptHandler() {
		this.maxLength = 10;
		this.interrupts = new Interrupt[this.maxLength];
		
		this.front = 0;//인터럽즈 시작점
		this.rear = 0; //지금까지 해줬던 인터럽즈 시작점
		this.length = 0; // 총개수

		this.resetTimer = new Timer();
	}
	public void associate(ProcessManager processManager) {
		this.processManager = processManager;
		this.resetTimer.associate(this);
	}
	public void addInterrupt(Interrupt interrupt) {
		this.interrupts[this.rear]=interrupt;
		this.rear = (this.rear+1)%this.maxLength;
		this.length++;
		
	}
	public void interruptChecking(Process process) {//어떻게 처리해야할지
		this.length = this.rear-this.front>=0?this.rear-this.front:this.rear+10-this.front;
 		if (this.length > 0) {
			switch (this.interrupts[front].geteType()) {
			case eProcessStart:
				System.out.println("processStart interrupt");
				this.front = (this.front+1)%this.maxLength;
				this.resetTimer.startTimer();
				break;
			case eProcessTerminated:
				System.out.println("processTerminated Interrupt");
				this.front = (this.front+1)%this.maxLength;
				try {
					Thread.sleep(100000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case eTimeStart:
				this.front = (this.front+1)%this.maxLength;
				this.resetTimer.startTimer();
				System.out.println("timefinished interrupt");

				break;
			case eTimeFinished:
				process.getPcb().setEstate(EProcessState.terminated);
				process.getPcb().setRegisters(centralProcessingUnit.getRegister());
				this.processManager.enReadyqueue(process);
				centralProcessingUnit.setProcess(processManager.deReadyQueue());

				this.front = (this.front+1)%this.maxLength;
				System.out.println("timefinished interrupt");
				break;
			case eIOStarted: 
				process.getPcb().setEstate(EProcessState.wait);
				this.processManager.enIOqueue(process);
				this.front = (this.front+1)%this.maxLength;
				System.out.println("iostarted interrupt");

				break;
			case eIOFinished:
				System.out.println("iofinished interrupt");
				this.front = (this.front+1)%this.maxLength;
				break;
			default:
				return;
			}
		}
	}
	public void connect(CentralProcessingUnit centralProcessingUnit) {
		this.centralProcessingUnit = centralProcessingUnit;
	}
}