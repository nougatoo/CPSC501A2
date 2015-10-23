
public class driver {

	public static void main(String[] args) {

		
		Inspector i = new Inspector();
		TestClass t1 = new TestClass();
		//t1.Method2();
		TestClass2 t2 = new TestClass2();
		ClassD d1 = new ClassD();
		ClassB b1 = null;
		try {
			b1 = new ClassB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		i.inspect(t1, true);

	}

}
