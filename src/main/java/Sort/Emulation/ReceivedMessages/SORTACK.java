package Sort.Emulation.ReceivedMessages;

import javax.xml.bind.JAXBElement;

import Sort.Emulation.Helpers.HpicGenerator;
import Sort.Emulation.Helpers.MessageIdGenerator;
import Sort.Emulation.Models.FromXSD.MSG;
import Sort.Emulation.Models.FromXSD.MSG.BODY;
import Sort.Emulation.Models.FromXSD.MSG.HEADER;

public class SORTACK {
	
	public static void sortackLogic(MSG msg){
		HEADER head = msg.getHEADER();
		BODY body = msg.getBODY();
		String hdmgid = head.getHDMGID();
		MessageIdGenerator.removeIdFromArray(hdmgid);
		String HPIC = null;
		for (JAXBElement<?> element : body.getHPICOrPIBOrPAB()) {
			if(element.getName().toString().equals("HPIC")){
				HPIC = (String) element.getValue();
			}
			
		}
		
		HpicGenerator.removeIdFromArray(HPIC);
	}

}
