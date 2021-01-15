package SymbolTable;

import java.util.Stack;

public class SymbolTable {
	
	/*tabela simbola za "language scope"
	u ovom slucaju tu pripadaju samo tipovi*/
	private SymbolNode types;
	
	/* tabela simbola za oblast vazenja programa */
	private Stack<SymbolNode> variableScopes;
	private SymbolNode variables;
	
	public SymbolTable( )
	{
		types = new TypeNode( "unknown", TypeNode.UNKNOWN, null);
		types = new TypeNode( "real", TypeNode.REAL, types );
		types = new TypeNode( "integer", TypeNode.INTEGER, types );
		types = new TypeNode( "boolean", TypeNode.BOOLEAN, types );
		variableScopes = new Stack<SymbolNode>();
		variables = null;
	}
	
	public void addScope() {
		variableScopes.push(null);
	}
	
	public boolean addVar( String name, TypeNode type )
	{
		Variable existing = this.getVar( name );
		if ( existing != null )
			return false;
		variables = new Variable( name, type, variables );
		
		// dodamo promenljivu na najugnjezdeniji scope
		SymbolNode scopeVar = variableScopes.pop();
		scopeVar = new Variable(name, type, scopeVar);
		variableScopes.push(scopeVar);
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
	
	public void exitScope() {
		SymbolNode scopeVariables = variableScopes.pop();
		while (scopeVariables != null) {
			remove(scopeVariables.name);
			scopeVariables = scopeVariables.next;
		}
	}

	private void remove(String variableName) {
		SymbolNode current = variables;
		if (current.name.equals(variableName)) {
			variables = current.next;
			current = null;
			return;
		}
		
		SymbolNode prev = null;
		while (current != null && !current.name.equals(variableName)) {
			prev = current;
			current = current.next;
		}
		
		if (current != null)
			prev.next = current.next;
	}

}
