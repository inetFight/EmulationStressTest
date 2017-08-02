package Sort.Emulation.SendMessages;

import java.awt.Toolkit;
import java.io.StringWriter;
import java.util.TimerTask;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import Sort.Emulation.Helpers.HpicGenerator;
import Sort.Emulation.Helpers.MessageIdGenerator;
import Sort.Emulation.Helpers.SendsMessagesTimeController;
import Sort.Emulation.Helpers.TimeStamp;
import Sort.Emulation.Models.FromXSD.MSG;
import Sort.Emulation.Models.FromXSD.MSG.BODY;
import Sort.Emulation.Models.FromXSD.MSG.HEADER;
import Sort.Emulation.Service.Sender;
import Sort.Emulation.Service.SenderSortack;

public class HEARTBEAT extends TimerTask {

	public static void sendHeartBeat() {

		try {

			new Thread() {
				public void run() {
					MSG msg = new MSG();
					HEADER header = new HEADER();
					header.setHDSDID("COY001");
					header.setHDRCID("NPHOST");
					header.setHDMGTP("HEARTBEAT");
					header.setHDMGID(MessageIdGenerator.GenerateNext());
					header.setHDEVTM(TimeStamp.getTimeStamp());
					msg.setHEADER(header);
					msg.setBODY(new BODY());

					SenderSortack sendMessage = new SenderSortack();

					try {
						JAXBContext jaxbContext = JAXBContext.newInstance(MSG.class);
						Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
						StringWriter sw = new StringWriter();
						jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
						jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
						jaxbMarshaller.marshal(msg, sw);
						String xmlMessage = sw.toString();
						sendMessage.sendMessage(xmlMessage);
						MessageIdGenerator.removeIdFromArray(header.getHDMGID());
					} catch (JAXBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}.start();

		} catch (Exception e) {
			System.err.println(e.getMessage());

		}
	}

	@Override
	public void run() {
		sendHeartBeat();
	}

}
