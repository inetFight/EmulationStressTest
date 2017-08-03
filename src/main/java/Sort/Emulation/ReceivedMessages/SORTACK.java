package Sort.Emulation.ReceivedMessages;

import java.util.Date;

import javax.xml.bind.JAXBElement;

import Sort.Emulation.Gui.GuiStressTest;
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
				GuiStressTest.data.get(HPIC).setSORTACKTIME(new Date());
			}
			
		}
		
		HpicGenerator.removeIdFromArray(HPIC);
	}

}
