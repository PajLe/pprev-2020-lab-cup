package SymbolTable;

import java.util.Stack;

public class SymbolTable {
	
	/*tabela simbola za "language scope"
	u ovom slucaju tu pripadaju samo tipovi*/
	private SymbolNode types;
	
	/* tabela simbola za oblast vazenja programa */
	private SymbolNode variables;
	
	public SymbolTable( )
	{
		types = new TypeNode( "unknown", TypeNode.UNKNOWN, null);
		types = new TypeNode( "real", TypeNode.REAL, types );
		types = new TypeNode( "integer", TypeNode.INTEGER, types );
		types = new TypeNode( "boolean", TypeNode.BOOLEAN, types );
		variables = null;
	}
	
	public boolean addVar( String name, TypeNode type )
	{
		Variable existing = this.getVar( name );
		if ( existing != null )
			return false;
		variables = new Variable( name, type, variables );
		return true;
	}
	
	public Variable getVar( String name )
	{
		SymbolNode current = variables;
		while ( current != null && 
				current.name.compareTo( name ) != 0 )
			current = current.next;
		return ( Variable ) current;
	}
	
	public TypeNode getType(String typeName)
	{
		SymbolNode current = types;
		while ( current != null && 
				current.name.compareTo( typeName ) != 0 )
			current = current.next;
		return ( TypeNode ) current;
	}
	
	public SymbolNode getVariables()
	{
		return variables;
	}
	
}
