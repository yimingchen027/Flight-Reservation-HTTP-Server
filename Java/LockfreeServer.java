import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.*;


public class LockfreeServer {
  public static void main(String[] args) throws IOException {
    InetSocketAddress addr = new InetSocketAddress(8090);
    HttpServer server = HttpServer.create(addr, 0);

    HttpContext context= server.createContext("/", new LockFreeHandler());
    LockfreeFlights LockfreeFlights = new LockfreeFlights();
    context.getFilters().add(new ParameterFilter());
    server.setExecutor(Executors.newCachedThreadPool());
    server.start();
    System.out.println("Server is listening on port 8090" );
  }
}

class LockFreeHandler implements HttpHandler {
  public void handle(HttpExchange exchange) throws IOException {
	  String info = new String();
	  String newlog = new String();
	  
	  Map<String, Object> params =
	           (Map<String, Object>)exchange.getAttribute("parameters"); 
	  if(params.containsKey("QUERY"))
	  {
		  String flight_name = (String) params.get("QUERY");
		  info = LockfreeFlights.getInfo(flight_name);
		  newlog = exchange.getRemoteAddress().toString() + "QUERY FOR "+ flight_name + "\r\n";
		  LockfreeFlights.log += newlog;
		  //System.out.print(newlog);
	  }
	  else if(params.containsKey("ORDER")) {
		  String flight_name = (String) params.get("ORDER");
		  info = LockfreeFlights.orderTicket(flight_name);
			 
		  newlog = exchange.getRemoteAddress().toString() + "  "+info + "\r\n";
		  LockfreeFlights.log += newlog;
		  //System.out.print(newlog);
	  }
	  
    String requestMethod = exchange.getRequestMethod();

    if (requestMethod.equalsIgnoreCase("GET")) {
      Headers responseHeaders = exchange.getResponseHeaders();
      responseHeaders.set("Content-Type", "text/plain");
      exchange.sendResponseHeaders(200, 0);

      OutputStream responseBody = exchange.getResponseBody();
//      Headers requestHeaders = exchange.getRequestHeaders();
//      Set<String> keySet = requestHeaders.keySet();
//      Iterator<String> iter = keySet.iterator();
//      while (iter.hasNext()) {
//        String key = iter.next();
//        List values = requestHeaders.get(key);
//        String s = key + " = " + values.toString() + "\n";
//        String news = "this is a test"
//        responseBody.write(news.getBytes());
//      }
     
      
      //String news1 = "this is a test";
      responseBody.write(info.getBytes());
      responseBody.close();
      try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
  }
  
}
