package topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


/**
 * 消息发布者
 */
public class JMSProducer {

    //ActiveMq 的默认用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //ActiveMq 的默认登录密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //ActiveMQ 的链接地址
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;

    // 发送消息的数量
    private static final int SENDNUM = 10;

    public static void main(String[] args) {
        // 连接工厂
        ConnectionFactory connectionFactory;
        // 连接
        Connection connection = null;
        // 会话 发送或者接受消息的线程
        Session session;
        // 消息的目的地
        Destination destination;
        // 消息生产者
        MessageProducer messageProducer;
        // 实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD,
                JMSProducer.BROKEURL);

        try {
            // 通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            // 启动连接
            connection.start();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            // 创建消息队列
            destination = session.createTopic("danYY‘s Topic");
            // 创建消息生产者
            messageProducer = session.createProducer(destination);
            sendMessage(session, messageProducer);
            // 由于设置添加事务，这里需要使用提交才能将数据发送出去
            session.commit();

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    // 发送消息
    public static void sendMessage(Session session, MessageProducer messageProducer) {
        for (int i = 0; i < JMSProducer.SENDNUM; i++) {
            try {
                TextMessage message = session.createTextMessage("Active MQ发送消息" + i);
                System.out.println("发布消息：Active MQ发送消息");
                messageProducer.send(message);
            } catch (JMSException e) {
                // TODO Auto-generated catc h block
                e.printStackTrace();
            }
        }
    }
}
