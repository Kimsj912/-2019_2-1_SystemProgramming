package cpu;

public class ArithmeticLogicUnit {
	private Register mbr, ac, status;

	public void connect(Register mbr, Register ac, Register status) {
		this.mbr = mbr;
		this.ac = ac;
		this.status = status;		
	}

	public void add(int readvalue) {
		this.ac.setData(this.ac.getData()+readvalue);
	}

}
