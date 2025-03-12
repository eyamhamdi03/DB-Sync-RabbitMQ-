import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RabbitMQSender {
    private final ConnectionFactory factory;
    private final String queueName;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RabbitMQSender() throws Exception {
        factory = new ConnectionFactory();
        factory.setHost(Config.RABBITMQ_HOST);
        queueName = Config.QUEUE_NAME;
    }

    public void sendSale(ProductSale sale) throws Exception {
            System.out.println("[BO] Sent sale ID " + sale.getId() + " to RabbitMQ");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
        channel.queueDeclare(
        queueName, 
        true, 
        false,  
        false,
        null
        );            
        String message = objectMapper.writeValueAsString(sale);
            channel.basicPublish("", queueName, null, message.getBytes());
        }
    }
}