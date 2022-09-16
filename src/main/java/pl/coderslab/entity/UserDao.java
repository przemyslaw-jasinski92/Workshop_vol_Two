package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

    private static final String CREATE_USER_QUERY = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY = "SELECT * FROM users where id = ?";
    private static final String UPDATE_USER_QUERY_WITH_PASS = "UPDATE users SET username = ?, email = ?, password=? where id = ?";
    private static final String UPDATE_USER_QUERY_WITH_NO_PASS = "UPDATE users SET username = ?, email = ? where id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users where id = ?";


    public User create(User user) {
        try(Connection conn = DbUtil.connect()){
            PreparedStatement preStmt = conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preStmt.setString(1, user.getUserName());
            preStmt.setString(2, user.getEmail());
            preStmt.setString(3, hashPass(user.getPassword()));
            preStmt.executeUpdate();

            ResultSet rs = preStmt.getGeneratedKeys();
            if(rs.next()){
                long id = rs.getLong(1);
                System.out.println("Inserted id: " + id);
            }return user;
        }catch (SQLException e){
            e.printStackTrace();
        }return null;
    }

    public User read(int userId){

        try(Connection conn = DbUtil.connect(); PreparedStatement preStmt = conn.prepareStatement(READ_USER_QUERY)){
            if(checkId(userId)){
                User user = new User();
                preStmt.setLong(1, userId);
                ResultSet rs = preStmt.executeQuery();
                if(rs.next()){
                    user.setId(rs.getLong("id"));
                    user.setUserName(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }return null;
    }

    public void updateWithPass(User user){
        try(Connection conn = DbUtil.connect(); PreparedStatement preStmt = conn.prepareStatement(UPDATE_USER_QUERY_WITH_PASS)){
            preStmt.setString(1, user.getUserName());
            preStmt.setString(2, user.getEmail());
            preStmt.setString(3, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            preStmt.setLong(4, user.getId());
            preStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateWithNoPass(User user){
        try(Connection conn = DbUtil.connect(); PreparedStatement preStmt = conn.prepareStatement(UPDATE_USER_QUERY_WITH_NO_PASS)){
            preStmt.setString(1, user.getUserName());
            preStmt.setString(2, user.getEmail());
            preStmt.setLong(3, user.getId());
            preStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(int userID){
        try(Connection conn = DbUtil.connect()){
            PreparedStatement preStmt = conn.prepareStatement(DELETE_USER_QUERY);

            if(checkId(userID)){
                preStmt.setInt(1, userID);
                preStmt.executeUpdate();
                System.out.println("Properly delete user of id: " + userID);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String hashPass(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User[] findAll(){
        User[] users = new User[0];
        try(Connection conn = DbUtil.connect(); Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery("select * from users");
            while(rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                users = addToArray(user, users);
            }
            return users;
        }catch(SQLException e) {
            e.printStackTrace();
        }return users;
    }

    private User[] addToArray(User u, User[] users){
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = u;
        return tmpUsers;
    }

    public static boolean checkId(int id) {
        try (Connection conn = DbUtil.connect()) {
            String sqlEdit = "select * from users where id =?";
            PreparedStatement statement = conn.prepareStatement(sqlEdit);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                System.out.println("Wrong value of user's id");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void showUsers(User[] users){
        for (User user: users) {
            System.out.print(user);
        }
    }
}
