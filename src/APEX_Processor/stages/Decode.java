package APEX_Processor.stages;

import APEX_Processor.Instruction;
import utillities.APEX_PreRequisits;
import utillities.Constants;
import utillities.LoggerFile;
import utillities.OperationCodes;

public class Decode {

	public static Integer info_dest_reg_previous_intFU1;
	public static OperationCodes info_opcode_prevois_intFU1;
	/*
	 * public static int info_dest_reg_previous_DecodeFU; public static
	 * OperationCodes info_opcode_prevois_DEcodeFU;
	 */

	public static void performDRF() {
		try {
			// If instruction is send from fetch stage to decode stage
			if (APEX_PreRequisits.inDecode != null) {
				/*
				 * Fetch.info_dest_reg_previous_DecodeFU =
				 * APEX_PreRequisits.inDecode.dest_register;
				 * Fetch.info_opcode_prevois_DEcodeFU =
				 * APEX_PreRequisits.inDecode.instruction_OPCODE;
				 */
				// Verifying if there is already stall in the pipeline
				if (APEX_PreRequisits.stalled) {
					APEX_PreRequisits.inDecodetoEX_INT_ALU1 = null;
					if (checkDecodeStall(APEX_PreRequisits.inDecode)) {
						return;
					}
					APEX_PreRequisits.stalled = false;
					update_registers_values();
				}
				// If there is no stall in the pipeline
				else {
					// Transfer instruction to the next stage. And then it will
					// verify if any stall might
					// introduce by the instruction which is introduce by the
					// current instruction.
					inter_stage_instrucution_transfer();
					// verify stall due to the
					steps_to_verify_stall();
				}
			}
			// This scenario will be for the cases when there is already stall
			// in the pipeline
			// and no data will be sent from the fetch to Decode stage
			// Freshly execution - No data passed from Fetch to Decode
			else {
				// Transferring instruction to the next stage in the pipeline
				inter_stage_instrucution_transfer();
				if (APEX_PreRequisits.inDecode != null) {
					steps_to_verify_stall();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerFile.logger.info("Exception in D/RF Stage  : " + e.getClass().getSimpleName());
		}
	}

	public static void inter_stage_instrucution_transfer() {

		if (APEX_PreRequisits.inDecode == null) {
			inter_stage_instrucution_transfer_ToEx1();
		} else {
			LoggerFile.logger.info("inDecode.instruction_OPCODE --> " + APEX_PreRequisits.inDecode.instruction_OPCODE);
			if (APEX_PreRequisits.inDecode.instruction_OPCODE == OperationCodes.BNZ
					|| APEX_PreRequisits.inDecode.instruction_OPCODE == OperationCodes.BZ
					|| APEX_PreRequisits.inDecode.instruction_OPCODE == OperationCodes.BAL
					|| APEX_PreRequisits.inDecode.instruction_OPCODE == OperationCodes.JUMP) {
				inter_stage_instrucution_transfer_toBFU();
			} else {
				inter_stage_instrucution_transfer_ToEx1();
			}
		}
	}

	public static void inter_stage_instrucution_transfer_ToEx1() {
		APEX_PreRequisits.inDecodetoEX_INT_ALU1 = APEX_PreRequisits.inDecode;
		APEX_PreRequisits.inDecode = APEX_PreRequisits.inFetchtoDecode;
	}

	public static void inter_stage_instrucution_transfer_toBFU() {
		APEX_PreRequisits.inDecodetoBranchFU = APEX_PreRequisits.inDecode;
		APEX_PreRequisits.inDecode = APEX_PreRequisits.inFetchtoDecode;
	}

	public static void steps_to_verify_stall() {
		// Verifyng Stall on the current instruction in the decode stage
		if (checkDecodeStall(APEX_PreRequisits.inDecode)) {
			// If stall then set stall variable value as true else false
			APEX_PreRequisits.stalled = true;
		} else {
			APEX_PreRequisits.stalled = false;
			// Since there is no stall due to the current instruction then it
			// can be forwarded to the execution stage.
			update_registers_values();
		}
	}

	public static boolean checkDecodeStall(Instruction inDecodeStage) {

		// any arithmatic instruction inside int ALU1 i.e not store and not null
		if (APEX_PreRequisits.inDecodetoEX_INT_ALU1 != null
				&& APEX_PreRequisits.inDecodetoEX_INT_ALU1.instruction_OPCODE != OperationCodes.STORE) {

			LoggerFile.logger.info("If not store in INTFU1 inside checkDecode Stall");
			/*
			 * if (inDecodeStage == null) return false;
			 */
			if (inDecodeStage.src1 != Constants.INVALID_DATA
					&& !APEX_PreRequisits.valid_registers[inDecodeStage.src1]) {
				LoggerFile.logger.info("Stall due to statemnt src1");

				return true;
			}
			if (inDecodeStage.src2 != Constants.INVALID_DATA
					&& !APEX_PreRequisits.valid_registers[inDecodeStage.src2]) {
				LoggerFile.logger.info("Stall due to statemnt src2");
				return true;
			}
			if (inDecodeStage.dest_register != Constants.INVALID_DATA
					&& !APEX_PreRequisits.valid_registers[inDecodeStage.dest_register]) {
				LoggerFile.logger.info("Stall due to statemnt dest 3");
				return true;
			}

			// return false;

			// not null and store inside
		} else if (APEX_PreRequisits.inDecodetoEX_INT_ALU1 != null
				&& APEX_PreRequisits.inDecodetoEX_INT_ALU1.instruction_OPCODE == OperationCodes.STORE) {

			LoggerFile.logger.info(" Destination resigter of STORE in next cycle to IntFU1 is "
					+ APEX_PreRequisits.inDecodetoEX_INT_ALU1.dest_register);
			LoggerFile.logger.info(" Destination resigter of any opcode in next cycle to Decode is "
					+ APEX_PreRequisits.inFetchtoDecode.dest_register);
			LoggerFile.logger.info(" store is in INTFU1 inside checkDecode Stall");

			if (APEX_PreRequisits.inFetchtoDecode.dest_register == APEX_PreRequisits.inDecodetoEX_INT_ALU1.dest_register) {
				LoggerFile.logger.info("Stall is true since destination registers are matching ");

				return true;
			}

			if (APEX_PreRequisits.inFetchtoDecode.dest_register == APEX_PreRequisits.inDecodetoEX_INT_ALU1.dest_register) {

				LoggerFile.logger.info("No Stall since destination registers are not matching ");
				return false;

			}

			/*
			 * if (inDecodeStage.dest_register !=
			 * APEX_PreRequisits.inFetch.dest_register | inDecodeStage.src1 ==
			 * APEX_PreRequisits.inFetch.src1 | inDecodeStage.src1 ==
			 * APEX_PreRequisits.inFetch.src2 | inDecodeStage.src1 ==
			 * APEX_PreRequisits.inFetch.dest_register) {
			 * //System.out.println("Stall is false due to complicated conditions"
			 * );
			 * 
			 * //System.out.println("Now checking for current condition ");
			 * 
			 * return false; }
			 */

		} else {
			if (inDecodeStage.src1 != Constants.INVALID_DATA
					&& !APEX_PreRequisits.valid_registers[inDecodeStage.src1]) {
				LoggerFile.logger.info("Stall due to statemnt src1");

				return true;
			}
			if (inDecodeStage.src2 != Constants.INVALID_DATA
					&& !APEX_PreRequisits.valid_registers[inDecodeStage.src2]) {
				LoggerFile.logger.info("Stall due to statemnt src2");
				return true;
			}
			if (inDecodeStage.dest_register != Constants.INVALID_DATA
					&& !APEX_PreRequisits.valid_registers[inDecodeStage.dest_register]) {
				LoggerFile.logger.info("Stall due to statemnt dest 3");
				return true;
			}
		} /*
			 * else { if (inDecodeStage == null) {
			 * //System.out.println("Stall is false  inside else Part 1"); return
			 * false; } //System.out.println(" Inside else");
			 * 
			 * 
			 * //System.out.println( " Destination resigter of in IntFU1 is " +
			 * APEX_PreRequisits.inEX_INT_ALU1_FU.dest_register);
			 * 
			 * //System.out.println(" Destination resigter  in Decode is " +
			 * inDecodeStage.dest_register);
			 * 
			 * if (inDecodeStage.src1 != Constants.INVALID_DATA &&
			 * !APEX_PreRequisits.valid_registers[inDecodeStage.src1]) {
			 * //System.out.println("Stall is true  inside else Part 2"); return
			 * true; }
			 * 
			 * if (inDecodeStage.src2 != Constants.INVALID_DATA &&
			 * !APEX_PreRequisits.valid_registers[inDecodeStage.src2]) {
			 * //System.out.println("Stall is true  inside else Part 3"); return
			 * true; }
			 * 
			 * if (inDecodeStage.dest_register != Constants.INVALID_DATA &&
			 * !APEX_PreRequisits.valid_registers[inDecodeStage.dest_register])
			 * { //System.out.println("Stall is true  inside else Part 4"); return
			 * true; }
			 * 
			 * }
			 */
		LoggerFile.logger.info("False by default");
		return false;
	}

	public static void update_registers_values() {

		if (APEX_PreRequisits.inDecode.src1 != Constants.INVALID_DATA) {

			int forward_reg_updated_cycle = APEX_PreRequisits.cycle_forward_reg_updated[APEX_PreRequisits.inDecode.src1];
			int ar_reg_updated_cycle = APEX_PreRequisits.cycle_ar_reg_updated[APEX_PreRequisits.inDecode.src1];
			if (APEX_PreRequisits.forwarded_register[APEX_PreRequisits.inDecode.src1] != Constants.INVALID_DATA) {
				APEX_PreRequisits.inDecode.src1_data = APEX_PreRequisits.forwarded_register[APEX_PreRequisits.inDecode.src1];
			} else {
				APEX_PreRequisits.inDecode.src1_data = APEX_PreRequisits.architectural_registers[APEX_PreRequisits.inDecode.src1];
			}

			APEX_PreRequisits.valid_registers[APEX_PreRequisits.inDecode.src1] = false;
			APEX_PreRequisits.store_current_ins_adr_in_ex[APEX_PreRequisits.inDecode.src1] = APEX_PreRequisits.inDecode.address;
		}

		if (APEX_PreRequisits.inDecode.src2 != Constants.INVALID_DATA) {

			int forward_reg_updated_cycle = APEX_PreRequisits.cycle_forward_reg_updated[APEX_PreRequisits.inDecode.src2];
			int ar_reg_updated_cycle = APEX_PreRequisits.cycle_ar_reg_updated[APEX_PreRequisits.inDecode.src2];
			if (APEX_PreRequisits.forwarded_register[APEX_PreRequisits.inDecode.src2] != Constants.INVALID_DATA) {
				APEX_PreRequisits.inDecode.src2_data = APEX_PreRequisits.forwarded_register[APEX_PreRequisits.inDecode.src2];
			} else {
				APEX_PreRequisits.inDecode.src2_data = APEX_PreRequisits.architectural_registers[APEX_PreRequisits.inDecode.src2];
			}

			APEX_PreRequisits.valid_registers[APEX_PreRequisits.inDecode.src2] = false;
			APEX_PreRequisits.store_current_ins_adr_in_ex[APEX_PreRequisits.inDecode.src2] = APEX_PreRequisits.inDecode.address;
		}

		if (APEX_PreRequisits.inDecode.instruction_OPCODE == OperationCodes.STORE
				&& APEX_PreRequisits.inDecode.dest_register != Constants.INVALID_DATA) {

			int forward_reg_updated_cycle = APEX_PreRequisits.cycle_forward_reg_updated[APEX_PreRequisits.inDecode.dest_register];
			int ar_reg_updated_cycle = APEX_PreRequisits.cycle_ar_reg_updated[APEX_PreRequisits.inDecode.dest_register];
			if (APEX_PreRequisits.forwarded_register[APEX_PreRequisits.inDecode.dest_register] != Constants.INVALID_DATA) {
				APEX_PreRequisits.inDecode.dest_register_data = APEX_PreRequisits.forwarded_register[APEX_PreRequisits.inDecode.dest_register];
			} else {
				APEX_PreRequisits.inDecode.dest_register_data = APEX_PreRequisits.architectural_registers[APEX_PreRequisits.inDecode.dest_register];
			}

		} else {
			if (APEX_PreRequisits.inDecode.dest_register != Constants.INVALID_DATA) {
				APEX_PreRequisits.valid_registers[APEX_PreRequisits.inDecode.dest_register] = false;
				APEX_PreRequisits.store_current_ins_adr_in_ex[APEX_PreRequisits.inDecode.dest_register] = APEX_PreRequisits.inDecode.address;
			}
		}
	}

	// public static boolean forwardingPossibilityBasedOnCycle(int
	// reg_destination, int simulationCycleIn){
	//
	// int forward_reg_updated_cycle
	// =APEX_PreRequisits.cycle_forward_reg_updated[reg_destination];
	// int ar_reg_updated_cycle =
	// APEX_PreRequisits.cycle_ar_reg_updated[reg_destination];
	//
	// if((ar_reg_updated_cycle != Constants.INVALID_DATA) &&
	// (ar_reg_updated_cycle == forward_reg_updated_cycle)){
	// if(simulationCycleIn > ar_reg_updated_cycle){
	// return true;
	// }
	// }
	//
	// if((forward_reg_updated_cycle != Constants.INVALID_DATA) &&
	// (forward_reg_updated_cycle > ar_reg_updated_cycle)){
	// if(simulationCycleIn > forward_reg_updated_cycle){
	// return true;
	// }
	// }
	// if((ar_reg_updated_cycle != Constants.INVALID_DATA) &&
	// (ar_reg_updated_cycle > forward_reg_updated_cycle)){
	// if(simulationCycleIn > ar_reg_updated_cycle){
	// return false;
	// }
	// }
	// return false;
	//
	// }
}
