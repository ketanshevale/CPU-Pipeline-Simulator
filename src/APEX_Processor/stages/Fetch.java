package APEX_Processor.stages;

import utillities.APEX_PreRequisits;
import utillities.Constants;
import utillities.LoggerFile;
import utillities.OperationCodes;

public class Fetch {

	public static int info_dest_reg_previous_DecodeFU;
	public static OperationCodes info_opcode_prevois_DEcodeFU;

	public static void performFetch() {
		try {

			APEX_PreRequisits.inBranch_FU = null;
			if (!(APEX_PreRequisits.flag_check_EOF_INC_PC_reached && APEX_PreRequisits.stalled)) {

				if (!APEX_PreRequisits.stalled) {
					APEX_PreRequisits.inFetchtoDecode = APEX_PreRequisits.inFetch;
					if (APEX_PreRequisits.flag_check_EOF_INC_PC_reached)
						APEX_PreRequisits.inFetch = null;

					// Fetching the instruction from the instruction list and
					// feeding it into the pipeline
					APEX_PreRequisits.inFetch = APEX_PreRequisits.instruction_set_list[(APEX_PreRequisits.PROGRAM_COUNTER
							- Constants.STARTING_INSTRUCTION_ADDRESS) / Constants.BYTE_SIZE];
					// If next instruction exist then increment the Program
					// counter
					if (APEX_PreRequisits.instruction_set_list[(APEX_PreRequisits.PROGRAM_COUNTER
							- Constants.STARTING_INSTRUCTION_ADDRESS) / Constants.BYTE_SIZE].contains) {
						APEX_PreRequisits.PROGRAM_COUNTER += Constants.BYTE_SIZE;
					} else {
						// Once EOF is reached; Program Counter will not be
						// incremented
						if (!APEX_PreRequisits.flag_check_EOF_INC_PC_reached) {
							APEX_PreRequisits.PROGRAM_COUNTER += Constants.BYTE_SIZE;
							APEX_PreRequisits.flag_check_EOF_INC_PC_reached = true;
						}
					}
				} else {
					return;
				}
			} else {
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			LoggerFile.logger.info("Exception in performfetch  : " + e.getClass().getSimpleName());
		}
	}
}
