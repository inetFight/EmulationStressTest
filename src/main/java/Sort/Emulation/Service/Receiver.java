package Sort.Emulation.Service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import Sort.Emulation.Gui.Gui;

public class Receiver {
	private ConnectionFactory factory = null;
	private Connection connection = null;
	private Session session = null;
	private Destination destination = null;
	private MessageConsumer consumer = null;

	public Receiver() {

	}

	public void receiveMessage(String Queues) {
		try {
			
			factory = new ActiveMQConnectionFactory("tcp://10.13.188.176:61616");
			
			
			((ActiveMQConnectionFactory)factory).setDispatchAsync(true);
			Gui.receiverConnectStatus.setText("<html><font color=\"Green\"><b>ОК</b></<font></html>");
			connection = factory.createConnection();
			
			connection.start();
			
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(Queues);
			
			consumer = session.createConsumer(destination);
			consumer.setMessageListener(new MessageController());
			
		} catch (JMSException e) {
			System.out.println(e.getMessage());
			Gui.receiverConnectStatus.setText("<html><font color=\"Red\"><b>Нет соединения</b></<font></html>");
			
		}
	}
}