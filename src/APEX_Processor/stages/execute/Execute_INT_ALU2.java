package APEX_Processor.stages.execute;

import APEX_Processor.Forwarding;
import utillities.APEX_PreRequisits;
import utillities.Constants;

public class Execute_INT_ALU2 {
	public static void performEX_INT_ALU2(int cycle) {
		try {

			APEX_PreRequisits.inEX_INT_ALU2toMEM = APEX_PreRequisits.inEX_INT_ALU2_FU;
			APEX_PreRequisits.inEX_INT_ALU2_FU = APEX_PreRequisits.inEX_INT_ALU1toALU2;
			
			if (APEX_PreRequisits.inEX_INT_ALU2_FU != null
					&& APEX_PreRequisits.inEX_INT_ALU2_FU.instruction_OPCODE != null) {

				switch (APEX_PreRequisits.inEX_INT_ALU2_FU.instruction_OPCODE) {
				case MOVC:
				case ADD:
				case SUB:
				case MUL:
				case AND:
				case OR:
				case XOR:
				case HALT:
					break;
				}
				
				Forwarding.forwardingExitingEXtoEnteringEX(APEX_PreRequisits.inEX_INT_ALU2_FU, 
						APEX_PreRequisits.inDecode,cycle);
				

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
