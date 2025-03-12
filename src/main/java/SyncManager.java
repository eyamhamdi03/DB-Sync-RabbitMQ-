import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SyncManager {
    public static void main(String[] args) {
        // For BO1
        new Thread(() -> syncBranch(Config.BO1_DB_URL)).start();
        
        // For BO2
        new Thread(() -> syncBranch(Config.BO2_DB_URL)).start();
        
        // Start HO Receiver
        new Thread(() -> {
            try {
                new RabbitMQReceiver().startConsuming();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void syncBranch(String branchDbUrl) {
        JDBCRetriever retriever = new JDBCRetriever();
        try {
            RabbitMQSender sender = new RabbitMQSender();
            while (true) {
                List<ProductSale> unsynced = retriever.getUnsyncedRecords(branchDbUrl);
                for (ProductSale sale : unsynced) {
                    sender.sendSale(sale);
                    markAsSynced(branchDbUrl, sale.getId());
                }
                Thread.sleep(60000); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

private static void markAsSynced(String branchDbUrl, int id) {
    String sql = "UPDATE product_sales SET synced = true WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(branchDbUrl, Config.DB_USER, Config.DB_PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}