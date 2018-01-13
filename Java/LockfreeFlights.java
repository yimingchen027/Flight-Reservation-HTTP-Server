import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;
//import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantLock;


public class LockfreeFlights {
	public static Hashtable<String, LockfreeSingleFlight> flights = new Hashtable<String, LockfreeSingleFlight>();
	public static String log;
	//public static ReentrantLock lock = new ReentrantLock();
	public LockfreeFlights(){
		flights.put("UA001_MON", new LockfreeSingleFlight("UA001","MON","1PM-2PM",200,200, 100));
		flights.put("UA001_TUE", new LockfreeSingleFlight("UA001","TUE","1PM-2PM",200,200, 100));
		flights.put("UA001_WED", new LockfreeSingleFlight("UA001","WED","1PM-2PM",200,200, 100));
		flights.put("UA001_THU", new LockfreeSingleFlight("UA001","THU","1PM-2PM",200,200, 100));
		flights.put("UA001_FRI", new LockfreeSingleFlight("UA001","FRI","1PM-2PM",200,200, 100));
		
		flights.put("UA002_MON", new LockfreeSingleFlight("UA002","MON","2PM-3PM",200,200, 200));
		flights.put("UA002_TUE", new LockfreeSingleFlight("UA002","TUE","2PM-3PM",200,200, 200));
		flights.put("UA002_WED", new LockfreeSingleFlight("UA002","WED","2PM-3PM",200,200, 200));
		flights.put("UA002_THU", new LockfreeSingleFlight("UA002","THU","2PM-3PM",200,200, 200));
		flights.put("UA002_FRI", new LockfreeSingleFlight("UA002","FRI","2PM-3PM",200,200, 200));
		
		flights.put("UA003_MON", new LockfreeSingleFlight("UA003","MON","3PM-4PM",200,200, 300));
		flights.put("UA003_TUE", new LockfreeSingleFlight("UA003","TUE","3PM-4PM",200,200, 300));
		flights.put("UA003_WED", new LockfreeSingleFlight("UA003","WED","3PM-4PM",200,200, 300));
		flights.put("UA003_THU", new LockfreeSingleFlight("UA003","THU","3PM-4PM",200,200, 300));
		flights.put("UA003_FRI", new LockfreeSingleFlight("UA003","FRI","3PM-4PM",200,200, 300));
		
		flights.put("UA004_MON", new LockfreeSingleFlight("UA004","MON","4PM-5PM",200,200, 400));
		flights.put("UA004_TUE", new LockfreeSingleFlight("UA004","TUE","4PM-5PM",200,200, 400));
		flights.put("UA004_WED", new LockfreeSingleFlight("UA004","WED","4PM-5PM",200,200, 400));
		flights.put("UA004_THU", new LockfreeSingleFlight("UA004","THU","4PM-5PM",200,200, 400));
		flights.put("UA004_FRI", new LockfreeSingleFlight("UA004","FRI","4PM-5PM",200,200, 400));
		
		flights.put("UA005_MON", new LockfreeSingleFlight("UA005","MON","5PM-6PM",200,200, 500));
		flights.put("UA005_TUE", new LockfreeSingleFlight("UA005","TUE","5PM-6PM",200,200, 500));
		flights.put("UA005_WED", new LockfreeSingleFlight("UA005","WED","5PM-6PM",200,200, 500));
		flights.put("UA005_THU", new LockfreeSingleFlight("UA005","THU","5PM-6PM",200,200, 500));
		flights.put("UA005_FRI", new LockfreeSingleFlight("UA005","FRI","5PM-6PM",200,200, 500));
		
		log = new String();
	}
	
	public static String getInfo(String flight_name){
		return flights.get(flight_name).getInfo();
	}
	
	public static String orderTicket(String flight_name){
		String info = new String();
		info = flights.get(flight_name).orderOneTicket();
		return info;
	}

}

class LockfreeSingleFlight{
	public String name;
	public String day;
	public String time;
	public int total;
	public AtomicInteger left = new AtomicInteger();
	public int price;
	public ReentrantLock lock;
	public LockfreeSingleFlight(String _name, String _day, String _time, int _total, int _left, int _price ){
		name = _name;
		day = _day;
		time = _time;
		total = _total;
		left.set(_left);
		price = _price;		
	}
	
	public String getInfo(){
		String info = name + day + time + "\r\n total " + total + " tickets, "+ left + " tickets left" + "\r\n price is: "+ price;
		return info;
	}
	
	public String orderOneTicket(){
		String result;
		if (left.get() >= 1){
			left.getAndDecrement();
			result = this.name + ' '+ this.day+ " 1 ticket sucessfully reserved, still "+ left +" tickets left";
		}
		else{
			result = this.name + ' '+ this.day+ " Reservation failed, not engough tickets";
		}
		
		return result;
	}
	
}


