package utillities;

public class Constants {
	
//	public static final int BYTE_SIZE = 4;
	
	public static final int INSTRUCTION_SIZE = 1000;

	// Data MEMORY_REGISTER ranges from 0 through 3999. So MEMORY_REGISTER size
	// should be 4000
	public static final int MEMORY_SIZE = 4000;
	
	public static final int BYTE_SIZE = 4;
	public static final int ZERO = 0;

	// 16 architectural registers, R0 through R15 AND 1 special
	// architectural_registers X TO save address of the next
	// instruction in BAL <architectural_registers>, literal
	// Total registers should be 16 + 1 = 17
	public static final int ARCHITECTURAL_REGISTERS = 17;

	// the first instruction in the ASCII code file, which is assumed to be at
	// address 4000. So starting address should be 4000
	public static final int STARTING_INSTRUCTION_ADDRESS = 4000;

	public static final String REGISTER_PREFIX = "R";
	public static final String PREFIX_X = "X";
	public static final String FETCH_STAGE = "FETCH";
	public static final String DECODE_STAGE = "DECODE";
	public static final String EXECUTE_INT_ALU1_STAGE = "EX_INT_ALU1";
	public static final String EXECUTE_INT_ALU2_STAGE = "EX_INT_ALU2";
	public static final String EXECUTE_FU_ALU_STAGE = "FU_ALU";
	public static final String EXECUTE_DELAY_STAGE = "DELAY";
	public static final String MEM_STAGE = "MEM";
	public static final String WB_STAGE = "WB";
	public static final String EMPTY_STAGE = "EMPTY";

	public static final String DELIMITER_SPACE = " ";
	public static final String DELIMITER_TAB = "\t";
	public static final String DELIMITER_COMMA = ",";
	public static final String DELIMITER_HASH = "#";
	public static final String DELIMITER_EMPTY = "";
	public static final int INVALID_DATA = Integer.MIN_VALUE;
	public static final int MINUSONE = -1;
	
	public static final String LOG = "ApexLog";
	public static final String LOGFILE = "ApexLogFile.log";
	
}
