package AST;

import java.io.*;


public class ElseStatement extends Statement{
	private Statement thenStatement;
	
	public ElseStatement(Statement thenBlock)
	{
		thenStatement = thenBlock;
	}

	public void translate( BufferedWriter out )
	throws IOException
	{
		thenStatement.translate( out );
		out.newLine();
	}
}


