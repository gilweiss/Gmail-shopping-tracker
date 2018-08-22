package main.java;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import main.java.seller_handlers.AmazonHandler;
import main.java.seller_handlers.HostelworldHandler;
import main.java.seller_handlers.SellerHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static main.java.MessageHandler.listMessagesMatchingQuery;

public class PurchaseList {


    ArrayList<Purchase> purchaseList;
    List<SellerHandler> sellerHandlers;

    /**
     * A class that contains the so desired purchases list, the constructor will also generate a list of sellerHandler objects
     *
     * @param service Authorized Gmail API instance.
     * @param userId  User's email address. The special value "me"
     *                can be used to indicate the authenticated user.
     */
    public PurchaseList(Gmail service, String userId) throws IOException {
        this.purchaseList = new  ArrayList<Purchase>();
        this.sellerHandlers = GenerateSellerHandlers();
        generatePurchasesList(service, userId);
    }


    /**
     * generate seller handlers into the sellerHandlers list
     * (the handlers in the list are responsible for generating the relevant purchases of the seller)
     *
     * @return a list of sellerHandler object
     */
    private List<SellerHandler> GenerateSellerHandlers() {
         List<SellerHandler> sellerHandlers = new ArrayList<SellerHandler>();

         sellerHandlers.add(new AmazonHandler());
         sellerHandlers.add(new HostelworldHandler());
         /*
         TODO CREATE AND ADD MORE SELLER HANDLERS (like amazon`s) HERE
          */

        return sellerHandlers;
    }

    /**
     * adds the relevant purchases of every seller`s handler found in the sellerHandlers list.
     *
     * @param service Authorized Gmail API instance.
     * @param userId  User's email address. The special value "me"
     *                can be used to indicate the authenticated user.
     */
    private void generatePurchasesList(Gmail service, String userId) throws IOException {
        for (SellerHandler sellerHandler : sellerHandlers) {
            addPurchasesFromSeller(service, userId, sellerHandler);
        }
    }


    /**
     * adds all relevant purchases of a seller to purchasesList
     *
     * @param service Authorized Gmail API instance.
     * @param userId  User's email address. The special value "me"
     *                can be used to indicate the authenticated user.
     * @param sellerHandler   seller`s unique handler object responsible for parsing messages to purchases.
     */
    private void addPurchasesFromSeller(Gmail service, String userId, SellerHandler sellerHandler) throws IOException {
            List<Message> orderMessages = listMessagesMatchingQuery(service, "me", sellerHandler.getQuery());
            List<Purchase> tempPurchaseList = new ArrayList();
            for (Message msg : orderMessages) {
                tempPurchaseList.add(sellerHandler.parseMsg(msg));
            }
            this.purchaseList.addAll(tempPurchaseList);
    }

    public void printPurchaseList() {
        System.out.println( "\n" + "\n" + "LIST OF PURCHASES:" + "\n" + "\n"  );
        for (Purchase purchase : purchaseList) {
            System.out.println(purchase);
        }
    }








}
