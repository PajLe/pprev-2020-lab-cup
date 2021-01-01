// import sekcija
import java_cup.runtime.*;

%%

// sekcija opcija i deklaracija
%class MPLexer

%cup

%line
%column
//%debug

%eofval{
	return new Symbol( sym.EOF );
%eofval}

%{
   public int getLine()
   {
      return yyline;
   }
%}

//stanja
%xstate KOMENTAR
//makroi
slovo = [a-zA-Z]
cifra = [0-9]

%%

// pravila
\/\*\* 			{ yybegin( KOMENTAR 	); }
<KOMENTAR>~"*/" { yybegin( YYINITIAL 	); }

// beli znaci
[\t\n\r ] { ; }

// zagrade
\( { return new Symbol( sym.LEFTPAR 	); }
\) { return new Symbol( sym.RIGHTPAR 	); }
\{ { return new Symbol( sym.LEFTCURLY 	); }
\} { return new Symbol( sym.RIGHTCURLY 	); }

//relacioni operatori
\< 		{ return new Symbol( sym.LESS 		); }
\<= 	{ return new Symbol( sym.LESSEQ 	); }
== 		{ return new Symbol( sym.EQ 		); }
\<\> 	{ return new Symbol( sym.NOTEQ 		); }
\> 		{ return new Symbol( sym.GREATER 	); }
\>= 	{ return new Symbol( sym.GREATEREQ 	); }

//separatori
; 	{ return new Symbol( sym.SEMICOLON 	); }
: 	{ return new Symbol( sym.COLON 		); }
:= 	{ return new Symbol( sym.ASSIGN 	); }

//kljucne reci
"main" 		{ return new Symbol( sym.MAIN 		);	}
"int" 		{ return new Symbol( sym.INT 		);	}
"real" 		{ return new Symbol( sym.REAL 		);	}
"boolean" 	{ return new Symbol( sym.BOOL	 	);	}
"if" 		{ return new Symbol( sym.IF 		);	}
"elif" 		{ return new Symbol( sym.ELIF 		);	}
"else" 		{ return new Symbol( sym.ELSE 		);	}

//konstante
//int
(2#[0-1]+)|(3#[0-2]+)|(4#[0-3]+)|(5#[0-4]+)|(6#[0-5]+)|(7#[0-6]+)|(8#[0-7]+)|(9#[0-8]+)|((16)?#[0-9A-F]+)|(11#[0-9A]+)|(12#[0-9A-B]+)|(13#[0-9A-C]+)|(14#[0-9A-D]+)|(15#[0-9A-E]+)|((10#)?[0-9]+) { return new Symbol( sym.CONST ); }
//real
{cifra}+\.{cifra}*(E[+-]{cifra}+)* { return new Symbol( sym.CONST ); }
//boolean
"true" 	{ return new Symbol( sym.CONST );	}
"false" { return new Symbol( sym.CONST );	}

//identifikatori
{slovo}({slovo}|{cifra})* { return new Symbol(sym.ID ); }


//obrada gresaka
. { if (yytext() != null && yytext().length() > 0) return new Symbol( sym.error ); }

