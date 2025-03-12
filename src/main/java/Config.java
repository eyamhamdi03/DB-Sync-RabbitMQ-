public class Config {
    // Database Configurations
    public static final String HO_DB_URL = "jdbc:mysql://localhost:3307/HO_DB?useSSL=false";
    public static final String BO1_DB_URL = "jdbc:mysql://localhost:3307/BO1_DB?useSSL=false";
    public static final String BO2_DB_URL = "jdbc:mysql://localhost:3307/BO2_DB?useSSL=false";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "eyamhamdi";
    
    // RabbitMQ Config
    public static final String RABBITMQ_HOST = "localhost";
    public static final String QUEUE_NAME = "HO_queue";
}