import java.sql.*;
import java.util.List;

public class JDBCPreparedInserter {
    public void insertRecords(List<ProductSale> sales) {
        System.out.println("[HO] Attempting to insert " + sales.size() + " records");
        
        String sql = "INSERT INTO product_sales (sale_date, region, product, qty, cost, amt, tax, total) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            

        
        try (Connection conn = DriverManager.getConnection(Config.HO_DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            for (ProductSale sale : sales) {
                System.out.println("[HO] Inserting: " + sale.getSaleDate() + ", " + sale.getProduct());
                pstmt.setString(1, sale.getSaleDate());
                pstmt.setString(2, sale.getRegion());
                pstmt.setString(3, sale.getProduct());
                pstmt.setInt(4, sale.getQty());
                pstmt.setDouble(5, sale.getCost());
                pstmt.setDouble(6, sale.getAmt());
                pstmt.setDouble(7, sale.getTax());
                pstmt.setDouble(8, sale.getTotal());
                pstmt.addBatch();
            }
            
            int[] results = pstmt.executeBatch();  
            System.out.println("[HO] Inserted " + results.length + " records");
            
        } catch (SQLException e) {
            System.err.println("[HO] SQL Error:");
            e.printStackTrace();
            System.err.println("Failed SQL: " + sql);
            return;  
        }
        
        System.out.println("[HO] Successfully inserted records");  // Moved here
    }
}