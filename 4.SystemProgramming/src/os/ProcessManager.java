package os;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

import constant.EnumConstants;
import constant.EnumConstants.EInstruction;
import constant.EnumConstants.EInterrupt;
import constant.EnumConstants.EProcessState;
import cpu.CentralProcessingUnit;
import cpu.Register;
import os.FileManager;
import memory.Memory;
import os.MemoryManager;

public class ProcessManager {
	// using

	// Declare child
	private ProcessQueue readyQueue;
	public Process deReadyQueue() {return readyQueue.dequeue();}
	public void enReadyqueue(Process readyProcess) {
		this.readyQueue.enqueue(readyProcess);}

	private ProcessQueue ioQueue;
	public Process getIoQueue() {return ioQueue.dequeue();}
	public void enIOqueue(Process ioProcess) {this.ioQueue.enqueue(ioProcess);}

	// constructor
	public ProcessManager() {
		this.readyQueue = new ProcessQueue();
		this.ioQueue = new ProcessQueue();

	}
	
	public void showProcessQueue() {
//		System.out.println("show ReadyQueue : ");
//		for(int i = 0; i< this.readyQueue.queue.size();i++) {
//			System.out.print(this.readyQueue.queue.elementAt(i));
//		}
//		System.out.println("show WaitQueue : ");
//		for(int i = 0; i< this.ioQueue.queue.size();i++) {
//			System.out.print(this.ioQueue.queue.elementAt(i));
//		}
	}
}
