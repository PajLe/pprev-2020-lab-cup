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
   
   public int convertToBase10(String text) {
        if (text.length() == 0) return -1;
        StringBuilder baseStr = new StringBuilder();
        StringBuilder numberStr = new StringBuilder();
       
        int base;
        if (text.contains("#")) {
            if (text.charAt(0) == '#') {
                baseStr.append(text.charAt(0));
                for (int i = 1; i < text.length(); i++)
                    numberStr.append(text.charAt(i));
            }
            else {
                for (int i = 0; text.charAt(i) != '#'; i++)
                    baseStr.append(text.charAt(i));
                
                for (int i = baseStr.length() + 1; i < text.length(); i++)
                    numberStr.append(text.charAt(i));
            }
        }
        else
            for (int i = 0; i < text.length(); i++)
                numberStr.append(text.charAt(i));
        
        if (baseStr.length() > 0) {
            if (baseStr.length() == 1 && baseStr.charAt(0) == '#')
                base = 16;
            else
                base = Integer.parseInt(baseStr.toString());
        }
        else
            base = 10;
        
        int number = 0;
        int expt = 0;
        for (int i = numberStr.length() - 1; i >= 0; i--) {
            number += Character.getNumericValue(numberStr.charAt(i)) * Math.pow(base, expt);
            expt++;
        }
        
        return number;
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
(2#[0-1]+)|(3#[0-2]+)|(4#[0-3]+)|(5#[0-4]+)|(6#[0-5]+)|(7#[0-6]+)|(8#[0-7]+)|(9#[0-8]+)|((16)?#[0-9A-F]+)|(11#[0-9A]+)|(12#[0-9A-B]+)|(13#[0-9A-C]+)|(14#[0-9A-D]+)|(15#[0-9A-E]+)|((10#)?[0-9]+) 
{ 
	int number = convertToBase10(yytext());
	return new Symbol( sym.INTCONST, new Integer(number) ); 
}
//real
{cifra}+\.{cifra}*(E[+-]{cifra}+)* { return new Symbol( sym.REALCONST, new Double(yytext()) ); }
//boolean
"true" 	{ return new Symbol( sym.BOOLCONST, new Boolean("true"));	}
"false" { return new Symbol( sym.BOOLCONST, new Boolean("false"));	}

//identifikatori
{slovo}({slovo}|{cifra})* { return new Symbol(sym.ID, yyline, yytext()); }


//obrada gresaka
. { if (yytext() != null && yytext().length() > 0) return new Symbol( sym.error ); }

