package APEX_Processor.stages.execute;

import APEX_Processor.APEX;
import utillities.APEX_PreRequisits;
import utillities.Constants;
import utillities.LoggerFile;

public class Execute_BranchFU {
	@SuppressWarnings("incomplete-switch")
	public static void performBranchFU() {
//		//System.out.println("Inside performBranchFU Stage");
		try {
			

			APEX_PreRequisits.inBranchFUtoDelay = APEX_PreRequisits.inBranch_FU;
			APEX_PreRequisits.inBranch_FU = APEX_PreRequisits.inDecodetoBranchFU;
			
			if (APEX_PreRequisits.inBranch_FU != null && APEX_PreRequisits.inBranch_FU.instruction_OPCODE != null) {
				LoggerFile.logger.info("inBranch_FU.instr_id --> " + APEX_PreRequisits.inBranch_FU.instruction_OPCODE);
				switch (APEX_PreRequisits.inBranch_FU.instruction_OPCODE) {
				case BNZ:
					System.err.println("BNZ in Branch FU Stage");
					if (APEX_PreRequisits.PSW_ZeroFlag != 0) {
						APEX.branchFlush();
						int branch_address = APEX_PreRequisits.inBranch_FU.address;
						int literal_value = APEX_PreRequisits.inBranch_FU.literal;
						APEX_PreRequisits.PROGRAM_COUNTER = branch_address + literal_value;
					}else if(APEX_PreRequisits.PSW_ZeroFlag == 0){
						System.err.println("BNZ Operation not performed since value of Zero Flag : " + APEX_PreRequisits.PSW_ZeroFlag);
						APEX_PreRequisits.inDecodetoEX_INT_ALU1 = null;
						APEX_PreRequisits.inDecodetoBranchFU = null;
					}
					break;
				case BZ:
					System.err.println("BZ in Branch FU Stage");
					if (APEX_PreRequisits.PSW_ZeroFlag == 0) {
						APEX.branchFlush();
						int branch_address = APEX_PreRequisits.inBranch_FU.address;
						int literal_value = APEX_PreRequisits.inBranch_FU.literal;
						APEX_PreRequisits.PROGRAM_COUNTER = branch_address + literal_value;
					}else if(APEX_PreRequisits.PSW_ZeroFlag != 0){
						System.err.println("BZ Operation not performed since value of Zero Flag : " + APEX_PreRequisits.PSW_ZeroFlag);
						APEX_PreRequisits.inDecodetoEX_INT_ALU1 = null;
						APEX_PreRequisits.inDecodetoBranchFU = null;
					}
					break;
				case JUMP:
					System.err.println("JUMP in Branch FU Stage");
					APEX.branchFlush();
					APEX_PreRequisits.stalled = false;
					APEX_PreRequisits.PROGRAM_COUNTER = 
							APEX_PreRequisits.architectural_registers[APEX_PreRequisits.inBranch_FU.dest_register]
							+ APEX_PreRequisits.inBranch_FU.literal;
					break;
				case BAL:
					System.err.println("BAL in Branch FU Stage");
					APEX.branchFlush();
					APEX_PreRequisits.stalled = false;
					// saves address of the next instruction in a special
					// register X
					APEX_PreRequisits.architectural_registers[Constants.ARCHITECTURAL_REGISTERS] = 
							APEX_PreRequisits.inBranch_FU.address
							+ Constants.BYTE_SIZE;
					// sets fetch PC to contents of <reg> plus the literal
					APEX_PreRequisits.PROGRAM_COUNTER = 
							APEX_PreRequisits.architectural_registers[APEX_PreRequisits.inBranch_FU.dest_register]
							+ APEX_PreRequisits.inBranch_FU.literal;
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			LoggerFile.logger.info("Exception in performBranchFU : " + e.getClass().getSimpleName());
		}
	}
}
