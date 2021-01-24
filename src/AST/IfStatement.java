package AST;

import java.io.*;
import java.util.ArrayList;


public class IfStatement extends Statement{
	private Statement thenStatement;
	private ArrayList<ElifStatement> elifStatements;
	private Statement elseStatement;
	private Expression condition;
	
	public IfStatement(Expression e, Statement thenBlock, 
			ArrayList<ElifStatement> elifStmts, 
			Statement elseS )
	{
		condition = e;
		thenStatement = thenBlock;
		elifStatements = elifStmts;
		elseStatement = elseS;
	}
	
	public void translate( BufferedWriter out )
	throws IOException
	{
		condition.translate( out );
		condition.genLoad( "R1", out );
		String elseLabel = ASTNode.genLab();
		String endLabel = ASTNode.genLab();
		out.write( "\tJumpIfZero\tR1, " + elseLabel );
		out.newLine();
		thenStatement.translate( out );
		out.write( "\tJump\t" + endLabel );
		out.newLine();
		if (!elifStatements.isEmpty() || elseStatement != null) {
			out.write( elseLabel + ":" );
			out.newLine();
		}
		for (ElifStatement elif : elifStatements) {
			elif.setGlobalEndLabel(endLabel);
			elif.translate(out);
		}
		if (elseStatement != null) 
			elseStatement.translate( out );
		out.write( endLabel + ":" );
		out.newLine();
	}
}


