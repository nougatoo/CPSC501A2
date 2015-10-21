
public class driver {

	public static void main(String[] args) {

		
		Inspector i = new Inspector();
		TestClass t1 = new TestClass();
		ClassD d1 = new ClassD();
		ClassB b1 = null;
		try {
			b1 = new ClassB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		i.inspect(d1, true);

	}

}
