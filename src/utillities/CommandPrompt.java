package utillities;
import java.util.Scanner;

import APEX_Processor.APEX;
import result.FinalResult;

public class CommandPrompt {
	
	public static void printCompleteMenu() {
        System.out.println("\n");
        System.out.println("============================");
        System.out.println("|   MENU SELECTION          |");
        System.out.println("============================");
        System.out.println("|   Options:                |");
        System.out.println("|  1. Initialize            |");
        System.out.println("|  2. Simulate  <n>         |");
        System.out.println("|  3. Display               |");
        System.out.println("| --------------------------|");
        System.out.println("|  4. Exit                  |");
        System.out.println("============================");
        System.out.println("<n> : Number of simulation cycles");
        System.out.println("============================");
        System.out.println("====== Information =========");
        System.out.println("Initialize\t:\tEnter 1");
        System.out.println("Simulate\t:\tEnter 2 <n>|<n>: Number of simulation cycles");
        System.out.println("Display\t\t:\tEnter 3 ");
        System.out.println("Exit\t\t:\tEnter 4 ");
        System.out.println("============================");
        System.out.println("Enter your choice of operation");
    }
	
	public int inputWithInitializer(Scanner scanner) {
		int input;
		input = scanner.nextInt();

		switch (input) {
		case 1:
			APEX.initialize();
			break;
		case 2:
			APEX.initialize();
			System.out.println("Enter the number of cycles for simulation ?");
			break;
		case 3:
			FinalResult.displayAll();
			break;
		case 4:
			logout();
			break;
		default:
			printCompleteMenu();
			input = inputWithInitializer(scanner);
		}
		return input;
	}
	
	private static void logout() {
        System.out.println("You Are Logged Out");
        System.exit(0);
    }

	public static void printSimulateMenu() {
        System.out.println("\n");
        System.out.println("============================");
        System.out.println("|   MENU SELECTION          |");
        System.out.println("============================");
        System.out.println("|   Options:                |");
        System.out.println("|  1. Simulate   <n>        |");
        System.out.println("|  2. Display               |");
        System.out.println("| --------------------------|");
        System.out.println("|  3. Exit                  |");
        System.out.println("============================");
        System.out.println("<n> : Number of simulation cycles");
        System.out.println("Enter your choice of operation");
    }

	public static int inputWithoutInitializer(Scanner s) {
		int input = s.nextInt();
		switch (input) {

		case 1:
			System.out.println("Enter the number of cycles for simulation ?");
			APEX_PreRequisits.cycles_count_for_simulation = s.nextInt();
			break;
		case 2:
			FinalResult.displayAll();
			break;
		case 3:
			logout();
			break;
		default:
			printSimulateMenu();
			APEX.initialize();
			input = inputWithoutInitializer(s);
		}
		return input;
	}
	
	public static void Simulation(){
		 int user_input = -1;
		 do {
			 Scanner key_input = new Scanner(System.in);
			 if (key_input.hasNextInt()) {
				 user_input = key_input.nextInt();
					 switch (user_input) {
					 case 1:
						 APEX.initialize();
						 break;
					 case 2:
						 APEX.initialize();
						 System.out.println("Enter the number of cycles for simulation ?");
						 APEX_PreRequisits.cycles_count_for_simulation = key_input.nextInt();
						 APEX.processCycles(APEX_PreRequisits.cycles_count_for_simulation);
						 break;
					 case 3:
						 FinalResult.displayAll();
						 break;
					 case 4:
						 logout();
						 break;
					 } 
				
				 key_input.reset();
				 printCompleteMenu();
			 }else {
	                System.out.println("Error: Input should be a valid Integer");
	            }
		 }while (user_input != 0);
	}
}
