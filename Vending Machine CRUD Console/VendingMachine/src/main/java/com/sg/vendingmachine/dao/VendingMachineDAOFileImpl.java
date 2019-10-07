/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author EricR
 */
public class VendingMachineDAOFileImpl implements VendingMachineDAO {

    public static final String DELIMITER = "::";
    private Map<String, Item> items;
    private final String INVENTORY_FILE;

    public VendingMachineDAOFileImpl() {
        items = new HashMap<>();
        this.INVENTORY_FILE = "inventory.txt";
    }
    
    public VendingMachineDAOFileImpl(String fileName) {
        items = new HashMap<>();
        this.INVENTORY_FILE = fileName;
    }
    
    @Override
    public void loadAllItems() throws VendingMachinePersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "-_- Could not load inventory data into memory.", e);
        }
        String currentLine;
        Item currentItem;

        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
            items.put(currentItem.getSlotId(), currentItem);
        }
        scanner.close();
    }

    @Override
    public void saveAllChanges() throws VendingMachinePersistenceException {
        
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not save student data.", e);
        }

        String itemAsText;
        List<Item> itemList = this.getAllItems();
        for (Item currentItem : itemList) {
            // turn an Item into a String
            itemAsText = marshallItem(currentItem);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }

    @Override
    public Item addItem(Item anItem, String slotId) {
        Item wasStoredUnderThatId = items.put(slotId, anItem);
        return wasStoredUnderThatId;
    }

    @Override
    public List<Item> getAllItems() {
        return new ArrayList<Item>(items.values());
    }

    @Override
    public Item getAnItem(String slotId) {
        return items.get(slotId);
    }

    @Override
    public void updateAnItem(String slotId, Item changedItem) {
        items.replace(slotId, changedItem);
    }

    @Override
    public Item removeAnItem(String slotId) {
        Item removed = items.remove(slotId);
        return removed;
    }

    public Item unmarshallItem(String itemAsText) {
        Item itemFromFile = new Item();
        String[] itemTokens = itemAsText.split(DELIMITER);
        
        itemFromFile.setSlotId(itemTokens[0]);
        itemFromFile.setItemName(itemTokens[1]);
        itemFromFile.setItemCost(itemTokens[2]);
        itemFromFile.setItemQuantity(itemTokens[3]);

        return itemFromFile;
//        String[] itemTokens = itemAsText.split(DELIMITER);
//        String slotId = itemTokens[0];
//        Item itemFromFile = new Item(slotId);
//        itemFromFile.setItemName(itemTokens[1]);
//        itemFromFile.setItemCost(itemTokens[2]);
//
//        itemFromFile.setItemQuantity(itemTokens[3]);
//
//        return itemFromFile;
    }

    public String marshallItem(Item anItem) {
        String itemAsText =  "";
        itemAsText = anItem.getSlotId() + DELIMITER;

        // add the rest of the properties in the correct order:
        // FirstName
        itemAsText += anItem.getItemName() + DELIMITER;

        // LastName
        itemAsText += anItem.getItemCost() + DELIMITER;

        // Cohort - don't forget to skip the DELIMITER here.
        itemAsText += anItem.getItemQuantity();

        // We have now turned a student to text! Return it!
        return itemAsText;
    }
}
