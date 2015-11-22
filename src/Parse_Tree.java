//package Interpreter.src;

import java.util.Scanner;


/**  
 * Parse_Tree object represents a parse tree of the program. 
 *  
 *
 * <b>Model:</b>
 * <ul>
 * <li>{@code cursor} - Represents the value that points to the current row of the parse tree arraylist </li>
 * </ul>

 *  
 *
* @author Igor Tolkachev
*/


public interface Parse_Tree {
	
	
	
	/**
	 * Outputs the current size of the tokenizer
	 * 
	 */
	
	void Output_Tokenizer_Size();
	
	
    /**
         * Returns the value of alternative from the row of the Parse_Tree at the index of the current cursor 
         *
         * @param no parameters
         *           

     */


    int currAlt();
   
    /**
     * 
     * The operation reads the file line by line, gets tokens from each line and puts them in
     *  an arraylist object representing a container for tokens. In case if illegal token is encountered or "legal token" rules are 
     *  violated, the operation prints the message and stops execution of the program
     * 
     * @param input
     *           The scanner object representing the scanner that reads from the input file, which
     *            is used  to collect tokens 
     *
     */
    
    void Collect_Tokens (Scanner input);
 
    /**
     * 
     * The operation reads the input data file line by line, gets a string value from each line and puts it  
     *  an arraylist object representing the cotainer for input data
     * 
     * @param input
     *           The scanner object representing the scanner that reads from the input data file, which
     *            is used  to collect input data
     *
     */
    
    void Output_Data_Table();
    
    void Collect_Input_Data (Scanner input);
    
    /**
     * 
     * The operation inserts first row in the arraylist object Parsee_Tree_Rep representing the Parse_Tree
     * 
     * No parameters
     *
     */
    void Insert_First();
    /**
     * 
     * The operation prints the integer and string values of the tokens extracted from the file 
     * 
     * No parameters
     *
     */
    void print_tokens();
    
    
    /**
     * 
     * Outputs the row of the parse tree at the index specified in the parameter
     * 
     * No parameters
     *
     */
   
   void Output_Row_Of_Parse_Tree (int index_row);
   /**
    * 
    * Outputs the size of the parse tree
    * 
    * No parameters
    *
    */
    void Output_Parse_Tree_Size();
    /**
     * 
     * Outputs the parse tree
     * 
     * No parameters
     *
     */
    
    void Output_Parse_Tree();
    
    /**
     * 
     * The operation returns a string value of the Id 
     * 
     * No parameters
     *
     */
   
    String getIDVal();
    
    /**
     * 
     * The operation returns an Integer value of the integer token
     * 
     * No parameters
     *
     */
    
    int getIntVal();
   
    
    /**
     * 
     * The operation returns an the number of the tokens left in the
     *  arraylist that contains the tokens
     * 
     * No parameters
     *
     */
    
    int TokensLeft();
   
    /**
     * 
     * The operation returns an the string of the first token in the
     *  arraylist that contains the tokens.The token is not removed from the arraylist.
     * 
     * No parameters
     *
     */
    
    int NextToken();
    
    /**
     * 
     * The operation returns an the string of the first token in the
     *  arraylist that contains the tokens.The token is  removed from the Parse_Tree.
     * 
     * No parameters
     *
     */
    
    int getToken();
   
    /**
     * 
     * The operation moves cursor to the row of the index of the left 
     * child of the parent in the Parse_Tree
     * 
     * No parameters
     *
     */
    
    void goDownLB();
   
    /**
     * 
     * The operation moves cursor to the row of the index of the middle
     * child of the parent in the Parse_Tree
     * 
     * No parameters
     *
     */
    void goDownMB();
   
    
    /**
     * 
     * The operation moves cursor to the row of the index of the right
     * child of the parent in the Parse_Tree
     * 
     * No parameters
     *
     */
    void goDownRB();
    
    /**
     * 
     * The operation returns the terminal (the value at the index [1]) 
     * of the row (the nonterminal that can be replaced only with a terminal)
     * at the given cursor. 
     * 
     * No parameters
     *
     */
   
    int get_terminal();
   
    
    /**
     * 
     * The operation returns the string value of the id at the index [0] of the ID_Table
     * No parameters
     *
     */
    String getID_From_Table();
   
    
    /**
     * Sets the value of production number at  to the row of the Parse_Tree at the index of the current cursor 
     *
     * @param no parameters
     *           

 */

    
    void setNTNo(int n_of_pr);
    
    
    /**
     * Sets the value of alternative number to the row of the Parse_Tree at the index of the current cursor 
     *
     * @param no parameters
     *           

 */
   
    void setAltNo(int alt_n);
    
    /**
     * 
     * The operation creates a new row (that represents a left child) in arraylist and puts the index value 
     * in the row of the position of the current cursor in arraylist
     * 
     * 
     * 
     * No parameters
     *
     */
    void createLB();
    /**
     * 
     * The operation creates a new row (that represents a middle child) in arraylist and puts the index value 
     * in the row of the position of the current cursor in arraylist
     * 
     * 
     * 
     * No parameters
     *
     */
    
   
    void createMB();   
    /**
     * 
     * The operation creates a new row (that represents a right child) in arraylist and puts the index value 
     * in the row of the position of the current cursor in arraylist
     * 
     * 
     * 
     * No parameters
     *
     */
    
    void createRB();
   
    /**
     * 
     * The operation moves cursor to the row of the index of the parent
     * node  in the Parse_Tree
     * 
     * No parameters
     *
     */
    void goUp(int cursor);
    /**
     * 
     * The operation returns a current value of the  cursor 
     * 
     * No parameters
     *
     */
    int getCursor();
   
    /**
     * 
     * The operation puts identifier in the Decl_ID_Table.
     *  If identifier is already in the table, an error message is printed and a program is terminated
     * 
     * @param identifier
     *          A string object that represents an identifier that is going to be put in the table
     *
     */
    void putin_Decl_ID_Table(String identifier);
    /**
     * 
     * The operation puts the index of the identifier of the table in the row of the arrayllist at 
     * position of the value of the current cursor
     * 
     * @param identifier
     *          A string object that represents an identifier that is going to be put in the table
     *
     */
   
    void putIDindex(String identifier);
    
    /**
     * 
     * The operation puts the terminal (the value at the index [1]) 
     * to the row (the nonterminal that can be replaced only with a terminal)
     * at the given cursor.
     *  
     * @param terminal
     *          An integer object that represents an integer value of the token that is terminal 
     *
     *
     */
   
    void put_terminal_INrow (int terminal);
    
    /**
     * 
     *The operation assign the identifier to the integer and put the pair in initialized IDs teble
     * 
     *
     *  @param id
     *          A string object that represents an identifier that is going to be put in the table
     *
     *
     *@param x
     *          An integer object that represents a number that is going to be put in the table
     *
     *
     */
  
    void setIdVal(String id, int x);
    
    /**
     * 
     *The operation returns the integer value of id that it curently has
     *
     *  @param id
     *          A string object that represents an identifier whose integer value is returned
     *
     *
     */
    
  
    int Get_Id_Value (String id);
    
    /**
     * 
     *The operation returns the integer value from the data_table
     *
     *No parameters  
     */
   
  
    int getInputValue ();
   
    
    
}



