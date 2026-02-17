import java.sql.*;
import java.util.*;

// DB connection
class ConnectDB {
    private static Connection con;

    private ConnectDB() {}

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:8000/TEST",
                    "postgres",
                    "2005"
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
} 


// DAO
class SiteDAO {

    public void insertSite(int siteId, String type, int length, int width,
                           boolean occupied, int cost) {

        String sql = "INSERT INTO sites VALUES (?,?,?,?,?,?)";

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, siteId);
            ps.setString(2, type);
            ps.setInt(3, length);
            ps.setInt(4, width);
            ps.setBoolean(5, occupied);
            ps.setInt(6, cost);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getSite(int siteId) {
        String sql = "SELECT * FROM sites WHERE site_id=?";
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, siteId);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateSite(String type, boolean occupied, int cost, int siteId) {
        String sql = "UPDATE sites SET site_type=?, occupied=?, cost_per_sqft=? WHERE site_id=?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, type);
            ps.setBoolean(2, occupied);
            ps.setInt(3, cost);
            ps.setInt(4, siteId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


// Request for permission
class RequestDAO {

    public void createRequest(int siteId, String newType, boolean occupied) {
        String sql = "INSERT INTO update_requests(site_id,new_site_type,new_occupied) VALUES (?,?,?)";

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, siteId);
            ps.setString(2, newType);
            ps.setBoolean(3, occupied);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getPendingRequests() {
        String sql = "SELECT * FROM update_requests WHERE status='PENDING'";
        try {
            Connection con = ConnectDB.getConnection();
            return con.prepareStatement(sql).executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateStatus(int reqId, String status) {
        String sql = "UPDATE update_requests SET status=? WHERE request_id=?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, reqId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Site service
class SiteService {

    SiteDAO siteDAO = new SiteDAO();
    RequestDAO requestDAO = new RequestDAO();

    public void addSite(int siteId, String type, boolean occupied) {

        int length, width;

        if (siteId <= 10) { length = 40; width = 60; }
        else if (siteId <= 20) { length = 30; width = 50; }
        else { length = 30; width = 40; }

        int cost = occupied ? 9 : 6;

        siteDAO.insertSite(siteId, type, length, width, occupied, cost);
        System.out.println("Site added successfully");
    }

    public int calculateMaintenance(int siteId) {
        try {
            ResultSet rs = siteDAO.getSite(siteId);
            if (rs.next()) {
                return rs.getInt("length")
                     * rs.getInt("width")
                     * rs.getInt("cost_per_sqft");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void requestUpdate(int siteId, String newType, boolean occupied) {
        requestDAO.createRequest(siteId, newType, occupied);
        System.out.println("Update request sent to admin");
    }

    public void approveRequest(int reqId, int siteId, String type, boolean occupied) {
        int cost = occupied ? 9 : 6;
        siteDAO.updateSite(type, occupied, cost, siteId);
        requestDAO.updateStatus(reqId, "APPROVED");
        System.out.println("Request approved");
    }
}

// Admin
class Admin {
    SiteService service = new SiteService();

    void menu(Scanner sc) {
        System.out.println("1.Add  2.Approve  3.Collect");
        int ch = sc.nextInt();
        
        switch (ch) {
            case 1:
                System.out.println("Enter the siteId,siteType and occupied(True/False):");
                service.addSite(sc.nextInt(), sc.next(), sc.nextBoolean());
                break;
            case 2:
                System.out.println("Enter the reqId, siteId, type, occupied or not");
                service.approveRequest(sc.nextInt(), sc.nextInt(), sc.next(), sc.nextBoolean());
                break;
            case 3:
                System.out.println("Enter the siteId");
                System.out.println(service.calculateMaintenance(sc.nextInt()));
                break;
        }
    }
}


// User
class User {
    SiteService service = new SiteService();

    void menu(Scanner sc) {
        System.out.println("1.View Maintenance  2.Request Update");
        int ch = sc.nextInt();

        switch (ch) {
            case 1:
                 System.out.println("Enter the siteId");
                 System.out.println(service.calculateMaintenance(sc.nextInt()));
                 break;
            case 2:
                System.out.println("Enter the siteId, newType and occupied:");
                service.requestUpdate(sc.nextInt(), sc.next(), sc.nextBoolean());
                break;
        }
    }
}


public class Sites {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter role (admin/user):");
        String role = sc.next();

        if (role.equalsIgnoreCase("admin"))
            new Admin().menu(sc);
        else
            new User().menu(sc);
    }
}

 




