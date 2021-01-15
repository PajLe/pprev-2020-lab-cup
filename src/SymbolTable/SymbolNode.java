package SymbolTable;

public class SymbolNode {
	public static int TYPE = 0;
	public static int VARIABLE = 1;
	public static int CONSTANT = 2;
	
	public String name;
	public int kind;
	public TypeNode type;
	public SymbolNode next;
	
	public SymbolNode( String symbolName, 
			int symbolKind, 
			TypeNode symbolType, 
			SymbolNode nextSymbol)
	{
		name = symbolName;
		kind = symbolKind;
		type = symbolType;
		next = nextSymbol;
	}
	
}
