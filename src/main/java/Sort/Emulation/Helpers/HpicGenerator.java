package Sort.Emulation.Helpers;

import java.util.HashMap;
import java.util.Random;

public class HpicGenerator {
	final private static HashMap<Long, Long> arryHPIC = new HashMap<Long, Long>();
	public static String GenerateHPIC(){
		Random rnd = new Random();
		Long n = (long) 1;
		while(true){
			n = (long) (1000000000 + rnd.nextInt(999999999));
			if(arryHPIC.get(n) != null){
				continue;
			}
			else {
				arryHPIC.put(n, n);
				break;
			}
		}
		
		setUsedIdToArray(n);
		
		
		return Long.toString(n);
	}
	
	private static void setUsedIdToArray(long n){
		arryHPIC.put(n, n);
	}
	public static void removeIdFromArray(String n){
		arryHPIC.remove(Long.parseLong(n));
	}
}
