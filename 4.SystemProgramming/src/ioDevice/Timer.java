package ioDevice;

import constant.EnumConstants.EInterrupt;
import os.Interrupt;
import os.InterruptHandler;

public class Timer extends IODevice {

	
	private long TIMESLICE;
	private long stimeBuffer;
	private InterruptHandler interruptHandler;

	public Timer() {
		this.TIMESLICE = constant.ValueConstants.TIMESLICE;
	}
	
	public void associate(InterruptHandler interruptHandler) {
		this.interruptHandler = interruptHandler;
	}

	public void startTimer() {
		System.out.println("startTimer");
		this.stimeBuffer = System.nanoTime();
		Thread thread = new Thread(new Runnable() {
			public void run() {
				while (!checkTimeExpired()) {
				}
				Interrupt i = new Interrupt();
				i.seteType(EInterrupt.eTimeFinished);
				interruptHandler.addInterrupt(i);
			}
		});
		System.out.println(thread.isAlive());

	}
	public boolean checkTimeExpired() {
		return System.nanoTime()- this.stimeBuffer>this.TIMESLICE ? true:false;
	}
}
