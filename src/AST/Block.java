package AST;


import java.io.*;
import java.util.ArrayList;

public class Block extends Statement {
	private ArrayList<Statement> statements = new ArrayList<Statement>();
	
	public void addStatement( Statement newStatement )
	{
		statements.add( newStatement );
	}
	
	public void translate( BufferedWriter out )
	throws IOException
	{
		for ( int i=0; i<statements.size(); i++ )
		{
			Statement current = (Statement) statements.get(i);
			current.translate( out );
		}
	}
}


