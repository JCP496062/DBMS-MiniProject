import java.sql.*;
import java.util.Scanner;

public class PropertyRentalManagement {

    // User related methods
    private static void createUser(Connection conn, Scanner scanner) {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Role: ");
        String role = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();

        String sql = "INSERT INTO Users (Name, Email, Password, Role, Phone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setString(4, role);
            pstmt.setString(5, phone);
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int userId = generatedKeys.getInt(1);
                System.out.println("User created successfully. User ID: " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteUser(Connection conn, Scanner scanner) {
        System.out.print("Enter User ID to delete: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String sql = "DELETE FROM Users WHERE UserID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchUser(Connection conn, Scanner scanner) {
        System.out.print("Enter User ID to search: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String sql = "SELECT * FROM Users WHERE UserID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("User found:");
                System.out.println("UserID: " + rs.getInt("UserID"));
                System.out.println("Name: " + rs.getString("Name"));
                System.out.println("Email: " + rs.getString("Email"));
                System.out.println("Role: " + rs.getString("Role"));
                System.out.println("Phone: " + rs.getString("Phone"));
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Property related methods
    private static void createProperty(Connection conn, Scanner scanner) {
        System.out.print("Enter Owner ID: ");
        int ownerId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Rent: ");
        double rent = scanner.nextDouble();
        System.out.print("Enter Size: ");
        int size = scanner.nextInt();
        System.out.print("Enter Number of Rooms: ");
        int numberOfRooms = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Availability Status: ");
        String availabilityStatus = scanner.nextLine();
        System.out.print("Enter Location: ");
        String location = scanner.nextLine();

        String sql = "INSERT INTO Properties (OwnerID, Address, Rent, Size, NumberOfRooms, AvailabilityStatus, Location) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, ownerId);
            pstmt.setString(2, address);
            pstmt.setDouble(3, rent);
            pstmt.setInt(4, size);
            pstmt.setInt(5, numberOfRooms);
            pstmt.setString(6, availabilityStatus);
            pstmt.setString(7, location);
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int propertyId = generatedKeys.getInt(1);
                System.out.println("Property created successfully. Property ID: " + propertyId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteProperty(Connection conn, Scanner scanner) {
        System.out.print("Enter Property ID to delete: ");
        int propertyId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String sql = "DELETE FROM Properties WHERE PropertyID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, propertyId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Property deleted successfully.");
            } else {
                System.out.println("Property not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchProperty(Connection conn, Scanner scanner) {
        System.out.print("Enter Property ID to search: ");
        int propertyId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String sql = "SELECT * FROM Properties WHERE PropertyID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, propertyId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Property found:");
                System.out.println("PropertyID: " + rs.getInt("PropertyID"));
                System.out.println("OwnerID: " + rs.getInt("OwnerID"));
                System.out.println("Address: " + rs.getString("Address"));
                System.out.println("Rent: " + rs.getDouble("Rent"));
                System.out.println("Size: " + rs.getInt("Size"));
                System.out.println("Number of Rooms: " + rs.getInt("NumberOfRooms"));
                System.out.println("Availability Status: " + rs.getString("AvailabilityStatus"));
                System.out.println("Location: " + rs.getString("Location"));
            } else {
                System.out.println("Property not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Rental Contract related methods
    private static void createRentalContract(Connection conn, Scanner scanner) {
        System.out.print("Enter Property ID: ");
        int propertyId = scanner.nextInt();
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter Start Date (YYYY-MM-DD): ");
        String startDate = scanner.next();
        System.out.print("Enter End Date (YYYY-MM-DD): ");
        String endDate = scanner.next();
        System.out.print("Enter Rent Amount: ");
        double rentAmount = scanner.nextDouble();

        String sql = "INSERT INTO RentalContracts (PropertyID, UserID, StartDate, EndDate, RentAmount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, propertyId);
            pstmt.setInt(2, userId);
            pstmt.setDate(3, Date.valueOf(startDate));
            pstmt.setDate(4, Date.valueOf(endDate));
            pstmt.setDouble(5, rentAmount);
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int contractId = generatedKeys.getInt(1);
                System.out.println("Rental Contract created successfully. Contract ID: " + contractId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteRentalContract(Connection conn, Scanner scanner) {
        System.out.print("Enter Rental Contract ID to delete: ");
        int contractId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String sql = "DELETE FROM RentalContracts WHERE ContractID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, contractId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Rental Contract deleted successfully.");
            } else {
                System.out.println("Rental Contract not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchRentalContract(Connection conn, Scanner scanner) {
        System.out.print("Enter Rental Contract ID to search: ");
        int contractId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String sql = "SELECT * FROM RentalContracts WHERE ContractID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, contractId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Rental Contract found:");
                System.out.println("ContractID: " + rs.getInt("ContractID"));
                System.out.println("PropertyID: " + rs.getInt("PropertyID"));
                System.out.println("UserID: " + rs.getInt("UserID"));
                System.out.println("StartDate: " + rs.getDate("StartDate"));
                System.out.println("EndDate: " + rs.getDate("EndDate"));
                System.out.println("RentAmount: " + rs.getDouble("RentAmount"));
            } else {
                System.out.println("Rental Contract not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Payment related methods
    private static void createPayment(Connection conn, Scanner scanner) {
        System.out.print("Enter Rental Contract ID: ");
        int contractId = scanner.nextInt();
        System.out.print("Enter Payment Date (YYYY-MM-DD): ");
        String paymentDate = scanner.next();
        System.out.print("Enter Amount Paid: ");
        double amountPaid = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Payment Method: ");
        String paymentMethod = scanner.nextLine();

        String sql = "INSERT INTO Payments (ContractID, PaymentDate, AmountPaid, PaymentMethod) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, contractId);
            pstmt.setDate(2, Date.valueOf(paymentDate));
            pstmt.setDouble(3, amountPaid);
            pstmt.setString(4, paymentMethod);
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int paymentId = generatedKeys.getInt(1);
                System.out.println("Payment created successfully. Payment ID: " + paymentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deletePayment(Connection conn, Scanner scanner) {
        System.out.print("Enter Payment ID to delete: ");
        int paymentId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String sql = "DELETE FROM Payments WHERE PaymentID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, paymentId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Payment deleted successfully.");
            } else {
                System.out.println("Payment not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchPayment(Connection conn, Scanner scanner) {
        System.out.print("Enter Payment ID to search: ");
        int paymentId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String sql = "SELECT * FROM Payments WHERE PaymentID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, paymentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Payment found:");
                System.out.println("PaymentID: " + rs.getInt("PaymentID"));
                System.out.println("ContractID: " + rs.getInt("ContractID"));
                System.out.println("PaymentDate: " + rs.getDate("PaymentDate"));
                System.out.println("AmountPaid: " + rs.getDouble("AmountPaid"));
                System.out.println("PaymentMethod: " + rs.getString("PaymentMethod"));
            } else {
                System.out.println("Payment not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Maintenance Request related methods
    private static void createMaintenanceRequest(Connection conn, Scanner scanner) {
        System.out.print("Enter Property ID: ");
        int propertyId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Request Date (YYYY-MM-DD): ");
        String requestDate = scanner.next();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        String sql = "INSERT INTO MaintenanceRequests (PropertyID, RequestDate, Description) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, propertyId);
            pstmt.setDate(2, Date.valueOf(requestDate));
            pstmt.setString(3, description);
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int requestId = generatedKeys.getInt(1);
                System.out.println("Maintenance Request created successfully. Request ID: " + requestId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateMaintenanceRequestStatus(Connection conn, Scanner scanner) {
        System.out.print("Enter Maintenance Request ID to update: ");
        int requestId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new status: ");
        String status = scanner.nextLine();

        String sql = "UPDATE MaintenanceRequests SET Status = ? WHERE RequestID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, requestId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Maintenance Request status updated successfully.");
            } else {
                System.out.println("Maintenance Request not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteMaintenanceRequest(Connection conn, Scanner scanner) {
        System.out.print("Enter Maintenance Request ID to delete: ");
        int requestId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String sql = "DELETE FROM MaintenanceRequests WHERE RequestID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, requestId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Maintenance Request deleted successfully.");
            } else {
                System.out.println("Maintenance Request not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchMaintenanceRequest(Connection conn, Scanner scanner) {
        System.out.print("Enter Maintenance Request ID to search: ");
        int requestId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String sql = "SELECT * FROM MaintenanceRequests WHERE RequestID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, requestId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Maintenance Request found:");
                System.out.println("RequestID: " + rs.getInt("RequestID"));
                System.out.println("PropertyID: " + rs.getInt("PropertyID"));
                System.out.println("RequestDate: " + rs.getDate("RequestDate"));
                System.out.println("Description: " + rs.getString("Description"));
                System.out.println("Status: " + rs.getString("Status"));
            } else {
                System.out.println("Maintenance Request not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/propertyrentalmanagement"; // replace with your database details
        String user = "postgres"; // replace with your username
        String password = "admin"; // replace with your password

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nMain Menu:");
                System.out.println("1. User");
                System.out.println("2. Property");
                System.out.println("3. Rental Contract");
                System.out.println("4. Payment");
                System.out.println("5. Maintenance Request");
                System.out.println("6. Exit");

                System.out.print("Select an option: ");
                int mainOption = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (mainOption) {
                    case 1: // User
                        System.out.println("\nUser Menu:");
                        System.out.println("a. Create User");
                        System.out.println("b. Delete User");
                        System.out.println("c. Search User");
                        System.out.println("d. Go Back to Main Menu");

                        System.out.print("Select an option: ");
                        char userOption = scanner.nextLine().charAt(0);
                        switch (userOption) {
                            case 'a':
                                createUser(conn, scanner);
                                break;
                            case 'b':
                                deleteUser(conn, scanner);
                                break;
                            case 'c':
                                searchUser(conn, scanner);
                                break;
                            case 'd':
                                continue; // go back to main menu
                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                        break;

                    case 2: // Property
                        System.out.println("\nProperty Menu:");
                        System.out.println("a. Create Property");
                        System.out.println("b. Delete Property");
                        System.out.println("c. Search Property");
                        System.out.println("d. Go Back to Main Menu");

                        System.out.print("Select an option: ");
                        char propertyOption = scanner.nextLine().charAt(0);
                        switch (propertyOption) {
                            case 'a':
                                createProperty(conn, scanner);
                                break;
                            case 'b':
                                deleteProperty(conn, scanner);
                                break;
                            case 'c':
                                searchProperty(conn, scanner);
                                break;
                            case 'd':
                                continue; // go back to main menu
                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                        break;

                    case 3: // Rental Contract
                        System.out.println("\nRental Contract Menu:");
                        System.out.println("a. Create Rental Contract");
                        System.out.println("b. Delete Rental Contract");
                        System.out.println("c. Search Rental Contract");
                        System.out.println("d. Go Back to Main Menu");

                        System.out.print("Select an option: ");
                        char rentalOption = scanner.nextLine().charAt(0);
                        switch (rentalOption) {
                            case 'a':
                                createRentalContract(conn, scanner);
                                break;
                            case 'b':
                                deleteRentalContract(conn, scanner);
                                break;
                            case 'c':
                                searchRentalContract(conn, scanner);
                                break;
                            case 'd':
                                continue; // go back to main menu
                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                        break;

                    case 4: // Payment
                        System.out.println("\nPayment Menu:");
                        System.out.println("a. Create Payment");
                        System.out.println("b. Delete Payment");
                        System.out.println("c. Search Payment");
                        System.out.println("d. Go Back to Main Menu");

                        System.out.print("Select an option: ");
                        char paymentOption = scanner.nextLine().charAt(0);
                        switch (paymentOption) {
                            case 'a':
                                createPayment(conn, scanner);
                                break;
                            case 'b':
                                deletePayment(conn, scanner);
                                break;
                            case 'c':
                                searchPayment(conn, scanner);
                                break;
                            case 'd':
                                continue; // go back to main menu
                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                        break;

                    case 5: // Maintenance Request
                        System.out.println("\nMaintenance Request Menu:");
                        System.out.println("a. Create Maintenance Request");
                        System.out.println("b. Update Maintenance Request Status");
                        System.out.println("c. Delete Maintenance Request");
                        System.out.println("d. Search Maintenance Request");
                        System.out.println("e. Go Back to Main Menu");

                        System.out.print("Select an option: ");
                        char maintenanceOption = scanner.nextLine().charAt(0);
                        switch (maintenanceOption) {
                            case 'a':
                                createMaintenanceRequest(conn, scanner);
                                break;
                            case 'b':
                                updateMaintenanceRequestStatus(conn, scanner);
                                break;
                            case 'c':
                                deleteMaintenanceRequest(conn, scanner);
                                break;
                            case 'd':
                                searchMaintenanceRequest(conn, scanner);
                                break;
                            case 'e':
                                continue; // go back to main menu
                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                        break;

                    case 6: // Exit
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
