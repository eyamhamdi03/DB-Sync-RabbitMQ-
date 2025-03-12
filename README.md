# Distributed Sales Data Synchronization with RabbitMQ

## Overview

This project synchronizes sales data between multiple branch offices (BOs) and a Head Office (HO) using RabbitMQ for message queuing. The system involves two branch offices (BO1 and BO2) and one Head Office (HO), where each office stores product sales data in its own MySQL database. The synchronization is done using RabbitMQ messages, ensuring that the sales data is transferred even with limited internet connectivity for the branch offices.

## Database Setup

You can find the SQL scripts to create the databases and insert the initial data in the `sql` folder.

### Step 1: Set Up MySQL Databases

1. **Navigate to the `sql` folder** where the database setup scripts are located.

2. **Run the SQL scripts to create and populate the databases**:

   - **Create databases and tables**: Run `create_db.sql` using MySQL.
   - **Insert sample data**: Run `insert_db.sql` to insert some initial data into the databases.

   You can execute the SQL scripts with the following commands:

   ```bash
   mysql -u your_mysql_user -p < path_to_sql_folder/create_db.sql
   mysql -u your_mysql_user -p < path_to_sql_folder/insert_db.sql
   ```

   Replace `your_mysql_user` with your MySQL username and `path_to_sql_folder` with the path where the `sql` folder is located.

   Example (on Windows):

   ```bash
   mysql -u root -p < "C:\path_to_sql_folder\create_db.sql"
   mysql -u root -p < "C:\path_to_sql_folder\insert_db.sql"
   ```

## Setup and Installation

### Step 1: Install MySQL and RabbitMQ

- Install **MySQL** and set up the databases (HO_DB, BO1_DB, and BO2_DB) as described above.
- Install **RabbitMQ** and ensure it is running.

### Step 2: Configure the Databases

Ensure that the `product_sales` table exists in all three databases (HO_DB, BO1_DB, BO2_DB). You can use the SQL scripts provided earlier to create the tables.

### Step 3: Modify the Configuration File

Update the `config.java` file with your MySQL database connection details and RabbitMQ server configurations.

```java
public class Config {
    public static final String MYSQL_HOST = "localhost";
    public static final String MYSQL_USER = "root";
    public static final String MYSQL_PASSWORD = "password";
    public static final String MYSQL_DB_HO = "HO_DB";
    public static final String MYSQL_DB_BO1 = "BO1_DB";
    public static final String MYSQL_DB_BO2 = "BO2_DB";

    public static final String RABBITMQ_HOST = "localhost";
    public static final String QUEUE_NAME = "salesQueue";
}
```

### Step 4: Compile and Run

#### Step 2: Compile and Run the Java Application

To compile and run the Java application, follow these steps:

1. **Navigate to the project directory** where the source code is located.

   Example:
   ```bash
   cd "C:\path_to_your_project"
   ```

2. **Compile the Java files**:

   Run the following command to compile the Java code. This command includes all the dependencies in the `lib` folder and compiles the source files from the `src/main/java` directory:

   ```bash
   javac -cp "lib/*;src/main/java" src/main/java/*.java -d out/
   ```

3. **Run the application**:

   Once the code is compiled, you can run the `SyncManager` class to start the synchronization process. Use the following command:

   ```bash
   java -cp "out;lib/*" SyncManager
   ```

   This will start the synchronization process, where the BOs send sales data to the RabbitMQ queue, and the HO listens for these messages to synchronize the data.

## Code Structure

The project is structured as follows:

1. **config.java**: 
   - Contains configuration details for database connections and RabbitMQ settings.

2. **JdbcPreparedInserter.java**:
   - Handles inserting data into the databases (for both BOs and HO).

3. **JdbcRetriever.java**:
   - Retrieves data from the databases to be sent to RabbitMQ for synchronization.

4. **ProductSale.java**:
   - Defines the structure of a product sale record, including fields like `sale_date`, `region`, `product`, `qty`, `cost`, etc.

5. **RabbitMqReceiver.java**:
   - Listens to the RabbitMQ queue for incoming sales data and inserts it into the HO database.

6. **RabbitMqSender.java**:
   - Sends sales data from the BO databases to the RabbitMQ queue for synchronization with the HO.

7. **SyncManager.java**:
   - Manages the synchronization process between BOs and HO, ensuring that data is sent and received correctly through RabbitMQ.

## How It Works

1. **Branch Office 1 (BO1)** and **Branch Office 2 (BO2)** each manage their own sales data and periodically push un-synced data to RabbitMQ when an internet connection is available.

2. The **Head Office (HO)** listens to the RabbitMQ queue, retrieves the data, and stores it in its own database.

3. The synchronization ensures that sales data from each BO is eventually available at the HO.

## Syncing Data from BOs to HO

- Whenever data is inserted or updated in the `product_sales` table in either **BO1** or **BO2**, the `synced` flag will remain `FALSE` until the data is successfully synchronized with the Head Office (HO).
- Once the synchronization is completed successfully through RabbitMQ, the `synced` flag for that record will be updated to `TRUE`.
- As soon as **BO1** or **BO2** is able to connect to the RabbitMQ server, the system will automatically attempt to synchronize all unsynced data to the **HO**.

### Final Note

You can update the `product_sales` table in **BO1** or **BO2** at any time by adding records. These changes will be synchronized with the **HO** once the system is able to connect and send the data via RabbitMQ. This ensures that your data is always up to date across all offices, even with intermittent internet connectivity.

## Troubleshooting

- Ensure that RabbitMQ is running and accessible from both the BOs and HO.
- Check that your MySQL connection credentials are correct in the `config.java` file.
- Verify that the `product_sales` tables are created properly and that the `synced` flag is being updated accordingly in BO1 and BO2 databases.

