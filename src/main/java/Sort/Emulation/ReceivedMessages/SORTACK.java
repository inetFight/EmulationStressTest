package Sort.Emulation.ReceivedMessages;

import java.awt.Rectangle;
import java.text.SimpleDateFormat;
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
		SimpleDateFormat format = new SimpleDateFormat("mm:ss,SSS");

		Object [] row = {
				HPIC,
				GuiStressTest.data.get(HPIC).getBarcode1(),
				GuiStressTest.data.get(HPIC).getBarcode2(),
				GuiStressTest.data.get(HPIC).getBarcode3(),
				GuiStressTest.data.get(HPIC).getBarcode4(),
				GuiStressTest.data.get(HPIC).getBarcode5(),
				format.format(GuiStressTest.data.get(HPIC).getSORTREQTIME()),
				format.format(GuiStressTest.data.get(HPIC).getSORTRPLTIME()),
				(GuiStressTest.data.get(HPIC).getSORTRPLTIME().getTime() + 3600000) - GuiStressTest.data.get(HPIC).getSORTREQTIME().getTime(),
				format.format(GuiStressTest.data.get(HPIC).getSORTRPTTIME()),
				format.format(GuiStressTest.data.get(HPIC).getSORTACKTIME()),
				(GuiStressTest.data.get(HPIC).getSORTACKTIME().getTime() + 3600000) - GuiStressTest.data.get(HPIC).getSORTRPTTIME().getTime()
						};
		GuiStressTest.dtm.addRow(row);
		GuiStressTest.table.scrollRectToVisible(new Rectangle(0, GuiStressTest.table.getHeight() - 1, GuiStressTest.table.getWidth(), 1));
		HpicGenerator.removeIdFromArray(HPIC);
		
	}

}
