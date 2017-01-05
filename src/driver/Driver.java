package driver;

import java.util.Scanner;
import APEX_Processor.APEX;
import utillities.APEX_PreRequisits;
import utillities.CommandPrompt;
import utillities.FileProcessor;
import utillities.LoggerFile;

public class Driver {
	public static void main(String[] args) {
		// Code to create log file
		LoggerFile.log();
		if(args != null){
			if(args.length >= 1){
				FileProcessor.setINPUTFILE(args[0]);
				CommandPrompt command = new CommandPrompt();
				CommandPrompt.printCompleteMenu();
				CommandPrompt.Simulation();
				Scanner scanner_ref = new Scanner(System.in);
//				command.inputWithInitializer(scanner_ref);
//				APEX.processCycles(APEX_PreRequisits.cycles_count_for_simulation);
//				while (true) {
//					CommandPrompt.printSimulateMenu();
//					CommandPrompt.inputWithoutInitializer(scanner_ref);
//					APEX.processCycles(APEX_PreRequisits.cycles_count_for_simulation);
//				}
			}
		}else{
			System.out.println("Instruction filename is missing with the command");
			System.out.println("Kindly provide the instruction in the below format");
			System.out.println("javac <java_executable> <input-file.txt>");
		}
	}
}
