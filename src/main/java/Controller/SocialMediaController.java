package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postAccountRegisterHandler);
        app.post("/login", this::postLoginHandler);// Endpoint for login
        app.patch("/messages/{message_id}", this::updateMessageTextHandler);
        app.get("/messages/{message_id}", this::getMessage_idHandler);// Endpoint for
        app.post("/messages", this::postMessageHandler);// Endpoint for submitting a new post
        app.get("/messages", this::getMessagesHandler);// Endpoint for retrieving messages/posts
        app.delete("/messages/{message_id}", this::deleteMessageHandler); // Endpoint
        app.get("/accounts/{account_id}/messages", this::getMessagesUser);// Endpoint for retrieving messages/posts

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * 
     * @param context The Javalin Context object manages information about both the
     *                HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * This is a handler for the /register endpoint.
     * register handler
     * 
     * @param ctx
     * @throws JsonProcessingException
     */
    private void postAccountRegisterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);

        Account registeredAccount = accountService.userRegister(account);
        if (registeredAccount != null) {
            ctx.json(registeredAccount).status(200);
        } else {
            ctx.status(400);
        }
    }

    /**
     * This is a handler for the /login endpoint.
     * login handler
     * 
     * @param ctx
     * @throws JsonProcessingException
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account userLogin = accountService.userLogin(account.getUsername(), account.getPassword());
        if (userLogin != null) {
            ctx.json(mapper.writeValueAsString(userLogin)).status(200);

        } else {
            ctx.status(401);
        }

    }

    /**
     * This is a handler for the /messages/{message_id} endpoint.
     * update message text handler
     * 
     * @param ctx
     * @throws JsonProcessingException
     */
    private void updateMessageTextHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = mapper.readValue(ctx.body(), Message.class);
        Message existingMessageText = messageService.getMessageById(messageId);
        if (existingMessageText == null) {
            ctx.status(400);
            return;
        }
        String newMessageText = updatedMessage.getMessage_text();
        
        existingMessageText.setMessage_text(newMessageText);
        // Now update the message in the database
        boolean isUpdateSuccessful = messageService.updateMessage(existingMessageText);
        if (!isUpdateSuccessful) {
            ctx.status(400);
            return;
        }
        
        ctx.json(existingMessageText); 
        ctx.status(200); // Success
    }

    /**
     * This is a handler for the /messages/{message_id} endpoint.
     * message_id
     * 
     * @param ctx
     */
    private void getMessage_idHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        List<Message> messages = messageService.getAllMessageById(messageId);
        ctx.status(200);
        if (!messages.isEmpty()) {
            ctx.json(messages.get(0));
        }
    }

    /**
     * This is a handler for the /messages endpoint.
     * create message part
     * 
     * @param ctx
     * @throws JsonProcessingException
     */
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createMessage = MessageService.createMessage(message);
        if (createMessage != null) {
            ctx.status(200).json(createMessage);
        } else {
            ctx.status(400);
        }

    }

    /**
     * This is a handler for the /messages endpoint.
     * get all messages
     * 
     * @param ctx
     */
    private void getMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    /**
     * This is a handler for the /messages/{message_id} endpoint.
     * delete message
     * 
     * @param ctx
     */
    private void deleteMessageHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message msg = messageService.getMessageById(messageId);
        if (msg != null) {
            boolean deletedMessage = messageService.deleteMessage(messageId);
            if (deletedMessage) {
                ctx.status(200).json(msg);
            } else {
                ctx.status(200);
            }

        }

    }

    /**
     * get all message user id
     * This is a handler for the /messages/{message_id} endpoint.
     * 
     * @param ctx
     */
    private void getMessagesUser(Context ctx) {
        int userId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = MessageService.getAllMessagesByUser(userId);
        if (messages != null) {
            ctx.json(messages);
        } else {
            ctx.status(200);
        }
    }
}
