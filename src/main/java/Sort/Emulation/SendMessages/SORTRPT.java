package Sort.Emulation.SendMessages;

import java.io.StringWriter;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import Sort.Emulation.Helpers.HpicGenerator;
import Sort.Emulation.Helpers.MessageIdGenerator;
import Sort.Emulation.Helpers.TimeStamp;
import Sort.Emulation.Models.FromXSD.MSG;
import Sort.Emulation.Models.FromXSD.ObjectFactory;
import Sort.Emulation.Models.FromXSD.MSG.HEADER;
import Sort.Emulation.Models.FromXSD.MSG.BODY;
import Sort.Emulation.Models.FromXSD.MSG.BODY.PAB;
import Sort.Emulation.Models.FromXSD.MSG.BODY.PIB;
import Sort.Emulation.Models.FromXSD.MSG.BODY.SRB;
import Sort.Emulation.Service.Sender;

public class SORTRPT {

	public static void sendSortrpt(final String HPIC, final String code, final String DID) throws JAXBException {

		new Thread() {
			public void run() {
				try {

					ObjectFactory factory = new ObjectFactory();
					Sender sendMessage = new Sender();

					MSG msg = new MSG();
					HEADER header = new HEADER();
					BODY body = new BODY();

					PAB pab = new PAB();
					header.setHDSDID("COY001");
					header.setHDRCID("NPHOST");
					header.setHDMGTP("SORTRPT");
					header.setHDMGID(MessageIdGenerator.GenerateNext());
					header.setHDEVTM(TimeStamp.getTimeStamp());

					JAXBElement<String> hpicJAX = factory.createMSGBODYHPIC(HPIC);
					body.getHPICOrPIBOrPAB().add(hpicJAX);

					PIB pib = new PIB();
					pib.setRDID("CC_IC01_01");
					pib.setRDST("GR");
					pib.setCDTP("1");
					pib.setCDDT(code);

					JAXBElement<MSG.BODY.PIB> pibJAX = factory.createMSGBODYPIB(pib);
					body.getHPICOrPIBOrPAB().add(pibJAX);

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

					String dstat = "P";
					JAXBElement<String> dstatJAX = factory.createMSGBODYDSTAT(dstat);
					body.getHPICOrPIBOrPAB().add(dstatJAX);

					SRB srb = new SRB();
					srb.setDID(DID);
					srb.setDRID("ND");
					srb.setDRSID("1000");
					srb.setET(TimeStamp.getTimeStamp());
					JAXBElement<MSG.BODY.SRB> srbJAX = factory.createMSGBODYSRB(srb);
					body.getHPICOrPIBOrPAB().add(srbJAX);

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
