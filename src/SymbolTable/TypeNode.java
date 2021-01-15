package SymbolTable;

public class TypeNode extends SymbolNode {
	public static int INTEGER = 0;
	public static int REAL = 1;
	public static int BOOLEAN = 3;
	public static int UNKNOWN = 2;
	public int tkind;
	
	public TypeNode ( String name, 
			int typeKind, 
			SymbolNode next)
	{
		super( name, SymbolNode.TYPE, null, next );
		this.tkind = typeKind;
		this.type = this;
	}
}
