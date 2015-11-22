//package Interpreter.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**Implements Parse_Tree Interface
 *
 * @author  Igor Tolkachev
 *
 */

public class Parse_Tree_Imp implements Parse_Tree {
   
   
    //--------------------------------------------------------------------------------
    //                                PRIVATE VARIABLES
    //--------------------------------------------------------------------------------
   
	 /** cursor */
    private int cursor = 0;
   
    /** Parse_Tree */
    private static ArrayList < int []> Parse_Tree_Rep = new ArrayList <int[]>();
    
    String s = "";
    
    /**Table of declared Ids */
    private final ArrayList <String> Decl_ID_Table= new ArrayList <String>();
    
    /**Table of initialized Ids */
    private final Map <String, Integer> init_ID_table = new HashMap <String, Integer>();
   
    /**Tokenizer object that is used by Parse_Tree object */
    private final Tokenizer tokenizer = new Tokenizer_Class();
   
    /**Table of data values read from the data input file */
    private final ArrayList <String> Input_Data_Table = new ArrayList <String>();
   
    //---------------------------------------------------------------------------------
    //                                PUBLIC OPERATIONS
    //---------------------------------------------------------------------------------
    
    
    public void print_tokens ()
    {
    	tokenizer.print_tokens();
    }
    
    
    
    public  void Insert_First(){
    	Parse_Tree_Rep.add(new int[5]);
    }
    
    public int getToken(){
       
    return tokenizer.getToken();
}
   
    public  int TokensLeft()
    {
        return tokenizer.TokensLeft();   
    }
   
   
    
    
    public int NextToken(){
       
        return tokenizer.NextToken();
       
    }
   
    public int currAlt() {
       
        int[]row = Parse_Tree_Rep.remove (cursor);
       
        Parse_Tree_Rep.add(cursor, row);
       
        return (row[1]);   
    }

    public void Collect_Tokens(Scanner input)
{
    tokenizer.Collect_Tokens(input);
}
   
   
    public void goDownLB() {
   
            //copy of the cursor
             int cursor_copy = cursor;
            
            //get value of the left child row of the parent
            int[]row = Parse_Tree_Rep.remove (cursor);
          // System.out.println("Row[2] is "+row[2]);
            cursor = row[2];
           //System.out.print("goDownLB cursor is "+cursor);
            //put the row back   
            Parse_Tree_Rep.add(cursor_copy, row);
             
         }


    public void goDownMB() {
        //copy of the cursor
         int cursor_copy = cursor;
        
        //get value of the middle child row of the parent
        int[]row = Parse_Tree_Rep.remove (cursor);
        cursor=row[3];
       
        //put the row back   
        Parse_Tree_Rep.add(cursor_copy, row);

    }


    public void goDownRB() {
        //copy of the cursor
         int cursor_copy = cursor;
        
        //assign value of the right child row of the parent
        int[]row = Parse_Tree_Rep.remove (cursor);
        cursor = row[4];
       
        //put the row back   
        Parse_Tree_Rep.add(cursor_copy, row);

    }
    
    public void setNTNo(int n_of_pr){
               
                //assign value of the left child row to the parent
                int[]row = Parse_Tree_Rep.remove (cursor);
               
                row[0]=n_of_pr;
               
                //put the row back   
                Parse_Tree_Rep.add(cursor, row);
                 
    }
   
    public void setAltNo(int alt_n){
       
        //assign value of the left child row to the parent
        int[]row = Parse_Tree_Rep.remove (cursor);
       
        row[1]=alt_n;
       
        //put the row back   
        Parse_Tree_Rep.add(cursor, row);
       
    }
   
     public void createLB(){
         //create row for the left branch
        Parse_Tree_Rep.add(new int[5]);
    
        //assign value of the left child row to the parent
        int[]row = Parse_Tree_Rep.remove (cursor);
        
        row[2]=Parse_Tree_Rep.size();
       
        //put the row back    ?
        Parse_Tree_Rep.add(cursor, row);
         
     }
   
    public void createMB(){
         //create row for the middle child
            Parse_Tree_Rep.add(new int[5]);
           
            //assign value of the middle child row to the parent
            int[]row = Parse_Tree_Rep.remove (cursor);
            row[3]=Parse_Tree_Rep.size();
           
            //put the row back    
            Parse_Tree_Rep.add(cursor, row);
    }
   
    public void createRB(){
         //create row for the right child
            Parse_Tree_Rep.add(new int[5]);
           
            //assign value of the right child row to the parent
            int[]row =    Parse_Tree_Rep.remove (cursor);
            row[4]=Parse_Tree_Rep.size();
           
            //put the row back
            Parse_Tree_Rep.add(cursor, row);
    }
   

    public int getCursor(){
     return cursor;
 }
   
    public void goUp(int curs){
        cursor = curs;
    }
   
    public void putin_Decl_ID_Table(String identifier)
    {
        if (Decl_ID_Table.contains(identifier))
        {
            Interpreter_Main.error ("Identfier " +identifier+ " has already been  declared!", true);
        }
        else
        {
            Decl_ID_Table.add(identifier);
        }
    }
   
     public void putIDindex(String identifier)
     {
         if (Decl_ID_Table.contains(identifier))
            {
            //assign value of the right child row to the parent
        	 
            int[]row = Parse_Tree_Rep.remove (cursor);
            //put the value of index of the given identifier in the row of a given cursor
            row[1]= Decl_ID_Table.indexOf(identifier);
            //put the row back
            Parse_Tree_Rep.add(cursor, row); 
            }
         else
         {
            Interpreter_Main.error("Identifier "+identifier+" is not declared", true);
         }
     }
     
    public void put_terminal_INrow (int comp_op)
    {
        int[]row =Parse_Tree_Rep.remove (cursor);
        row[1] = comp_op;
        //put the row back
        Parse_Tree_Rep.add(cursor, row);   
       
    }
   
    
    public int getIntVal()
    {
    	return tokenizer.getIntVal();
    }
    public String getIDVal ()
    {
        return tokenizer.getIDVal();
    }
   
    public String getID_From_Table()
    {
        int[]row =Parse_Tree_Rep.remove (cursor);
        
        //System.out.print(cursor + "is here!");
        
        int index = row[1];
       
       // System.out.print(index);
        Parse_Tree_Rep.add(cursor, row);
        
        //Print_Decl_ID_Table();
        
        //System.out.print(Decl_ID_Table.size());
       
        String identifier = Decl_ID_Table.remove (index);
       
       // System.out.print("G" +identifier);
        
        Decl_ID_Table.add (index, identifier);
        
        return (identifier);
    }
    	
    
    public int get_terminal()
    {
        int[]row =Parse_Tree_Rep.remove (cursor);
       
        Parse_Tree_Rep.add(cursor, row);
       
        int terminal = row[1];
       
        return(terminal);
    }
   
    //---------------------------------------------------------------------
    //                      EXECUTE HELPER-OPERATIONS
    //---------------------------------------------------------------------
   
    public void setIdVal(String id, int x)
    {
       
        if (init_ID_table.containsKey(id))
        {
            init_ID_table.remove (id);
            init_ID_table.put(id, x);
        }
        else
        {
            init_ID_table.put(id, x);   
        }
       
    }
   
   
    public int Get_Id_Value (String id)
    {
    	
        if (init_ID_table.containsKey(id))
        {
            int value = init_ID_table.get (id);
           
            return value;
       
        }
        else
        {
            Interpreter_Main.error("Identifier " + id+" was not initialized!" , true);
            return 0;//weird case
        }   
       
    }
   
   public void Collect_Input_Data(Scanner input2)
   {
	   String data_val = "";
	   
	   String inputLine = "";
	   
	   while (input2.hasNext()) {
       	boolean found = true;
		   char f_letter;
			
	        	inputLine = input2.nextLine();
	        	
	        	 while (inputLine.length()>0)
		            {
	        		 
	        		 f_letter = inputLine.charAt (0); 
	       	        
	      		     inputLine = tokenizer.removeCharFrom(inputLine, 0);
	        		 
	        		 while ((!tokenizer.Is_Whitespace(f_letter))&&found)
	        		 {
	        			 data_val= tokenizer.AddChar(data_val, f_letter);
	        			System.out.println(data_val);
	        			 if (inputLine.length()>0)
	        			 {
	        				 f_letter = inputLine.charAt (0); 
	        				 inputLine = tokenizer.removeCharFrom(inputLine, 0);
	        				 if (tokenizer.Is_Whitespace(f_letter))
	        				 {
	        					
	        					 Input_Data_Table.add(data_val);
	        	        		 
	 	        				data_val = "";  
	        				 }
	        				 
	        			 }
	        			 else
	        			 {
	        				
	        				 //Interpreter_Main.error("Stop", true);
	        				Input_Data_Table.add(data_val);
	        				found=false;
	        				data_val = ""; 
	        			 }
	        		 }
	        		 
	        		 
		          }
	        	
	   }
	        	
	        	
	   
   }
   
   public void Output_Data_Table()
   {
	   int index=0;
	   System.out.println ("//Output data table");
	   while (index < Input_Data_Table.size())
	   {
		  String data_value = Input_Data_Table.remove(index);
		  System.out.println (data_value);
		  Input_Data_Table.add(index,data_value);
		  index++;
		  
		   
	   }
	 }
	   
   
   
   
   
   public int getInputValue()
   {
	 if (Input_Data_Table.size()==0)
	 {
		Interpreter_Main.error("There is no corresponding data value in the data file!", true); 
	 }
	  String value = Input_Data_Table.remove(0);
	  
	  int index = 0;
	  
	  while (index < value.length())
	  {
		  if (Character.isDigit(value.charAt(index)))
		  {
			  index ++;
		  }
		  else 
		  {
			  Interpreter_Main.error("Not a numeber given in an input data file!" , true);
		  }
	  }
	  
	  return (Integer.valueOf(value));
   }
   //------------------------------------------------------------------------------
   //                                  DEBUGGING HELPER OPERATIONS 
   //------------------------------------------------------------------------------
  private  void  Print_Decl_ID_Table ( )
  {
	  int index = 0;
	  while (index < Decl_ID_Table.size())
	  {
		String id = Decl_ID_Table.remove (index);
		System.out.println(id); 
		Decl_ID_Table.add (index, id);
		index++;
	  }
 }
  
  public void Output_Parse_Tree ()
  {
  	int index = 0;
  	while (index < Parse_Tree_Rep.size())
  	{
  		int array_index = 0;
  		 int[]row =Parse_Tree_Rep.remove (index);
  		 
  		 System.out.print(index+"| ");
  		 while (array_index<5)
  		 {
  			 if (array_index==0)
  			 {
  				 System.out.print("Pr.N:");
  			 }
  			 else if (array_index==1)
  			 {
  				 System.out.print("Alt:");
  			 }
  			 else if (array_index==2)
  			 {
  				 System.out.print("LB:");
  			 }
  			 else if (array_index==3)
  			 {
  				 System.out.print("MB:");
  			 }
  			 else if (array_index==4)
  			 {
  				 System.out.print("RB:");
  			 }
  			 System.out.print(row[array_index]+" ");
  			 array_index++;
  		 }
  		System.out.println("");
  		Parse_Tree_Rep.add (index, row);
  		 index++;
  	}
  	
  	
  }
  
  public void Output_Parse_Tree_Size()
  {
	  System.out.println("Size of Parse_Tree "+ Parse_Tree_Rep.size()); 
  }
  
  

  public void Output_Row_Of_Parse_Tree (int index_row)
  {
	  int array_index = 0;
		 int[]row =Parse_Tree_Rep.remove (index_row);
		 
		 System.out.print(index_row+"| ");
		 while (array_index<5)
		 {
			 if (array_index==0)
			 {
				 System.out.print("Pr.N:");
			 }
			 else if (array_index==1)
			 {
				 System.out.print("Alt:");
			 }
			 else if (array_index==2)
			 {
				 System.out.print("LB:");
			 }
			 else if (array_index==3)
			 {
				 System.out.print("MB:");
			 }
			 else if (array_index==4)
			 {
				 System.out.print("RB:");
			 }
			 System.out.print(row[array_index]+" ");
			 array_index++;
		 }
		System.out.println("");
		Parse_Tree_Rep.add (index_row, row); 
  }
  public void Output_Tokenizer_Size()
  {
	  tokenizer.Output_Tokenizer_Size();
  }
  
  
}
 
    
   
