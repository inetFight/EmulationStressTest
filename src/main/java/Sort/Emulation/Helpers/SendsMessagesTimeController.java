package Sort.Emulation.Helpers;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;

public class SendsMessagesTimeController {
	final private static HashMap<String, Long> messagesIdAndTime = new HashMap<String, Long>();
	
	public static void addMessageId (String id){
		messagesIdAndTime.put(id, System.currentTimeMillis());
	}
	
	public static String getTimeResponseMessage(String id){
		if(messagesIdAndTime.get(id) != null){
		long totalTime = System.currentTimeMillis() - messagesIdAndTime.get(id) ;
		
		return String.valueOf(totalTime/(double)1000);
		}
		else {
			return "Нет HDMGID";
		}
	}
}
