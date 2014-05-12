import java.util.Random;

import ist.meic.pa.Trace;

class Test04 {

	public enum Animal {
		REPTILE(2);

		Animal(int i) {

		}

		public void getAnimal(Object o) {
			System.out.println("ANIMAL");
		}

	}

	abstract static class Zoo {
		public void cat(Object o) {
			System.err.println("CAT");
		}

		public Object rat(Object o) {
			System.err.println("RAT");
			return o;
		}

		public void dog(Object o) {
			System.err.println("DOG");
		}

		public abstract void bird(Object o);

		public static void lion(Object o) {
			System.err.println("LION");
		}

	}

	class ZooLx extends Zoo {

		@Override
		public void bird(Object o) {
			System.err.println("BIRD");

		}

		@Override
		public void dog(Object o) {
			System.err.println("BIG DOG");
		}

	}

	public void test(int i) {
		Object o = new int[i];
		Zoo zooLx = new ZooLx();

		// int c = 2147479999;
		int c = 5;
		while (c > 0) {
			Zoo.lion(o);
			if (c % 2 == 0) {
				zooLx.cat(o);
				zooLx.rat(o);
			} else {
				zooLx.dog(o);
				zooLx.bird(o);

			}
			c--;
		}

		Animal.REPTILE.getAnimal(o);

		Trace.print(o);
	}
}

public class Test4 {
	public static void main(String args[]) {
		(new Test04()).test(Integer.parseInt(args[0]));
	}
}
