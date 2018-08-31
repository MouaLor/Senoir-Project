package importDatabaseTimer;

public class timerHelloWorld {

	public static void main(String args[]) {
	    try {
	      while (true) {
	        System.out.println(new String("Hello world"));
	        Thread.sleep(5 * 1000); // every 5 seconds
	      }
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	  }
	
}
