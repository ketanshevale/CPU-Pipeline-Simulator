package APEX_Processor;

import APEX_Processor.stages.Decode;
import APEX_Processor.stages.Fetch;
import APEX_Processor.stages.MEM;
import APEX_Processor.stages.WriteBack;
import APEX_Processor.stages.execute.Execute_BranchFU;
import APEX_Processor.stages.execute.Execute_Delay;
import APEX_Processor.stages.execute.Execute_INT_ALU2;
import result.FinalResult;
import utillities.APEX_PreRequisits;
import utillities.Constants;
import utillities.FileProcessor;
import utillities.LoggerFile;
import utillities.OperationCodes;
import APEX_Processor.stages.execute.Execute_INT_ALU1;

public class APEX {

	// constructor
	public static void initializeVariales() {
		APEX_PreRequisits.inBranch_FU = null;
		APEX_PreRequisits.inFetch = null;
		APEX_PreRequisits.inFetchtoDecode = null;
		APEX_PreRequisits.inDecode = null;
		APEX_PreRequisits.inDecodetoBranchFU = null;
		APEX_PreRequisits.inDecodetoEX_INT_ALU1 = null;
		APEX_PreRequisits.inEX_INT_ALU1_FU = null;
		APEX_PreRequisits.inEX_INT_ALU2_FU = null;
		APEX_PreRequisits.inEX_INT_ALU1toALU2 = null;
		APEX_PreRequisits.inEX_INT_ALU2toMEM = null;
		APEX_PreRequisits.inBranch_FU = null;
		APEX_PreRequisits.inBranchFUtoDelay = null;
		APEX_PreRequisits.inDelay_FU = null;
		APEX_PreRequisits.inMEM = null;
		APEX_PreRequisits.inMEMtoWB = null;
		APEX_PreRequisits.inWB = null;
		APEX_PreRequisits.inMEM = null;
	}

	public static void initialize() {

		APEX_PreRequisits.PSW_ZeroFlag = Constants.MINUSONE;
		APEX_PreRequisits.architectural_registers = new Integer[Constants.ARCHITECTURAL_REGISTERS + 1];
		APEX_PreRequisits.forwarded_register = new Integer[Constants.ARCHITECTURAL_REGISTERS + 1];
		APEX_PreRequisits.valid_registers = new boolean[Constants.ARCHITECTURAL_REGISTERS + 1];
		APEX_PreRequisits.cycle_forward_reg_updated = new Integer[Constants.ARCHITECTURAL_REGISTERS + 1];
		APEX_PreRequisits.cycle_ar_reg_updated = new Integer[Constants.ARCHITECTURAL_REGISTERS + 1];

		APEX_PreRequisits.store_current_ins_adr_in_ex = new Integer[Constants.ARCHITECTURAL_REGISTERS + 1];

		APEX_PreRequisits.MEMORY_REGISTER = new Integer[Constants.MEMORY_SIZE];
		APEX_PreRequisits.PROGRAM_COUNTER = Constants.STARTING_INSTRUCTION_ADDRESS;
		APEX_PreRequisits.instruction_set_list = new Instruction[Constants.INSTRUCTION_SIZE];
		
		for (int i = 0; i < Constants.INSTRUCTION_SIZE; i++) {
			APEX_PreRequisits.instruction_set_list[i] = new Instruction();
		}

		for (int i = 0; i < Constants.ARCHITECTURAL_REGISTERS + 1; i++) {
			APEX_PreRequisits.valid_registers[i] = true;
			APEX_PreRequisits.store_current_ins_adr_in_ex[i] = Constants.INVALID_DATA;
			APEX_PreRequisits.forwarded_register[i] = Constants.INVALID_DATA;
			APEX_PreRequisits.architectural_registers[i] = Constants.INVALID_DATA;
			APEX_PreRequisits.cycle_forward_reg_updated[i] = Constants.ZERO;
			APEX_PreRequisits.cycle_ar_reg_updated[i] = Constants.ZERO;
			
		}
		APEX.initializeVariales();
		for (int i = 0; i < Constants.MEMORY_SIZE; i++) {
			APEX_PreRequisits.MEMORY_REGISTER[i] = 0;
		}
		FileProcessor.processInputFile(FileProcessor.getINPUTFILE());
	}

	public static String fetchOperandExecuting(String instruction) {
		String operation = "";
		if (instruction != null) {
			operation = instruction.split(Constants.DELIMITER_SPACE)[0];
		}
		return operation;
	}

	public static void processCycles(int simulation_cycles_count) {

		int cyclecount = 1;
		for (int i = 1; i <= simulation_cycles_count; i++) {
			cyclecount = i;
			System.out.println("Cycle: " + cyclecount);
			// swap MEMORY_REGISTER
			if (APEX_PreRequisits.PROGRAM_COUNTER < Constants.STARTING_INSTRUCTION_ADDRESS) {
				APEX_PreRequisits.PROGRAM_COUNTER = (APEX_PreRequisits.PROGRAM_COUNTER
						% Constants.STARTING_INSTRUCTION_ADDRESS) + Constants.STARTING_INSTRUCTION_ADDRESS;
			}

			// System.out.println("=====Fetch starts executing");
			Fetch.performFetch();
			// System.out.println("=====Decode starts");
			Decode.performDRF();
			if (APEX_PreRequisits.inDecodetoBranchFU != null) {
				LoggerFile.logger.info("=====performBranchFU starts =====");
				Execute_BranchFU.performBranchFU();
				LoggerFile.logger.info("=====performDelayStage starts === ");
				Execute_Delay.performDelayStage();
			}
			LoggerFile.logger.info("=====performEX_INT_ALU1 starts === ");
			Execute_INT_ALU1.performEX_INT_ALU1(cyclecount);
			LoggerFile.logger.info("=====EX_INT_ALU2 starts === ");
			Execute_INT_ALU2.performEX_INT_ALU2(cyclecount);

			// Forwarding scenario possibility here
			LoggerFile.logger.info("=====MEM starts ==== ");
			MEM.performMEM(cyclecount);
			// Forwarding scenario possibility here
			LoggerFile.logger.info("=====WB starts ==== ");
			WriteBack.performWB(cyclecount);
			// Forwarding scenario possibility here
			FinalResult.populateStagesToPrint();
			FinalResult.displayAll();
		}
		
		
	}

	public static void branchFlush() {
		try {
			for (int i = 0; i < Constants.ARCHITECTURAL_REGISTERS; i++) {
//				System.out.println("APEX_PreRequisits.inDecode --> " + APEX_PreRequisits.inDecode);
//				System.out.println("APEX_PreRequisits.inEX_INT_ALU1_FU --> " + APEX_PreRequisits.inEX_INT_ALU1_FU);
				if (APEX_PreRequisits.inDecode != null && 
						APEX_PreRequisits.store_current_ins_adr_in_ex[i] == APEX_PreRequisits.inDecode.address) {
					
					APEX_PreRequisits.store_current_ins_adr_in_ex[i] = Constants.INVALID_DATA;
					APEX_PreRequisits.valid_registers[i] = true;
					
				}else if (APEX_PreRequisits.inEX_INT_ALU1_FU != null &&
						APEX_PreRequisits.store_current_ins_adr_in_ex[i] == APEX_PreRequisits.inEX_INT_ALU1_FU.address) {
					
					APEX_PreRequisits.store_current_ins_adr_in_ex[i] = Constants.INVALID_DATA;
					APEX_PreRequisits.valid_registers[i] = true;
				}
			}

			APEX_PreRequisits.inDecode = null;
			APEX_PreRequisits.inFetch = null;
			APEX_PreRequisits.inFetchtoDecode = null;
			APEX_PreRequisits.inDecodetoBranchFU = null;
			APEX_PreRequisits.inDecodetoEX_INT_ALU1 = null;
			APEX_PreRequisits.stalled = false;
			System.err.println("Fetch and Decode stage is Flushed");
		} catch (Exception e) {
			e.printStackTrace();
			LoggerFile.logger.info("Exception : " + e.getClass().getSimpleName());
		}
	}

}