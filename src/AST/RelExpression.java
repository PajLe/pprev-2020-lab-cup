package AST;

import java.io.*;


public class RelExpression 
extends Expression {
	private Expression left;
	private Expression right;
	private String opCode;
	
	public RelExpression(Expression l)
	{
		left = l;
		right = null;
	}
	
	public RelExpression(Expression l,Expression r, String opCode)
	{
		left = l;
		right = r;
		this.opCode = opCode;
	}
	
	public void translate(BufferedWriter out)
	throws IOException
	{
		if (right != null) {
			left.translate( out );
			right.translate( out );
			left.genLoad( "R1", out );
			right.genLoad( "R2", out );
			out.write( "\t" + opCode + "\t\tR1, R2" );
			out.newLine();
			this.result = ASTNode.genVar();
			out.write( "\tStore\t\tR1, " + this.result );
			out.newLine();
		} else {
			left.genLoad("R1", out);
			this.result = ASTNode.genVar();
			out.write( "\tStore\t\tR1, " + this.result );
		}
	}	
}



