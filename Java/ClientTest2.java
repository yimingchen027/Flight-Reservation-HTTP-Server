
import java.io.IOException;

//100 random query + 100 all flight order

public class ClientTest2 {

	  private final static int THREADS = 8;
	  Thread[] thread = new Thread[THREADS];
	  
	  Client instance= new Client();
	  public void testParallel() throws Exception {
	    System.out.println("test parallel");
	    //ThreadID.reset();
	    for (int i = 0; i < THREADS; i++) {
	      thread[i] = new QueryThread();
	    }
	    for (int i = 0; i < THREADS; i++) {
	      thread[i].start();
	    }
	    for (int i = 0; i < THREADS; i++) {
	      thread[i].join();
	    }

	  }
	  
	  class QueryThread extends Thread {
		  
	    public void run() {
	    	
	    	for(int i =0; i<(100/THREADS); i++){
	    		try {
					instance.randomQuery();
					instance.randomOrder();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    		
	      }
	      //System.out.println("ThreadID: "+ThreadID.get());
	    
	  }

	  public static void main(String[] args) {
		  
		long start = System.currentTimeMillis();
		  
		ClientTest2 mft = new ClientTest2();
	    try {
	      mft.testParallel();
	    }
	    catch (Exception e) {}   
	    long time = System.currentTimeMillis() - start; 
	    System.out.println("Running time: "+time+ " ms");
	  }

	

}
