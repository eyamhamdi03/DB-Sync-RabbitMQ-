import com.rabbitmq.client.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;  

public class RabbitMQReceiver {
    private final ConnectionFactory factory;
    private final String queueName;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JDBCPreparedInserter inserter = new JDBCPreparedInserter();

    public RabbitMQReceiver() throws Exception {
        factory = new ConnectionFactory();
        factory.setHost(Config.RABBITMQ_HOST);
        queueName = Config.QUEUE_NAME;
    }

    public void startConsuming() throws Exception {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
channel.queueDeclare(
    queueName, 
    true,   
    false, 
    false, 
    null
);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
    try {

        String message = new String(delivery.getBody(), "UTf-8");
        System.out.println("[HO] Raw message received: " + message); 
        
        ProductSale sale = objectMapper.readValue(message, ProductSale.class);
        System.out.println("[HO] Deserialized: " + sale.getProduct()); // Verify object
        
        inserter.insertRecords(Collections.singletonList(sale));
    } catch (Exception e) {
        System.err.println("[HO] Message processing failed:");
        e.printStackTrace();
    }
};
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}