package rom;

public class Main {
	
	public static void main(String[] args) {
		BIOS bios = new BIOS();
		bios.initialize();
		bios.run(); 
	}
}
