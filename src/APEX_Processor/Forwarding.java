package APEX_Processor;

import utillities.APEX_PreRequisits;
import utillities.Constants;
import utillities.LoggerFile;
import utillities.OperationCodes;

public class Forwarding {

	/*
	 * Forwarding possibilities: From : Instruction exiting EX stage --> To :
	 * Instruction Entering EX stage Destination of instruction exiting the EX
	 * stage = (SRC1 || SRC2) of instruction entering EX Stage Usability : 1)
	 * Register to Register operations like ADD, SUB, MUL, DIV, OR, AND, XOR. 2)
	 * Register to LOAD 3) Register to STORE Sender Instruction :
	 * APEX_PreRequisits.inEX_INT_ALU2toMEM Receiver Instruction :
	 * APEX_PreRequisits.inDecodetoNext
	 * 
	 * 
	 * For LOAD and STORE based instructions.
	 * 
	 * LOAD can forward data in below listed circumstances 1) From : Instruction
	 * exiting MEM stage -> To : Instruction entering EX Stage Load can Forward
	 * its value to either sources of <op> instruction or <rsrc1> of LOAD
	 * instruction or <rscr2> of store instruction which is in D/RF stage.
	 * Usability : 1) LOAD -> LOAD 2) LOAD -> STORE 3) LOAD -> REGISTER 4) STORE
	 * -> LOAD 5) STORE -> REGISTER Sender Instruction :
	 * APEX_PreRequisits.inMEMtoWB Receiver Instruction :
	 * APEX_PreRequisits.inDecodetoNext
	 * 
	 * 2) From : Instruction exiting MEM stage -> To : Instruction entering MEM
	 * Stage Load can forward the value to <rsrc1> of store instruction, which
	 * is coming out of EX Stage into MEM-Stage. Usability : LOAD -> STORE
	 * Sender Instruction : APEX_PreRequisits.inMEMtoWB Receiver Instruction :
	 * APEX_PreRequisits.inEX_INT_ALU2toMEM
	 * 
	 */

	public static void instructionStatus() {
		System.out.println("APEX_PreRequisits.inFetch --> " + APEX_PreRequisits.inFetch);
		System.out.println("APEX_PreRequisits.inFetchtoDecode --> " + APEX_PreRequisits.inFetchtoDecode);
		System.out.println("APEX_PreRequisits.inDecode --> " + APEX_PreRequisits.inDecode);
		System.out.println("APEX_PreRequisits.inDecodetoEX_INT_ALU1 --> " + APEX_PreRequisits.inDecodetoEX_INT_ALU1);
		System.out.println("APEX_PreRequisits.inEX_INT_ALU1_FU --> " + APEX_PreRequisits.inEX_INT_ALU1_FU);
		System.out.println("APEX_PreRequisits.inEX_INT_ALU1toALU2 --> " + APEX_PreRequisits.inEX_INT_ALU1toALU2);
		System.out.println("APEX_PreRequisits.inEX_INT_ALU2_FU --> " + APEX_PreRequisits.inEX_INT_ALU2_FU);
	}

	/**
	 * @param sender
	 * @param receiver
	 * @param reg
	 * @param cycle
	 */
	public static void print_MEMoMEM(Instruction sender, Instruction receiver, int reg, int cycle) {
		APEX_PreRequisits.stalled = false;
		System.err
				.println("\nForwarding Possible between Instructions : Exiting MEM Stage -> Entering Execution Stage");
		System.err.println("From Instruction\t:\t" + sender);
		System.err.println("To Instruction\t\t:\t" + receiver);
		System.err.println("Register\t\t:\t" + reg);
		System.err.println("Cycle\t\t\t:\t" + cycle);
		return;
	}

	/**
	 * @param sender
	 * @param receiver
	 * @param reg
	 * @param cycle
	 */
	public static void print_MEMtoEX(Instruction sender, Instruction receiver, int reg, int cycle) {
		APEX_PreRequisits.stalled = false;
		System.err.println("\nForwarding Possible between Instructions : Exiting MEM Stage -> Entering MEM Stage");
		System.err.println("From Instruction\t:\t" + sender);
		System.err.println("To Instruction\t\t:\t" + receiver);
		System.err.println("Register\t\t:\t" + reg);
		System.err.println("Cycle\t\t\t:\t" + cycle);
		return;
	}

	/**
	 * @param sender
	 * @param receiver
	 * @param reg
	 * @param cycle
	 */
	public static void print_EXtoEX(Instruction sender, Instruction receiver, int reg, int cycle) {
		APEX_PreRequisits.stalled = false;
		System.err.println("\nForwarding Possible between Instructions : Exiting EX-2 Stage -> Entering EX-1 Stage");
		System.err.println("From Instruction\t:\t" + sender);
		System.err.println("To Instruction\t\t:\t" + receiver);
		System.err.println("Register\t\t:\t" + reg);
		System.err.println("Cycle\t\t\t:\t" + cycle);
		return;
	}
	
	public static void print_EXtoEX_ALU1(Instruction sender, Instruction receiver, int reg, int cycle) {
		System.err.println("\nForwarding Possible between Instructions : Exiting EX-2 Stage -> Entering EX-1 Stage");
		System.err.println("From Instruction\t:\t" + sender);
		System.err.println("To Instruction\t\t:\t" + receiver);
		System.err.println("Register\t\t:\t" + reg);
		System.err.println("Cycle\t\t\t:\t" + cycle);
		return;
	}

	/**
	 * @param sender
	 * @param receiver
	 * @param reg
	 * @param literal
	 * @param cycle
	 */
	public static void print_EXtoEx_MemoryDependency(Instruction sender, Instruction receiver, int reg, int literal,
			int cycle) {
		APEX_PreRequisits.stalled = false;
		System.err.println("\nForwarding Possible between Instructions : Exiting EX-2 Stage -> Entering EX-1 Stage");
		System.err.println("Memory Dependency");
		System.err.println("From Instruction\t:\t" + sender);
		System.err.println("To Instruction\t\t:\t" + receiver);
		System.err.println("Register\t:\t" + reg);
		System.err.println("Literal\t\t:\t" + literal);
		System.err.println("Cycle\t\t\t:\t" + cycle);
		return;
	}

	/**
	 * @param mem_ins
	 *            : Sending the data which can be forwarded
	 * @param decode_ins
	 *            : Receiving the values send by the sender
	 */
	public static void forwardingExitingMEMtoEnteringEX(Instruction mem_ins, Instruction decode_ins, int cycle) {

		// instructionStatus();

		try {
			if (mem_ins != null && decode_ins != null) {

				// For LOAD to LOAD
				if (mem_ins.instruction_OPCODE == OperationCodes.LOAD
						&& decode_ins.instruction_OPCODE == OperationCodes.LOAD) {
					if (mem_ins.dest_register == decode_ins.src1) {
						print_MEMoMEM(mem_ins, decode_ins, decode_ins.src1, cycle);
						return;
					}
				}
				// For LOAD to STORE
				if (mem_ins.instruction_OPCODE == OperationCodes.LOAD
						&& decode_ins.instruction_OPCODE == OperationCodes.STORE) {
					if (mem_ins.dest_register == decode_ins.dest_register) {
						print_MEMoMEM(mem_ins, decode_ins, decode_ins.dest_register, cycle);
						return;
					}
				}

				// For LOAD to REGISTER
				if (mem_ins.instruction_OPCODE == OperationCodes.LOAD
						&& !(decode_ins.instruction_OPCODE == OperationCodes.LOAD
								|| decode_ins.instruction_OPCODE == OperationCodes.STORE
								|| decode_ins.instruction_OPCODE == OperationCodes.BAL
								|| decode_ins.instruction_OPCODE == OperationCodes.HALT
								|| decode_ins.instruction_OPCODE == OperationCodes.BNZ
								|| decode_ins.instruction_OPCODE == OperationCodes.BZ
								|| decode_ins.instruction_OPCODE == OperationCodes.JUMP)) {
					if (mem_ins.dest_register == decode_ins.src1) {
						print_MEMoMEM(mem_ins, decode_ins, decode_ins.src1, cycle);
						return;
					}
					if (mem_ins.dest_register == decode_ins.src2) {
						print_MEMoMEM(mem_ins, decode_ins, decode_ins.src2, cycle);
						return;
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerFile.logger
					.info("Exception in forwarding ExitingMEMtoEnteringEX Stage : " + e.getClass().getSimpleName());
		}
	}

	/**
	 * @param exiting_mem
	 * @param entering_mem
	 * @param cycle
	 */
	public static void forwardingExitingMEMtoEnteringMEM(Instruction exiting_mem, Instruction entering_mem, int cycle) {
		try {
			// For LOAD to STORE
			if (exiting_mem != null && entering_mem != null) {
				if (exiting_mem.instruction_OPCODE == OperationCodes.LOAD
						&& entering_mem.instruction_OPCODE == OperationCodes.STORE) {
					if (exiting_mem.dest_register == entering_mem.dest_register) {
						print_MEMtoEX(exiting_mem, entering_mem, entering_mem.dest_register, cycle);
						return;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerFile.logger
					.info("Exception in forwarding ExitingMEMtoEnteringMEM Stage : " + e.getClass().getSimpleName());
		}
	}

	// For all Register to Register Operations || Register -> LOAD || REGISTER
	// --> STORE || MOVC -> ANY
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
						print_EXtoEX(ex2_instruction, decode_ins, decode_ins.src1, cycle);
						return;
					}
					if (ex2_instruction.dest_register == decode_ins.src2) {
						print_EXtoEX(ex2_instruction, decode_ins, decode_ins.src2, cycle);
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
						print_EXtoEX(ex2_instruction, decode_ins, decode_ins.dest_register, cycle);
						return;
					}
				}

				if ((decode_ins.instruction_OPCODE == OperationCodes.STORE)
						&& (!(ex2_instruction.instruction_OPCODE == OperationCodes.STORE
								&& ex2_instruction.instruction_OPCODE == OperationCodes.LOAD))) {
					if (ex2_instruction.dest_register == decode_ins.dest_register) {
						print_EXtoEX(ex2_instruction, decode_ins, decode_ins.dest_register, cycle);
						return;
					}
				}

				if ((decode_ins.instruction_OPCODE == OperationCodes.LOAD)
						&& (!(ex2_instruction.instruction_OPCODE == OperationCodes.STORE
								&& ex2_instruction.instruction_OPCODE == OperationCodes.LOAD))) {
					if (ex2_instruction.dest_register == decode_ins.src1) {
						print_EXtoEX(ex2_instruction, decode_ins, decode_ins.src1, cycle);
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
					.info("Exception in forwarding ExitingEXtoEnteringEX Stage : " + e.getClass().getSimpleName());
		}
	}

}
