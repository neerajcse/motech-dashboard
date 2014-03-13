package org.cometd.tutorials;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.ActiveMQConnectionFactory;
 
import javax.jms.*;
 
public class AMQConsumer implements MessageListener {
    private static int ackMode;
    private static String messageQueueName;
    private static String messageBrokerUrl;
 
    private Session session;
    private boolean transacted = false;
    private MessageProducer replyProducer;
 
    static {
        messageBrokerUrl = "tcp://localhost:61616";
        messageQueueName = "MY.TEST.FOO.QUEUE";
        ackMode = Session.AUTO_ACKNOWLEDGE;
    }
 
    public AMQConsumer() {
        System.out.println("***********Started AMQ consumer**********");
        try {
            //This message broker is embedded
            BrokerService broker = new BrokerService();
            broker.setPersistent(false);
            broker.setUseJmx(false);
            broker.addConnector(messageBrokerUrl);
            broker.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        //Delegating the handling of messages to another class, instantiate it before setting up JMS so it
        //is ready to handle messages
        //this.messageProtocol = new MessageProtocol();
        this.setupMessageQueueConsumer();
    }
 
    private void setupMessageQueueConsumer() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(messageBrokerUrl);
        Connection connection;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            this.session = connection.createSession(this.transacted, ackMode);
            Destination adminQueue = this.session.createQueue(messageQueueName);
 
            //Setup a message producer to respond to messages from clients, we will get the destination
            //to send to from the JMSReplyTo header field from a Message
            this.replyProducer = this.session.createProducer(null);
            this.replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
 
            //Set up a consumer to consume messages off of the admin queue
            MessageConsumer consumer = this.session.createConsumer(adminQueue);
            consumer.setMessageListener(this);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
 
    public void onMessage(Message message) {
        try {
            TextMessage response = this.session.createTextMessage();
            if (message instanceof TextMessage) {
                TextMessage txtMsg = (TextMessage) message;
                String messageText = txtMsg.getText();
                System.out.println("Consumer got message " + messageText);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}