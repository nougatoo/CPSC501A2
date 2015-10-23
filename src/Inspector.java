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
	
	/*
	 * Constructor used for jUnit testing
	 */
	public Inspector(Class testClass)
	{
		try {
			object = testClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	Class[] beenAdded = new Class[100];
	
	private Inspector i;
	private Object object;
	private Object[] objectInspecQueue = new Object[10];
	int objInspectedNextEmpty = 0;
	int lastOIQIndex = 0;
	int methodNumber; 
	int constructorNumber;
	int fieldNumber;
	int index = 0;
	boolean inspecInspec;
	boolean recursingNow = false;
	boolean doRecursion;
	Class superClass;
	Class[] tempClassList;
	
	public void inspect(Object obj, boolean recursive )
	{
		//lastOIQIndex--;
		methodNumber = 0;
		constructorNumber = 0;
		fieldNumber = 0;		
		Class classObject;
		doRecursion = recursive;
		int mods;
	
		/*
		 * Gets the class object of the object input
		 */
		object = obj;
		classObject = obj.getClass();
		
		
		/*
		 * Name of the declaring class
		 */
		String declaringClass;
		if(classObject.getDeclaringClass() != null)
			declaringClass = classObject.getDeclaringClass().toString();
		else
			declaringClass = "null";
		System.out.print("\n\nName of the declaring class: " + declaringClass);			
		drawFormattingLine();
		
		/*
		 * Name of immediate super class
		 */
		superClass = classObject.getSuperclass();
		System.out.print("\nName of the immediate super class: " + superClass.getName());		
		drawFormattingLine();
		
		
		/*
		 * Name of the interfaces the class implements
		 */
		tempClassList = classObject.getInterfaces();
		if(tempClassList.length > 0)
		{
			for(int i = 0; i<tempClassList.length;i++)
			{
				System.out.print("\nName of interface " + (i+1) + ": " + tempClassList[i].getName());
			}
		}
		else
			System.out.print("\nThis class implements no interfaces.");
				
		
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
		 * Prints out the fields. If they are an object and recursive is set to true, then they
		 * get added to a queue so that they will be insepcted later 
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
	
	/*
	 * Takes all the objects that were added to queue (because they are an
	 * object field) and inspects them
	 */
	public void inspectObjectQueue()
	{
		while(lastOIQIndex > 0)
		{
			
			inspect(objectInspecQueue[--lastOIQIndex], false);
			recursingNow = true;

		}	
	}
	
	/*
	 * Takes a class object and inspects all the fields and all the fields from the 
	 * inheritance hierarchy recursively by calling inspectFields on the super class of
	 * and all the interfaces that it implements  
	 */
	public void inspectFields(Class clsObj)
	{
		Field[] listOfFields = clsObj.getDeclaredFields();

		Class classInterfaces[] = clsObj.getInterfaces();
		int temp = 0;
		Class<?> asdf = null;
		
		/*
		 * Traversing through all the fields and outputing them
		 */
		for(int i = 0;i<listOfFields.length;i++)
		{
			
			if(listOfFields[i].isAccessible() == false)
				listOfFields[i].setAccessible(true);
			
			System.out.print("\nField " + (i+1) + ": "); 
			
			if(listOfFields[i].getType().isArray()==false)
				System.out.print("\n\tType: " + listOfFields[i].getType().getName());
			else
			{
				System.out.print("\n\tType: Array");
				System.out.print("\n\tComponent Type: " + listOfFields[i].getType().getComponentType());
					
			}
			System.out.print("\n\tModifiers: " + Modifier.toString(listOfFields[i].getModifiers()));
			
			
			/*
			 * Checks the field type and prints out the value accordingly
			 * If it is an object, we add it to a queue and inspect them after
			 */
			try {
				if(listOfFields[i].getType() == int.class)
				{				
						System.out.print("\n\tValue: " + listOfFields[i].getInt(object) + "\n");				
				}
				else if(listOfFields[i].getType() == double.class)
				{				
						System.out.print("\n\tValue: " + listOfFields[i].getDouble(object));				
				}
				else if(listOfFields[i].getType() == float.class)
				{				
						System.out.print("\n\tValue: " + listOfFields[i].getFloat(object));				
				}
				else if(listOfFields[i].getType() == char.class)
				{				
						System.out.print("\n\tValue: " + listOfFields[i].getChar(object));				
				}
				else if(listOfFields[i].getType() == short.class)
				{				
						System.out.print("\n\tValue: " + listOfFields[i].getShort(object));		
				}
				else if(listOfFields[i].getType() == long.class)
				{				
						System.out.print("\n\tValue: " + listOfFields[i].getLong(object));		
				}
				else if(listOfFields[i].getType() == byte.class)
				{				
						System.out.print("\n\tValue: " + listOfFields[i].getByte(object));		
				}
				else if (listOfFields[i].getType() == String.class)
				{				
						System.out.print("\n\tValue: " + (String) listOfFields[i].get(object));		
				}
				else if (listOfFields[i].getType().isArray())
				{
					inspectArray(listOfFields[i]);	
				}
				else if (listOfFields[i].getType().isPrimitive() == false && doRecursion == true)
				{
					
					/*
					 * If they are an object and we haven't looked at them before, then add them to queue
					 */
					try{
						if(Arrays.asList(beenAdded).contains(listOfFields[i].get(object).getClass()))
						{
							System.out.print("\n\tAlready been inspected");
						}
						else
						{
							objectInspecQueue[lastOIQIndex] = listOfFields[i].get(object);
							beenAdded[index]= listOfFields[i].get(object).getClass();
							index++;
							lastOIQIndex++;
							
							System.out.print("\n\t--Added to object inspection queue---");
						}
						
					}
					catch (NullPointerException e)
					{
						System.out.print("\n\tThere is no instantiation to inspect");
					}
					
				}
				
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}//End of for loop
		
		
		/*
		 * Looking at all the fields of the interfaces
		 */
		for(int i = 0;i<classInterfaces.length;i++)
		{
			inspectFields(classInterfaces[i]);
		}
		
		/*
		 * Fields of the super class
		 */
		if(clsObj.getSuperclass() != null)
			inspectFields(clsObj.getSuperclass());
		
		
	}
	
	/*
	 * Called by inspectFields if the field found was an array
	 */
	public void inspectArray(Field arr)
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
			System.out.print("\n\tLength: " +length);
			System.out.print("\n\tName: " + arr.getName());
			System.out.print("\n\tElements: ");
			for(int i = 0; i<length;i++)
			{
				try{
				if(Array.get(asdf, i).equals(null) == false);
					System.out.print("\n\t\t" + i + ": " + Array.get(asdf, i).toString());
				}
				catch (NullPointerException e) {
					System.out.print("\n\t\t" + i+ ": " + "Array Element not instantiated.");
				}
			}	
		}
	}
	
	
	/*
	 * Looks at the constructors of the class object (recursive)
	 */
	public void inspectConstructors(Class clsObj)
	{

		Constructor[] listOfConstructors = clsObj.getDeclaredConstructors();
		for(int i = 0; i<listOfConstructors.length;i++)
		{
			constructorNumber++;
			System.out.print("\nConstructor " + constructorNumber + ":");
			
			/*
			 * Constructor Parameter info
			 */
			tempClassList =listOfConstructors[i].getParameterTypes(); //tempList now holds list of parameters
			
			//Prints a small message if there are no exceptions
			if(tempClassList.length == 0)
				System.out.print("\n\tNo Parameter Constructor");
			
			//Loop to print out all the parameters
			for(int j = 0; j<tempClassList.length;j++)
			{
				if(j == 0)
					System.out.print("\n\tParameters:");
				System.out.print("\n\t\tType of parameter " + (j+1) + ": " + tempClassList[j].getName());				
			}
			
			
			/*
			 * Modifier info
			 */
			
			System.out.print("\n\tModifiers: " + Modifier.toString(listOfConstructors[i].getModifiers()));
			
			System.out.print("\n");
		}
		
		if(clsObj.getSuperclass() != null)
			inspectConstructors(clsObj.getSuperclass());
		
	}
	
	/*
	 * Looks at all the methods in class, inherited methods and superclass methods
	 */
	 void inspectMethods(Class clsObj)
	{
		
		Method[] listOfMethods = clsObj.getDeclaredMethods();	
		for(int i = 0; i<listOfMethods.length;i++)
		{
			methodNumber++;
			System.out.print("\nName of method " + methodNumber + ": " + listOfMethods[i].getName());
			
			/*
			 * Exceptions info
			 */
			tempClassList = listOfMethods[i].getExceptionTypes(); //tempList holds the exceptions right now	
			
			//Prints a small message if there are no exceptions
			if(tempClassList.length == 0)
				System.out.print("\n\tThere are no exceptions");
			
			//Loop to print out all the Exceptions
			for(int j = 0; j<tempClassList.length;j++)
			{
				if(j == 0)
					System.out.print("\n\tExceptions:");
				System.out.print("\n\t\tName of exception " + (j+1) + ": " + tempClassList[j].getName());
				
			}
							
			
			/*
			 * Parameter info
			 */
			tempClassList = listOfMethods[i].getParameterTypes(); //tempList now holds list of parameters
			
			//Prints a small message if there are no exceptions
			if(tempClassList.length == 0)
				System.out.print("\n\tThere are no parameters");
			
			//Loop to print out all the parameters
			for(int j = 0; j<tempClassList.length;j++)
			{
				if(j == 0)
					System.out.print("\n\tParameters:");
				System.out.print("\n\t\tType of parameter " + (j+1) + ": " + tempClassList[j].getName());				
			}
			
			/*
			 * Return type info
			 */
					
			System.out.print("\n\tReturn type: " + listOfMethods[i].getReturnType().getName());
			
			
			/*
			 * Modifier info
			 */
			
			System.out.print("\n\tModifiers: " + Modifier.toString(listOfMethods[i].getModifiers()));
			
			System.out.print("\n");
		
							
		}//End of for loop
		

		if(clsObj.getSuperclass() != null)
			inspectMethods(clsObj.getSuperclass());
		
		
		
	}

	public void drawFormattingLine()
	{
		System.out.print("\n---------------------------------------------------------");		
	}
	
	public void drawFormattingLine2()
	{
		System.out.print("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");		
	}

}
