package main.java.seller_handlers;

import com.google.api.services.gmail.model.Message;
import main.java.Purchase;

public abstract class SellerHandler {


    private  String seller;
    private  String query;

    public abstract String getQuery();


    /**
     * return a purchase object created from parsed data of a message
     *
     * @param msg relevant message that answered the search query.
     * @return a purchase object corresponding msg data.
     */
    public abstract Purchase parseMsg (Message msg);

    /*
    suggestion to use existing AmazonHandler class as template
     */

}
