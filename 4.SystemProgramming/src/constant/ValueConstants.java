package constant;

public class ValueConstants {
	//process interrupt
	public final static long TIMESLICE = 300000; // time expired만드는 시간.
	public static final String CDriveAdr = "myCDrive\\";

	
	//memory
	public static final int MemorySize =10000;
	public static final int ProcessCount = 10;
	
	public static final int ProcessSize = MemorySize/ProcessCount;
	public static final int CSStartingP = 0;
	public static final int DSStartingP= ProcessSize/2;

	public static final String Remarker ="//";
	public static final String CodeMarker = ".code";
	public static final String StackMarker =".stack";
	
	
}
