package AST;

import java.io.*;


public class ElifStatement extends Statement{
	private Statement thenStatement;
	private Expression condition;
	private String globalEndLabel;
	
	public ElifStatement(Expression e, Statement thenBlock)
	{
		condition = e;
		thenStatement = thenBlock;
	}
	
	public void setGlobalEndLabel(String lab) {
		this.globalEndLabel = lab;
	}
	
	public void translate( BufferedWriter out )
	throws IOException
	{
		condition.translate( out );
		condition.genLoad( "R1", out );
		String elseLabel = ASTNode.genLab();
		out.write( "\tJumpIfZero\tR1, " + elseLabel );
		out.newLine();
		thenStatement.translate( out );
		out.write( "\tJump\t" + globalEndLabel );
		out.newLine();
		out.write( elseLabel + ":" );
		out.newLine();
	}
}


