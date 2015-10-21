import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


public class Inspector {
	
	Object object = null;
	Class classObject = null;
	Class[] tempList;
	
	public void inspect(Object obj, boolean recursive )
	{
		int mods;
		/*
		 * Gets: 
		 * 	name of declaring class
		 * 	name of immediate super class
		 * 	name of the interfaces the class implements
		 * 	The methods of the class declares. For each, also find the following:
		 * 		Exceptions, parameter types, the return type, modifiers
		 *	The constructors: 
		 *		find parameter types and modifiers 
		 *	The fields the class declares:
		 *		type
		 *		modifiers
		 *	The current value in each fields
		 * 
		 */
		

			classObject = obj.getClass();
			try {
				object = classObject.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*
			 * Name of the declaring class
			 */
			System.out.println("Name of the object: " + classObject.getName());
			
			/*
			 * Name of immediate super class
			 */
			System.out.println("Name of the immediate super class: " + classObject.getSuperclass().getName());
			
			/*
			 * Name of the interfaces the class implements
			 */
			tempList = classObject.getInterfaces();
			for(int i = 0; i<tempList.length;i++)
			{
				System.out.println("Name of interface " + (i+1) + ": " + tempList[i].getName());
			}
			
			
			/*
			 * Gets the methods and prints out the info required
			 */
			Method[] listOfMethods = classObject.getDeclaredMethods();		
			for(int i = 0; i<listOfMethods.length;i++)
			{

				System.out.println("Name of method " + (i+1) + ": " + listOfMethods[i].getName());
				
				/*
				 * Exceptions info
				 */
				tempList = listOfMethods[i].getExceptionTypes(); //tempList holds the exceptions right now	
				
				//Prints a small message if there are no exceptions
				if(tempList.length == 0)
					System.out.println("\tThere are no exceptions");
				
				//Loop to print out all the Exceptions
				for(int j = 0; j<tempList.length;j++)
				{
					if(j == 0)
						System.out.println("\tExceptions:");
					System.out.println("\t\tName of exception " + (j+1) + ": " + tempList[j].getName());
					
				}
								
				
				/*
				 * Parameter info
				 */
				tempList = listOfMethods[i].getParameterTypes(); //tempList now holds list of parameters
				
				//Prints a small message if there are no exceptions
				if(tempList.length == 0)
					System.out.println("\tThere are no parameters");
				
				//Loop to print out all the parameters
				for(int j = 0; j<tempList.length;j++)
				{
					if(j == 0)
						System.out.println("\tParameters:");
					System.out.println("\t\tType of parameter " + (j+1) + ": " + tempList[j].getName());				
				}
				
				/*
				 * Return type info
				 */
						
				System.out.println("\tReturn type: " + listOfMethods[i].getReturnType().getName());
				
				
				/*
				 * Modifier info
				 */
				
				System.out.println("\tModifiers: " + Modifier.toString(listOfMethods[i].getModifiers()));
								
			}
			

	}

}
