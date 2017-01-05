package APEX_Processor.stages.execute;

import APEX_Processor.APEX;
import APEX_Processor.Forwarding;
import APEX_Processor.ForwardingVerificationFronALU1;
import APEX_Processor.FunctionalUnits;
import APEX_Processor.stages.Decode;
import utillities.APEX_PreRequisits;
import utillities.Constants;
import utillities.LoggerFile;

public class Execute_INT_ALU1 {

	public static void performEX_INT_ALU1(int cycle) {
		try {


			APEX_PreRequisits.inEX_INT_ALU1toALU2 = APEX_PreRequisits.inEX_INT_ALU1_FU;
			APEX_PreRequisits.inEX_INT_ALU1_FU = APEX_PreRequisits.inDecodetoEX_INT_ALU1;

			if (APEX_PreRequisits.inEX_INT_ALU1_FU != null
					&& APEX_PreRequisits.inEX_INT_ALU1_FU.instruction_OPCODE != null) {
				LoggerFile.logger
						.info("inEX_INT_ALU1_FU.instr_id --> " + APEX_PreRequisits.inEX_INT_ALU1_FU.instruction_OPCODE);
				switch (APEX_PreRequisits.inEX_INT_ALU1_FU.instruction_OPCODE) {
				case ADD:
					FunctionalUnits.AddFU(APEX_PreRequisits.inEX_INT_ALU1_FU,cycle);
					System.out.println("Add APEX_PreRequisits.inEX_INT_ALU1_FU.dest_register_data --> " + APEX_PreRequisits.inEX_INT_ALU1_FU.dest_register_data);
					break;
				case SUB:
					FunctionalUnits.SubFU(APEX_PreRequisits.inEX_INT_ALU1_FU,cycle);
					break;
				case MOVC:
					APEX_PreRequisits.inEX_INT_ALU1_FU.literal = APEX_PreRequisits.inEX_INT_ALU1_FU.literal
							+ Constants.ZERO;
					APEX_PreRequisits.forwarded_register[APEX_PreRequisits.inEX_INT_ALU1_FU.dest_register] = 
							APEX_PreRequisits.inEX_INT_ALU1_FU.literal;
					break;
				case MUL:
					FunctionalUnits.MulFU(APEX_PreRequisits.inEX_INT_ALU1_FU,cycle);
					break;
				case AND:
					FunctionalUnits.AndFU(APEX_PreRequisits.inEX_INT_ALU1_FU,cycle);
					break;
				case OR:
					FunctionalUnits.OrFU(APEX_PreRequisits.inEX_INT_ALU1_FU,cycle);
					break;
				case XOR:
					FunctionalUnits.XorFU(APEX_PreRequisits.inEX_INT_ALU1_FU,cycle);
					break;
				case LOAD:
					FunctionalUnits.calculatingMemoryAddressForLOAD_STORE(APEX_PreRequisits.inEX_INT_ALU1_FU);
					break;
				case STORE:
					FunctionalUnits.calculatingMemoryAddressForLOAD_STORE(APEX_PreRequisits.inEX_INT_ALU1_FU);
					break;
				case HALT:
					break;
//				default:
//					Decode.info_dest_reg_previous_intFU1 = Constants.INVALID_DATA;
//					Decode.info_opcode_prevois_intFU1 = null;
//					break;
				}
				ForwardingVerificationFronALU1.forwardingExitingEXtoEnteringEX(APEX_PreRequisits.inEX_INT_ALU1_FU, 
						APEX_PreRequisits.inDecode,cycle);
				
			}
		} catch (

		Exception e) {
			e.printStackTrace();
			System.out.println("Exception in performEX_INT_ALU1 : " + e.getClass().getSimpleName());
		}
	}

}
