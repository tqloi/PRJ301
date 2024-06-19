package Model;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FruitDB implements DatabaseInfo {

    private static int lastProductId = 20;

    public Connection getConnect() {
        try {
            Class.forName(DRIVERNAME);
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver" + e);
        }
        try {
            Connection con = DriverManager.getConnection(DBURL, USERDB, PASSDB);
            return con;
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    /*public static Connection getConnect(){
        try{
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
                    DataSource ds = (DataSource) envContext.lookup("jdbc/mydb");
                    return ds.getConnection();
        } catch (SQLException | NamingException ex){
            System.out.println("Error: " + ex);
        }
        return null;
    }*/
    public Fruit getFruit(int id) {
        Fruit s = null;
        try (Connection con = getConnect()) {
            PreparedStatement stmt = con.prepareStatement("Select productName, description, price  from Products where productID=?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString(1);
                String description = rs.getString(2);
                double price = rs.getDouble(3);
                s = new Fruit(id, name, description, price);
            }
            con.close();
        } catch (Exception ex) {
            Logger.getLogger(FruitDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
//--------------------------------------------------------------------------------------------

    public String login(String email) throws Exception {
        String pw = null;
        Connection con = getConnect();
        try {
            PreparedStatement stmt = con.prepareStatement("Select Password from Customert where email=?");

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                pw = rs.getString(1);

            }

        } catch (Exception ex) {
            Logger.getLogger(FruitDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return pw;
    }

    public int newFruit(Fruit s) {
        int id = -1;
        try (Connection con = getConnect()) {
            PreparedStatement stmt = con.prepareStatement("Insert into Fruit(ProductName, Description) output inserted.id values(?, ?)");
            stmt.setString(1, s.getProductName());
            stmt.setString(2, s.getDescription());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            con.close();
        } catch (Exception ex) {
            Logger.getLogger(FruitDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

     public int addFruit(String name, String descrip, double price) {
        int id = lastProductId++; // Sử dụng và tăng giá trị của biến static
        String query = "INSERT INTO Products (productID, productName, description, price) VALUES (?, ?, ?, ?)";

        try (Connection con = getConnect();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, descrip);
            stmt.setDouble(4, price);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating fruit failed, no rows affected.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(FruitDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

//-----------------------------------------------------------------------------------
    public Fruit update(Fruit s) {
        try (Connection con = getConnect()) {
            PreparedStatement stmt = con.prepareStatement("UPDATE Products SET productName=?, description=?, price=? WHERE productId=?");
            stmt.setString(1, s.getProductName());
            stmt.setString(2, s.getDescription());
            stmt.setDouble(3, s.getPrice());
            stmt.setInt(4, s.getProductId());

            int rc = stmt.executeUpdate();
            con.close();
            if (rc == 0) {
                throw new RuntimeException("No rows were updated");
            }
            return s;
        } catch (SQLException ex) {
            Logger.getLogger(FruitDB.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error updating product", ex);
        }
    }

//--------------------------------------------------------------------------------
    public int delete(int id) {
        try (Connection con = getConnect()) {
            PreparedStatement stmt = con.prepareStatement("Delete Products where productID =?");
            stmt.setInt(1, id);
            int rc = stmt.executeUpdate();
            con.close();
            return rc;
        } catch (Exception ex) {
            Logger.getLogger(FruitDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
//--------------------------------------------------------------------------------------------

    public ArrayList<Fruit> search(Predicate<Fruit> p) {
        ArrayList<Fruit> list = listAll();
        ArrayList<Fruit> res = new ArrayList<Fruit>();
        for (Fruit s : list) {
            if (p.test(s)) {
                res.add(s);
            }
        }
        return res;
    }
//--------------------------------------------------------------------------------------------

    public ArrayList<Fruit> listAll() {
        ArrayList<Fruit> list = new ArrayList<Fruit>();
        //Connection con = getConnect();
        try (Connection con = getConnect()) {
            PreparedStatement stmt = con.prepareStatement("Select productID, productName, description, price from Products");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Fruit(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4)));
            }
            con.close();
            return list;
        } catch (Exception ex) {
            Logger.getLogger(FruitDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//--------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        FruitDB fruitDB = new FruitDB();
        List<Fruit> list = fruitDB.listAll(); // Lấy danh sách đã được cập nhật
        for (Fruit item : list) {
            System.out.println(item); // In ra danh sách đã được cập nhật
        }
    }
//---------------------------------------------------------------------------
}
