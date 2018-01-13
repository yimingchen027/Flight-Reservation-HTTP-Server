import java.util.Hashtable;
import java.util.concurrent.locks.ReentrantLock;


public class SequentialFlights {
	public static Hashtable<String, singleFlight> flights = new Hashtable<String, singleFlight>();
	public static String log;
	public SequentialFlights(){
		flights.put("UA001_MON", new singleFlight("UA001","MON","1PM-2PM",200,200, 100));
		flights.put("UA001_TUE", new singleFlight("UA001","TUE","1PM-2PM",200,200, 100));
		flights.put("UA001_WED", new singleFlight("UA001","WED","1PM-2PM",200,200, 100));
		flights.put("UA001_THU", new singleFlight("UA001","THU","1PM-2PM",200,200, 100));
		flights.put("UA001_FRI", new singleFlight("UA001","FRI","1PM-2PM",200,200, 100));
		
		flights.put("UA002_MON", new singleFlight("UA002","MON","2PM-3PM",200,200, 200));
		flights.put("UA002_TUE", new singleFlight("UA002","TUE","2PM-3PM",200,200, 200));
		flights.put("UA002_WED", new singleFlight("UA002","WED","2PM-3PM",200,200, 200));
		flights.put("UA002_THU", new singleFlight("UA002","THU","2PM-3PM",200,200, 200));
		flights.put("UA002_FRI", new singleFlight("UA002","FRI","2PM-3PM",200,200, 200));
		
		flights.put("UA003_MON", new singleFlight("UA003","MON","3PM-4PM",200,200, 300));
		flights.put("UA003_TUE", new singleFlight("UA003","TUE","3PM-4PM",200,200, 300));
		flights.put("UA003_WED", new singleFlight("UA003","WED","3PM-4PM",200,200, 300));
		flights.put("UA003_THU", new singleFlight("UA003","THU","3PM-4PM",200,200, 300));
		flights.put("UA003_FRI", new singleFlight("UA003","FRI","3PM-4PM",200,200, 300));
		
		flights.put("UA004_MON", new singleFlight("UA004","MON","4PM-5PM",200,200, 400));
		flights.put("UA004_TUE", new singleFlight("UA004","TUE","4PM-5PM",200,200, 400));
		flights.put("UA004_WED", new singleFlight("UA004","WED","4PM-5PM",200,200, 400));
		flights.put("UA004_THU", new singleFlight("UA004","THU","4PM-5PM",200,200, 400));
		flights.put("UA004_FRI", new singleFlight("UA004","FRI","4PM-5PM",200,200, 400));
		
		flights.put("UA005_MON", new singleFlight("UA005","MON","5PM-6PM",200,200, 500));
		flights.put("UA005_TUE", new singleFlight("UA005","TUE","5PM-6PM",200,200, 500));
		flights.put("UA005_WED", new singleFlight("UA005","WED","5PM-6PM",200,200, 500));
		flights.put("UA005_THU", new singleFlight("UA005","THU","5PM-6PM",200,200, 500));
		flights.put("UA005_FRI", new singleFlight("UA005","FRI","5PM-6PM",200,200, 500));
		
		log = new String();
	}
	
	public static String getInfo(String flight_name){
		return flights.get(flight_name).getInfo();
	}
	
	public static String orderTicket(String flight_name){
		return flights.get(flight_name).orderOneTicket();
	}

}
