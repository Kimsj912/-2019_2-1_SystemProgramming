package mornitor;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cpu.CentralProcessingUnit;
import memory.Memory;
import os.MemoryManager;

public class OSFrame extends JFrame {
	
	
    private static JPanel consolePane;
    

	public AddressPanel addressPanel;
	public AddressPanel getAddressPanel() {return addressPanel;};
	private JPanel centerPane;
	public DirectoryPanel directoryPanel;
	public DirectoryPanel getDirectoryPanel() {return directoryPanel;};


	private MemoryManager memoryManager;


	private CentralProcessingUnit centralProcessingUnit;


	private Memory memory;

	public OSFrame() {
		super("Virual Machine Making");
		this.setBounds(400, 100, 1300, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //toppane
        this.addressPanel = new AddressPanel();
		this.add(this.addressPanel, BorderLayout.NORTH);

        //bottomPane
        this.centerPane = new JPanel();
        this.centerPane.setLayout(new GridLayout(1, 3));
	        //inputPane
	        this.directoryPanel = new DirectoryPanel();
	        this.centerPane.add(directoryPanel);
        
        //consolePane
        consolePane = new ConsolePane();
        this.centerPane.add(consolePane);

        this.add(this.centerPane, BorderLayout.CENTER);
        this.setVisible(true);

	}
	public void associate(MemoryManager memoryManager) {
		this.memoryManager = memoryManager;
		this.directoryPanel.associate(this.memoryManager);

		this.directoryPanel.associate(this.addressPanel, this.consolePane);
	
	}
	public void connect(CentralProcessingUnit centralProcessingUnit, Memory memory) {
		this.centralProcessingUnit = centralProcessingUnit;
        this.memory = memory;
        
        this.directoryPanel.connect(this.centralProcessingUnit);
	}
	public void initialize(){
		
	}
	public static void printConsole(String text) {
    	ConsolePane.setText(text);
    	consolePane.repaint();

	}
	
}
