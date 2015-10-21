import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;


public class Inspector {
	
	public Inspector()
	{
		inspecInspec = false;
	}
	
	int index = 0;
	public Inspector(Class[] doNotInspect, int lastEmptyIndex)
	{
		beenAdded = doNotInspect; 
		index = lastEmptyIndex;
		inspecInspec = true;
	}
	
	Class[] beenAdded = new Class[100];

	
	boolean inspecInspec;
	//private Inspector i = new Inspector();
	private String[] alreadyDone = new String[2];
	private int doneNextEmptyIndex = 0;
	private Object object;
	private Object[] objectInspecQueue = new Object[10];
	private String[] objInspected = new String[100];
	int objInspectedNextEmpty = 0;
	int lastOIQIndex = 1;
	int methodNumber; 
	int constructorNumber;
	int fieldNumber;
	
	boolean recursingNow = false;
	
	boolean doRecursion;
	
	Class superClass;
	

	Class[] tempClassList;
	
	public void inspect(Object obj, boolean recursive )
	{
		lastOIQIndex--;
		methodNumber = 0;
		constructorNumber = 0;
		fieldNumber = 0;
		
		
		
		Class classObject;
		doRecursion = recursive;
		
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
		
			System.out.println(obj == null);
			classObject = obj.getClass();
			object = obj;
			
			
			/*
			 * Name of the declaring class
			 */
			System.out.println("Name of the declaring class: " + classObject.getDeclaringClass());			
			drawFormattingLine();
			
			
			/*
			 * Name of immediate super class
			 */
			superClass = classObject.getSuperclass();
			System.out.println("Name of the immediate super class: " + superClass.getName());		
			drawFormattingLine();
			
			
			/*
			 * Name of the interfaces the class implements
			 */
			tempClassList = classObject.getInterfaces();
			if(tempClassList.length > 0)
			{
				for(int i = 0; i<tempClassList.length;i++)
				{
					System.out.println("Name of interface " + (i+1) + ": " + tempClassList[i].getName());
				}
			}
			else
				System.out.println("This class implements no interfaces.");
					
			
			/*
			 * Gets the methods and prints out the info required
			 * Traverses up the hierarchy
			 */
			
			drawFormattingLine();
			inspectMethods(classObject);
			drawFormattingLine();
			
			/*
			 * Prints out all the information about constructors
			 */
			inspectConstructors(classObject);
			drawFormattingLine();
			
			
			/*
			 * Prints out the fields and recursively inspects objects if they have an object field type (if set) 
			 */
			
			inspectFields(classObject);
			drawFormattingLine();

			/*
			 * Now we have to inspec any object fields we find if recursive == true
			 */
			
			drawFormattingLine2();
			drawFormattingLine2();
			drawFormattingLine2();
			drawFormattingLine2();
			
			if(recursive == true && recursingNow == false)
			{
				inspectObjectQueue();
			}
			

	}
	
	private void inspectObjectQueue()
	{
		int temp = lastOIQIndex;
		while(lastOIQIndex > 0)
		{
			System.out.println("asdfasdfasdfasdfasdf");
			alreadyDone[doneNextEmptyIndex] = objectInspecQueue[lastOIQIndex-1].getClass().getName();
			//System.out.println(objectInspecQueue[lastOIQIndex-1].getClass().getName());
			doneNextEmptyIndex++;
			
			Inspector test = new Inspector(beenAdded, index);
			test.inspect(objectInspecQueue[lastOIQIndex-1], true);
			lastOIQIndex--;

		}	
		
		System.out.println(lastOIQIndex);

		

	}
	
	private void inspectFields(Class clsObj)
	{
		Field[] listOfFields = clsObj.getDeclaredFields();

		Class classInterfaces[] = clsObj.getInterfaces();
		int temp = 0;
		Class<?> asdf = null;
		for(int i = 0;i<listOfFields.length;i++)
		{
			
			if(listOfFields[i].isAccessible() == false)
				listOfFields[i].setAccessible(true);
			
			System.out.println("Field " + (i+1) + ": "); 
			
			if(listOfFields[i].getType().isArray()==false)
				System.out.println("\tType: " + listOfFields[i].getType().getName());
			else
			{
				System.out.println("\tType: Array");
				System.out.println("\tComponent Type: " + listOfFields[i].getType().getComponentType());
					
			}
			System.out.println("\tModifiers: " + Modifier.toString(listOfFields[i].getModifiers()));
			

			
			/*
			 * Checks the field type and prints out the value accordingly
			 * If it is an object, we add it to a queue and inspect them after
			 */
			try {
				if(listOfFields[i].getType() == int.class)
				{				
						System.out.println("\tValue: " + listOfFields[i].getInt(object));				
				}
				else if(listOfFields[i].getType() == double.class)
				{				
						System.out.println("\tValue: " + listOfFields[i].getDouble(object));				
				}
				else if(listOfFields[i].getType() == float.class)
				{				
						System.out.println("\tValue: " + listOfFields[i].getFloat(object));				
				}
				else if(listOfFields[i].getType() == char.class)
				{				
						System.out.println("\tValue: " + listOfFields[i].getChar(object));				
				}
				else if(listOfFields[i].getType() == short.class)
				{				
						System.out.println("\tValue: " + listOfFields[i].getShort(object));		
				}
				else if(listOfFields[i].getType() == long.class)
				{				
						System.out.println("\tValue: " + listOfFields[i].getLong(object));		
				}
				else if(listOfFields[i].getType() == byte.class)
				{				
						System.out.println("\tValue: " + listOfFields[i].getByte(object));		
				}
				else if (listOfFields[i].getType() == String.class)
				{				
						System.out.println("\tValue: " + (String) listOfFields[i].get(object));		
				}
				else if (listOfFields[i].getType().isArray())
				{
					inspectArray(listOfFields[i]);	
				}
				else if (listOfFields[i].getType().isPrimitive() == false && doRecursion == true)
				{
					
					try{
						if(Arrays.asList(beenAdded).contains(listOfFields[i].get(object).getClass()) || (listOfFields[i].getType().getName() == "Inspector" && inspecInspec == true))
						{
							System.out.println("\tAlready been inspected");
						}
						else
						{
							System.out.println(lastOIQIndex);
							objectInspecQueue[lastOIQIndex] = listOfFields[i].get(object);
							beenAdded[index]= listOfFields[i].get(object).getClass();
							index++;
							System.out.println(listOfFields[i].getType());
							lastOIQIndex++;
							
							System.out.println(listOfFields[i].get(object).getClass().getName());
							System.out.println("\t--Added to object inspection queue---");
						}
						
					}
					catch (NullPointerException e)
					{
						
					}
					
				}
				
				
			
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	
			
		}//End of for loop
		
		/*
		 * Get all the fields of the immediate superClass (recursive)
		 */
		
		/*
		 * Looking at all the fields of the interfaces
		 */
		for(int i = 0;i<classInterfaces.length;i++)
		{
			inspectFields(classInterfaces[i]);
		}
		
		if(clsObj.getSuperclass() != null)
			inspectFields(clsObj.getSuperclass());
		
	}
	
	private void inspectArray(Field arr)
	{

		Object asdf= null;
		int length;
		
		try {
			asdf= arr.get(object);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(asdf != null)
		{
			length = Array.getLength(asdf);
			System.out.println("\tLength: " +length);
			System.out.println("\tName: " + arr.getName());
			System.out.println("\tElements: ");
			for(int i = 0; i<length;i++)
			{
				try{
				if(Array.get(asdf, i).equals(null) == false);
					System.out.println("\t\t" + i + ": " + Array.get(asdf, i).toString());
				}
				catch (NullPointerException e) {
					System.out.println("\t\t" + i+ ": " + "Array Element not instantiated.");
				}
			}
			
				
		}
		
		
	}
	
	private void inspectConstructors(Class clsObj)
	{

		Constructor[] listOfConstructors = clsObj.getDeclaredConstructors();
		for(int i = 0; i<listOfConstructors.length;i++)
		{
			constructorNumber++;
			System.out.println("Constructor " + constructorNumber + ":");
			
			/*
			 * Constructor Parameter info
			 */
			tempClassList =listOfConstructors[i].getParameterTypes(); //tempList now holds list of parameters
			
			//Prints a small message if there are no exceptions
			if(tempClassList.length == 0)
				System.out.println("\tNo Parameter Constructor");
			
			//Loop to print out all the parameters
			for(int j = 0; j<tempClassList.length;j++)
			{
				if(j == 0)
					System.out.println("\tParameters:");
				System.out.println("\t\tType of parameter " + (j+1) + ": " + tempClassList[j].getName());				
			}
			
			
			/*
			 * Modifier info
			 */
			
			System.out.println("\tModifiers: " + Modifier.toString(listOfConstructors[i].getModifiers()));
			
			System.out.println();
		}
		
		if(clsObj.getSuperclass() != null)
			inspectConstructors(clsObj.getSuperclass());
		
	}
	
	private void inspectMethods(Class clsObj)
	{
		
		Method[] listOfMethods = clsObj.getDeclaredMethods();	
		for(int i = 0; i<listOfMethods.length;i++)
		{
			methodNumber++;
			System.out.println("Name of method " + methodNumber + ": " + listOfMethods[i].getName());
			
			/*
			 * Exceptions info
			 */
			tempClassList = listOfMethods[i].getExceptionTypes(); //tempList holds the exceptions right now	
			
			//Prints a small message if there are no exceptions
			if(tempClassList.length == 0)
				System.out.println("\tThere are no exceptions");
			
			//Loop to print out all the Exceptions
			for(int j = 0; j<tempClassList.length;j++)
			{
				if(j == 0)
					System.out.println("\tExceptions:");
				System.out.println("\t\tName of exception " + (j+1) + ": " + tempClassList[j].getName());
				
			}
							
			
			/*
			 * Parameter info
			 */
			tempClassList = listOfMethods[i].getParameterTypes(); //tempList now holds list of parameters
			
			//Prints a small message if there are no exceptions
			if(tempClassList.length == 0)
				System.out.println("\tThere are no parameters");
			
			//Loop to print out all the parameters
			for(int j = 0; j<tempClassList.length;j++)
			{
				if(j == 0)
					System.out.println("\tParameters:");
				System.out.println("\t\tType of parameter " + (j+1) + ": " + tempClassList[j].getName());				
			}
			
			/*
			 * Return type info
			 */
					
			System.out.println("\tReturn type: " + listOfMethods[i].getReturnType().getName());
			
			
			/*
			 * Modifier info
			 */
			
			System.out.println("\tModifiers: " + Modifier.toString(listOfMethods[i].getModifiers()));
			
			System.out.println();
		
							
		}//End of for loop
		

		if(clsObj.getSuperclass() != null)
			inspectMethods(clsObj.getSuperclass());
		
		
		
	}

		

	
	public void drawFormattingLine()
	{
		System.out.println("---------------------------------------------------------");		
	}
	
	public void drawFormattingLine2()
	{
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");		
	}

}
