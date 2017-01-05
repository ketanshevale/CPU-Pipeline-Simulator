package result;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import APEX_Processor.Instruction;
import utillities.APEX_PreRequisits;
import utillities.Constants;

public class FinalResult {

	public static LinkedHashMap<String, Instruction> stages_status_post_simulation;

	public static void populateStagesToPrint() {
		Instruction inFetchIn = APEX_PreRequisits.inFetch;
		Instruction inDecodeIn = APEX_PreRequisits.inDecode;
		Instruction inEX_INT_ALU1_FUIn = APEX_PreRequisits.inEX_INT_ALU1_FU;
		Instruction inEX_INT_ALU2_FUIn = APEX_PreRequisits.inEX_INT_ALU2_FU;
		Instruction inBranch_FUIn = APEX_PreRequisits.inBranch_FU;
		Instruction inDelay_FUIn = APEX_PreRequisits.inDelay_FU;
		Instruction inMEMIn = APEX_PreRequisits.inMEM;
		Instruction inWBIn = APEX_PreRequisits.inWB;
		stages_status_post_simulation = new LinkedHashMap<String, Instruction>();
		stages_status_post_simulation.put("Fetch Stage", inFetchIn);
		stages_status_post_simulation.put("Decode Stage", inDecodeIn);
		stages_status_post_simulation.put("INT ALU1 Stage", inEX_INT_ALU1_FUIn);
		stages_status_post_simulation.put("INT ALU2 Stage", inEX_INT_ALU2_FUIn);
		stages_status_post_simulation.put("Branch FU Stage", inBranch_FUIn);
		stages_status_post_simulation.put("Delay Stage", inDelay_FUIn);
		stages_status_post_simulation.put("Memory Stage", inMEMIn);
		stages_status_post_simulation.put("WriteBack Stage", inWBIn);
	}

	public static void printStages() {
		System.out.println("\n==============Contents of Each Stage ==========================================");
		Set<String> keys = stages_status_post_simulation.keySet();
		Iterator<String> iterator = (Iterator) keys.iterator();

		String stage;
		Instruction instruction;
		while (iterator.hasNext()) {
			stage =  iterator.next();
			instruction = stages_status_post_simulation.get(stage);
			if(instruction == null){
				System.out.println(stage + "\t\t:\t" + Constants.EMPTY_STAGE);
			}else{
			System.out.println(stage + "\t\t:\t" + instruction);
			}
		}
		System.out.println("=============================================================================\n");
	}

	public static void displayRegistersStatus() {
		System.out.println("\n=======================================================================");
		System.out.println("==========Contents of all Architectural Registers======================");
		System.out.println("=======================================================================");
		int count = Constants.ARCHITECTURAL_REGISTERS - 1;
		try {
			for (int i = 0; i < count; ++i) {
				if(APEX_PreRequisits.architectural_registers[i] == Constants.INVALID_DATA){
					APEX_PreRequisits.architectural_registers[i] = Constants.ZERO;
				}
			}
			
			for (int i = 0; i < count; ++i) {
				System.out.println("R["+i+"]\t:\t"+APEX_PreRequisits.architectural_registers[i] + "\t");
			}
			if(APEX_PreRequisits.architectural_registers[Constants.ARCHITECTURAL_REGISTERS] == Constants.INVALID_DATA){
				APEX_PreRequisits.architectural_registers[Constants.ARCHITECTURAL_REGISTERS] = Constants.ZERO;
			}
			System.out.println("X\t:\t" + APEX_PreRequisits.architectural_registers[Constants.ARCHITECTURAL_REGISTERS]+"\t");

			System.out.println("Z\t:\t" + APEX_PreRequisits.PSW_ZeroFlag+"\t");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n=======================================================================\n");
	}
	
	public static void displayForwardingRegistersStatus() {
		System.out.println("\n=======================================================================");
		System.out.println("==========Contents of all Forwarding Registers======================");
		System.out.println("=======================================================================");
		int count = Constants.ARCHITECTURAL_REGISTERS - 1;
		try {
			for (int i = 0; i < count; ++i) {
				System.out.println("F["+i+"]\t:\t"+APEX_PreRequisits.forwarded_register[i] + "\t");
			}
			System.out.println("X\t:\t" + APEX_PreRequisits.forwarded_register[Constants.ARCHITECTURAL_REGISTERS]+"\t");

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n=======================================================================\n");
	}

	public static void printMemoryStatus() {
		System.out.println("\n=======================================================================");
		System.out.println("====================First 100 Memory Locations Data====================");
		System.out.println("=======================================================================");
		for (int i = 0; i <= 100; i++) {
			if (i  % 4 == 0)
				System.out.print("M["+i+"]\t:\t");
			System.out.print(APEX_PreRequisits.MEMORY_REGISTER[i] + "\t");
			if ((i + 1) % 4 == 0)
				System.out.println();
		}
		System.out.println("\n=======================================================================\n");
	}

	public static void printProgramCounter() {
		System.out.println("\n\n\nProgram Counter:" + APEX_PreRequisits.PROGRAM_COUNTER);
	}

	public static void displayAll() {
		displayRegistersStatus();
//		displayForwardingRegistersStatus();
		printMemoryStatus();
		printStages();	
	}

}
