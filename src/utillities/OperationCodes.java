package utillities;

public enum OperationCodes {
	
	ADD("ADD"),
	SUB("SUB"),
	MUL("MUL"),
	AND("AND"),
	OR("OR"),
	XOR("EX-OR"),
	MOVC("MOVC"),  
    LOAD("LOAD"),
    STORE("STORE"),
    BZ("BZ"),
    BNZ("BNZ"),
    JUMP("JUMP"),
    BAL("BAL"),
    HALT("HALT");
    
   private String operationCode;
    
   private OperationCodes(String s) {
		operationCode = s;
    }
   
    public String getStatusCode() {
        return operationCode;
    }
    
	
}
