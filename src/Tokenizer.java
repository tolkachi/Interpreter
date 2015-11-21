//package Interpreter.src;

import java.util.Scanner;


public interface Tokenizer {

	void Collect_Tokens (Scanner input);

	int getToken();
	
	String AddChar(String s, char ch);
	
	String removeCharFrom(String s, int pos);
	
	boolean Is_Whitespace (char ch);
	
	int TokensLeft();
	
	int NextToken();
	
	String getIDVal ();
	
	void Output_Tokenizer_Size();
	
	int getIntVal();
	
	void print_tokens();
}
