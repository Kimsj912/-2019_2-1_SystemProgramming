package mornitor;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.Vector;

import javax.swing.*;

import cpu.CentralProcessingUnit;
import os.FileManager;
import fileSystem.FileSystem;
import memory.Memory;
import os.MemoryManager;
import os.ProcessManager;

public class UXManager {

    // associate
    private ProcessManager processManager;
    private FileManager fileManager;

    // connet HW
    private CentralProcessingUnit centralProcessingUnit;
    private Memory memory;
    private FileSystem fileSystem;
    
    //child
    private static OSFrame osFrame;
    private JPanel registerPane;
    private JPanel centerPane;

    private Object memoryM;
    private MemoryManager memoryManager;
	private CodeFrame codeFrame;

    // constructor
    public UXManager() {
        this.osFrame = new OSFrame();
       
    }

    // basic method
    //associate
    public void associate(FileManager fileManager, ProcessManager processManager, MemoryManager memoryManager) {
        // TODO Auto-generated method stub
        this.fileManager = fileManager;
        this.processManager = processManager;
        this.memoryManager = memoryManager;
        
        this.osFrame.associate(memoryManager);
        
//        Thread thread = new Thread(new Runnable() {
//        	public void run() {
//        		centralProcessingUnit.run();	
//        	}
//        });
    }

    //connect HW
    public void connect(CentralProcessingUnit centralProcessingUnit, Memory memory, FileSystem fileSystem) {
        this.centralProcessingUnit = centralProcessingUnit;
        this.memory = memory;
        this.fileSystem = fileSystem;
        
        this.osFrame.connect(this.centralProcessingUnit, this.memory);
    }

    // initialize
    public void initialize() {//보여줄 창을 켜줘. 그리고 여기서 선택되면 이제 바꾸ㅡㄴ 루틴을 만들도록 해야함.
    	String firstFile = this.fileManager.getFirstFile().getAbsolutePath();
        this.osFrame.getDirectoryPanel().subDirList(firstFile);
		this.osFrame.getAddressPanel().driveName.setText(firstFile);
        this.osFrame.getDirectoryPanel().directoryList.setModel(this.osFrame.getDirectoryPanel().setDirectory());
    }

   
    public static void print(String text){
    	osFrame.printConsole(text);
    	
    }


}
