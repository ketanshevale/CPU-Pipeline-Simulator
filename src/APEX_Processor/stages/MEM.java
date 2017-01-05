package APEX_Processor.stages;

import APEX_Processor.Forwarding;
import APEX_Processor.FunctionalUnits;
import utillities.APEX_PreRequisits;
import utillities.LoggerFile;

/*
 * 
 * 
 * * *
 * method: forwardingScenario(Instruction sender_ins, Instruction receiver_ins)
 * @param sender_ins	: Sending the data which can be forwarded
 * @param receiver_ins	: Receiving the values send by the sender
 * Forwarding possibilities: 
	 * From 	 :  Instruction exiting EX stage --> To : Instruction Entering EX stage
	 * Destination of instruction exiting the EX stage = (SRC1 || SRC2) of instruction entering EX Stage
	 * Usability 			: Register to Register operations like ADD, SUB, MUL, DIV, OR, AND, XOR.
	 * Sender Instruction 	: APEX_PreRequisits.inEX_INT_ALU2toMEM
	 * Receiver Instruction : APEX_PreRequisits.inDecodetoNext
	 * Function call		: Forwarding.forwardingScenario(APEX_PreRequisits.inEX_INT_ALU2toMEM, APEX_PreRequisits.inDecodetoNext)
* * *
* 
 */

public class MEM {
	public static void performMEM(int cycle) {
		LoggerFile.logger.info("Inside MEM Stage");
		try {
//			Verifying the forwarding possibilities
//			if(APEX_PreRequisits.inEX_INT_ALU2toMEM != null && APEX_PreRequisits.inDecodetoNext != null){
//				Forwarding.forwardingExitingEXtoEnteringEX(APEX_PreRequisits.inEX_INT_ALU2toMEM, 
//						APEX_PreRequisits.inDecodetoNext);
//			}			
			
			APEX_PreRequisits.inMEMtoWB = APEX_PreRequisits.inMEM;
			APEX_PreRequisits.inMEM = APEX_PreRequisits.inEX_INT_ALU2toMEM;
			
			LoggerFile.logger.info(" MEM " + APEX_PreRequisits.inMEM + "\n");
			LoggerFile.logger.info(" inEX_INT_ALU2toMEM " + APEX_PreRequisits.inEX_INT_ALU2toMEM + "\n");
			LoggerFile.logger.info(" inMEMtoWB " + APEX_PreRequisits.inMEMtoWB + "\n");
			if (APEX_PreRequisits.inMEM != null && APEX_PreRequisits.inMEM.instruction_OPCODE != null) {
				LoggerFile.logger.info("Debugging NPE" + APEX_PreRequisits.inMEM);
				switch (APEX_PreRequisits.inMEM.instruction_OPCODE) {
				case MOVC:
					APEX_PreRequisits.inMEM.dest_register_data = APEX_PreRequisits.inMEM.literal;
					break;
				case LOAD:
					FunctionalUnits.LoadFU(APEX_PreRequisits.inMEM);
					break;
				case STORE:
					FunctionalUnits.StoreFU(APEX_PreRequisits.inMEM);
					break;
				}
				
				if(APEX_PreRequisits.inDecode != null){
					
					Forwarding.forwardingExitingMEMtoEnteringMEM(APEX_PreRequisits.inMEM, 
							APEX_PreRequisits.inDecode,cycle);
					
					Forwarding.forwardingExitingMEMtoEnteringEX(APEX_PreRequisits.inMEM, 
							APEX_PreRequisits.inDecode,cycle);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerFile.logger.info("Exception in MEM Stage : " + e.getClass().getSimpleName());
		}
	}
}
