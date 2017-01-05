package utillities;

import APEX_Processor.Instruction;

public class APEX_PreRequisits {

	public static int INSTRUCTION_COUNT;

	// CPU representations
	public static int PSW_ZeroFlag;
	public static Integer[] architectural_registers;
	public static boolean[] valid_registers;
	public static Integer[] store_current_ins_adr_in_ex;
	public static Integer[] MEMORY_REGISTER;
	public static Integer PROGRAM_COUNTER;

	// hold the instruction_set_list of the input file
	public static Instruction[] instruction_set_list;

	// hold pipeline details in real time
	public static Instruction inFetch = null, inDecode = null, inEX_INT_ALU1_FU = null, inEX_INT_ALU2_FU = null,
			inBranch_FU = null, inDelay_FU = null, inMEM = null, inWB = null;

	// passing on. work as pipeline registers.
	public static Instruction inFetchtoDecode = null, inDecodetoEX_INT_ALU1 = null, inDecodetoBranchFU = null,
			inEX_INT_ALU1toALU2 = null, inBranchFUtoDelay = null, inMEMtoWB = null, inEX_INT_ALU2toMEM = null;

	public static Instruction tempstage = null;

	// various flags to handle message passing
	public static boolean stalled = false;

	// used to stop incrementing PC once once EOF reached
	public static boolean flag_check_EOF_INC_PC_reached = false;

	// take input for no of cycles to simulate.
	public static int cycles_count_for_simulation;
	
	public static boolean halt_flag = false;
	
	// hold the instruction_set_list of the input file
	public static Integer[] forwarded_register;
	public static Integer[] cycle_forward_reg_updated;
	public static Integer[] cycle_ar_reg_updated;
	
	public static boolean check_stall_possibility_in_EX1 = false;

}