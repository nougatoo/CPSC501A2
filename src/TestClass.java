import java.io.Serializable;


public class TestClass implements Serializable, Runnable, Comparable{
	
	public int field1 = 5;
	public int[] testArrayField = new int[10];
	public Inspector objectField = new Inspector();
	public Inspector objectField2 = new Inspector();
	public Inspector[] testArrayObjects = new Inspector[2];
	public String[] testArrayFieldString = new String[3];
	private String field2 = "This is a string field";
	

	private TestClass(int a, int b)
	{
		
	}
	
	
	public TestClass()
	{
		testArrayField[0] =1;

		testArrayField[1] =2;

		testArrayField[2] =3;

		testArrayField[3] =4;
		testArrayField[4] =5;

		testArrayField[5] =6;

		testArrayField[6] =7;

		testArrayField[7] =8;

		testArrayField[8] =9;

		testArrayField[9] =10;
		
		
		testArrayFieldString[0] = "Hello world 0";
		testArrayFieldString[1] = "Hello world 1";
		testArrayFieldString[2] = "Hello world 2";
		
		testArrayObjects[0] = new Inspector();
		testArrayObjects[1] = new Inspector();
}
	
	
	
	private int Method1(double x) throws NullPointerException, Exception 
	{
		return 5;
	}
	
	public void Method2()
	{
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}



}
