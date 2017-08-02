package Sort.Emulation.SendMessages;

import java.io.StringWriter;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import Sort.Emulation.Helpers.HpicGenerator;
import Sort.Emulation.Helpers.MessageIdGenerator;
import Sort.Emulation.Helpers.SendsMessageHPIC_TimeControll;
import Sort.Emulation.Helpers.SendsMessagesTimeController;
import Sort.Emulation.Helpers.TimeStamp;
import Sort.Emulation.Models.FromXSD.MSG;
import Sort.Emulation.Models.FromXSD.ObjectFactory;
import Sort.Emulation.Models.FromXSD.MSG.HEADER;
import Sort.Emulation.Models.FromXSD.MSG.BODY;
import Sort.Emulation.Models.FromXSD.MSG.BODY.PAB;
import Sort.Emulation.Models.FromXSD.MSG.BODY.PIB;
import Sort.Emulation.Service.Sender;

public class SORTREQ {

	public static void sendSortreq(final ArrayList<String> barcode) throws JAXBException {

		new Thread() {
			public void run() {

				try {
					Sender sendMessage = new Sender();

					ObjectFactory factory = new ObjectFactory();

					MSG msg = new MSG();
					HEADER header = new HEADER();
					BODY body = new BODY();
					String HPIC = new String();
					PAB pab = new PAB();
					header.setHDSDID("COY001");
					header.setHDRCID("NPHOST");
					header.setHDMGTP("SORTREQ");
					header.setHDMGID(MessageIdGenerator.GenerateNext());
					SendsMessagesTimeController.addMessageId(header.getHDMGID());
					header.setHDEVTM(TimeStamp.getTimeStamp());

					HPIC = HpicGenerator.GenerateHPIC();
					SendsMessageHPIC_TimeControll.addHPICId(HPIC);
					JAXBElement<String> hpicJAX = factory.createMSGBODYHPIC(HPIC);
					body.getHPICOrPIBOrPAB().add(hpicJAX);

					for (String code : barcode) {
						PIB pib = new PIB();
						pib.setRDID("CC_IC01_01");
						pib.setRDST("GR");
						pib.setCDTP("1");
						pib.setCDDT(code);

						JAXBElement<MSG.BODY.PIB> pibJAX = factory.createMSGBODYPIB(pib);
						body.getHPICOrPIBOrPAB().add(pibJAX);
					}
					pab.setCID("1");
					pab.setDDWE("321");
					pab.setLE("10.0");
					pab.setWI("20.0");
					pab.setHE("30.0");
					pab.setVO("6000");
					pab.setET(TimeStamp.getTimeStamp());
					pab.setIU("INF01");
					pab.setIT(TimeStamp.getTimeStamp());
					pab.setTR("N");

					JAXBElement<MSG.BODY.PAB> pabJAX = factory.createMSGBODYPAB(pab);
					body.getHPICOrPIBOrPAB().add(pabJAX);
					msg.setBODY(body);
					msg.setHEADER(header);
					JAXBContext jaxbContext = JAXBContext.newInstance(MSG.class);

					Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
					StringWriter sw = new StringWriter();
					jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
					jaxbMarshaller.marshal(msg, sw);
					String xmlMessage = sw.toString();
					sendMessage.sendMessage(xmlMessage);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}.start();

	}
}
