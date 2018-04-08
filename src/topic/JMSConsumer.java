package topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息订阅者
 */
public class JMSConsumer {
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;// 默认的连接的用户名
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;// 默认的连接密码
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;// 默认的连接地址

	public static void main(String[] args) {
		// 连接工厂
		ConnectionFactory connectionFactory;
		// 连接
		Connection connection = null;
		// 会话 接受或者发送消息的线程
		Session session;
		// 消息的目的地
		Destination destination;
		// 消息的消费者
		MessageConsumer messageConsumer;
		

		connectionFactory = new ActiveMQConnectionFactory(JMSConsumer.USERNAME, JMSConsumer.PASSWORD,
				JMSConsumer.BROKEURL);
		try {
			// 通过连接工厂获取连接
			connection=connectionFactory.createConnection();
			// 启动连接
			connection.start();
			// 创建会话Session
			session=connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			destination=session.createTopic("danYY‘s Topic");
			// 创建消息消费者
			messageConsumer=session.createConsumer(destination);
			// 注册消息监听
			messageConsumer.setMessageListener(new Listener());
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
