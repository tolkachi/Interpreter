//package Interpreter.src;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;


public class Tokenizer_Class implements Tokenizer {

	//------------------------------------------------
	//PRIVATE VARIABLES
	//-------------------------------------------------
	 
	 	private static final class Value_token {
	        /** Integer value of the token*/
	        public int value;

	        /** String value of the token. */
	        public String token;

	        
	        /**
	         * Initializes a new symbol with the given value and relativity flag.
	         */
	        public Value_token(int value, String token) {
	            this.value = value;
	            this.token = token;
	        }
	    }
	//Initialize arraylist to store two-tuple of token number and its string value
	
	ArrayList<Value_token> tokens = new ArrayList<Value_token>();
	
	private String inputLine;
	//private String tok=""; 
	private int i = 0;
	
	//-------------------------------------------------------------------------------------------------------
	//                                                     PUBLIC OPERATIONS
	//-------------------------------------------------------------------------------------------------------
    /**Adds a character to a string
     * 
	 *
	 * @param s
	 * 		a string to which a character is added
	 * @param ch
	 * 		a character that is added to a string
	 
     */


	public String AddChar(String s,  char ch) {
		
		String a = Character.toString(ch);
	    return s.concat(a);
	}
	
	
	
	
	
	
	public void Output_Tokenizer_Size()
	{
		
		System.out.println(tokens.size());
	}
	
	 public  boolean Is_Whitespace (char wh)
	 {
		 return ( wh=='\n' || wh=='\t' || wh==' ');
	 }
	
	
	
	
	public void Collect_Tokens (Scanner input)
	{
		 while (input.hasNext()) {
	        	
			 String inputLine = "";
	        	inputLine = input.nextLine();
	        	
	            	
	            //Read get a character from the line until a line becomes empty
	           while (inputLine.length()>0)
	            {
	            		
	             char f_letter = inputLine.charAt (0); 
	        
		     inputLine = removeCharFrom(inputLine, 0);
	             //checks whether the removed character is the first character of any token. If yes, makes a call to the procedure that checks the 
	             //characters and builds the token. After calling a procedure, get the value of the modified string (the rest of the inputline).
	             if (f_letter == 'p')
	             { 
	            	program_token(inputLine, f_letter);
	            	 inputLine=get_inputLine();
	             }
	             
	             else if (f_letter == 'b')
	             { 
	            	begin_token(inputLine, f_letter);
	            	 inputLine=get_inputLine();
	             }
	             
	             else if (f_letter == 'e')
	             { 
	            	else_or_end_token(inputLine, f_letter);
	            	 inputLine= get_inputLine();
	             }
	              
	             else  if (f_letter == 'l')
	             { 
	            	loop_token(inputLine, f_letter);
	            	 inputLine=get_inputLine();
	             }
	             
	             else  if (f_letter == 't')
	             { 
	            	 then_token(inputLine, f_letter);
	            	 inputLine=get_inputLine();
	             }
	             
	             else  if (f_letter == 'w')
	             { 
	            	 while_or_write_token(inputLine, f_letter);
	            	 inputLine=get_inputLine();
	             }
	            	
	             else  if (f_letter == 'r')
	             { 
	            	read_token(inputLine, f_letter);
	            	 inputLine=get_inputLine();
	             }
	             
	             else  if (f_letter == 'i')
	             { 
	            	integer_if_token(inputLine, f_letter);
	            	 inputLine=get_inputLine();
	             }
	             //if two char special makes a call that checks two characters token
	             else if (Is_Two_Char_Special (f_letter))
	             {
	           

	            	if (f_letter=='=')
	            	{
	            	eq_token(inputLine, f_letter);
	            	inputLine=get_inputLine();
	            	}
	            	 
	            	if (f_letter =='!')
	            	{
	            	not_token (inputLine, f_letter);
	            	inputLine=get_inputLine();
	            	}
	            	  	
	            	 if (f_letter == '&')
	            	 {
	            	and_token (inputLine, f_letter);
	            	inputLine=get_inputLine();
	            	 }
	            	 
	            	 if (f_letter == '|')
	            	 {
	            	 or_token (inputLine, f_letter);
	            	 inputLine=get_inputLine();
	            	 }
	            	 
	            	 if (f_letter == '<')
	            	 {
	            	 lte_token (inputLine, f_letter);
	            	 inputLine=get_inputLine();
	            	 }
	            	 
	            	 if (f_letter == '>')
	            	 {
	            	gte_token (inputLine, f_letter);
	            	inputLine=get_inputLine();
	            	 }
	            	 	 
	             }
	            //if a character is one character special token, make a call to the procedure that stores the token in array list 
	             else if (Is_Special_C(f_letter))
	             {
	            	 special_token(f_letter);
	             }
	            //if a character is an integer, make a call to the procedure that buillds integer token and puts the integer string value and token integer 
	            //value in tokens table 
	             else if (Is_Integer (f_letter))
	             {
		    	 	 Integer (inputLine, f_letter); 
	            	 inputLine=get_inputLine();
	             }
	           //if a character is an uppercase letter, make a call to the procedure that buillds identifier token and puts the identifier string value and 
		//identifier token integer value in tokens table 
	             else if ( Character.isUpperCase(f_letter))
	             {
	           
	            	 identifier(inputLine, f_letter);
	            	 inputLine=get_inputLine();
	            	
	             }
	           //if a character is whitespace, skip
	        	else if (Is_Whitespace(f_letter))
	        	{

	        	}
	           //if a character is not any of the characters  that were mentioned above, output error message
	             else 
	            {
	            	 error ("FATAL ERROR: Illegal token!",true);
	            }
	            	
	         } 
	    }
			//} catch (IOException e) {
	         //   System.err.println("Error 201: Could not read input file: " + e);
	       // } finally {
	           // try {
	               // if (in!= null) {
	    input.close();
	}
	            
	        
	//Print the integer values of the tokens 
	       // tokenizer.print_tokens();
	//Print an integer value of EOF token
	       // System.out.println ("33");

		public int getToken()
		{
			
		
			if (tokens.size()==0)
			{
				error ("There are no tokens in tokenizer!", true);
			}
			Value_token token_pair = tokens.remove(0);
		
			return (token_pair.value);
		}
		
		public int TokensLeft()
		{
			return tokens.size();
		}
		
		public int NextToken()
		{
			Value_token token_pair = tokens.remove(0);
			tokens.add(0,token_pair);
			return (token_pair.value);
		}

		public void print_tokens() {
			int index=0;
			while (index < tokens.size())
			{
				 Value_token token = tokens.get(index);		
				
				System.out.print(token.value + "   ");
				System.out.print(token.token+ "	  ");
				System.out.println (index);
				 index++;
			}

		}

		public String getIDVal()
		{
			
			Value_token token_pair=tokens.remove(0) ;
			
			return (token_pair.token);
			
		}
		
		public int getIntVal()
		{
			Value_token token_pair=tokens.remove(0) ;
			return (Integer.valueOf(token_pair.token));
			
		}
		
		public  String removeCharFrom(String s, int pos) {
			if (s.length()==1)
			{
			return s.replaceFirst(Character.toString(s.charAt(pos)), "");
			}
			else
			{
		    return s.substring(pos+1, s.length());
		    }
		}
		
	//----------------------------------------------------------------------------------------------------------------
    //	                     PRIVATE OPERATIONS
	//-----------------------------------------------------------------------------------------------------------------

		 /**
	     * Checks whether a character is an integer 
		 *
		 * @param sp
		 * 		char that is checked 
		 
	     */ 


		 private static boolean Is_Integer (char in)
		 {
			 return ( in=='0' || in=='1' || in=='2'||in=='3'||in=='4' || in=='5' || in=='6'||in=='7'|| in=='8'||in=='9');
		 }
		
	 /**
	     * Checks whether a character is a whitespace character
		 *
		 * @param sp
		 * 		char that is checked 
		 
	     */ 

		 
		

	//--------------------------------------------------------------------------------------
//	                    PRIVATE OPERATIONS
	//--------------------------------------------------------------------------------------
		 /**
	     * Checks whether a character is a one character special token
		 *
		 * @param sp
		 * 		char that is checked 
		 
	     */ 

		private static boolean Is_Special_C (char sp)
		 {
			 return ( sp==';'||sp==',' || sp=='='|| sp=='['||sp==']'|| sp=='('||sp==')'|| sp=='+'||sp=='-'||sp=='*');
		 }

	 /**
	     * Checks whether a character is a first character of two character special token
		 *
		 * @param sp
		 * 		char that is checked 
		 
	     */ 


		 private static boolean Is_Two_Char_Special (char sp) {
			
				 return (sp=='='||sp=='!'|sp=='&'|| sp=='|'||sp=='<'||sp=='>');
		 }

	


		
		/**
	     * Checks whether a character is a one character special token
		 *
		 * @param sp
		 * 		char that is checked 
		 
	     */ 
		 private static boolean Is_Spec (char sp)
		 {
			 return ( sp==';'||sp==',' || sp=='='||sp=='!' || sp=='['||sp==']'||sp=='&'|| sp=='|'||sp=='('||sp==')'|| sp=='+'||sp=='-'||sp=='*' ||sp=='<'||sp=='>');
		 }
		

	 


		

	//-------------------------------------------------------------------------------------------------------
    //	                                       PRIVATE OPERATIONS
	//-------------------------------------------------------------------------------------------------------
	    
	private void error(String message, boolean fatal) {
	        System.err.println(message);
	        if (fatal) {
	            System.exit(1);
	        }
	    }
		
	private void program_token(String token, char f_letter) {
		
	String	tok ="";
	tok = AddChar(tok, f_letter);
		
		//checks there are enough characters in the legal token. If not,prints an error message and terminates the program 
		if (token.length()>=6)
		{	
			
			f_letter = token.charAt (0);
			
			if (f_letter == 'r')
			{
				token = removeCharFrom(token, 0);
				tok = AddChar(tok, f_letter);
			}
			else 
			{
	    	 error ("FATAL ERROR: Illegal token!",true);
			}
		 
			f_letter = token.charAt (0);
			if (f_letter == 'o')
			{
				token = removeCharFrom(token, 0);
				tok = AddChar(tok, f_letter);
			}
			else 
			{
	    	 error ("FATAL ERROR: Illegal token!",true);
			}
	     
			f_letter = token.charAt (0);
			if (f_letter == 'g')
			{
				token = removeCharFrom(token, 0);
				tok = AddChar(tok, f_letter);
			}
			else 
			{
	    	 error ("FATAL ERROR: Illegal token!",true);
			}
	     
			f_letter = token.charAt (0);
			if (f_letter == 'r')
			{
				token = removeCharFrom(token, 0);
				tok = AddChar(tok, f_letter);
			}
			else 
			{
	    	 error ("FATAL ERROR: Illegal token!",true);
			}
	     
			f_letter = token.charAt (0);
			if (f_letter == 'a')
			{
				token = removeCharFrom(token, 0);
				tok = AddChar(tok, f_letter);
			}
			else 
			{
	    	 error ("FATAL ERROR: Illegal token!",true);
			}
		 
			f_letter = token.charAt (0);
			if (f_letter == 'm')	
			{
				token = removeCharFrom(token, 0);
				tok = AddChar(tok, f_letter);
			}
			else 
			{
	    	 error ("FATAL ERROR: Illegal token!",true);
			}
		 //checks there is a whitespace or special token after or end of the input line.If no, rules are violated. Error message is printed to console 
		//and program terminates
			if ( token.length()==0||Is_Spec(token.charAt(0)) || Is_Whitespace (token.charAt(0)))
			{
	    	 tokens.add(new Value_token (1,tok));
			}
			else 
			{
	    	 error ("FATAL ERROR: Illegal token!",true);
			}
		}	
		else
		{
			error ("FATAL ERROR: Illegal token!",true);	
		}
			inputLine = token;
	 }


		private void begin_token (String token, char f_letter) {
			
			String tok = "";
		tok = AddChar(tok, f_letter);
		//checks there are enough characters in the legal token. If not,prints an error message and terminates the program 
		if (token.length()>=4)
		{
			f_letter = token.charAt (0);
			
			 if (f_letter == 'e')
			 {
		     token = removeCharFrom(token, 0);
		     tok = AddChar(tok, f_letter);
			 }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true);
		     }
			 
			 f_letter = token.charAt (0);
			 if (f_letter == 'g')
			 {
		     token = removeCharFrom(token, 0);
		     tok = AddChar(tok, f_letter);
			 }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true);
		     }
		     
			 f_letter = token.charAt (0);
			 if (f_letter == 'i')
			 {
		     token = removeCharFrom(token, 0);
		     tok = AddChar(tok, f_letter);
			 }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true);
		     }
		     
			 f_letter = token.charAt (0);
			 if (f_letter == 'n')
			 {
		     token = removeCharFrom(token, 0);
		     tok = AddChar(tok, f_letter);
			 }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true);
		     }
		      
			// f_letter = token.charAt (0);
			 
			 //checks there is a whitespace or special token after or end of the input line.If no, rules are violated. Error message is 
			//printed to console and program terminates
		     if (token.length()==0||Is_Spec(token.charAt(0)) || Is_Whitespace (token.charAt(0)))
		     {
		    	 tokens.add(new Value_token (2,tok));
		     }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true);
		     }
		}     
		else
		{
			error ("FATAL ERROR: Illegal token!",true);	
		}
	        inputLine = token;
	}


		private void else_or_end_token(String token, char f_letter) {
			
		String tok = "";
		tok =AddChar(tok, f_letter);
		//checks there are enough characters in both legal tokens. If not,prints an error message and terminates the program 	
		if (token.length()>=2)
		{
			f_letter = token.charAt (0);
			
			 if (f_letter == 'l')
			 {
				 token = removeCharFrom(token, 0);
				 tok = AddChar(tok, f_letter);
			//checks there are enough characters in the legal token. If not,prints an error message and terminates the program 
			   if (token.length()>=2)
			   {
				 f_letter = token.charAt (0);
				 if (f_letter == 's')
				 {
					 token = removeCharFrom(token, 0);
					 tok = AddChar(tok, f_letter);
				 }
				 else 
				 {
		    	 error ("FATAL ERROR: Illegal token!",true);
				 }
		     
				 f_letter = token.charAt (0);
				 if (f_letter == 'e')
				 {
					 token = removeCharFrom(token, 0);
					 tok = AddChar(tok, f_letter);
				 }
				 else 
				 {
		    	 error ("FATAL ERROR: Illegal token!",true);
				 }
				  //checks there is a whitespace or special token after or end of the input line
			     if (token.length()==0||Is_Spec(token.charAt(0)) || Is_Whitespace (token.charAt(0)))
			     {
			    	 tokens.add(new Value_token (7, tok));
			     }
			     else 
			     {
			    	 error ("FATAL ERROR: Illegal token!",true);
			     }
			   }
			   else
			   {
				   error ("FATAL ERROR: Illegal token!",true);
			   }
			 }

			 else if (f_letter == 'n')
			 {
				 token = removeCharFrom(token, 0);
				 tok = AddChar(tok, f_letter);
			 
				 f_letter = token.charAt (0);
				 if (f_letter == 'd')
				 {
					 token = removeCharFrom(token, 0);
					 tok = AddChar(tok, f_letter);
				 }
				 else 
				 {
		    	 error ("FATAL ERROR: Illegal token!",true);
				 }
		//checks there is a whitespace or special token after or end of the input line.If no, rules are violated. Error message is 
		//printed to console and program terminates
				 if (token.length()==0||Is_Spec(token.charAt(0)) || Is_Whitespace (token.charAt(0)))
				 {
		    	 tokens.add(new Value_token (3, tok));
				 }
				  else 
				 {
		    	 error ("FATAL ERROR: Illegal token!",true);
				 }
			}
			 
			else
			{
				 error ("FATAL ERROR: Illegal token!",true); 
			}
		}	 
		 else
		{
			error ("FATAL ERROR: Illegal token!",true);  
		}
		//get a current value of the string
			 inputLine = token;
		}

		
		private void then_token(String token, char f_letter) {
			
		String tok = "";
		tok = AddChar(tok, f_letter);
		//checks there are enough characters in the legal token. If not,prints an error message and terminates the program 	
		if (token.length()>=3)
		{
			 f_letter = token.charAt (0);
			 
			 if (f_letter == 'h')
			 {
				 token = removeCharFrom(token, 0);
				 tok = AddChar(tok, f_letter);
			 }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true);
		     }
			 
			 f_letter = token.charAt (0);
			 if (f_letter == 'e')
			 {
				 token = removeCharFrom(token, 0);
				 tok = AddChar(tok, f_letter);
			 }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true);
		     }
		     
			 f_letter = token.charAt (0);
			 if (f_letter == 'n')
			 {
				 token = removeCharFrom(token, 0);
				 tok = AddChar(tok, f_letter);
			 }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true);
		     }
			//checks there is a whitespace or special token after or end of the input line.If no, rules are violated. Error message is 
		//printed to console and program terminates
		     if (token.length()==0||Is_Spec(token.charAt(0)) || Is_Whitespace (token.charAt(0)))
		     {
		    	  tokens.add(new Value_token (6,tok));
		     }
		     
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true);
		     }
		}
		else
		{
			 error ("FATAL ERROR: Illegal token!",true);	
		}
	     inputLine = token;
		}


		private void loop_token(String token, char f_letter) {
			
			String tok = "";
			tok = AddChar(tok, f_letter);
		//checks there are enough characters in the legal token. If not,prints an error message and terminates the program 
			if (token.length()>=3)
			{
			 f_letter = token.charAt (0);
			 
			 if (f_letter == 'o')
			 {
		     token = removeCharFrom(token, 0);
		     tok = AddChar(tok, f_letter);
			 }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true);
		     }
			 
			 f_letter = token.charAt (0);
			 if (f_letter == 'o')
			 {
		     token = removeCharFrom(token, 0);
		     tok = AddChar(tok, f_letter);
			 }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true);
		     }
		     
			 f_letter = token.charAt (0);
			 if (f_letter == 'p')
			 {
		     token = removeCharFrom(token, 0);
		     tok = AddChar(tok, f_letter);
			 }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true);
		     }
			//checks there is a whitespace or special token after or end of the input line.If no, rules are violated. Error message is 
		//printed to console and program terminates
		     if (token.length()==0||Is_Spec(token.charAt(0)) || Is_Whitespace (token.charAt(0)))
		     {
		    	 tokens.add(new Value_token (9,tok));
		     }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true);
		     }
		}
			else
			{
				 error ("FATAL ERROR: Illegal token!",true);
			}
	    inputLine = token;
		}

		
		private void read_token(String token, char f_letter) {
			
	  String tok = "";
	  tok = AddChar(tok, f_letter);
	//checks there are enough characters in the legal token. If not,prints an error message and terminates the program 
	   if (token.length()>=3)
		{
			 f_letter = token.charAt (0);
			 
			 if (f_letter == 'e')
			 {
				 token = removeCharFrom(token, 0);
				 tok = AddChar(tok, f_letter);
			 }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true);
		     }
			 
			 f_letter = token.charAt (0);
			 if (f_letter == 'a')
			 {
				 token = removeCharFrom(token, 0);
				 tok = AddChar(tok, f_letter);
			 }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true);
		     }
		     
			 f_letter = token.charAt (0);
			 if (f_letter == 'd')
			 {
				 token = removeCharFrom(token, 0);
		     	 tok = AddChar(tok, f_letter);
			 }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true);
		     }
			//checks there is a whitespace or special token after or end of the input line.If no, rules are violated. Error message is 
		//printed to console and program terminates
		     if (token.length()>=0||Is_Spec(token.charAt(0)) || Is_Whitespace (token.charAt(0)))
		     {
		    	 tokens.add(new Value_token (10,tok));
		     }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true);
		     }
		}
	   else
	   {
		   error ("FATAL ERROR: Illegal token!",true);  
	   }
	    inputLine = token;
		}


		private void while_or_write_token(String token, char f_letter) {

		String tok = "";
		tok = AddChar(tok, f_letter);
	//checks there are enough characters in both legal tokens. If not,prints an error message and terminates the program 
		if (token.length()>=4)	
		{
			f_letter = token.charAt (0);
			
			 if (f_letter == 'h')
			 {
				 token = removeCharFrom(token, 0);
				 tok = AddChar(tok, f_letter);
			 
				 f_letter = token.charAt (0);
				 if (f_letter == 'i')
				 {
					 token = removeCharFrom(token, 0);
					 tok = AddChar(tok, f_letter);
				 }
				 else 
				 {
					 error ("FATAL ERROR: Illegal token!",true);
				 }
		     
				 f_letter = token.charAt (0);
				 if (f_letter == 'l')
				 {
					 token = removeCharFrom(token, 0);
					 tok = AddChar(tok, f_letter);
				 }
				 else 
				 {
		    	 error ("FATAL ERROR: Illegal token!",true);
				 }
				 
				 f_letter = token.charAt (0);
				 if (f_letter == 'e')
				 {
					 token = removeCharFrom(token, 0);
					 tok = AddChar(tok, f_letter);
				 }
				 else 
				 {
		    	 error ("FATAL ERROR: Illegal token!",true);
				 }
				//checks there is a whitespace or special token after or end of the input line.If no, rules are violated. Error message is 
		//printed to console and program terminates
			     if (token.length()==0||Is_Spec(token.charAt(0)) || Is_Whitespace (token.charAt(0)))
			     {
			    	 tokens.add(new Value_token (8, tok));
			     }
			     else 
			     {
			    	 error ("FATAL ERROR: Illegal token!",true);
			     }
			 }

			 else if (f_letter == 'r')
			 {
				 token = removeCharFrom(token, 0);
				 tok = AddChar(tok, f_letter);
			 
				 f_letter = token.charAt (0);
				 if (f_letter == 'i')
				 {
					 token = removeCharFrom(token, 0);
					 tok = AddChar(tok, f_letter);
				 }
				 else 
				 {
		    	 error ("FATAL ERROR: Illegal token!",true);
				 }
				 
				 f_letter = token.charAt (0);
				 if (f_letter == 't')
				 {
					 token = removeCharFrom(token, 0);
					 tok = AddChar(tok, f_letter);
				 }
				 else 
				 {
		    	 error ("FATAL ERROR: Illegal token!",true);
				 }
				 
				 f_letter = token.charAt (0);
				 if (f_letter == 'e')
				 {
					 token = removeCharFrom(token, 0);
					 tok = AddChar(tok, f_letter);
				 }
				 else 
				 {
		    	 error ("FATAL ERROR: Illegal token!",true);
				 }
			 
				 if (token.length()==0||Is_Spec(token.charAt(0)) || Is_Whitespace (token.charAt(0)))
				 {
		    	  tokens.add(new Value_token (11, tok));
				 }
				  else 
				 {
		    	 error ("FATAL ERROR: Illegal token!",true);
				 }
			 }
			 else
			 {
				 error ("FATAL ERROR: Illegal token!",true);	 
			 }
		}
		else
		{
			error ("FATAL ERROR: Illegal token!",true);
		}
			 inputLine = token;
		}


		private void integer_if_token(String token, char f_letter) {
			
		 String tok = "";
		 tok = AddChar(tok, f_letter);
	//checks there are enough characters in bothlegal token. If not,prints an error message and terminates the program 
		if (token.length()>=1)
		{
			f_letter = token.charAt (0);
			 
			 if (f_letter=='f')
			 {
			     token = removeCharFrom(token, 0);
			     tok = AddChar(tok, f_letter);
			      
				 f_letter = token.charAt (0);
				 
			
			     if (token.length()==0||Is_Spec(token.charAt(0)) || Is_Whitespace (token.charAt(0)))
			     {
			    	 
			    	 tokens.add(new Value_token (5,tok));}
			     
			     else 
			     {
			    	 error ("FATAL ERROR: Illegal token!",true);
			     }
			 }
		
		     else if (f_letter=='n')
		     {
			
		    	 token = removeCharFrom(token, 0);
		    	 tok = AddChar(tok, f_letter);
	//checks there are enough characters in the legal token "if". If not,prints an error message and terminates the program 
		  if (token.length()>=1)
		  {
		    	 f_letter = token.charAt (0);
		    	 if (f_letter == 't')
		    	 {
		    		 token = removeCharFrom(token, 0);
		    		 tok = AddChar(tok, f_letter);
		    	 }
		    	 else 
		    	 {
		    		 error ("FATAL ERROR: Illegal token!",true);
		    	 }
		     
			
		    	 if (token.length()==0||Is_Spec(token.charAt(0)) || Is_Whitespace (token.charAt(0)))
		    	 {
		    		 tokens.add(new Value_token (4,tok));
		    	 }
		    	 else 
		    	 {
		    		 error ("FATAL ERROR: Illegal token!",true);
		    	 }
			   
		       }
		       else
		       {
		    	   error ("FATAL ERROR: Illegal token!",true);
		       } 
		   } 
		   else 
		   {
			   error ("FATAL ERROR: Illegal token!",true);	 
		   }
		 
		}
		else 
		{
			error ("FATAL ERROR: Illegal token!",true);	 
		}
		inputLine=token;
		}

		private void Integer (String token, char f_letter) {
			
			String tok = "";
			
			tok = AddChar(tok, f_letter);
			 
			while (token.length()>0 && Is_Integer(token.charAt (0)))
			{
				f_letter=token.charAt(0);
				 token = removeCharFrom(token, 0);
			     tok = AddChar(tok, f_letter);
			 }
		//checks there is a whitespace or special token after or end of the input line.If no, rules are violated. Error message is 
		//printed to console and program terminates
			 if (token.length()==0||Is_Spec(token.charAt(0)) || Is_Whitespace (token.charAt(0)))
			 {
				 
			 	tokens.add(new Value_token (31,tok));
			 }
			 else 
			 {
			 	error ("FATAL ERROR: Illegal token!",true);
			 }
		inputLine=token;
		}
		
		

		private void identifier(String token, char f_letter) {
		
			//System.out.print(f_letter);
			String tok ="";
			tok= AddChar(tok, f_letter);
			boolean notfound = true;
			//System.out.print(t);
			 //checks the identifier rules and builds the identifier token till there are characters in the token
			while (token.length()>0 && (Is_Integer(token.charAt (0))||Character.isUpperCase(token.charAt(0)))&&notfound)
			{
				f_letter = token.charAt(0);
				 token = removeCharFrom(token, 0);
			     tok = AddChar(tok, f_letter);
			     if (Is_Integer(f_letter))
			     {
			    	 notfound=false;
			 		while (token.length()>0 && (Is_Integer(token.charAt (0))||Character.isUpperCase(token.charAt(0))))
			    	 {
			 			if (Character.isUpperCase(token.charAt(0)))
			 			{
			 				error ("FATAL ERROR: Illegal token!",true);
			 			}
			 			else
			 			{
			    		 f_letter = token.charAt(0);
						 token = removeCharFrom(token, 0);
					     tok = AddChar(tok, f_letter);
					     
			 			}
			    	 } 
			     }
			}
			
			
			//checks there is a whitespace or special token after or end of the input line.If no, rules are violated. Error message is 
		//printed to console and program terminates
			 if (token.length()==0 || Is_Spec(token.charAt(0)) || Is_Whitespace (token.charAt(0)))
			 {
				 //System.out.println(tok);
				 
			 	tokens.add(new Value_token (32,tok));
			 }
			 else 
			 {
			 	error ("FATAL ERROR: Illegal token!",true);
			 }
			 
			inputLine=token;
		}


		private void special_token(char a) {
			if (a==';')
		   {
				tokens.add(new Value_token (12,Character.toString(a)));
		   }
			else if (a==',')
			{
				tokens.add(new Value_token (13,Character.toString(a)));	
			}
			else if (a=='[')
			{
				tokens.add(new Value_token (16,Character.toString(a)));	
			}
			else if (a==']')
			{
				tokens.add(new Value_token (17,Character.toString(a)));	
			}
			else if (a=='(')
			{
				tokens.add(new Value_token (20,Character.toString(a)));	
			}
			else if (a==')')
			{
				tokens.add(new Value_token (21,Character.toString(a)));	
			}
			else if (a=='+')
			{
				tokens.add(new Value_token (22,Character.toString(a)));	
			}
			else if (a=='-')
			{
				tokens.add(new Value_token (23,Character.toString(a)));	
			}
			else if (a=='*')
			{
				tokens.add(new Value_token (24,Character.toString(a)));	
			}
			//Tokenizer_Main. Is_Special();
		
		}

		private void and_token(String token, char f_letter) {
			
			String tok = "";
			tok = AddChar(tok, f_letter);
		//checks there are enough characters for the two characters special token. 
		//If not,prints an error message and terminates the program 
		 if (token.length()>=1)
		{
				 
			 f_letter = token.charAt (0);
			 if (f_letter == '&')
			 {
				 token = removeCharFrom(token, 0);
				 tok = AddChar(tok, f_letter);
				 tokens.add(new Value_token (18,tok));	
			 }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true); 
		     }
		}
		 else
		 {
			 error ("FATAL ERROR: Illegal token!",true);  
		 }
	    inputLine= token;
		}


		private void or_token(String token, char f_letter) {

		String tok = "";
		tok  = AddChar(tok, f_letter);
		//checks there are enough characters for the two characters special token. 
		//If not,prints an error message and terminates the program 
		if (token.length()>=1)
		{
			 f_letter = token.charAt (0);
			 if (f_letter == '|')
			 {
				 token = removeCharFrom(token, 0);
				 tok = AddChar(tok, f_letter);
				 tokens.add(new Value_token (19,tok));	
			 }
		     else 
		     {
		    	 error ("FATAL ERROR: Illegal token!",true); 
		     }
		}
		else
		{
			 error ("FATAL ERROR: Illegal token!",true);	
		}
	    inputLine=token;
			 
		}

		
		private void not_token(String token, char f_letter) {
			
		String tok = "";
		tok = AddChar(tok, f_letter);
		//checks there are enough characters for the two characters special token. If not,puts one char token to the array list
		 if (token.length()>=1)
		 {
			 f_letter = token.charAt (0);
			 if (f_letter == '=')
			 {
				
				 token = removeCharFrom(token, 0);
				 tok = AddChar(tok, f_letter);
				 tokens.add(new Value_token (25,tok));	
			 }
		     else 
		     {
		    	
		    	 tokens.add(new Value_token (15,tok));
		     }
		 }
		 else
		 {
			 tokens.add(new Value_token (15,tok));
		 }
	    inputLine=token;
		}


		private void eq_token(String token, char f_letter) {
			
			String tok = "";
			tok = AddChar(tok, f_letter);
		//checks there are enough characters for the two characters special token. If not,puts one char token to the array list
		if (token.length()>=1)
		{
			 f_letter = token.charAt (0);
			 if (f_letter == '=')
			 {
				 token = removeCharFrom(token, 0);
				 tok = AddChar(tok, f_letter);
				 tokens.add(new Value_token (26,tok));	
			 }
		     else 
		     {
		    	 tokens.add(new Value_token (14,tok));
		     }
		}
		else
		{
			tokens.add(new Value_token (14,tok));
		}
		inputLine=token;
		}

		
		private void lte_token(String token, char f_letter) {
			
			String tok = "";
			tok = AddChar(tok, f_letter);
		//checks there are enough characters for the two characters special token. If not,puts one char token to the array list
			 if (token.length()>=1)
	          {  
			 f_letter = token.charAt (0);
	              
			 if (f_letter == '=')
			 {
				 token = removeCharFrom(token, 0);
				 tok = AddChar(tok, f_letter);
				 tokens.add(new Value_token (29,tok));	
			 }
		         else 
		         {
		    	 	tokens.add(new Value_token (27,tok));
	                 }
		   }
	           else
	           {
			
	            	tokens.add(new Value_token (27,tok));
	            }
	             
		inputLine=token;
		}


		private void gte_token(String token, char f_letter) {
		
	               
		String tok = "";
		tok = AddChar(tok, f_letter);
		//checks there are enough characters for the two characters special token. If not,puts one char token to the array list
			if (token.length()>=1)
	        {  
			 f_letter = token.charAt (0);
			 if (f_letter == '=')
			 {
				 token = removeCharFrom(token, 0);
				 tok = AddChar(tok, f_letter);
				 tokens.add(new Value_token (30,tok));	
			 }
		     else 
		     {
		    	 tokens.add(new Value_token (28,tok));
		     }
	          }
	        else
		{
			tokens.add(new Value_token (28,tok));
		}

		inputLine=token;

		}

	
		

	    private String get_inputLine()
	    {
	    	return inputLine;
	    }
	}

	
	
	
	

