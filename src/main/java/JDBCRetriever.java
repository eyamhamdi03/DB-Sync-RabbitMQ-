import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class JDBCRetriever {
    public List<ProductSale> getUnsyncedRecords(String branchDbUrl) {
        System.out.println("[BO] Checking for unsynced records in: " + branchDbUrl);
        List<ProductSale> sales = new ArrayList<>();
        String sql = "SELECT * FROM product_sales WHERE synced = false";
        
        try (Connection conn = DriverManager.getConnection(branchDbUrl, Config.DB_USER, Config.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                ProductSale sale = new ProductSale(
                    rs.getString("sale_date"),
                    rs.getString("region"),
                    rs.getString("product"),
                    rs.getInt("qty"),
                    rs.getDouble("cost"),
                    rs.getDouble("amt"),
                    rs.getDouble("tax"),
                    rs.getDouble("total")
                );
                sale.setId(rs.getInt("id"));
                sales.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("[BO] Found " + sales.size() + " unsynced records");
        return sales;
    }
}