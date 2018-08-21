//package main.java;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import main.java.MessageHandler;
import main.java.PurchaseList;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class Main {

public static void main(String... args) throws IOException, GeneralSecurityException {



    Gmail service = ServiceGenerator.generateService();
    PurchaseList purchaseList = new PurchaseList(service, "me");
    purchaseList.printPurchaseList();

    }
}