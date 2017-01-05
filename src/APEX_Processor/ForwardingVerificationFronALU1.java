package APEX_Processor;


import utillities.APEX_PreRequisits;
import utillities.LoggerFile;
import utillities.OperationCodes;

public class ForwardingVerificationFronALU1 {
	
	public static void print_EXtoEX_ALU1(Instruction sender, Instruction receiver, int reg, int cycle) {
		APEX_PreRequisits.stalled = true;
		System.err.println("\nForwarding Possible between Instructions : Exiting EX-1 Stage -> Entering EX-1 Stage");
		System.err.println("Instruction will leave decode stage in next cycle");
		System.err.println("From Instruction\t:\t" + sender);
		System.err.println("To Instruction\t\t:\t" + receiver);
		System.err.println("Register\t\t:\t" + reg);
		System.err.println("Cycle\t\t\t:\t" + cycle);
		return;
	}
	
	public static void print_EXtoEx_MemoryDependency(Instruction sender, Instruction receiver, int reg, int literal,
			int cycle) {
		System.err.println("\nForwarding Possible between Instructions : Exiting EX-1 Stage -> Entering EX-1 Stage");
		System.err.println("Memory Dependency");
		System.err.println("From Instruction\t:\t" + sender);
		System.err.println("To Instruction\t\t:\t" + receiver);
		System.err.println("Register\t:\t" + reg);
		System.err.println("Literal\t\t:\t" + literal);
		System.err.println("Cycle\t\t\t:\t" + cycle);
		return;
	}
	
	public static void forwardingExitingEXtoEnteringEX(Instruction ex2_instruction, Instruction decode_ins, int cycle) {
		// instructionStatus();
		try {
			if (ex2_instruction != null && decode_ins != null && decode_ins.instruction_OPCODE != OperationCodes.HALT
					&& ex2_instruction.instruction_OPCODE != OperationCodes.HALT) {

				if (!(decode_ins.instruction_OPCODE == OperationCodes.BNZ
						|| decode_ins.instruction_OPCODE == OperationCodes.BZ
						|| decode_ins.instruction_OPCODE == OperationCodes.BAL
						|| decode_ins.instruction_OPCODE == OperationCodes.JUMP
						|| decode_ins.instruction_OPCODE == OperationCodes.HALT

				) && !(ex2_instruction.instruction_OPCODE == OperationCodes.STORE)) {

					if (ex2_instruction.dest_register == decode_ins.src1) {
						print_EXtoEX_ALU1(ex2_instruction, decode_ins, decode_ins.src1, cycle);
						return;
					}
					if (ex2_instruction.dest_register == decode_ins.src2) {
						print_EXtoEX_ALU1(ex2_instruction, decode_ins, decode_ins.src2, cycle);
						return;
					}

				}

				if ((decode_ins.instruction_OPCODE == OperationCodes.STORE)
						&& !(ex2_instruction.instruction_OPCODE == OperationCodes.BNZ
								|| ex2_instruction.instruction_OPCODE == OperationCodes.BZ
								|| ex2_instruction.instruction_OPCODE == OperationCodes.BAL
								|| ex2_instruction.instruction_OPCODE == OperationCodes.JUMP
								|| ex2_instruction.instruction_OPCODE == OperationCodes.HALT)) {
					if (ex2_instruction.dest_register == decode_ins.dest_register) {
						print_EXtoEX_ALU1(ex2_instruction, decode_ins, decode_ins.dest_register, cycle);
						return;
					}
				}

				if ((decode_ins.instruction_OPCODE == OperationCodes.STORE)
						&& (!(ex2_instruction.instruction_OPCODE == OperationCodes.STORE
								&& ex2_instruction.instruction_OPCODE == OperationCodes.LOAD))) {
					if (ex2_instruction.dest_register == decode_ins.dest_register) {
						print_EXtoEX_ALU1(ex2_instruction, decode_ins, decode_ins.dest_register, cycle);
						return;
					}
				}

				if ((decode_ins.instruction_OPCODE == OperationCodes.LOAD)
						&& (!(ex2_instruction.instruction_OPCODE == OperationCodes.STORE
								&& ex2_instruction.instruction_OPCODE == OperationCodes.LOAD))) {
					if (ex2_instruction.dest_register == decode_ins.src1) {
						print_EXtoEX_ALU1(ex2_instruction, decode_ins, decode_ins.src1, cycle);
						return;
					}
				}

				if ((decode_ins.instruction_OPCODE == OperationCodes.STORE)
						&& ex2_instruction.instruction_OPCODE == OperationCodes.LOAD) {

					if ((ex2_instruction.src1 == decode_ins.src1) && (ex2_instruction.literal == decode_ins.literal)) {
						print_EXtoEx_MemoryDependency(ex2_instruction, decode_ins, ex2_instruction.src1,
								ex2_instruction.literal, cycle);
						return;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerFile.logger
					.info("Exception in forwarding forwardingExitingEXtoEnteringEX Stage : " + e.getClass().getSimpleName());
		}
	}
}
