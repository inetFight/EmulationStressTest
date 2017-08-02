package Sort.Emulation.Helpers;


import java.util.HashMap;
import java.util.Random;

public class MessageIdGenerator {
	final private static HashMap<Integer, String> arrayIdMessage = new HashMap<Integer, String>();
	public static String GenerateNext(){
		Random rnd = new Random();
		int n = 1;
		while(true){
			n = 100000 + rnd.nextInt(899999);
			if(arrayIdMessage.get(n) != null){
				continue;
			}
			else {
				arrayIdMessage.put(n, Integer.toString(n));
				break;
			}
		}
		setUsedIdToArray(n);
		
		
		return Integer.toString(n);
	}
	
	private static void setUsedIdToArray(int n){
		arrayIdMessage.put(n, Integer.toString(n));
	}
	public static void removeIdFromArray(String n){
		arrayIdMessage.remove(String.valueOf(n));
	}
}
