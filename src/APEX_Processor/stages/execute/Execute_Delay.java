package APEX_Processor.stages.execute;

import utillities.APEX_PreRequisits;
import utillities.LoggerFile;

public class Execute_Delay {
	public static void performDelayStage() {
		LoggerFile.logger.info("Inside performDelayStage Stage");
		try {
			System.err.println("Adding Delay in the Pipeline");
			APEX_PreRequisits.inDelay_FU = APEX_PreRequisits.inBranchFUtoDelay;
			
			if (APEX_PreRequisits.inDelay_FU != null && APEX_PreRequisits.inDelay_FU.instruction_OPCODE != null) {
				switch (APEX_PreRequisits.inDelay_FU.instruction_OPCODE) {
				case BNZ:
					System.err.println("BNZ in Delay Stage");
					break;
				case BZ:
					System.err.println("BZ in Delay Stage");
					break;
				case JUMP:
					System.err.println("JUMP in Delay Stage");
					break;
				case BAL:
					System.err.println("BAL in Delay Stage");
					break;
				}
			}
			APEX_PreRequisits.inBranch_FU = null;
			APEX_PreRequisits.inDecodetoBranchFU = null;
			APEX_PreRequisits.inBranchFUtoDelay = null;
			APEX_PreRequisits.inDelay_FU = null;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
