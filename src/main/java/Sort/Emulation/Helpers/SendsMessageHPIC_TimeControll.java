package Sort.Emulation.Helpers;

import java.util.HashMap;

public class SendsMessageHPIC_TimeControll {

final private static HashMap<String, Long> HPIC_ID_TIME = new HashMap<String, Long>();
	
	public static void addHPICId (String hpic){
		HPIC_ID_TIME.put(hpic, System.currentTimeMillis());
	}
	
	public static String getTimeResponseMessage(String hpic){
		if(HPIC_ID_TIME.get(hpic) != null){
		long totalTime = System.currentTimeMillis() - HPIC_ID_TIME.get(hpic) ;
		
		return String.valueOf(totalTime/(double)1000);
		}
		else {
			return "Нет HPIC";
		}
	}
}
