package SymbolTable;

public class Constant extends SymbolNode {
	public Object value;
	
	public Constant( TypeNode constType, Object constValue )
	{
		super( null, SymbolNode.CONSTANT, constType, null);
		value = constValue;
	}
}
