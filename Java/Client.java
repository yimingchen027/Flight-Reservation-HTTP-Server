import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;


public class Client {
	final String GET_URL = "http://localhost:8090";
	String[] flight_name = {"UA001", "UA002","UA003", "UA004", "UA005"};
	String[] day = {"MON", "TUE", "WED", "THU", "FRI"};
	Random random = new Random();
	public String randomFlightkey(){
		int flight_index = (int)(Math.random() * 4); 
		int day_index = (int)(Math.random() * 4);
		String flight_key = flight_name[flight_index]+"_"+day[day_index];
		return flight_key;
	}
	public void randomQuery() throws IOException{
		String getURL = GET_URL + "?QUERY=" + URLEncoder.encode(randomFlightkey(), "utf-8");
		URL url = new URL(getURL);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
//        String lines;
//        while ((lines = reader.readLine()) != null) {
//            System.out.println(lines);
//        }
        reader.close();
        // 断开连接
        connection.disconnect();
	}
	
	public void randomOrder() throws IOException{
		String getURL = GET_URL + "?ORDER=" + URLEncoder.encode(randomFlightkey(), "utf-8");
		URL url = new URL(getURL);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
//        String lines;
//        while ((lines = reader.readLine()) != null) {
//            System.out.println(lines);
//        }
        reader.close();
        // 断开连接
        connection.disconnect();
		
	}
	
	public void certainOrder() throws IOException{
		String getURL = GET_URL + "?ORDER=" + URLEncoder.encode("UA001_MON", "utf-8");
		URL url = new URL(getURL);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
//        String lines;
//        while ((lines = reader.readLine()) != null) {
//            System.out.println(lines);
//        }
        reader.close();
        // 断开连接
        connection.disconnect();
		
	}

}
