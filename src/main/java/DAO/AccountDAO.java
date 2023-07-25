
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    /**
     * get account by username
     * 
     * @param username
     * @return
     */
    public static Account getAccountLogin(String username) {
        // TODO Auto-generated method stub
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Account account = new Account(resultSet.getInt("account_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"));
                return account;
            }

        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
    * register a user
    * @param account
    * @return
    */
    public static Account registerUserCreateAccount(Account account) {
    // TODO Auto-generated method stub
    Connection connection = ConnectionUtil.getConnection();
    try {
        String sql = "INSERT INTO account (username , password) VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, account.getUsername());
        statement.setString(2, account.getPassword());
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            account.setAccount_id(id);
            return account;
        }

    } catch (SQLException e) {
        // TODO: handle exception
    }
    return null;
}

}
