package utillities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import APEX_Processor.Instruction;

public class FileProcessor {
	
	private static String INPUTFILE;

	private static String formatInstructionLine(String lineIn) {
		if (lineIn != null | !lineIn.equals("")) {
			lineIn = lineIn.replaceAll(Constants.DELIMITER_COMMA, Constants.DELIMITER_EMPTY)
					.replaceAll(Constants.DELIMITER_HASH, Constants.DELIMITER_EMPTY);
		}
		return lineIn;
	}

	public static void processInputFile(String filenameIn) {
		BufferedReader buffer_reader;
		String line;
		int ins_address = 0;
		APEX_PreRequisits.INSTRUCTION_COUNT = 0;
		try {
			buffer_reader = new BufferedReader(new FileReader(filenameIn));
			while ((line = buffer_reader.readLine()) != null) {
				line = formatInstructionLine(line);
				System.out.println((ins_address+1) + "  " + line);
				APEX_PreRequisits.instruction_set_list[ins_address].completeInstructionStringInSymbolicForm = line;
				APEX_PreRequisits.instruction_set_list[ins_address].contains = true;
				APEX_PreRequisits.instruction_set_list[ins_address].address = 
						(ins_address*Constants.BYTE_SIZE) + 
						Constants.STARTING_INSTRUCTION_ADDRESS;
				
				FileProcessor.compileInstructions(APEX_PreRequisits.instruction_set_list[ins_address]);
//				ins_address =+ Constants.BYTE_SIZE;
				ins_address++;
				APEX_PreRequisits.INSTRUCTION_COUNT++;
			}
			LoggerFile.logger.info("Total Instructions --> " + APEX_PreRequisits.INSTRUCTION_COUNT);
			
		} catch (FileNotFoundException ex) {
			System.out.println("Entered file "+ filenameIn +" is missing.");
			System.out.println("Kindly provide the correct input file");
			System.exit(0);
		} catch (IOException ex) {
			System.out.println("Exception encountered : " + ex.getMessage().toString());
			System.exit(0);
		} catch (Exception ex) {
			System.out.println("Data in the "+ filenameIn +" is not in the specified format");
			System.out.println(ex);
			System.exit(0);
		}
	}

	private static void compileInstructions(Instruction instruction) throws Exception {
		String nextToken;
		String instructionString = instruction.completeInstructionStringInSymbolicForm;
		StringTokenizer tokenizer = new StringTokenizer(instructionString, Constants.DELIMITER_SPACE);
//		Copying Instruction Name
		instruction.instruction_name = tokenizer.nextToken();
		if (instruction.instruction_name.equalsIgnoreCase(OperationCodes.XOR.getStatusCode())) {
			instruction.instruction_OPCODE = OperationCodes.XOR;
		} else {
			instruction.instruction_OPCODE = OperationCodes.valueOf(instruction.instruction_name);
		}

		if (instruction.instruction_OPCODE == OperationCodes.HALT)
			return;

		if (tokenizer.hasMoreTokens()) {
			nextToken = tokenizer.nextToken();
			if (nextToken.equalsIgnoreCase(Constants.PREFIX_X)) {
				instruction.dest_register = Constants.ARCHITECTURAL_REGISTERS;
			} else if (!nextToken.startsWith(Constants.REGISTER_PREFIX)) {
				instruction.literal = Integer.parseInt(nextToken);
				return;
			} else {
				instruction.dest_register = Integer.parseInt(nextToken.substring(1));
			}
		}

		if (tokenizer.hasMoreTokens()) {
			nextToken = tokenizer.nextToken();
			if (nextToken.equalsIgnoreCase(Constants.PREFIX_X)) {
				instruction.src1 = Constants.ARCHITECTURAL_REGISTERS;
			} else if (!nextToken.startsWith(Constants.REGISTER_PREFIX)) {
				instruction.literal = Integer.parseInt(nextToken);
				return;
			} else {
				instruction.src1 = Integer.parseInt(nextToken.substring(1));
			}
			
		}
		if (tokenizer.hasMoreTokens()) {
			nextToken = tokenizer.nextToken();
			if (!nextToken.startsWith(Constants.REGISTER_PREFIX)){
				instruction.literal = Integer.parseInt(nextToken);
				return;
			} else {
				instruction.src2 = Integer.parseInt(nextToken.substring(1));
			}
		}
	}
	
	
	public static String getINPUTFILE() {
		return INPUTFILE;
	}
	
	public static void setINPUTFILE(String iNPUTFILE) {
		INPUTFILE = iNPUTFILE;
	}
	
}
