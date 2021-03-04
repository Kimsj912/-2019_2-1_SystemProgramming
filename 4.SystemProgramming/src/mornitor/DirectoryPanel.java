package mornitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import cpu.CentralProcessingUnit;
import memory.Memory;
import os.MemoryManager;

public class DirectoryPanel extends JPanel {

    private Vector<String> dircetoryStrings;
    private Vector<String> pDircetories;
//	private JButton pDircetoryButton; //거슬거슬
	private JButton launch;
    public JList<String> directoryList;
    private String nowDircetory;
	private MemoryManager memoryManager;
	private AddressPanel addressPanel;
	private JPanel consolePane;
	private CodeFrame codeFrame;

    private DefaultListModel<String> model;
	private Object centralProcessingU;
	private CentralProcessingUnit centralProcessingUnit;

	public DirectoryPanel() {
		this.dircetoryStrings = new Vector<>();
        this.pDircetories = new Vector<>();
        
		this.setLayout(new BorderLayout());
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 3));

        JPanel a = new JPanel();
        a.setLayout(new GridLayout(1, 3));
        JPanel j = new JPanel();
        j.setBackground(Color.white);
        a.add(j);

        JLabel sdirectory = new JLabel("<directory>");
        sdirectory.setHorizontalAlignment(JLabel.CENTER);
        sdirectory.setFont(new Font("SansSerif", Font.PLAIN, 20));
        sdirectory.setOpaque(true);
        sdirectory.setBackground(Color.white);
        sdirectory.setForeground(Color.black);
        a.add(sdirectory);


        this.launch = new JButton("Launch");
        this.launch.setFont(new Font("SansSerif", Font.PLAIN, 20));
        a.add(launch);
        
        this.add(a, BorderLayout.NORTH);

        this.directoryList = new JList<>(this.dircetoryStrings);
        this.directoryList.setFont(new Font("SansSerif", Font.PLAIN, 20));
        this.directoryList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.directoryList.setVisibleRowCount(10);
        this.directoryList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String value = directoryList.getSelectedValue();
				addressPanel.driveName.setText(value);
				if (isDirectory(value)) {
                    pDircetories.add(nowDircetory);
                    dircetoryStrings.clear();
                    subDirList(value);
                    directoryList.setModel(new AbstractListModel<String>() {
                        public int getSize() {
                            return dircetoryStrings.size();
                        }

                        public String getElementAt(int i) {
                            return dircetoryStrings.elementAt(i);
                        }
                    });
                }
            }
        });
        this.add(this.directoryList, BorderLayout.CENTER);
//        this.pDircetoryButton.addActionListener((e) -> {
//            String temp = this.pDircetories.get(this.pDircetories.size() - 1);
//            this.addressPanel.driveName.setText(temp);
//            subDirList(temp);
//            this.pDircetories.removeElementAt(this.pDircetories.size() - 1);
//            directoryList.setModel(new AbstractListModel<String>() {
//                public int getSize() {
//                    return dircetoryStrings.size();
//                }
//
//                public String getElementAt(int i) {
//                    return dircetoryStrings.elementAt(i);
//                }
//            });
//        });
	}
	public void associate(MemoryManager memoryManager) {
		this.memoryManager = memoryManager;
	}
	public void associate(AddressPanel addressPanel, JPanel consolePane) {
		this.addressPanel = addressPanel;
		this.consolePane = consolePane;
	
		this.launch.addActionListener((e) -> {
        	//load
			File file = new File(this.directoryList.getSelectedValue());
            this.memoryManager.fileOpen(file);
            
            this.codeFrame = new CodeFrame(); // 얘또 이동시킬것. 코드가 실행되면 생성되게 한다.
            
            this.model = new DefaultListModel<>();
            this.model.addAll(this.memoryManager.getCodeLine());
            this.codeFrame.codeList.setModel(model);
            
            Thread thread = new Thread(()->this.centralProcessingUnit.run());
            thread.start();
            
        });		
	}
	public void connect(CentralProcessingUnit centralProcessingUnit) {
		this.centralProcessingUnit = centralProcessingUnit;
	}
	
	 public void subDirList(String source) {
	        nowDircetory = source;
	        File dir = new File(source);
	        File[] fileList = dir.listFiles();
	        for (int i = 0; i < fileList.length; i++) {
	            File file = fileList[i];
	            this.dircetoryStrings.add(file.getAbsolutePath());
	        }

	    }

	 public boolean isDirectory(String source) {
	        File file = new File(source);
	        return file.isDirectory();
	 }
	 public DefaultListModel<String> setDirectory() {
	        DefaultListModel<String> model = new DefaultListModel<>();
	        for (String s : dircetoryStrings) {
	            model.addElement(s);
	        }
	        return model;
	    }
}
