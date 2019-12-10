package lab3;
import java.util.Scanner;
import lincalc.LinCalcJohn;
import java.util.Arrays;



public class LinCalc {

    public static void printArray(String[] array){
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            sb.append(", ");
        }
        // Replace the last ", " with "]"
        sb.replace(sb.length() - 2, sb.length(), "]");
        System.out.println(sb);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String expression;

        double result;

        System.out.print("Enter expression: ");
        expression = in.nextLine();
        do {
            result = evaluate(expression);
            System.out.println("Result was: " + result);
            System.out.print("Enter expression: ");
        } while (!"".equals(expression = in.nextLine()));
    }
    
    
    
    
	
    //Bygga metod 3a
    public static double calc(String[] lexedPostfix) {
    	System.out.println(Arrays.toString(lexedPostfix)+"Postfix"+"\n");

    	
    	
    	int arrLength= lexedPostfix.length;
    	
    	//initialiserar våran stack
        Stack stack = new Stack(); 

        
        String holder1;
        String holder2;
        double holder3;
        
    	for(int i=0; i<arrLength; i++)
    	{
    		
    		//om operator lägg i stacken
    		if (!allaTecken(lexedPostfix[i]))
    		{
    			stack.push(lexedPostfix[i]);
    		}
    		
    		
    		//om operand
    		if (allaTecken(lexedPostfix[i]) && !unarOp(lexedPostfix[i]))
    		{
    			//spara operanden som char
    			char operand = lexedPostfix[i].charAt(0);
    			
    			//poppa stacken till 2 variabler  
    			holder2 = stack.pop();
    			holder1 = stack.pop();
    			
    			
        		//kollar vilken operand och utför beräkning samt sparar in resultatet i stacken
    			if(operand == '+')
    			{
    				holder3 =  Double.parseDouble(holder1) + Double.parseDouble(holder2);
    				stack.push(Double.toString(holder3));
    				System.out.println(stack.peek()+"adderar");
    			}
    			else if(operand == '-')
    			{
    				holder3 =  Double.parseDouble(holder1) - Double.parseDouble(holder2);
    				stack.push(Double.toString(holder3));
    				System.out.println(stack.peek()+"subtraherar");
    			}
    			else if(operand == '*')
    			{
    				holder3 =  Double.parseDouble(holder1) * Double.parseDouble(holder2);
    				stack.push(Double.toString(holder3));
    				System.out.println(stack.peek()+"multiplicerar");
    			}
    			else if(operand == '/')
    			{
    				holder3 =  Double.parseDouble(holder1) / Double.parseDouble(holder2);
    				stack.push(Double.toString(holder3));
    				System.out.println(stack.peek()+"dividerar");
    			}
    			
    			
    			
    			
    		}
    		
    		
    		//om unärt minus så inverterar värdet längst upp i stacken
    		if(unarOp(lexedPostfix[i]))
    		{
    			holder1 = stack.pop();
    			holder3 = -Double.parseDouble(holder1);
    			stack.push(Double.toString(holder3));
    		}
    		
    	}

    		
    	//kommer nu finnas 1 variabel i stacken, sparar den som double i result
    	double result = Double.parseDouble(stack.pop());
    	System.out.println(result+"egetresult");
    	return result;
    	
        //return LinCalcJohn.calc(lexedPostfix);
        
    	
    }
	
    
    
    
    //Bygga metod 2a
    public static String[] toPostfix(String[] inputData) {
    	System.out.println(Arrays.toString(inputData)+"inputData"+"\n");

    	//skapar en array med längden av våran input data
    	int arrLength = inputData.length;
    	String result[] = new String [arrLength];
    	
    	//initialiserar våran stack
        Stack stack = new Stack(); 
        
    	
    	int x = 0;
    	
    	//for loop som går igenom hela våran infix
    	//och följer reglerna för järnvägsalgoritmen + unära minus
    	for(int i=0; i<arrLength; i++) {
    		if(!allaTecken(inputData[i])) {
    			result[x] = inputData[i];
    			x++;
    		}
    		else if(allaOp(inputData[i])) {
    			 while (!stack.isEmpty() && prio(inputData[i]) <= prio(stack.peek())) {
    				 result[x] = stack.pop();
    				 x++;
    			 }
    			 stack.push(inputData[i]);
    		}
    		else if(vansterPar(inputData[i])) {
    			stack.push(inputData[i]);
    		}
    		else if(hogerPar(inputData[i]) || !stack.isEmpty()) {
    			while (!vansterPar(stack.peek())) {
    				result[x] = stack.pop();
    				x++;
    			}
    			stack.pop();
    		}		
    	}
    	
    	//om stacken inte är tom så flyttar vi in värderna i result
    	while (!stack.isEmpty())
		{
			result[x] = stack.pop();
			x++;
		}
    	
    	
    	//result = clearNulls(result);
    	
    	return clearNulls(result);
     
    }
    
    
    
    public static double evaluate(String expression) {
        String[] lexedInfix = lex(expression);
        String[] lexedPostfix = toPostfix(lexedInfix);
        return calc(lexedPostfix);
    }
    
    
    
     //Bygga metod 1a
    public static String[] lex(String expr) {

    	
    	String[] result = new String[expr.length()];
    	
    	//j används för att veta vilken plats vi är på i arrayen
    	int j = 0;
    	//x används för att hålla koll på var vi är i stringen
    	int x = 0;
    	
    	
    	
    	for(int i = 0; i < expr.length(); i++)
    	{
    		
    		//kollar om operand
    		if(allaTecken(expr.substring(i, i+ 1))) 
    		{
    			result[j] = expr.substring(x, i +1);
    		j++;
    		x++;
			}
    		
    		//om inte operator
    		else if(!allaTecken(expr.substring(i, i+1))) 
    		{
    					
    			result[j] = expr.substring(x, i+1);
    			if (i + 1<expr.length() && allaTecken(expr.substring(i + 1, i + 2)))
    			{
    				j++;
    				x = i + 1;
    			}
    		}	
    	}
    	//clearar nulls 
    	result = clearNulls(result);	
    	
    	int arrLength = result.length;
    	
    	
    	
    	//kollar om första tecknet i stringen ska vara ett unärt minus
    	if (result[0].equals("-")) {
			result[0] = "~";
		}

    			
    	//kolllar efter andra unära minus
    	for (int i = 1; i < arrLength; i++) {
			if(normOp(result[i-1]) && result[i].equals("-"))
					{
						result[i] = "~";
					}
			else if(vansterPar(result[i-1]) && result[i].equals("-"))
			{
				result[i] = "~";
			}
		}

    	
    		return result;
    		
    		
    		
    }

    
    
  
    
    //metod som tar bort nulls ur en array
    public static String[] clearNulls(String[] arr) {
    	int numberOfNonNullElements=0;
    	//for loop som räknar upp alla element som inte är null
    	for(int i=0; i<arr.length; i++) {
    		if(arr[i] != null) {
    			numberOfNonNullElements++;
    		}
    	}
    	//skapar en ny array med samma element minus alla nulls
    	String[] clearedArr = new String[numberOfNonNullElements];
    	for(int i=0; i<clearedArr.length; i++) {
    		clearedArr[i] = arr[i];
    	}
    	return clearedArr;
    	
    }
    
    
    	//metod för att kolla prio på operander
   public static int prio(String s) {
    	if ("+".equals(s) || "-".equals(s)) 
    	{
    		return 0;
    	}
    	else if ("*".equals(s) || "/".equals(s))
    	{
    		return 1;
    	}
    	else if ("~".equals(s))
    	{
    		return 2;
    	}
    	
    	else
    		return -1;
	}
    
    
   
   //kollar om string är någon av följande tecken
    public static boolean allaTecken(String s) 
    {
    		return allaOp(s) || vansterPar(s) || hogerPar(s);
    }
    
    public static boolean allaOp(String s)
	{
	    return normOp(s) || unarOp(s);
	}
    
    public static boolean normOp(String s)
	{
	    return "+-/*".indexOf(s) >= 0;
	}	
    
    public static boolean unarOp(String s)
	{
	    return "~".equals(s);
	}
    
    public static boolean vansterPar(String s)
	{
	    return "(".equals(s);
	}
  
	public static boolean hogerPar(String s)
	{
	    return ")".equals(s);
	}		

	
	
	
	
	
}
   

