package rom;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import board.Board;

public class BIOS {
	
	// Component
	private Board board;
	
	// Constructor
	public BIOS() {this.board = new Board();}
	
	public void initialize() {this.board.initialize();}
	
	public void run() { 
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {e.printStackTrace();}
	}
}
