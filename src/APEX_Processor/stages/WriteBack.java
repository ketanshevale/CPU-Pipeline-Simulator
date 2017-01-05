package APEX_Processor.stages;

import APEX_Processor.Forwarding;
import result.FinalResult;
import utillities.APEX_PreRequisits;
import utillities.Constants;
import utillities.LoggerFile;
import utillities.OperationCodes;

/*
 * 
 * 
 * * *
 * method: forwardingScenario(Instruction sender_ins, Instruction receiver_ins)
 * @param sender_ins	: Sending the data which can be forwarded
 * @param receiver_ins	: Receiving the values send by the sender
 * 
 * Forwarding possibilities:
 	 * LOAD can forward data in below listed circumstances
	 * 1) From : Instruction exiting MEM stage -> To : Instruction entering EX Stage
	 * Load can Forward its value to either sources of <op> instruction or <rsrc1> of LOAD
	 * instruction or <rscr2> of store instruction which is in D/RF stage.
	 * Usability 			:  1) Register to Register operations like ADD, SUB, MUL, DIV, OR, AND, XOR
	 * 						   2) LOAD to Register operations like ADD, SUB, MUL, DIV, OR, AND, XOR
	 * Sender Instruction	: APEX_PreRequisits.inMEMtoWB
	 * Receiver Instruction	: APEX_PreRequisits.inDecodetoNext
	 * Function call		: Forwarding.forwardingScenario(APEX_PreRequisits.inMEMtoWB, APEX_PreRequisits.inDecodetoNext)
	 * 
	 * 2) From : Instruction exiting MEM stage -> To : Instruction entering MEM Stage
	 * Load can forward the value to <rsrc1> of store instruction, which is coming out of EX Stage into MEM-Stage.
	 * Usability 			: LOAD to following STORE instruction.
	 * Sender Instruction	: APEX_PreRequisits.inMEMtoWB
	 * Receiver Instruction	: APEX_PreRequisits.inEX_INT_ALU2toMEM
	 * Function call		: Forwarding.forwardingScenario(APEX_PreRequisits.inMEMtoWB, APEX_PreRequisits.inEX_INT_ALU2toMEM)
* * *
* 
 */

public class WriteBack {
	public static void performWB(int cycle) {
		LoggerFile.logger.info("Inside WB Stage");
		try {
			// Verifying the forwarding possibilities
			// if(APEX_PreRequisits.inMEMtoWB != null){
			// if(APEX_PreRequisits.inDecodetoNext != null){
			// Forwarding.forwardingExitingMEMtoEnteringEX(APEX_PreRequisits.inMEMtoWB,
			// APEX_PreRequisits.inDecodetoNext);
			// }
			//
			// if(APEX_PreRequisits.inEX_INT_ALU2toMEM != null){
			// Forwarding.forwardingExitingMEMtoEnteringMEM(APEX_PreRequisits.inMEMtoWB,
			// APEX_PreRequisits.inEX_INT_ALU2toMEM);
			// }
			// }

			APEX_PreRequisits.inWB = APEX_PreRequisits.inMEMtoWB;
			if (APEX_PreRequisits.inWB != null && APEX_PreRequisits.inWB.instruction_OPCODE != null) {

				LoggerFile.logger.info(" WB " + APEX_PreRequisits.inWB + "\n");

				switch (APEX_PreRequisits.inWB.instruction_OPCODE) {
				case HALT:
					APEX_PreRequisits.inWB.instruction_OPCODE = OperationCodes.HALT;
					FinalResult.populateStagesToPrint();
					System.err.println("\nExecution is HALT"); 
					System.err.println("Reason : HALT Insruction in Write Back Stage\n"); 
					FinalResult.displayAll();
					System.exit(0);
					break;
				case MOVC:
				case LOAD:
				case ADD:
				case MUL:
				case SUB:
				case AND:
				case XOR:
				case OR:
					if(APEX_PreRequisits.inWB.dest_register_data != Constants.INVALID_DATA){
						APEX_PreRequisits.architectural_registers[APEX_PreRequisits.inWB.dest_register] = 
								APEX_PreRequisits.inWB.dest_register_data;
						APEX_PreRequisits.cycle_ar_reg_updated[APEX_PreRequisits.inWB.dest_register] 
								= cycle;
					}
//					APEX_PreRequisits.cycle_ar_reg_updated[APEX_PreRequisits.inWB.dest_register] = 
//							simulationCycleIn;
					break;
				}

				if (APEX_PreRequisits.inWB.src1 != Constants.INVALID_DATA) {
					APEX_PreRequisits.valid_registers[APEX_PreRequisits.inWB.src1] = true;
				}
				if (APEX_PreRequisits.inWB.src2 != Constants.INVALID_DATA) {
					APEX_PreRequisits.valid_registers[APEX_PreRequisits.inWB.src2] = true;
				}
				if (APEX_PreRequisits.inWB.dest_register != Constants.INVALID_DATA) {
					APEX_PreRequisits.valid_registers[APEX_PreRequisits.inWB.dest_register] = true;
				}
				// Validation for stall
				if (APEX_PreRequisits.stalled) {
					APEX_PreRequisits.inDecodetoEX_INT_ALU1 = null;
					if (Decode.checkDecodeStall(APEX_PreRequisits.inDecode))
						return;
					APEX_PreRequisits.stalled = false;
					Decode.update_registers_values();
				}

			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LoggerFile.logger.info("Exception in WB Stage : " + e.getClass().getSimpleName());
		}
	}

}
