package APEX_Processor;

import utillities.APEX_PreRequisits;
import utillities.Constants;
import utillities.LoggerFile;

public class FunctionalUnits {

	private static void updatingPSW_ZeroFlag(Instruction instruction) {
		if (instruction.dest_register_data == Constants.ZERO)
			APEX_PreRequisits.PSW_ZeroFlag = Constants.ZERO;
		else
			APEX_PreRequisits.PSW_ZeroFlag = Constants.MINUSONE;
	}

	private static int fetchingOperand2(Instruction instruction) {
		int forward_reg_updated_cycle = APEX_PreRequisits.cycle_forward_reg_updated[instruction.src2];
		int ar_reg_updated_cycle = APEX_PreRequisits.cycle_ar_reg_updated[instruction.src2];
		
		if (APEX_PreRequisits.forwarded_register[instruction.src2] != Constants.INVALID_DATA) {
			instruction.src2_data = APEX_PreRequisits.forwarded_register[instruction.src2];
		} else {
			instruction.src2_data = APEX_PreRequisits.architectural_registers[instruction.src2];
		}
		int operand2;
		operand2 = instruction.src2 != Constants.INVALID_DATA ? instruction.src2_data : instruction.literal;
		return operand2;
	}
	

	public static void AddFU(Instruction instruction, int cycle) {
			LoggerFile.logger.info("in add fu");
			int operand2 = 0;
			operand2 = fetchingOperand2(instruction);
			
//			System.out.println("Add instruction.src1 --> " + instruction.src1);
//			System.out.println("Add instruction.src1_data --> " + instruction.src1_data);
//			System.out.println("Add instruction.src2 --> " + instruction.src2);
//			System.out.println("Add instruction.src2_data --> " + instruction.src2_data);
//			System.out.println("Add instruction.dest_register_data --> " + instruction.dest_register_data);
//			System.out.println("Add instruction.dest_register --> " + instruction.dest_register);
			int forward_reg_updated_cycle = APEX_PreRequisits.cycle_forward_reg_updated[instruction.src1];
			int ar_reg_updated_cycle = APEX_PreRequisits.cycle_ar_reg_updated[instruction.src1];
			
			if (APEX_PreRequisits.forwarded_register[instruction.src1] != Constants.INVALID_DATA) {
				instruction.src1_data = APEX_PreRequisits.forwarded_register[instruction.src1];
			} else {
				instruction.src1_data = APEX_PreRequisits.architectural_registers[instruction.src1];
			}
			
			instruction.dest_register_data = instruction.src1_data + instruction.src2_data;
			APEX_PreRequisits.forwarded_register[instruction.dest_register] = 
					instruction.dest_register_data;
			APEX_PreRequisits.cycle_forward_reg_updated[instruction.dest_register] 
					= cycle;
			updatingPSW_ZeroFlag(instruction);
	}

	public static void MulFU(Instruction instruction, int cycle) {
		LoggerFile.logger.info("in Mul fu");
		int operand2 = 0;
		operand2 = fetchingOperand2(instruction);
		int forward_reg_updated_cycle = APEX_PreRequisits.cycle_forward_reg_updated[instruction.src1];
		int ar_reg_updated_cycle = APEX_PreRequisits.cycle_ar_reg_updated[instruction.src1];
		
		if (APEX_PreRequisits.forwarded_register[instruction.src1] != Constants.INVALID_DATA) {
			instruction.src1_data = APEX_PreRequisits.forwarded_register[instruction.src1];
		} else {
			instruction.src1_data = APEX_PreRequisits.architectural_registers[instruction.src1];
		}
		instruction.dest_register_data = instruction.src1_data * operand2;
//		System.out.println("MUL instruction.dest_register_data --> " + instruction.dest_register_data);
//		System.out.println("MUL instruction.dest_register --> " + instruction.dest_register);
		APEX_PreRequisits.forwarded_register[instruction.dest_register] = 
				instruction.dest_register_data;
		APEX_PreRequisits.cycle_forward_reg_updated[instruction.dest_register] 
				= cycle;
		updatingPSW_ZeroFlag(instruction);
	}

	public static void SubFU(Instruction instruction, int cycle) {
		LoggerFile.logger.info("in sub fu");
		int operand2 = 0;
		operand2 = fetchingOperand2(instruction);
		int forward_reg_updated_cycle = APEX_PreRequisits.cycle_forward_reg_updated[instruction.src1];
		int ar_reg_updated_cycle = APEX_PreRequisits.cycle_ar_reg_updated[instruction.src1];
		
		if (APEX_PreRequisits.forwarded_register[instruction.src1] != Constants.INVALID_DATA) {
			instruction.src1_data = APEX_PreRequisits.forwarded_register[instruction.src1];
		} else {
			instruction.src1_data = APEX_PreRequisits.architectural_registers[instruction.src1];
		}
		instruction.dest_register_data = instruction.src1_data - operand2;
//		System.out.println("SUB instruction.dest_register_data --> " + instruction.dest_register_data);
//		System.out.println("SUB instruction.dest_register --> " + instruction.dest_register);
		APEX_PreRequisits.forwarded_register[instruction.dest_register] = 
				instruction.dest_register_data;
		APEX_PreRequisits.cycle_forward_reg_updated[instruction.dest_register] 
				= cycle;
		updatingPSW_ZeroFlag(instruction);
	}

	public static void AndFU(Instruction instruction, int cycle) {
		LoggerFile.logger.info("in OR fu");
		int operand2 = 0;
		operand2 = fetchingOperand2(instruction);
		int forward_reg_updated_cycle = APEX_PreRequisits.cycle_forward_reg_updated[instruction.src1];
		int ar_reg_updated_cycle = APEX_PreRequisits.cycle_ar_reg_updated[instruction.src1];
		
		if (APEX_PreRequisits.forwarded_register[instruction.src1] != Constants.INVALID_DATA) {
			instruction.src1_data = APEX_PreRequisits.forwarded_register[instruction.src1];
		} else {
			instruction.src1_data = APEX_PreRequisits.architectural_registers[instruction.src1];
		}
		instruction.dest_register_data = instruction.src1_data & operand2;
//		System.out.println("AND instruction.dest_register_data --> " + instruction.dest_register_data);
//		System.out.println("AND instruction.dest_register --> " + instruction.dest_register);
		APEX_PreRequisits.forwarded_register[instruction.dest_register] = 
				instruction.dest_register_data;
		APEX_PreRequisits.cycle_forward_reg_updated[instruction.dest_register] 
				= cycle;
		updatingPSW_ZeroFlag(instruction);
	}

	public static void OrFU(Instruction instruction, int cycle) {
		LoggerFile.logger.info("in OR fu");
		int operand2 = 0;
		operand2 = fetchingOperand2(instruction);
		int forward_reg_updated_cycle = APEX_PreRequisits.cycle_forward_reg_updated[instruction.src1];
		int ar_reg_updated_cycle = APEX_PreRequisits.cycle_ar_reg_updated[instruction.src1];
		
		if (APEX_PreRequisits.forwarded_register[instruction.src1] != Constants.INVALID_DATA) {
			instruction.src1_data = APEX_PreRequisits.forwarded_register[instruction.src1];
		} else {
			instruction.src1_data = APEX_PreRequisits.architectural_registers[instruction.src1];
		}
		instruction.dest_register_data = instruction.src1_data | operand2;
		////System.out.println("AND instruction.dest_register_data --> " + instruction.dest_register_data);
//		System.out.println("OR instruction.dest_register_data --> " + instruction.dest_register_data);
//		System.out.println("OR instruction.dest_register --> " + instruction.dest_register);
		APEX_PreRequisits.forwarded_register[instruction.dest_register] = 
				instruction.dest_register_data;
		APEX_PreRequisits.cycle_forward_reg_updated[instruction.dest_register] 
				= cycle;
		updatingPSW_ZeroFlag(instruction);
	}

	public static void XorFU(Instruction instruction, int cycle) {
		LoggerFile.logger.info("in OR fu");
		int operand2 = 0;
		operand2 = fetchingOperand2(instruction);
		int forward_reg_updated_cycle = APEX_PreRequisits.cycle_forward_reg_updated[instruction.src1];
		int ar_reg_updated_cycle = APEX_PreRequisits.cycle_ar_reg_updated[instruction.src1];
		
		if (APEX_PreRequisits.forwarded_register[instruction.src1] != Constants.INVALID_DATA) {
			instruction.src1_data = APEX_PreRequisits.forwarded_register[instruction.src1];
		} else {
			instruction.src1_data = APEX_PreRequisits.architectural_registers[instruction.src1];
		}
		instruction.dest_register_data = instruction.src1_data ^ operand2;
//		System.out.println("XOR instruction.dest_register_data --> " + instruction.dest_register_data);
//		System.out.println("XOR instruction.dest_register --> " + instruction.dest_register);
		APEX_PreRequisits.forwarded_register[instruction.dest_register] = 
				instruction.dest_register_data;
		APEX_PreRequisits.cycle_forward_reg_updated[instruction.dest_register] 
				= cycle;
		updatingPSW_ZeroFlag(instruction);
	}

	// Address computed by adding rsrc1 and sign extended literal is stored in rsrc2.
	public static void calculatingMemoryAddressForLOAD_STORE(Instruction instruction) {
		if(instruction.src1_data == Constants.INVALID_DATA){
//			System.err.println(instruction.src1 + " Invalid data in source 1 register = " + instruction.src1_data);
			instruction.src1_data = APEX_PreRequisits.architectural_registers[instruction.src1];
		}
		if(instruction.literal == Constants.INVALID_DATA){
			instruction.literal = Constants.ZERO;
//			System.err.println("Invalid data in literal " + instruction.literal);
		}
		if(instruction.src1_data != Constants.INVALID_DATA && instruction.literal != Constants.INVALID_DATA){
			instruction.address_load_store = instruction.src1_data + instruction.literal;
//			System.err.println("address_load_store --> " + instruction.address_load_store);
		}
	}

	// Reading the contents of memory location whose address was computed by
	// adding rsrc1 and sign extended literal
	public static void LoadFU(Instruction instrucion) {
		if(instrucion.address_load_store !=  Constants.INVALID_DATA && 
				APEX_PreRequisits.MEMORY_REGISTER[instrucion.address_load_store]  != Constants.INVALID_DATA){
			instrucion.dest_register_data = APEX_PreRequisits.MEMORY_REGISTER[instrucion.address_load_store];
			
			if(instrucion.dest_register_data != Constants.INVALID_DATA 
					&& instrucion.dest_register != Constants.INVALID_DATA){
				APEX_PreRequisits.forwarded_register[instrucion.dest_register] = 
						instrucion.dest_register_data;
			}
		}
		
//		System.err.println("instrucion.dest_register_data --> " + instrucion.dest_register_data);
	}

	// Write the contents of rsrc2 (read out in the D/RF stage) to the memory
	// location whose address was computed in the EX stage by adding the 
	// contents of rsrc1 and sign extended literal 
	public static void StoreFU(Instruction instrucion) {
//		System.err.println("instrucion.dest_register_data --> " + APEX_PreRequisits.architectural_registers[instrucion.dest_register]);
//		System.err.println("address_load_store --> " + instrucion.address_load_store);
		if(APEX_PreRequisits.architectural_registers[instrucion.dest_register] != Constants.INVALID_DATA && 
				instrucion.address_load_store != Constants.INVALID_DATA
				){
			APEX_PreRequisits.MEMORY_REGISTER[instrucion.address_load_store] = 
					APEX_PreRequisits.architectural_registers[instrucion.dest_register];
		}

	}
}
