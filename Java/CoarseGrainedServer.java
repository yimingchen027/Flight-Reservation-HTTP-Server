import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.*;


public class CoarseGrainedServer {
  public static void main(String[] args) throws IOException {
    InetSocketAddress addr = new InetSocketAddress(8090);
    HttpServer server = HttpServer.create(addr, 0);

    HttpContext context= server.createContext("/", new CoarseGrainedHandler());
    CoarseGrainedflights CoarseGrainedflights = new CoarseGrainedflights();
    context.getFilters().add(new ParameterFilter());
    server.setExecutor(Executors.newCachedThreadPool());
    server.start();
    System.out.println("Server is listening on port 8090" );
  }
}

class CoarseGrainedHandler implements HttpHandler {
  public void handle(HttpExchange exchange) throws IOException {
	  String info = new String();
	  String newlog = new String();
	  
	  Map<String, Object> params =
	           (Map<String, Object>)exchange.getAttribute("parameters"); 
	  if(params.containsKey("QUERY"))
	  {
		  String flight_name = (String) params.get("QUERY");
		  info = CoarseGrainedflights.getInfo(flight_name);
		  newlog = exchange.getRemoteAddress().toString() + "QUERY FOR "+ flight_name + "\r\n";
		  CoarseGrainedflights.log += newlog;
		  //System.out.print(newlog);
	  }
	  else if(params.containsKey("ORDER")) {
		  String flight_name = (String) params.get("ORDER");
		  info = CoarseGrainedflights.orderTicket(flight_name);
		  newlog = exchange.getRemoteAddress().toString() + "  "+info + "\r\n";
		  CoarseGrainedflights.log += newlog;
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
    }
  }
  
}

class ParameterFilter extends Filter {

    @Override
    public String description() {
        return "Parses the requested URI for parameters";
    }

    @Override
    public void doFilter(HttpExchange exchange, Chain chain)
        throws IOException {
        parseGetParameters(exchange);
        parsePostParameters(exchange);
        chain.doFilter(exchange);
    }    

    private void parseGetParameters(HttpExchange exchange)
        throws UnsupportedEncodingException {

        Map<String, Object> parameters = new HashMap<String, Object>();
        URI requestedUri = exchange.getRequestURI();
        String query = requestedUri.getRawQuery();
        parseQuery(query, parameters);
        exchange.setAttribute("parameters", parameters);
    }

    private void parsePostParameters(HttpExchange exchange)
        throws IOException {

        if ("post".equalsIgnoreCase(exchange.getRequestMethod())) {
            @SuppressWarnings("unchecked")
            Map<String, Object> parameters =
                (Map<String, Object>)exchange.getAttribute("parameters");
            InputStreamReader isr =
                new InputStreamReader(exchange.getRequestBody(),"utf-8");
            BufferedReader br = new BufferedReader(isr);
            String query = br.readLine();
            parseQuery(query, parameters);
        }
    }

     @SuppressWarnings("unchecked")
     private void parseQuery(String query, Map<String, Object> parameters)
         throws UnsupportedEncodingException {

         if (query != null) {
             String pairs[] = query.split("[&]");

             for (String pair : pairs) {
                 String param[] = pair.split("[=]");

                 String key = null;
                 String value = null;
                 if (param.length > 0) {
                     key = URLDecoder.decode(param[0],
                         System.getProperty("file.encoding"));
                 }

                 if (param.length > 1) {
                     value = URLDecoder.decode(param[1],
                         System.getProperty("file.encoding"));
                 }

                 if (parameters.containsKey(key)) {
                     Object obj = parameters.get(key);
                     if(obj instanceof List<?>) {
                         List<String> values = (List<String>)obj;
                         values.add(value);
                     } else if(obj instanceof String) {
                         List<String> values = new ArrayList<String>();
                         values.add((String)obj);
                         values.add(value);
                         parameters.put(key, values);
                     }
                 } else {
                     parameters.put(key, value);
                 }
             }
         }
    }
}
