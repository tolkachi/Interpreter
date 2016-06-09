

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


/** Interpreter_main opens the input program file reads it line by line and calls
 *  Parse_Tree object that extracts all the tokens from the file. Then Interpreter_Main
 *  opens an input data file and calls another operation of the Parse_Tree object. After
 *  that the main class parses the program by using the private operations.The program is
 *  parsed by using recursive descent approach. Each parse operation parses each nonterminal
 *  by calling  other parse operations if needed.After parsing the program Interpreter_Main prints
 *  the program by using the private printing operations. The program is printed by using 
 * 	recursive descent approach.  Each print operation prints the data for each nonterminal
 *  by calling  other print operations if needed. After printing the main class executes the program.
 *  To execute the program the Interpreter_Main uses private operations and recursive descent approach. 
 *  Each executing operation executes or evaluates each nonterminal. 
 *  
* @author Igor Tolkachev
*
*/

public class Interpreter_Main {

/**identation factor used for pretty print*/
 static int idnt_factor = 0;
  
    public static void main(String[] args) {
    
        /**first file that is passed as an argument*/
         String inputLine=args[0];
         /**second file that is passed as an argument*/
         String inputLine2=args[1];
       
         File inputFile = null;
         File inputFile2 = null;
         
     
         Scanner in = null;
         Scanner in2 = null;
        
        Parse_Tree p = new Parse_Tree_Imp();
       
    try {
        inputFile = new File (inputLine);
        in = new Scanner (inputFile);
       
        inputFile2 = new File (inputLine2);
       
        in2 = new Scanner (inputFile2);
       
      //tokenizer wrapped in parse tree gets all the tokens
        p.Collect_Tokens(in);
       
        p.Collect_Input_Data(in2);
       
    } catch (IOException e) {
        System.err.println(" Could not read input file: " + e);
    } finally {
      
              in.close();
            }
    
  
    	//p.print_tokens();
    	
    	//p.Output_Data_Table();
        //Parse program
        Parse_Prog(p);
       
        //p.Output_Parse_Tree();
        //Print program
        Print_Prog(p);
       
        Exec_Prog(p);
                          
    }
   
    //--------------------------------------------------------------------------------------
    //                                PUBLIC OPERATIONS
    //--------------------------------------------------------------------------------------
       
     public static void error(String message, boolean fatal) {
            System.err.println(message);
            if (fatal) {
                System.exit(1);
            }
        }
     
    //--------------------------------------------------------------------------------------
    //                                     PRIVATE OPERATIONS
    //--------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------
    //                                      PARSE OPERATIONS
    //--------------------------------------------------------------------------------------
     
     
     /**
   * Checks whether a token is a starting token of any of the statements 
       *
       * @param token
       *            a token that is checked

   */

     private static boolean Stmt_Token(int token){
         
        return (token == 32 || token == 5 || token == 8|| token == 10 || token==11);
     }
     
     /**
      * Checks whether a token is a comparison token 
          *
          * @param token
          *            a token that is checked

      */   
     private static boolean Comp_Op(int token)
     {
         return (token == 30 || token == 29 || token == 28|| token == 27 || token==26|| token==25);
     }
     
     /**
      * Parses the program nonterminal by making  a call to parsing operations that parse the 
      * nonterminal possible substitutions. 
          *
          * @param Parse_Tree
          *            a Parse_Tree object that is going to represent a parse_tree of the program
      */   
     
    private static void Parse_Prog(Parse_Tree p)
    {
        // cursor variable
        int curs = p.getCursor();
        
       //add to the table first row
        p.Insert_First();
        //p.add(new int[5]);
       
       // System.out.println("cursor is "+curs);
        //if the token is not "program", print an error message and stop program execution
        if (p.getToken()!=1)
        {
            error ("A token \"program\" is expected!",true);
        }
        else
        {
           
            p.setNTNo(1);
            //move cursor to the future position of the left child
            p.setAltNo(1);
           
            p.createLB();
           
           // System.out.print("curs is "+ p.getCursor());
            p.goDownLB();
            
            Parse_Decl_Seq(p);
            //go to the initial cursor of the
            p.goUp(curs);
           
         
           
            //check if the next token is "begin"
            
            if (p.getToken()!=2)
            {
                error ("A token \"begin\" is expected!",true);
                
            }
           
            else
            {
            	
                p.createMB();
               
                p.goDownMB();
               
                Parse_Stmt_Seq(p);
                
               
                p.goUp(curs);
            }
         
           int tok = p.getToken();
          
                if (tok!=3)
                {
                    error ("A token \"end\" is expected!",true);
                }
           
            if (p.TokensLeft()!=0)
            {
                error ("There should be no tokens left!",true);
            }
        }
       
        }
        
    
    
    
    /**
     * Parses the Decl_Seq nonterminal by making  a call to parsing operations that parse the 
     * nonterminal possible substitutions. 
         *
         * @param Parse_Tree
         *            a Parse_Tree object that is going to represent a parse_tree of the program
     */   
    
        private static void Parse_Decl_Seq(Parse_Tree p)
        {
       
            int curs = p.getCursor();
           
            p.setNTNo(2);
       
            p.createLB();
            
            p.goDownLB();
           
            Parse_Decl(p);
           
            p.goUp(curs);
           
            if (p.NextToken()==4)
            {     
                p.setAltNo(2);
               
                p.createMB();
               
                p.goDownMB();
               
                Parse_Decl_Seq (p);
               
                p.goUp(curs);
            }
           
            else
            {
                p.setAltNo(1);
            }
       
           
        }
        /**
         * Parses the Decl nonterminal by making  a call to parsing operations that parse the 
         * nonterminal possible substitutions. 
             *
             * @param Parse_Tree
             *            a Parse_Tree object that is going to represent a parse_tree of the program
         */   
        
        private static void Parse_Decl(Parse_Tree p)
        {
           
        int curs = p.getCursor();
       
        int token = p.getToken();

        if (token!=4)
        {
            error ("Token \"int\" is expected!", true);
        }
        else
        {
            p.setNTNo(4);
               
            p.setAltNo(1);
               
            p.createLB();
               
            p.goDownLB();
               
            //System.out.print(p.getToken());
            Parse_Id_List(p);   
               
            p.goUp(curs);
        }   
        //check if a token is ";"
       token = p.getToken();
   
        if (token!=12)
        {
            error ("Token';' is expected!", true);
        }
                   
   }                                                                   
       
        /**
         * Parses the Id_List nonterminal by making  a call to parsing operations that parse the 
         * nonterminal possible substitutions. 
             *
             * @param Parse_Tree
             *            a Parse_Tree object that is going to represent a parse_tree of the program
         */   
        
    private static void Parse_Id_List (Parse_Tree p){
       
        int curs = p.getCursor();
        
                p.setNTNo(5);
               
                p.createLB();
               
                p.goDownLB();
               
                Parse_Id(p);   
               
                p.goUp(curs);
           
             if (p.NextToken()==13)
            {
                 p.getToken();
                 
                 p.setAltNo(2);
                 
                p.createMB();
               
                p.goDownMB();
               
                Parse_Id_List(p);
               
                p.goUp(curs);
            }
             else
             {
                 p.setAltNo(1);
             }
    }
    
    
    /**
     * Parses the id nonterminal, puts Id in the declared ID_Table, and puts the index of the position of the 
     * id in the ID table in the row (row that represent Id nonterminal) at position 2
         *
         * @param Parse_Tree
         *            a Parse_Tree object that is going to represent a parse_tree of the program
     */   
    
   
     private static void Parse_Id (Parse_Tree p)
     {
         int curs = p.getCursor();
         
         p.setNTNo(18);
    
         if (p.NextToken()!=32)
         {
             error ("Identifier token is expected!", true);
         }
         else
         {
             //get a String value of the identifier
             String identifier = p.getIDVal();
             
            
    
             //put identifier in the declared ID table
             p.putin_Decl_ID_Table (identifier);
             
             //put an index of the location of the identifier in the ID row
             p.putIDindex(identifier);
         // p.Output_Row_Of_Parse_Tree(4);
             
         }
     }
    
     
     /**
      * Parses the Stmt_Seq nonterminal by making  a call to parsing operations that parse the 
      * nonterminal possible substitutions. 
          *
          * @param Parse_Tree
          *            a Parse_Tree object that is going to represent a parse_tree of the program
      */   
     private static void Parse_Stmt_Seq (Parse_Tree p)
     {
             int curs = p.getCursor();
      
            p.setNTNo(3);
       
            p.createLB();
           
            p.goDownLB();
           
            Parse_Stmt(p);
           
            p.goUp(curs);
           
            int token = p.NextToken();
           
            if (Stmt_Token(token))
            {
               
                p.setAltNo(2);
               
                p.createMB();
               
                p.goDownMB();
               
                Parse_Stmt_Seq (p);
               
                p.goUp(curs);
            }
           
            else
            {
             
                p.setAltNo(1);
               
            }
         
     }
     
     /**
      * Parses the Stmt nonterminal by making  a call to parsing operations that parse the 
      * nonterminal possible substitutions. 
          *
          * @param Parse_Tree
          *            a Parse_Tree object that is going to represent a parse_tree of the program
      */  
     private static void Parse_Stmt(Parse_Tree p)
     {
         
             int curs = p.getCursor();
           
            p.setNTNo(6);
   
            int token = p.NextToken();
       
            if (token==32)
            {
               
                p.setAltNo(1);
               
                p.createLB();
               
                p.goDownLB();
               
                Parse_Assign(p);
               
                p.goUp(curs);
            }
           
            else if (token==5)
            {
                p.setAltNo(2);
               
                p.createLB();
               
                p.goDownLB();
               
                Parse_If(p);
               
                p.goUp(curs);
            }
           
            else if (token==8)
            {
                p.setAltNo(3);
               
                p.createLB();
               
                p.goDownLB();
               
                Parse_While(p);
               
                p.goUp(curs);
            }
           
            else if (token==10)
            {
                p.setAltNo(4);
               
                p.createLB();
               
                p.goDownLB();
               
                Parse_Input(p);
               
                p.goUp(curs);
            }
           
            else if (token==11)
            {
                p.setAltNo(5);
               
                p.createLB();
               
                p.goDownLB();
               
                Parse_Output(p);
               
                p.goUp(curs);
            }
           
            else
            {
                error (" Token \"if\" or \"while\" or \"read\" or \"write\" or identifier is expected!", true);
            }
           
     }
     /**
      * Parses the Assign nonterminal by making  a call to parsing operations that parse the 
      * nonterminal possible substitutions. 
          *
          * @param Parse_Tree
          *            a Parse_Tree object that is going to represent a parse_tree of the program
      */  
             private static void Parse_Assign(Parse_Tree p)
             {
                    int curs = p.getCursor();
                   
                    p.setNTNo(7);
                   
                    p.setAltNo(1);
                   
                    p.createLB();
                   
                    p.goDownLB();
                   
                    Parse_Declared_Id(p);
                   
                    p.goUp(curs);
                   
                    int token = p.getToken();
                    
                    if (token!=14)
                    {
                        error("Token \"=\" is expected!", true);
                    }
                   
                    else
                    {
                        p.createMB();
                   
                        p.goDownMB();
                   
                        Parse_Exp(p);
                   
                        p.goUp(curs);
                   
                        if (p.getToken()!=12)
                        {
                        	error ("Token \";\" is expected!", true);
                        }
                    }  
             }
           
             /**
              * Parses the If nonterminal by making  a call to parsing operations that parse the 
              * nonterminal possible substitutions. 
                  *
                  * @param Parse_Tree
                  *            a Parse_Tree object that is going to represent a parse_tree of the program
              */  
             private static void Parse_If (Parse_Tree p)
             {
              int token = p.getToken();
              
                if (token!= 5)
                {
                    error ("Token \"if\" is expected!", true);
                }
                else
                {
                    int curs = p.getCursor();
                 
                    p.setNTNo(8);
                 
                    p.createLB();
                 
                    p.goDownLB();
                 
                    Parse_Cond(p);
                 
                    p.goUp(curs);
                 
                 if (p.getToken()!=6)
                 {
                     error ("Token \"then\" is expected!",true);
                            
                 }
                 
                 else
                 {
                     p.createMB();
                     
                     p.goDownMB();
                     
                     Parse_Stmt_Seq(p);
                     
                     p.goUp(curs);
                 }
                 
                 token = p.getToken();
              
                 if (token==3)
                 {
                     p.setAltNo(1);
                     //check if next token is ";"
                     
                      token = p.getToken();
                    
                     if (token!=12)
                     {
                         error ("Token \";\" is expected!", true);
                     }     
                 }
                 
                 //check if token is "else". If yes, then it is "if then stat else stat"
                 else if (token==7)
                 {
                     p.setAltNo(2);
                     
                     p.createRB();
                     
                     p.goDownRB();
                     
                     Parse_Stmt_Seq(p);
                     
                     p.goUp(curs);
                     
                     token = p.getToken();
                     
                     if (token!=3)
                     {
                         error ("Token \"end\" is expected!", true);
                            
                     }
                     
                     if (p.getToken()!=12)
                     {
                         error ("Token \";\" is expected!", true);
                     }
                 }
                 
                 else
                 {
                     error ("Token \"end\" or \"else\" is expected!", true);
                 }
              }
       }
             /**
              * Parses While nonterminal by making  a call to parsing operations that parse the 
              * nonterminal possible substitutions. 
                  *
                  * @param Parse_Tree
                  *            a Parse_Tree object that is going to represent a parse_tree of the program
              */  
             
                 private static void Parse_While (Parse_Tree p)
                 {
                     
                    int curs = p.getCursor();
                     
                    if (p.getToken()!=8)
                     {
                         error ("Token \"while\" is expected!", true);
                     }
               
                     else
                     {
                         p.setNTNo(9);
                   
                         p.setAltNo(1);
                   
                         p.createLB();
                   
                         p.goDownLB();
                   
                         Parse_Cond(p);
                   
                         p.goUp(curs);
                         
                         if (p.getToken()!= 9)
                         {
                       
                             error ("Token \"loop\" is expected!", true);   
                         }
                   
                         else
                         {
                             p.createMB();
                       
                             p.goDownMB();
                       
                             Parse_Stmt_Seq(p);
                       
                             p.goUp(curs);
                         }
                   
                         if (p.getToken()!=3)
                         {
                             error ("Token \"end\" is expected!", true);
                            
                         }
                     
                         if (p.getToken()!=12)
                         {
                             error ("Token \";\" is expected!", true);
                         }
                    
                     }
             }    
                 
                 
  /**
   * Parses the Input nonterminal by making  a call to parsing operations that parse the 
   * nonterminal possible substitutions. 
   *
   * @param Parse_Tree
   *            a Parse_Tree object that is going to represent a parse_tree of the program
   */  
             private static void Parse_Input (Parse_Tree p)
             
             {
                int curs = p.getCursor();
               
                if (p.getToken()!=10)
                {
                    error ("Token \"read\" is expected!", true);
                }
                else
                {
                   
                 p.setNTNo(10);
                   
                 p.setAltNo(1);
                 
                 p.createLB();
                 
                 p.goDownLB();
                 
                 Parse_Declared_Id_List (p);
                 
                 p.goUp(curs);
                 
                 if (p.getToken()!=12)
                 {
                     error ("Token \";\" is expected!", true);
                 }
                }
             }
    
 /**
  * Parses the Output nonterminal by making  a call to parsing operations that parse the 
  * nonterminal possible substitutions. 
  *
  * @param Parse_Tree
                  *            a Parse_Tree object that is going to represent a parse_tree of the program
              */     
             private static void Parse_Output (Parse_Tree p)
             
             {
                 int curs = p.getCursor();
             
                 if(p.getToken()!=11)
                 {
                     error ("Token \"write\" is expected!", true);
                 }
                 
                 else
                 {     
               
                     p.setNTNo(11);
                   
                     p.setAltNo(1);
                 
                     p.createLB();
                 
                     p.goDownLB();
                 
                     Parse_Declared_Id_List (p);
                 
                     p.goUp(curs);
                 
                     if (p.getToken()!=12)
                     {
                         error ("Token \";\" is expected!", true);
                     }
           
                 }
             }
/**
* Parses the DEclared_Id_List nonterminal by making  a call to parsing operations that parse the 
* nonterminal possible substitutions. Checks whether id_list was declared before
*
* @param Parse_Tree
*            a Parse_Tree object that is going to represent a parse_tree of the program
*/           
             private static void Parse_Declared_Id_List (Parse_Tree p)
             {
            	 
            	  int curs = p.getCursor();
                  
                  p.setNTNo(5);
                 
                  p.createLB();
                 
                  p.goDownLB();
                 
                  Parse_Declared_Id(p);   
                  
                  p.goUp(curs);
             
               if (p.NextToken()==13)
              {
                   p.getToken();
                   
                   p.setAltNo(2);
                   
                  p.createMB();
                 
                  p.goDownMB();
                 
                  Parse_Declared_Id_List(p);
                 
                  p.goUp(curs);
              }
               else
               {
                   p.setAltNo(1);
               }
      }	 
           
             	/**
             	 * Parses the Declared_Id nonterminal by making  a call to parsing operations that parse the 
             	 * nonterminal possible substitutions. Checks whether id was declared before
             	 *
             	 * @param Parse_Tree
             	 *           a Parse_Tree object that is going to represent a parse_tree of the program
             	 */                     
             
             private static void Parse_Declared_Id (Parse_Tree p)
             {
                 p.setNTNo(18);
                 int token = p.NextToken();
                 
                 if (token!=32)
                 {
                     error ("Identifier token is expected!", true);
                 }
                
                 else
                 {
                    String identifier = p.getIDVal();
                     p.putIDindex(identifier);
                 }
                
             } 
             /**
              * Parses the Cond nonterminal by making  a call to parsing operations that parse the 
              * nonterminal possible substitutions. 
                  *
                  * @param Parse_Tree
                  *            a Parse_Tree object that is going to represent a parse_tree of the program
              */            
             public static void  Parse_Cond (Parse_Tree p)
             {
                 int curs = p.getCursor();
                 
                 p.setNTNo(12);
                 
                 int token = p.NextToken();
                
                         //check if starts with "(" to determine whether it is a condition
                         if (token==20)
                         {
                        	 
                             
                            p.setAltNo(1);
                           
                            p.createLB();
                           
                            p.goDownLB();
                           
                            Parse_Comp(p);
                           
                            p.goUp(curs);
                           
                         }
                 //check if a token is "!" 
                         else if (token==15)
                         {
                             //remove "!" token
                                int tok = p.getToken();
                             
                                p.setAltNo(2);
                               
                                p.createLB();
                               
                                p.goDownLB();
                               
                                Parse_Cond(p);
                               
                                p.goUp(curs);
                         }
                         //check if a token is "["
                         else if (token==16)
                         {
                             //skip token
                             int tok = p.getToken();
                               
                                p.createLB();
                               
                                p.goDownLB();
                               
                                Parse_Cond(p);
                               
                                p.goUp(curs);
                               
                                token = p.NextToken();
                        //check if a token is "&&". If yes, it is a 3rd alternative  
                           
                                if (token==18)
                                   
                                {
                                	token = p.getToken();
                                   
                                	p.setAltNo(3);
                               
                                	p.createMB();
                               
                                	p.goDownMB();
                               
                                	Parse_Cond(p);
                               
                                	p.goUp(curs);
                               
                                }
                               //check if a token is "||". If yes, it is a 4th alternative
                                else if (token==19)
                                {
                                	token = p.getToken();
                                    p.setAltNo(4);
                                   
                                    p.createMB();
                                   
                                    p.goDownMB();
                                   
                                    Parse_Cond(p);
                                   
                                    p.goUp(curs);
                                }
                               //if the token is not "]" out put an error
                                if (p.getToken()!=17)
                                {
                                    error ("Token \"]\" is expected!", true);
                                }
                         }
                         //if not either of allowed tokens, output an error
                         else
                         {
                             error (" Token \"[\" or \"(\" or \"!\" is expected!", true);
                         }
                 
                 
             }
    
             
             /**
              * Parses the Comp nonterminal by making  a call to parsing operations that parse the 
              * nonterminal possible substitutions. 
                  *
                  * @param Parse_Tree
                  *            a Parse_Tree object that is going to represent a parse_tree of the program
              */
     private static void Parse_Comp (Parse_Tree p)
     {
         int curs = p.getCursor();
        
        //check if a token is "(" 
       int token =  p.getToken();
       
   if (token!=20)
  {
     error("Token \"(\" is required!", true);
  }
   else 
  {
     p.setNTNo(13);
        
     p.setAltNo(1);
          //p.Output_Row_Of_Parse_Tree(17);
     p.createLB();
       
     p.goDownLB();
     
     Parse_Op(p);
       
     p.goUp(curs);
       
     p.createMB();
       
     p.goDownMB();
       
     Parse_Comp_Op(p);
       
     p.goUp(curs);
       
     p.createRB();
       
     p.goDownRB();
       
    
     Parse_Op(p);
   
     p.goUp(curs);
      //check if it is a ")" token 
     	if (p.getToken()!=21)
     	{
     		error ("Token ')' is expected!", true);   
         }
      }	
     } 
   private static void Parse_Op( Parse_Tree p)
   {
      int curs = p.getCursor();
     //System.out.println("Cursor at Parse_Op is"+curs);
      int token=p.NextToken();
      p.setNTNo(16);
     //System.out.println("Token here is" + token);
    //if a token is ant , call Parse_Int
      if (token==31)
      {
      	
      	p.setAltNo(1);
      	 p.createLB();
           p.goDownLB();
          Parse_Int(p);
          p.goUp(curs);
      }
      //if a token is a ID, call Parse_ID
      else if (token==32)
         
      {
      	p.setAltNo(2);
      	 p.createLB();
           p.goDownLB();
          Parse_Declared_Id(p);
          p.goUp(curs);
         
      }
     //if token is "(" then cal to parse expression procedure
      else if (token==20)   
      {
      	token = p.getToken();
      	p.setAltNo(3);
      	
      	 p.createLB();
           
           p.goDownLB();
           
           Parse_Exp(p);
           
           p.goUp(curs);
      	//check ')'
         int tok = p.getToken();
     
          if (tok!=21)
          {
              error ("Token \")\" is expected!", true);
          }
      }
      else
      {
         
          error ("Id or integer or \")\" token is expected!",true);   
      }
            
   } 
   
   /**
    * Parses the Comp_op nonterminal. Puts the value of the comp_op in the row of comp_op nonterminal 
        *
        * @param Parse_Tree
        *            a Parse_Tree object that is going to represent a parse_tree of the program
    */
   
  private static void Parse_Comp_Op (Parse_Tree p)
   {
       p.setNTNo(17);
       
       int curs = p.getCursor();
       
       int token = p.NextToken();
    
       if (Comp_Op(token))
       {
           int comp_op = p.getToken();
           p.put_terminal_INrow (comp_op);
       }
       else
       {
           error ("Comparison token is expected!", true);
       }
   }
	   
  /**
   * Parses the Expression nonterminal by making  a call to parsing operations that parse the 
   * nonterminal possible substitutions. 
       *
       * @param Parse_Tree
       *            a Parse_Tree object that is going to represent a parse_tree of the program
   */   
    
     private static void Parse_Exp ( Parse_Tree p)
     {
    
         int curs = p.getCursor();
         
         p.setNTNo(14);
         
         p.createLB();
         
         p.goDownLB();
         
         Parse_Factor(p);
         
         p.goUp(curs);
         
         int token = p.NextToken();
        
         if (token == 22)
         {
             //skip token '+'
            int tok = p.getToken();
             
             p.setAltNo(2);
             
             p.createMB();
             
             p.goDownMB();
             
             Parse_Exp(p);
             
             p.goUp(curs);
                  
         }
            //checks if a token is '-'
         else  if (token == 23)
         {
          
        	 //remove '-' token
        	 
        	 int tok = p.getToken();
        	 
              p.setAltNo(3);
             
             p.createMB();
             
             p.goDownMB();
             
             Parse_Exp(p);
             
             p.goUp(curs);
             
         }
         else
         {
             p.setAltNo(1);
         }
         
    }
    
     /**
      * Parses the Factor nonterminal by making  a call to parsing operations that parse the 
      * nonterminal possible substitutions. 
          *
          * @param Parse_Tree
          *            a Parse_Tree object that is going to represent a parse_tree of the program
      */  
     private static void Parse_Factor (Parse_Tree p)
     {
         int curs = p.getCursor();
         
         p.setNTNo(15);
         
         p.createLB();
         
         p.goDownLB();
         
         Parse_Op(p);
         
         p.goUp(curs);
         
         int token = p.NextToken();
        
         
         if (token == 24)
         {
             p.setAltNo(2);
             //skip a token
             int tok = p.getToken();
             
             p.createMB();
             
             p.goDownMB();
             
             Parse_Factor (p);
             
             p.goUp(curs);
             
         }
         
         else
         {
             p.setAltNo(1);
         }
   }
    
    
     /**
      * Parses the Integer nonterminal. Puts the value of the integer in the node of the Integer nonterminal
          *
          * @param Parse_Tree
          *            a Parse_Tree object that is going to represent a parse_tree of the program
      */
    private static void Parse_Int(Parse_Tree p)
    {

         p.setNTNo(20);
         
         int integer = p.NextToken();
         //if a token is not an integer token, output an error message
         if (integer!=31)
         {
             error("Integer is expected!", true);
         }
         else
         {
            int intvalue = p.getIntVal();
             
             p.put_terminal_INrow (intvalue);
         }
    }
  
  
   //--------------------------------------------------------------------------------------------------
   //                                              PRINT OPERATIONS
   //--------------------------------------------------------------------------------------------------
  
    
    /**
     * Prints the Prog nonterminal by making  a call to printing operations that print the 
     * nonterminal possible substitutions. 
         *
         * @param Parse_Tree
         *            a Parse_Tree object that represents a parse_tree of the program
     */
  
   private static void Print_Prog(Parse_Tree p)
   {
      
   int curs = p.getCursor();
  
   System.out.println("program");
  
   p.goDownLB();
  

   Print_Decl_Seq(p, idnt_factor+1);
  
   p.goUp(curs);
  
   System.out.println("begin");
  
   p.goDownMB();
  
   Print_Stmt_Seq(p, idnt_factor+1);
   
   p.goUp(curs);
   
   System.out.println("end");
  
 
   }
      
   
   /**
    * Prints the Decl_Seq nonterminal by making  a call to printing operations that print the 
    * nonterminal possible substitutions. 
        *
        * @param Parse_Tree
        *            a Parse_Tree object that represents a parse_tree of the program
    */
  private static void Print_Decl_Seq(Parse_Tree p, int idnt_factor)
   {
	  
    int curs = p.getCursor();
 
    p.goDownLB();
   
    Print_Decl(p, idnt_factor);
   
    p.goUp(curs);
   
    int alt = p.currAlt();
   
    if (alt==2)
    {
        p.goDownMB();
        Print_Decl_Seq(p, idnt_factor);
        p.goUp(curs);
    }
   }
  /**
   * Prints the Decl nonterminal by making  a call to printing operations that print the 
   * nonterminal possible substitutions. 
       *
       * @param Parse_Tree
       *            a Parse_Tree object that represents a parse_tree of the program
   */
   private static void Print_Decl (Parse_Tree p, int idnt_factor)
   {
	  
	  
       int curs = p.getCursor();
      
       Print_Idnt(idnt_factor);
      
       System.out.print("int ");
     
       p.goDownLB();
      
       Print_Id_List(p);
      
       p.goUp(curs);
      
       System.out.println(";");
     
   }
   
   
   /**
    * Prints the Id_List nonterminal by making  a call to printing operations that print the 
    * nonterminal possible substitutions. 
        *
        * @param Parse_Tree
        *            a Parse_Tree object that represents a parse_tree of the program
    */
  private static void Print_Id_List(Parse_Tree p)
  {
      int curs = p.getCursor();
     
      p.goDownLB();
     
      Print_Id(p);
     
      p.goUp(curs);
     
      int alt = p.currAlt();
      
      if (alt==2)
      {
          System.out.print(", ");
          
          p.goDownMB();
         
          Print_Id_List(p);
         
          p.goUp(curs);
      }
  }
  /**
   * Prints the id nonterminal. Gets a value of the id from declared ids table and prints it 
       *
       * @param Parse_Tree
       *            a Parse_Tree object that represents a parse_tree of the program
   */
  private static void Print_Id(Parse_Tree p)
  {
	  
	  String id = p.getID_From_Table(); 
   
    System.out.print(id);
  }
 
  /**
   * Prints the Stmt_Seq nonterminal by making  a call to printing operations that print the 
   * nonterminal possible substitutions. 
       *
       * @param Parse_Tree
       *            a Parse_Tree object that represents a parse_tree of the program
   */
  private static void Print_Stmt_Seq(Parse_Tree p, int idnt_factor)
  {
      int curs = p.getCursor();
     
      p.goDownLB();
     
      Print_Stmt (p, idnt_factor);
     
      p.goUp(curs);
     
      int alt = p.currAlt();
     
      if (alt==2)
      {
         p.goDownMB();
         
         Print_Stmt_Seq (p, idnt_factor);
         
         p.goUp(curs);
      }
  }
  /**
   * Prints the Stmt nonterminal by making  a call to printing operations that print the 
   * nonterminal possible substitutions. 
       *
       * @param Parse_Tree
       *            a Parse_Tree object that represents a parse_tree of the program
   */
  private static void Print_Stmt (Parse_Tree p, int idnt_factor)
  {
     int curs = p.getCursor();
     
     int alt = p.currAlt();
     
     p.goDownLB();
     
     if (alt==1)
     {
         Print_Assign(p, idnt_factor);
     }
     else if (alt==2)
     {
         Print_If(p, idnt_factor);
     }
     else if (alt==3)
     {
         Print_While(p, idnt_factor);
     }
     else if (alt==4)
     {
         Print_Input(p, idnt_factor);
     }
     else if (alt==5)
     {
         Print_Output(p, idnt_factor);
     }
    p.goUp(curs);
  }
  /**
   * Prints the Assign nonterminal by making  a call to printing operations that print the 
   * nonterminal possible substitutions. 
       *
       * @param Parse_Tree
       *            a Parse_Tree object that represents a parse_tree of the program
   */
  private static void Print_Assign (Parse_Tree p, int idnt_factor)
  {
      int curs = p. getCursor();
     
      p.goDownLB();
      //print identation
      Print_Idnt(idnt_factor);
     
      Print_Id(p);
     
      p.goUp(curs);
     
      System.out.print(" = ");
     
      p.goDownMB ();
     
      Print_Exp (p);
     
      p.goUp(curs);
     
      System.out.println(";");
  }
  /**
   * Prints the If nonterminal by making  a call to printing operations that print the 
   * nonterminal possible substitutions. 
       *
       * @param Parse_Tree
       *            a Parse_Tree object that represents a parse_tree of the program
   */
  private static void Print_If (Parse_Tree p, int idnt_factor)
  {
    int curs = p.getCursor();
    
    int alt = p.currAlt();
    
    
    if (alt == 1)
    {    
    	Print_Idnt (idnt_factor);
   
    	System.out.print ("if ");
   
    	p.goDownLB();
   
    	Print_Cond(p);
   
    	p.goUp(curs);
   
    	System.out.println(" then");
    	
    	p.goDownMB();
   
    	Print_Stmt_Seq(p, idnt_factor+1);
    	
    	Print_Idnt (idnt_factor);
   
    	System.out.println("end;");
   
    	p.goUp(curs);
  }
    else if (alt==2)
    {
    	Print_Idnt (idnt_factor);
    	   
    	System.out.print ("if ");
   
    	p.goDownLB();
   
    	Print_Cond(p);
   
    	p.goUp(curs);
    	
    	System.out.println(" then");
   
    	p.goDownMB();
   
    	Print_Stmt_Seq(p, idnt_factor+1);
    	
    	p.goUp(curs);
    	
    	Print_Idnt (idnt_factor);
    	
    	System.out.println ("else ");
    	
    	p.goDownRB();
    	   
    	Print_Stmt_Seq(p, idnt_factor+1);
    	
    	p.goUp(curs);
    	
    	Print_Idnt (idnt_factor);
   
    	System.out.println("end; ");
   
    	p.goUp(curs);	
    	
    }
   }
  /**
   * Prints the While nonterminal by making  a call to printing operations that print the 
   * nonterminal possible substitutions. 
       *
       * @param Parse_Tree
       *            a Parse_Tree object that represents a parse_tree of the program
   */
  private static void Print_While (Parse_Tree p, int idnt_factor)
  {
    int curs = p.getCursor();
    
    Print_Idnt (idnt_factor);
   
    System.out.print("while ");
   
    p.goDownLB();
   
    Print_Cond(p);
   
    p.goUp(curs);
       
    System.out.println(" loop");
   
    p.goDownMB();
   
    Print_Stmt_Seq (p, idnt_factor+1);
   
    p.goUp(curs);
    
    Print_Idnt (idnt_factor);
   
    System.out.println("end; ");
   
 }
  /**
   * Prints the Input nonterminal by making  a call to printing operations that print the 
   * nonterminal possible substitutions. 
       *
       * @param Parse_Tree
       *            a Parse_Tree object that represents a parse_tree of the program
   */
  private static void Print_Input (Parse_Tree p, int idnt_factor)
  {
      int curs = p.getCursor();
      
      Print_Idnt (idnt_factor);
     
      System.out.print("read ");
     
      p.goDownLB();
     
      Print_Id_List(p);
      
      System.out.println(";");
     
      p.goUp(curs);
  }
  /**
   * Prints the Output nonterminal by making  a call to printing operations that print the 
   * nonterminal possible substitutions. 
       *
       * @param Parse_Tree
       *            a Parse_Tree object that represents a parse_tree of the program
   */
  private static void Print_Output (Parse_Tree p, int idnt_factor)
  {
      int curs = p.getCursor();
      
      Print_Idnt (idnt_factor);
     
      System.out.print("write ");
     
      p.goDownLB();
     
      Print_Id_List(p);
      
      System.out.println(";");
     
      p.goUp(curs);
     
  }
  /**
   * Prints the Cond nonterminal by making  a call to printing operations that print the 
   * nonterminal possible substitutions. 
       *
       * @param Parse_Tree
       *            a Parse_Tree object that represents a parse_tree of the program
   */
  private static void Print_Cond (Parse_Tree p)
  {
      int curs = p.getCursor();
     
      int alt = p.currAlt();
     
      if (alt==1)
      {
         
          p.goDownLB();
          Print_Comp(p);
          p.goUp(curs);
      }
      else if (alt==2)
      {
          System.out.print("!");
          p.goDownLB();
          Print_Cond(p);
          p.goUp(curs);
      }
     
      else if (alt==3)
      {
          System.out.print("[");
          p.goDownLB();
          Print_Cond(p);
          p.goUp(curs);
          System.out.print(" && ");
          p.goDownMB();
          Print_Cond(p);
          p.goUp(curs);
          System.out.print("] ");
           
      }
     
      else if (alt==4)
      {
          System.out.print("[");
          p.goDownLB();
          Print_Cond(p);
          p.goUp(curs);
          System.out.print(" || ");
          p.goDownMB();
          Print_Cond(p);
          p.goUp(curs);
          System.out.print("] ");
      }
    }
  
  
  /**
   * Prints the Comp nonterminal by making  a call to printing operations that print the 
   * nonterminal possible substitutions. 
       *
       * @param Parse_Tree
       *            a Parse_Tree object that represents a parse_tree of the program
   */
  private static void Print_Comp(Parse_Tree p)
  {
	
      int curs = p.getCursor();
     
      System.out.print("(");
     
      p.goDownLB();
     
      Print_Op(p);
     
      p.goUp(curs);
      
      p.goDownMB();
     
      Print_Comp_Op(p);
      
      p.goUp(curs);
     
      p.goDownRB();
     
      Print_Op(p);
     
      p.goUp(curs);
     
      System.out.print(")");
        
  }
  /**
   * Prints the Op nonterminal by making  a call to printing operations that print the 
   * nonterminal possible substitutions. 
       *
       * @param Parse_Tree
       *            a Parse_Tree object that represents a parse_tree of the program
   */
  private static void Print_Op(Parse_Tree p)
  {
      int curs= p.getCursor();
     
      int alt = p.currAlt();
 
      if (alt==1)
      {
          p.goDownLB();
          
          Print_Int(p);
          
          p.goUp(curs);
      }
      else if (alt==2)
      {
     	
          p.goDownLB();
          
          Print_Id(p);
          
          p.goUp(curs);
      }     
  
  
      else if (alt==3)
      {
     	 p.goDownLB();

     	 System.out.print ("(");

     	 Print_Exp(p);

     	 System.out.print(")");
       }
  }
  /**
   * Prints the comparison sign  
       *
       * @param Parse_Tree
       *            a Parse_Tree object that represents a parse_tree of the program
   */
  private static void Print_Comp_Op(Parse_Tree p)
  {
 	 
     int curs = p.getCursor();
    
     
     int comp_op = p.get_terminal();
    
     if (comp_op==25)
     {
         System.out.print(" != ");   
     }
    
     else if (comp_op==26)
     {
         System.out.print(" == ");   
     }
     else if (comp_op==27)
     {
         System.out.print (" < ");
     }
    
     else if (comp_op==28)
     {
         System.out.print (" > ");
     }
     else if (comp_op==29)
     {
         System.out.print(" <= ");
     }
     else if (comp_op==30)
     {
         System.out.print(" >= ");
     }
    
  }
  
  /**
   * Prints the Expression nonterminal by making  a call to printing operations that print the 
   * nonterminal possible substitutions. 
       *
       * @param Parse_Tree
       *            a Parse_Tree object that represents a parse_tree of the program
   */
  private static void Print_Exp (Parse_Tree p)
  {
     int curs = p.getCursor();
     
     int alt = p.currAlt();
     
     if (alt==1)
     {
        p.goDownLB();
       
        Print_Factor(p);
       
        p.goUp(curs);
     }
     
     else if (alt==2)
     {
            p.goDownLB();
           
            Print_Factor(p);
           
            p.goUp(curs);
           
            System.out.print(" + ");
           
            p.goDownMB();
           
            Print_Exp(p);
           
            p.goUp(curs);
     }
     else if (alt==3)
     {
             p.goDownLB();
           
            Print_Factor(p);
           
            p.goUp(curs);
           
            System.out.print(" - ");
           
            p.goDownMB();
           
            Print_Exp(p);
           
            p.goUp(curs);
     }
  }
 
  /**
   * Prints the Factor nonterminal by making  a call to printing operations that print the 
   * nonterminal possible substitutions. 
       *
       * @param Parse_Tree
       *            a Parse_Tree object that represents a parse_tree of the program
   */
         private static void Print_Factor(Parse_Tree p)
         {
             
             int curs = p.getCursor();
             
             int alt = p.currAlt();
             
             if (alt==1)
             {
                 p.goDownLB();
                 Print_Op(p);
                 p.goUp(curs);
                 
             }
             else if (alt==2)
             {
                 p.goDownLB();
                 Print_Op(p);
                 p.goUp(curs);
                 System.out.print(" * ");
                 p.goDownMB();
                 Print_Factor(p);
                 p.goUp(curs);
             }
             
       }
     
         
       
         /**
          * Prints the integer by getting it from the row of the integer nonterminal  
              *
              * @param Parse_Tree
              *            a Parse_Tree object that represents a parse_tree of the program
          */
            private static void Print_Int(Parse_Tree p)
            {
               
                int curs = p.getCursor();
               
                int Integer = p.get_terminal();
               
                System.out.print(Integer);
              
            }
           
            
            
            /**
             * Prints the number of tabs specified in the parameter
                 *
                 * @param ident_factor
                 *           integer object representing the number of tabs printed to the console
             */
           private static void Print_Idnt (int ident_factor)
           {   
        	   int number = 0;
        	   while (number < ident_factor)
        	   {
        		   
        		   System.out.print ("	");
        		   number++;
        	   }
           }
//-----------------------------------------------------------------------------------------------
//                                        EXECUTE_OPERATIONS
//-----------------------------------------------------------------------------------------------
    
           
           
           
           
           
           /**
            * Executes the program nonterminal by making  a call to executing operations that executes the 
            * nonterminal possible substitutions. 
                *
                * @param Parse_Tree
                *            a Parse_Tree object that represents a parse_tree of the program
            */
           private static void Exec_Prog(Parse_Tree p)
           {
    
               int curs = p. getCursor();
              
               p.goDownMB();
              
               Exec_Stmt_Seq(p);
              
               p.goUp(curs);
           }
              
           
           /**
            * Executes the Stmt_Seq nonterminals by making  a call to executing operations that executes the 
            * nonterminal possible substitutions. 
                *
                * @param Parse_Tree
                *            a Parse_Tree object that represents a parse_tree of the program
            */
           private static void Exec_Stmt_Seq(Parse_Tree p)
              {
                  int curs = p.getCursor();
                 
                  p.goDownLB();
                 
                  Exec_Stmt (p);
                 
                  p.goUp(curs);
                 
                  int alt = p.currAlt();
                 
                  if (alt==2)
                  {
                     p.goDownMB();
                     
                     Exec_Stmt_Seq (p);
                     
                     p.goUp(curs);
                  }
              }
           /**
            * Executes the Stmt nonterminal by making  a call to executing operations that execute the 
            * nonterminal possible substitutions. 
                *
                * @param Parse_Tree
                *            a Parse_Tree object that represents a parse_tree of the program
            */
              private static void Exec_Stmt (Parse_Tree p)
              {
                 int curs = p.getCursor();
                 
                 int alt = p.currAlt();
                 
                 p.goDownLB();
                 
                 if (alt==1)
                 {
                     Exec_Assign(p);
                 }
                 else if (alt==2)
                 {
                     Exec_If(p);
                 }
                 else if (alt==3)
                 {
                     Exec_While(p);
                 }
                 else if (alt==4)
                 {
                     Exec_Input(p);
                 }
                 else if (alt==5)
                 {
                     Exec_Output(p);
                 }
                p.goUp(curs);
              }
              /**
               * Executes the Assign nonterminal by making  a call to executing operations that execute the 
               * nonterminal possible substitutions. 
                   *
                   * @param Parse_Tree
                   *            a Parse_Tree object that represents a parse_tree of the program
               */
              private static void Exec_Assign (Parse_Tree p)
              {
                  int curs = p. getCursor();
                 
                  p.goDownMB();
                 
                  int x = Eval_Exp(p);
                 
                  p.goUp(curs);
                 
                  p.goDownLB();
                 
                  String id = p.getID_From_Table(); ;
                 
                  p.goUp(curs);
                 
                  p.setIdVal(id, x);
              }
              /**
               * Executes the If nonterminal by making  a call to executing operations that execute the 
               * nonterminal possible substitutions. 
                   *
                   * @param Parse_Tree
                   *            a Parse_Tree object that represents a parse_tree of the program
               */
              private static void Exec_If (Parse_Tree p)
              {
                int curs = p.getCursor();
                
                p.goDownLB();
                
                boolean cond = Eval_Cond(p);
                
                p.goUp(curs);
               
                int alt = p.currAlt();
               
                 if (alt==1)
                 {
                	 if (cond)
                	 {
                		 p.goDownMB();
               
                		 Exec_Stmt_Seq(p);
                		 
                		 p.goUp(curs);
                	 }
                 }	 
                 else if (alt==2)
                 {
                	 if (cond)
                	 {
                		 p.goDownMB();
               
                		 Exec_Stmt_Seq(p);
                		 
                		 p.goUp(curs);
                	 }
                	 else
                	 {
                		 p.goDownRB();
                	
                		 Exec_Stmt_Seq(p);
               
                		 p.goUp(curs);
                	 }
                }
              } 
              
              
              
              /**
               * Executes the While nonterminal by making  a call to executing operations that execute the 
               * nonterminal possible substitutions. 
                   *
                   * @param Parse_Tree
                   *            a Parse_Tree object that represents a parse_tree of the program
               */
              private static void Exec_While (Parse_Tree p)
              {
                int curs = p.getCursor();
                
                boolean cond;
               
                p.goDownLB();
               
                cond = Eval_Cond(p);
               
                p.goUp(curs);
                   
               while (cond)
               {
            	   p.goDownMB();
               
            	   Exec_Stmt_Seq (p);
               
            	   p.goUp(curs);
            	   //evaluating condition
            	   p.goDownLB();
            	   
            	   cond = Eval_Cond(p);
            	   
            	   p.goUp(curs);
               }
             
               
             }
              /**
               * Executes the Input nonterminal by making  a call to executing operations that execute the 
               * nonterminal possible substitutions. 
                   *
                   * @param Parse_Tree
                   *            a Parse_Tree object that represents a parse_tree of the program
               */
              private static void Exec_Input (Parse_Tree p)
              {
                  int curs = p.getCursor();
                 
                  p.goDownLB();
                 
                 Read_Id_List(p);
                 
                  p.goUp(curs);
              }
             
              /**
               * Reads the id _list from inputdata file
                   *
                   * @param Parse_Tree
                   *            a Parse_Tree object that represents a parse_tree of the program
               */
              private static void Read_Id_List(Parse_Tree p)
              {
            	  int curs = p.getCursor();
                  
                  p.goDownLB();
                 
                  Read_Id(p);
                 
                  p.goUp(curs);
                 
                  int alt = p.currAlt();
                  
                  if (alt==2)
                  {
                      p.goDownMB();
                     
                     Read_Id_List(p);
                     
                      p.goUp(curs);
                  }
              }
              /**
               * Reads id from inputdata file
                   *
                   * @param Parse_Tree
                   *            a Parse_Tree object that represents a parse_tree of the program
               */   
             
              private static void Read_Id(Parse_Tree p)
              { 
                 String id = p.getID_From_Table();
                 
                 int inputvalue = p.getInputValue();
                 
                 p.setIdVal(id, inputvalue);
                 
                 
              }
              
              /**
               * The operation execute output nonterminal
                   *
                   * @param Parse_Tree
                   *            a Parse_Tree object that represents a parse_tree of the program
               */
              private static void Exec_Output (Parse_Tree p)
              {
                  int curs = p.getCursor();
                
                  p.goDownLB();
                 
                  Write_Id_List(p);
                 
                  p.goUp(curs);
                 
              }
              /**
               * Writes the id _list values to the console
                   *
                   * @param Parse_Tree
                   *            a Parse_Tree object that represents a parse_tree of the program
               */
              private static void Write_Id_List (Parse_Tree p)
              {
            	  
            	  int curs = p.getCursor();
                  
                  p.goDownLB();
                 
                  Write_Id(p);
                 
                  p.goUp(curs);
                 
                  int alt = p.currAlt();
                  
                  if (alt==2)
                  {
                      p.goDownMB();
                     
                     Write_Id_List(p);
                     
                      p.goUp(curs);
                  }  
            	  
            	  
              }
              
              /**
               * Writes the id  value to the console
                   *
                   * @param Parse_Tree
                   *            a Parse_Tree object that represents a parse_tree of the program
               */    
              private static void Write_Id(Parse_Tree p)
              { 
                 String id = p.getID_From_Table();
                 
                 int value = p.Get_Id_Value(id);
                 
               System.out.println(id+" = "+value);
                 
                 
              } 
              
              /**
               * Evaluates the condition nonterminal by making  a call to evaluating operations that evaluate the 
               * nonterminal possible substitutions. 
                   *
                   * @param Parse_Tree
                   *            a Parse_Tree object that represents a parse_tree of the program
               */
              private static boolean Eval_Cond (Parse_Tree p)
              {
                  int curs = p.getCursor();
                 
                  int alt = p.currAlt();
                  
                  boolean cond1, cond2;
                 
                  if (alt==1)
                  {
                     
                      p.goDownLB();
                      cond1 = Eval_Comp(p);
                      p.goUp(curs);
                      return cond1;
                  }
                  else if (alt==2)
                  { 
                    p.goDownLB();
                    cond1 = Eval_Cond(p);
                    p.goUp(curs);
                    return (!cond1);
                  }
                 
                  else if (alt==3)
                  {
                      p.goDownLB();
                      cond1 = Eval_Cond(p);
                      p.goUp(curs);
                      p.goDownMB();
                      cond2 = Eval_Cond(p);
                      p.goUp(curs);
                   return (cond1 && cond2);
                       
                  }
                 
                  else if (alt==4)
                  {
                      p.goDownLB();
                      cond1 = Eval_Cond(p);
                      p.goUp(curs);
                      p.goDownMB();
                      cond2 = Eval_Cond(p);
                      p.goUp(curs);
                  return (cond1||cond2);
                  }
                  else
                  {
                	  return false;
                  }
                }
              
              
              /**
               * Evaluates the comparison nonterminal by making  a call to evaluating operations that evaluate the 
               * nonterminal possible substitutions. 
                   *
                   * @param Parse_Tree
                   *            a Parse_Tree object that represents a parse_tree of the program
               */
              private static boolean Eval_Comp(Parse_Tree p)
              {
                  int curs = p.getCursor();
                 
                  int op1, op2, comp_op;
                 
                  p.goDownLB();
                 
                 op1 =  Eval_Op(p);
                 
                  p.goUp(curs);
                  
                  p.goDownMB();
                 
                  comp_op = p.get_terminal();
                  
                  p.goUp(curs);
                 
                  p.goDownRB();
                 
                 op2 = Eval_Op(p);
                 
                  p.goUp(curs);
                 
                 if (comp_op==25)
                 {
                	 return (op1!=op2);
                 }
                 else if (comp_op==26)
                 {
                	return (op1==op2);
                 }
                 else if (comp_op==27)
                 {
                	return (op1<op2);
                 }
                 else if (comp_op==28)
                 {
                	 return (op1>op2);
                 }
                 else if (comp_op==29)
                 {
                	 return (op1<=op2);
                 }
                 else if (comp_op==30)
                 {
                	 return (op1>=op2);
                 }
                 else 
                 {
                	 return false;
                 }
                 
              }
             

              /**
               * Evaluates the expression nonterminal by making  a call to evaluating operations that evaluate the 
               * nonterminal possible substitutions. 
                   *
                   * @param Parse_Tree
                   *            a Parse_Tree object that represents a parse_tree of the program
               */
              private static int Eval_Exp (Parse_Tree p)
              {
                 int curs = p.getCursor();
                 
                 int alt = p.currAlt();
                 
                 int factor, exp_val;
                 
                 if (alt==1)
                 {
                    p.goDownLB();
                   
                    factor = Eval_Factor(p);
                   
                    p.goUp(curs);
                   
                    return (factor);
                 }
                 
                 else if (alt==2)
                 {
                        p.goDownLB();
                       
                        factor = Eval_Factor(p);
                       
                        p.goUp(curs);
                       
                        p.goDownMB();
                       
                        exp_val = Eval_Exp(p);
                       
                        p.goUp(curs);
                       
                        return (factor+exp_val);
                 }
                 else if (alt==3)
                 {
                         p.goDownLB();
                       
                        factor = Eval_Factor(p);
                       
                        p.goUp(curs);
                       
                        p.goDownMB();
                       
                        exp_val = Eval_Exp(p);
                       
                        p.goUp(curs);
                       
                        return (factor-exp_val);
                 }
                 else
                 {
                	 return 0;
                 }
              }

              /**
               * Evaluates the factor nonterminal by making  a call to evaluating operations that evaluate the 
               * nonterminal possible substitutions. 
                   *
                   * @param Parse_Tree
                   *            a Parse_Tree object that represents a parse_tree of the program
               */ 
             
                     private static int Eval_Factor(Parse_Tree p)
                     {
                         
                         int curs = p.getCursor();
                         
                         int alt = p.currAlt();
                         
                         int op, factor;
                         
                         if (alt==1)
                         {
                             p.goDownLB();
                             op = Eval_Op(p);
                             p.goUp(curs);
                             return(op);
                             
                         }
                         else if (alt==2)
                         {
                             p.goDownLB();
                             op = Eval_Op(p);
                             p.goUp(curs);
                             p.goDownMB();
                             factor = Eval_Factor(p);
                             p.goUp(curs);
                             return(op*factor);
                         }
                         else
                         {
                        	 return 0;
                         }
                   }
                 
                     
                     private static int Eval_Op(Parse_Tree p)
                     {
                         int curs = p.getCursor();
                         
                         int integer =0;
                         
                         int alt = p.currAlt();
                         
                         if (alt==1)
                         {
                             p.goDownLB();
                             
                            integer = Get_Int(p);
                             
                             p.goUp(curs);
                         }
                         else if (alt==2)
                         {
                             p.goDownLB();
                             
                            String id = p.getID_From_Table();
                             
                            integer = p.Get_Id_Value(id);
                             
                             p.goUp(curs);
                         }     
                       
                     
                         else if (alt==3)
                         {
                         
                             p.goDownLB();
                             integer = Eval_Exp(p);
                         
                         }
                         return integer;
                     }
                     
                     

                     /**
                      * Returns the integer value of the nonterminal 
                          *
                          * @param Parse_Tree
                          *            a Parse_Tree object that represents a parse_tree of the program
                      */
                     
                        private static int Get_Int(Parse_Tree p)
                        {
                           
                            int curs = p.getCursor();
                           
                            int Integer = p.get_terminal();
                           
                            return (Integer);
                           
                        }
                       
                       
          
  }