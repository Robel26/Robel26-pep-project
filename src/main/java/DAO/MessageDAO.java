package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    /**
     * get message by id
     * 
     * 
     * @param id
     * @return
     */
    public static Message getMessageById(int id) {
        // TODO Auto-generated method stub
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, id);

            ResultSet rs = prepareStatement.executeQuery();

            while (rs.next()) {
                Message message = new Message();
                message.setMessage_id(rs.getInt("message_id"));
                message.setMessage_text(rs.getString("message_text"));
                message.setPosted_by(rs.getInt("posted_by"));
                message.setTime_posted_epoch(rs.getInt("time_posted_epoch"));

                return message;
            }
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * get all message
     * 
     * @return
     */
    public static List<Message> getAllMessages() {
        // TODO Auto-generated method stub
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            ResultSet rs = prepareStatement.executeQuery();

            while (rs.next()) {
                Message message = new Message();
                message.setMessage_id(rs.getInt("message_id"));
                message.setMessage_text(rs.getString("message_text"));
                message.setPosted_by(rs.getInt("posted_by"));
                message.setTime_posted_epoch(rs.getInt("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return messages;

    }

    /**
     * update message
     * 
     * @param message
     * @return
     */
    public static boolean updatedMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? , time_posted_epoch = ? , posted_by = ? WHERE message_id = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);

            prepareStatement.setString(1, message.getMessage_text());
            prepareStatement.setLong(2, message.getTime_posted_epoch());
            prepareStatement.setInt(3, message.getPosted_by());
            prepareStatement.setInt(4, message.getMessage_id());

            int rowsAffected = prepareStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return false;

    }

    /**
     * delete message
     * 
     * @param messageId
     */
    public static boolean deleteMessage(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * get all message by user id
     * 
     * @param userId
     * @return
     */
    public static List<Message> getAllMessagesByUser(int userId) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, userId);
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message();
                message.setMessage_id(rs.getInt("message_id"));
                message.setMessage_text(rs.getString("message_text"));
                message.setPosted_by(rs.getInt("posted_by"));
                message.setTime_posted_epoch(rs.getInt("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return messages;
    }
    /**
    * create message part
    * @param message
    * @return
    */
    public static Message createMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message( message_text, posted_by, time_posted_epoch) VALUES(?,?,?)";
            PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            
            prepareStatement.setString(1, message.getMessage_text());
            prepareStatement.setInt(2, message.getPosted_by());
            prepareStatement.setLong(3, message.getTime_posted_epoch());

            prepareStatement.executeUpdate();
            ResultSet rs = prepareStatement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                message.setMessage_id(id);
                return message;
            }

        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }

}
