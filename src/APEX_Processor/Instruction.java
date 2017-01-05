package APEX_Processor;

import utillities.Constants;
import utillities.OperationCodes;


public class Instruction {
	// flag to detect if contains any data or not
	public boolean contains;
	// instruction type and corresponding naming

	public OperationCodes instruction_OPCODE = null;
	public String instruction_name = "";
	// dest
	public int dest_register = Constants.INVALID_DATA;
	public int dest_register_data = Constants.INVALID_DATA;
	// src
	public int src1 = Constants.INVALID_DATA, src2 = Constants.INVALID_DATA;
	public int src1_data = Constants.INVALID_DATA, src2_data = Constants.INVALID_DATA;
	public static int second_operand_data_for_paralleloperation;
//	public static int dest_register_data;
	// literal
	public int literal = Constants.INVALID_DATA;
	// plain ascii representation of the memory_reference_array_ref
	public String completeInstructionStringInSymbolicForm;
	// Address in Memory
	public int address;
	// Variable to hold current stage which is in Execution no of operands of a given instruction
	int noOfOperands;
	// checking for forwarding possibility	
	public boolean forwarding_possibility = false;
	
	public int instruction_serial_number = Constants.INVALID_DATA;

	public int address_load_store = Constants.INVALID_DATA;
	
	public Instruction() {
		contains = false;
		instruction_OPCODE = null;
	}
	
	public void print() {
		StringBuilder stringbuilder = new StringBuilder();
		if (instruction_OPCODE != null) {
			stringbuilder.append("Instruction:" + instruction_OPCODE.toString());
		} else {
			System.out.println("Empty");
			return;
		}
		stringbuilder.append("Dest:R " + dest_register + "\tSrc1:R " + 
		src1 + "\tSrc2:R " + src2 + "\tliteral:" + literal
				+ "\tAddress(PC):" + address);
		System.out.println(stringbuilder);

	}

	public void printRaw() {

		System.out.println(completeInstructionStringInSymbolicForm);
	}

	public String toString() {

		return (completeInstructionStringInSymbolicForm);
	}

}