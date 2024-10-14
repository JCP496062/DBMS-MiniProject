import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.*;

public class PropertyRentalGUI extends JFrame {
    private Connection connection;
    private JTextArea outputArea;

    public PropertyRentalGUI() {
        // Initialize GUI components
        setTitle("Property Rental Management System");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton userButton = new JButton("User");
        JButton propertyButton = new JButton("Property");
        JButton rentalContractButton = new JButton("Rental Contract");
        JButton paymentButton = new JButton("Payment");
        JButton maintenanceButton = new JButton("Maintenance Request");

        userButton.addActionListener(e -> showUserOptions());
        propertyButton.addActionListener(e -> showPropertyOptions());
        rentalContractButton.addActionListener(e -> showRentalContractOptions());
        paymentButton.addActionListener(e -> showPaymentOptions());
        maintenanceButton.addActionListener(e -> showMaintenanceOptions());

        add(userButton);
        add(propertyButton);
        add(rentalContractButton);
        add(paymentButton);
        add(maintenanceButton);

        outputArea = new JTextArea(10, 50);
        add(new JScrollPane(outputArea));

        connectToDatabase(); // Connect to the database
    }

    private void connectToDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/propertyrentalmanagement", "postgres", "admin");
            outputArea.append("Connected to the database successfully.\n");
        } catch (SQLException | ClassNotFoundException e) {
            outputArea.append("Error connecting to database: " + e.getMessage() + "\n");
        }
    }

    // User options
    private void showUserOptions() {
        String[] options = {"Create User", "Delete User", "Update User", "Search User"};
        int choice = JOptionPane.showOptionDialog(this, "Choose an option:", "User Management", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0 -> createUser();
            case 1 -> deleteUser();
            case 2 -> updateUser();
            case 3 -> searchUser();
        }
    }

    private void createUser() {
        String name = JOptionPane.showInputDialog("Enter Name:");
        String email = JOptionPane.showInputDialog("Enter Email:");
        String password = JOptionPane.showInputDialog("Enter Password:");
        String role = JOptionPane.showInputDialog("Enter Role:");
        String phone = JOptionPane.showInputDialog("Enter Phone:");

        String sql = "INSERT INTO Users (Name, Email, Password, Role, Phone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setString(4, role);
            pstmt.setString(5, phone);
            pstmt.executeUpdate();
            outputArea.append("User created successfully.\n");
        } catch (SQLException e) {
            outputArea.append("Error creating user: " + e.getMessage() + "\n");
        }
    }

    private void deleteUser() {
        String userIdStr = JOptionPane.showInputDialog("Enter User ID to delete:");
        int userId = Integer.parseInt(userIdStr);

        String sql = "DELETE FROM Users WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                outputArea.append("User deleted successfully.\n");
            } else {
                outputArea.append("User ID not found.\n");
            }
        } catch (SQLException e) {
            outputArea.append("Error deleting user: " + e.getMessage() + "\n");
        }
    }

    private void updateUser() {
        String userIdStr = JOptionPane.showInputDialog("Enter User ID to update:");
        int userId = Integer.parseInt(userIdStr);
        String name = JOptionPane.showInputDialog("Enter new Name:");
        String email = JOptionPane.showInputDialog("Enter new Email:");
        String password = JOptionPane.showInputDialog("Enter Password:");
        String role = JOptionPane.showInputDialog("Enter new Role:");
        String phone = JOptionPane.showInputDialog("Enter new Phone:");

        String sql = "UPDATE Users SET Name = ?, Email = ?, Password = ?, Role = ?, Phone = ? WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setString(4, role);
            pstmt.setString(5, phone);
            pstmt.setInt(6, userId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                outputArea.append("User updated successfully.\n");
            } else {
                outputArea.append("User ID not found.\n");
            }
        } catch (SQLException e) {
            outputArea.append("Error updating user: " + e.getMessage() + "\n");
        }
    }

    private void searchUser() {
        String userIdStr = JOptionPane.showInputDialog("Enter User ID to search:");
        int userId = Integer.parseInt(userIdStr);

        String sql = "SELECT * FROM Users WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String userInfo = "UserID: " + rs.getInt("UserID") + ", Name: " + rs.getString("Name") +
                                  ", Email: " + rs.getString("Email") + ", Password: " + rs.getString("Password") +
                                  ", Role: " + rs.getString("Role") + ", Phone: " + rs.getString("Phone") + "\n";
                outputArea.append(userInfo);
            } else {
                outputArea.append("User ID not found.\n");
            }
        } catch (SQLException e) {
            outputArea.append("Error searching user: " + e.getMessage() + "\n");
        }
    }

    // Property options
    private void showPropertyOptions() {
        String[] options = {"Create Property", "Delete Property", "Update Property", "Search Property"};
        int choice = JOptionPane.showOptionDialog(this, "Choose an option:", "Property Management", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0 -> createProperty();
            case 1 -> deleteProperty();
            case 2 -> updateProperty();
            case 3 -> searchProperty();
        }
    }

    private void createProperty() {
        String ownerIdStr = JOptionPane.showInputDialog("Enter Owner ID:");
        int ownerId = Integer.parseInt(ownerIdStr);
        String address = JOptionPane.showInputDialog("Enter address:");
        String rentStr = JOptionPane.showInputDialog("Enter rent:");
        BigDecimal rent = new BigDecimal(rentStr);
        String sizeStr = JOptionPane.showInputDialog("Enter size:");
        int size = Integer.parseInt(sizeStr);
        String numberOfRoomsStr = JOptionPane.showInputDialog("Enter number of rooms:");
        int numberOfRooms = Integer.parseInt(numberOfRoomsStr);
        String availabilityStatus = JOptionPane.showInputDialog("Enter availability status:");
        String location = JOptionPane.showInputDialog("Enter location:");

        String sql = "INSERT INTO Properties (OwnerID, Address, Rent, Size, NumberOfRooms, AvailabilityStatus, Location) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, ownerId);
            pstmt.setString(2, address);
            pstmt.setBigDecimal(3, rent);
            pstmt.setInt(4, size);
            pstmt.setInt(5, numberOfRooms);
            pstmt.setString(6, availabilityStatus);
            pstmt.setString(7, location);
            pstmt.executeUpdate();
            outputArea.append("Property created successfully.\n");
        } catch (SQLException e) {
            outputArea.append("Error creating property: " + e.getMessage() + "\n");
        }
    }

    private void deleteProperty() {
        String propertyIdStr = JOptionPane.showInputDialog("Enter Property ID to delete:");
        int propertyId = Integer.parseInt(propertyIdStr);

        String sql = "DELETE FROM Properties WHERE PropertyID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, propertyId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                outputArea.append("Property deleted successfully.\n");
            } else {
                outputArea.append("Property ID not found.\n");
            }
        } catch (SQLException e) {
            outputArea.append("Error deleting property: " + e.getMessage() + "\n");
        }
    }

    private void updateProperty() {
        String propertyIdStr = JOptionPane.showInputDialog("Enter Property ID to update:");
        int propertyId = Integer.parseInt(propertyIdStr);
        String address = JOptionPane.showInputDialog("Enter new address:");
        String rentStr = JOptionPane.showInputDialog("Enter new rent:");
        BigDecimal rent = new BigDecimal(rentStr);
        String sizeStr = JOptionPane.showInputDialog("Enter new size:");
        int size = Integer.parseInt(sizeStr);
        String numberOfRoomsStr = JOptionPane.showInputDialog("Enter new number of rooms:");
        int numberOfRooms = Integer.parseInt(numberOfRoomsStr);
        String availabilityStatus = JOptionPane.showInputDialog("Enter new availability status:");
        String location = JOptionPane.showInputDialog("Enter new location:");

        String sql = "UPDATE Properties SET Address = ?, Rent = ?, Size = ?, NumberOfRooms = ?, AvailabilityStatus = ?, Location = ? WHERE PropertyID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, address);
            pstmt.setBigDecimal(2, rent);
            pstmt.setInt(3, size);
            pstmt.setInt(4, numberOfRooms);
            pstmt.setString(5, availabilityStatus);
            pstmt.setString(6, location);
            pstmt.setInt(7, propertyId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                outputArea.append("Property updated successfully.\n");
            } else {
                outputArea.append("Property ID not found.\n");
            }
        } catch (SQLException e) {
            outputArea.append("Error updating property: " + e.getMessage() + "\n");
        }
    }

    private void searchProperty() {
        String propertyIdStr = JOptionPane.showInputDialog("Enter Property ID to search:");
        int propertyId = Integer.parseInt(propertyIdStr);

        String sql = "SELECT * FROM Properties WHERE PropertyID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, propertyId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String propertyInfo = "PropertyID: " + rs.getInt("PropertyID") + ", OwnerID: " + rs.getInt("OwnerID") +
                                      ", Address: " + rs.getString("Address") + ", Rent: " + rs.getBigDecimal("Rent") +
                                      ", Size: " + rs.getInt("Size") + ", Number of Rooms: " + rs.getInt("NumberOfRooms") +
                                      ", Availability Status: " + rs.getString("AvailabilityStatus") +
                                      ", Location: " + rs.getString("Location") + "\n";
                outputArea.append(propertyInfo);
            } else {
                outputArea.append("Property ID not found.\n");
            }
        } catch (SQLException e) {
            outputArea.append("Error searching property: " + e.getMessage() + "\n");
        }
    }

    // Rental Contract options
    private void showRentalContractOptions() {
        String[] options = {"Create Rental Contract", "Delete Rental Contract", "Search Rental Contract"};
        int choice = JOptionPane.showOptionDialog(this, "Choose an option:", "Rental Contract Management", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0 -> createRentalContract();
            case 1 -> deleteRentalContract();
            case 2 -> searchRentalContract();
        }
    }

    private void createRentalContract() {
        String propertyIdStr = JOptionPane.showInputDialog("Enter Property ID:");
        int propertyId = Integer.parseInt(propertyIdStr);
        String userIdStr = JOptionPane.showInputDialog("Enter User ID:");
        int userId = Integer.parseInt(userIdStr);
        String startDate = JOptionPane.showInputDialog("Enter start date (YYYY-MM-DD):");
        String endDate = JOptionPane.showInputDialog("Enter end date (YYYY-MM-DD):");
        String rentAmountStr = JOptionPane.showInputDialog("Enter rent amount:");
        BigDecimal rentAmount = new BigDecimal(rentAmountStr);

        String sql = "INSERT INTO RentalContracts (PropertyID, UserID, StartDate, EndDate, RentAmount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, propertyId);
            pstmt.setInt(2, userId);
            pstmt.setDate(3, Date.valueOf(startDate));
            pstmt.setDate(4, Date.valueOf(endDate));
            pstmt.setBigDecimal(5, rentAmount);
            pstmt.executeUpdate();
            outputArea.append("Rental contract created successfully.\n");
        } catch (SQLException e) {
            outputArea.append("Error creating rental contract: " + e.getMessage() + "\n");
        }
    }

    private void deleteRentalContract() {
        String contractIdStr = JOptionPane.showInputDialog("Enter Contract ID to delete:");
        int contractId = Integer.parseInt(contractIdStr);

        String sql = "DELETE FROM RentalContracts WHERE ContractID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, contractId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                outputArea.append("Rental contract deleted successfully.\n");
            } else {
                outputArea.append("Contract ID not found.\n");
            }
        } catch (SQLException e) {
            outputArea.append("Error deleting rental contract: " + e.getMessage() + "\n");
        }
    }

    private void searchRentalContract() {
        String contractIdStr = JOptionPane.showInputDialog("Enter Contract ID to search:");
        int contractId = Integer.parseInt(contractIdStr);

        String sql = "SELECT * FROM RentalContracts WHERE ContractID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, contractId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String contractInfo = "ContractID: " + rs.getInt("ContractID") + ", PropertyID: " + rs.getInt("PropertyID") +
                                      ", UserID: " + rs.getInt("UserID") + ", StartDate: " + rs.getDate("StartDate") +
                                      ", EndDate: " + rs.getDate("EndDate") + ", RentAmount: " + rs.getBigDecimal("RentAmount") + "\n";
                outputArea.append(contractInfo);
            } else {
                outputArea.append("Contract ID not found.\n");
            }
        } catch (SQLException e) {
            outputArea.append("Error searching rental contract: " + e.getMessage() + "\n");
        }
    }

    // Payment options
    private void showPaymentOptions() {
        String[] options = {"Create Payment", "Delete Payment", "Search Payment"};
        int choice = JOptionPane.showOptionDialog(this, "Choose an option:", "Payment Management", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0 -> createPayment();
            case 1 -> deletePayment();
            case 2 -> searchPayment();
        }
    }

    private void createPayment() {
        String contractIdStr = JOptionPane.showInputDialog("Enter Contract ID:");
        int contractId = Integer.parseInt(contractIdStr);
        String paymentDate = JOptionPane.showInputDialog("Enter payment date (YYYY-MM-DD):");
        String amountStr = JOptionPane.showInputDialog("Enter payment amount:");
        BigDecimal amount = new BigDecimal(amountStr);

        String sql = "INSERT INTO Payments (ContractID, PaymentDate, Amount) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, contractId);
            pstmt.setDate(2, Date.valueOf(paymentDate));
            pstmt.setBigDecimal(3, amount);
            pstmt.executeUpdate();
            outputArea.append("Payment created successfully.\n");
        } catch (SQLException e) {
            outputArea.append("Error creating payment: " + e.getMessage() + "\n");
        }
    }

    private void deletePayment() {
        String paymentIdStr = JOptionPane.showInputDialog("Enter Payment ID to delete:");
        int paymentId = Integer.parseInt(paymentIdStr);

        String sql = "DELETE FROM Payments WHERE PaymentID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, paymentId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                outputArea.append("Payment deleted successfully.\n");
            } else {
                outputArea.append("Payment ID not found.\n");
            }
        } catch (SQLException e) {
            outputArea.append("Error deleting payment: " + e.getMessage() + "\n");
        }
    }

    private void searchPayment() {
        String paymentIdStr = JOptionPane.showInputDialog("Enter Payment ID to search:");
        int paymentId = Integer.parseInt(paymentIdStr);

        String sql = "SELECT * FROM Payments WHERE PaymentID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, paymentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String paymentInfo = "PaymentID: " + rs.getInt("PaymentID") + ", ContractID: " + rs.getInt("ContractID") +
                                     ", PaymentDate: " + rs.getDate("PaymentDate") + ", Amount: " + rs.getBigDecimal("Amount") + "\n";
                outputArea.append(paymentInfo);
            } else {
                outputArea.append("Payment ID not found.\n");
            }
        } catch (SQLException e) {
            outputArea.append("Error searching payment: " + e.getMessage() + "\n");
        }
    }

    // Maintenance Request options
    private void showMaintenanceOptions() {
        String[] options = {"Create Maintenance Request", "Delete Maintenance Request", "Update Maintenance Request", "Search Maintenance Request"};
        int choice = JOptionPane.showOptionDialog(this, "Choose an option:", "Maintenance Request Management", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    
        switch (choice) {
            case 0 -> createMaintenanceRequest();
            case 1 -> deleteMaintenanceRequest();
            case 2 -> updateMaintenanceRequest();
            case 3 -> searchMaintenanceRequest();
        }
    }
    
    private void createMaintenanceRequest() {
        String propertyIdStr = JOptionPane.showInputDialog("Enter Property ID:");
        int propertyId = Integer.parseInt(propertyIdStr);
        String requestDetails = JOptionPane.showInputDialog("Enter maintenance request details:");
        String requestDateStr = JOptionPane.showInputDialog("Enter Request Date (YYYY-MM-DD):");
        String status = "Pending"; // Default status
    
        String sql = "INSERT INTO MaintenanceRequests (PropertyID, RequestDate, Description, Status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, propertyId);
            pstmt.setDate(2, java.sql.Date.valueOf(requestDateStr)); // Convert to SQL Date
            pstmt.setString(3, requestDetails);
            pstmt.setString(4, status);
            pstmt.executeUpdate();
            outputArea.append("Maintenance request created successfully.\n");
        } catch (SQLException e) {
            outputArea.append("Error creating maintenance request: " + e.getMessage() + "\n");
        }
    }
    
    private void deleteMaintenanceRequest() {
        String requestIdStr = JOptionPane.showInputDialog("Enter Request ID to delete:");
        int requestId = Integer.parseInt(requestIdStr);
    
        String sql = "DELETE FROM MaintenanceRequests WHERE RequestID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, requestId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                outputArea.append("Maintenance request deleted successfully.\n");
            } else {
                outputArea.append("Request ID not found.\n");
            }
        } catch (SQLException e) {
            outputArea.append("Error deleting maintenance request: " + e.getMessage() + "\n");
        }
    }
    
    private void updateMaintenanceRequest() {
        String requestIdStr = JOptionPane.showInputDialog("Enter Request ID to update:");
        int requestId = Integer.parseInt(requestIdStr);
        String newDetails = JOptionPane.showInputDialog("Enter new details:");
        String newStatus = JOptionPane.showInputDialog("Enter new status (default is 'Pending'):");
    
        String sql = "UPDATE MaintenanceRequests SET Description = ?, Status = ? WHERE RequestID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newDetails);
            pstmt.setString(2, newStatus);
            pstmt.setInt(3, requestId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                outputArea.append("Maintenance request updated successfully.\n");
            } else {
                outputArea.append("Request ID not found.\n");
            }
        } catch (SQLException e) {
            outputArea.append("Error updating maintenance request: " + e.getMessage() + "\n");
        }
    }
    
    private void searchMaintenanceRequest() {
        String requestIdStr = JOptionPane.showInputDialog("Enter Request ID to search:");
        int requestId = Integer.parseInt(requestIdStr);
    
        String sql = "SELECT * FROM MaintenanceRequests WHERE RequestID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, requestId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String requestInfo = "RequestID: " + rs.getInt("RequestID") + 
                                     ", PropertyID: " + rs.getInt("PropertyID") + 
                                     ", RequestDate: " + rs.getDate("RequestDate") + 
                                     ", Description: " + rs.getString("Description") + 
                                     ", Status: " + rs.getString("Status") + "\n";
                outputArea.append(requestInfo);
            } else {
                outputArea.append("Request ID not found.\n");
            }
        } catch (SQLException e) {
            outputArea.append("Error searching maintenance request: " + e.getMessage() + "\n");
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PropertyRentalGUI gui = new PropertyRentalGUI();
            gui.setVisible(true);
        });
    }
}
