package main.java;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MessageHandler {



    /**
     * List all Messages of the user's mailbox matching the query.
     *
     * @param service Authorized Gmail API instance.
     * @param userId  User's email address. The special value "me"
     *                can be used to indicate the authenticated user.
     * @param query   String used to filter the Messages listed.
     * @return list of desired messages (full format)
     * @throws IOException
     */
    public static List<Message> listMessagesMatchingQuery(Gmail service, String userId,
                                                          String query) throws IOException {
        ListMessagesResponse response = service.users().messages().list(userId).setQ(query).execute();

        List<Message> messages = new ArrayList<Message>();
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = service.users().messages().list(userId).setQ(query)
                        .setPageToken(pageToken).execute();
            } else {
                break;
            }
        }

        List<Message> resMessages = new ArrayList<Message>();
        for (Message message : messages) {
            resMessages.add(getMessage(service,userId,message.getId()));
        }

        return resMessages;
    }




        /**
         * retrieves a full message from user inbox using its ID
         *
         * @param service Authorized Gmail API instance.
         * @param userId  User's email address. The special value "me"
         *                can be used to indicate the authenticated user.
         * @param messageId   String of unique ID of the desired message.
         * @return a message (full format)
         * @throws IOException
         */
        public static Message getMessage(Gmail service, String userId, String messageId)
                throws IOException {
            Message message = service.users().messages().get(userId, messageId).execute();
            //System.out.println("(DEBUG) Message snippet: " + message.getSnippet());
            return message;
        }

    /**
     * retrieves the body of a message
     *
     * @param msg a message (full format).
     * @return a String of the message body
     */
        public static String getMessageBody (Message msg){
            List<MessagePart> partList = msg.getPayload().getParts();
            String res="";
            for(MessagePart part: partList) {
                res = res + StringUtils.newStringUtf8(Base64.decodeBase64(part.getBody().getData()));

            }
            return res;
        }

    /**
     * retrieves the body of a message
     *
     * @param service Authorized Gmail API instance.
     * @param userId  User's email address. The special value "me"
     *                can be used to indicate the authenticated user.
     * @param messageId   String of unique ID of the desired message.
     * @return a String of the message body
     * @throws IOException
     */
    public static String getMessageBody (Gmail service, String userId, String messageId) throws IOException {
        return getMessageBody(getMessage(service, userId, messageId));
    }

    /**
     * retrieves the date of a message
     *
     * @param msg a message (full format).
     * @return a String of the message date
     */
    public static String getMessageDate (Message msg){
        List<MessagePartHeader> headerList = msg.getPayload().getHeaders();
        for(MessagePartHeader headerPart: headerList) {
            if (headerPart.getName().toString().equals("Date")) {
                return headerPart.getValue().toString();
            }
        }
        return null;
    }
}