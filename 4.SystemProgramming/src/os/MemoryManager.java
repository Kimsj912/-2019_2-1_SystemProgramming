package os;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

import constant.EnumConstants.EInstruction;
import cpu.CentralProcessingUnit;
import memory.Memory;
import os.FileManager;
import os.Process;
import os.ProcessManager;

public class MemoryManager {

	//Declare associate
	private ProcessManager processManager;
	private FileManager fileManager;
	
	//Declare connect hw
	private Memory memory;
	private CentralProcessingUnit centralProcessingUnit;

	//component
	private int pidCount;
	public int getPidCount() {return pidCount;}
	public void setPidCount(int pidCount) {this.pidCount = pidCount;}
	
	private Vector<String> codes = new Vector<String>();
	private File nowDirectory;

	//cunstructor
	public MemoryManager() {
		this.pidCount=0;
	}
	//basic method
	//associate
	public void associate(ProcessManager processManager, FileManager fileManager) {
		this.processManager = processManager;
		this.fileManager = fileManager;
	}
	//connet HW
	public void connect(Memory memory, CentralProcessingUnit centralProcessingUnit) {
		this.memory = memory;
		this.centralProcessingUnit = centralProcessingUnit;
	}
	//initialize
	public void initialize() { this.nowDirectory = this.fileManager.getFirstFile(); }

	public Vector<String> getCodeLine(){ return this.codes;}
	
	public void fileOpen(File file) {
		boolean isfileExist = false;
		for (String fileN : nowDirectory.list()) {
			if (fileN.equals(file.getName())) {
				isfileExist = true;
				break;
			}
		}
		if (!isfileExist) return;
			File wantfile = new File(file.getPath());
			if (!wantfile.isFile()) {
				this.nowDirectory = wantfile;
				fileOpen(new File(this.nowDirectory.getPath()));
			} else {
				this.load(wantfile);
			}

	}

	public void load(File wantfile) {
		Process process = this.getfile(wantfile.getAbsolutePath());
		process.connect(this.centralProcessingUnit);
		process.getPcb().setId(pidCount++);
		
		this.processManager.enReadyqueue(process);
		this.memory.addProcess(process);
	}

	public Process getfile(String fileName) {// 로더가 할일과 파일매니저가 할일이 섞여있어.
		try {
			int stackSegmentSize = 0;
			int[] codes = null;
			Scanner sc = new Scanner(new File(fileName));

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (!line.isEmpty()) {
					if (!line.substring(0, 2).equals(constant.ValueConstants.Remarker)) {
						if (line.substring(0, 5).equals(constant.ValueConstants.CodeMarker)) {
							codes = this.parseCode(sc);
						} else if (line.substring(0, 6).equals(constant.ValueConstants.StackMarker)) {
							stackSegmentSize = this.parseStack(sc);
						}
					}
				}
			}
			System.out.println("=========================process생성============================");
			System.out.println("=> process에는 \"" + stackSegmentSize + "\"의 stackSegmentSize가 들어갑니다.");
			System.out.println("=> codeSegment에는 코드가 int로 변형되어 어레이에 저장된 " + Arrays.toString(codes) + "로 저장됩니다.");
			Process process = new Process(stackSegmentSize, codes);
			sc.close();
			return process;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private int parseStack(Scanner sc) {
		String line = sc.nextLine();
		while (line != ".code") {
			if (!(line.substring(0, 2).equals("//") || line.isEmpty())) {
				String str = line.replaceAll("[^0-9]", "");
				return Integer.parseInt(str);
			}
			line = sc.nextLine();
		}
		return 0;
	}

	private int[] parseCode(Scanner sc) {
		Vector<Integer> forreturnVect = new Vector<Integer>();
		String line = sc.nextLine();
		while (!line.equals("halt")) {
			if (!line.isEmpty()) {
				if (!line.substring(0, 2).equals(constant.ValueConstants.Remarker)) {
					String[] linearr = line.split(" ");
					int opcode = EInstruction.valueOf(linearr[0]).ordinal() << 16;
					int value = Integer.parseInt(linearr[1]);
					forreturnVect.add(opcode + value);
					codes.add(line);
				}
			}
			line = sc.nextLine();
		}
		int halt = EInstruction.halt.ordinal() << 16;
		forreturnVect.add(halt);

		int[] forreturn = new int[forreturnVect.size()];
		for (int i = 0; i < forreturnVect.size(); i++) {
			forreturn[i] = forreturnVect.elementAt(i);
		}
		return forreturn;
	}
	
	

}
