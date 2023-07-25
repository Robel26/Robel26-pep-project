package Service;

import java.util.ArrayList;
import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {


    public MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    /**
     * get message by id
     * 
     * @param id
     * @return
     */
    public Message getMessageById(int id) {
        return MessageDAO.getMessageById(id);
    }

    /**
     * update message
     * 
     * @param existingMessageText
     * @return
     */
    public boolean updateMessage(Message message) {
        // Perform any necessary validation before updating the message
        if (message == null || message.getMessage_text() == null ||
                message.getMessage_text().trim().isEmpty()
                || message.getMessage_text().length() >= 255) {
            return false;
        }

        return MessageDAO.updatedMessage(message);
    }

    /**
     * get all message
     * get message by id
     * 
     * @param id
     * @return
     */
    public List<Message> getAllMessageById(int id) {
        List<Message> messages = new ArrayList<>();
        Message message = MessageDAO.getMessageById(id);
        if (message != null) {
            messages.add(message);
        }
        return messages;
    }

    /**
     * get all message
     * 
     * @return
     */
    public List<Message> getAllMessages() {
        return MessageDAO.getAllMessages();
    }

    /**
     * delete message
     * 
     * @param messageId
     * @return
     */
    public boolean deleteMessage(int messageId) {
        return MessageDAO.deleteMessage(messageId);
    }

    /**
     * get all message by user id
     * 
     * @param userId
     * @return
     */
    public static List<Message> getAllMessagesByUser(int userId) {
        return MessageDAO.getAllMessagesByUser(userId);
    }

    /**
     *  create messages
     * @param message
     * @return
     */
    public static Message createMessage(Message message) {
        if (message == null || message.getMessage_text() == null ||
        message.getMessage_text().trim().isEmpty()
        || message.getMessage_text().length() >= 255) {
        return null;
        }
        return MessageDAO.createMessage(message);
    }

}
