package os;

import java.util.Vector;

public class ProcessQueue {
	
	public Vector<Process> queue;

	public ProcessQueue() {
		this.queue = new Vector<Process>();
	}
	public void enqueue(Process process) {
		this.queue.add(process);
	}

	public Process dequeue() {
		Process p = queue.firstElement();
		queue.removeElementAt(queue.indexOf(p));
		return p;
	}

}
