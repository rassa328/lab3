package lab3;
import java.util.Arrays;

public class Stack{

	private static int startStorlek = 10;
	private String[] stackAr;
	private int nuvarandePos = -1;
	
	//konstruktör för våran stack
	public Stack()
	{
	stackAr = new String[startStorlek]; 
	}
		
	
	//tar ut ett värde ur stacken
	public String pop()
	{
		return stackAr[nuvarandePos--];
	}
	
	
	//sparar in ett värde i stacken
	public void push(String inData)
	{
		
		if(nuvarandePos + 1 == stackAr.length)
		{
			expanderaAr();
		}
		
		
		stackAr[++nuvarandePos] = inData;
		
	}
	
	//kollar översta värdet i stacken
	public String peek()
	{
		return stackAr[nuvarandePos];
	}
	
	//kollar om stacken är tom 
	public boolean isEmpty()
	{
		return (nuvarandePos == -1);
	}
	
	//tar bort översta värdet på stacken
	public void removePeek()
	{
		if (nuvarandePos > -1)
		{
		nuvarandePos--;
		}
	}
	
	
	//dubblar storleken på arrayen
	private void expanderaAr()
	{
		int OkadStorlek = 2 * stackAr.length;
		stackAr = Arrays.copyOf(stackAr, OkadStorlek);
	}
		
}
