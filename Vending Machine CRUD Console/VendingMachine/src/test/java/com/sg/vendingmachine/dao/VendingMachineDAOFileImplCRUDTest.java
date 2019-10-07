/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author EricR
 */
public class VendingMachineDAOFileImplCRUDTest {
    
    
    VendingMachineDAOFileImpl testDao;
    
    public VendingMachineDAOFileImplCRUDTest() throws IOException {
        String testFileName = "testitems.txt";
        new FileWriter(testFileName);
        testDao = new VendingMachineDAOFileImpl(testFileName);
    }

    @Test
    public void addGetOneItemTest() {
        // ARRANGE - already know we have a blank DAO because when we make
        // a map it begins empty
        // we also need an item, and an id
        String itemID = "001";
        Item toStore =  new Item(itemID, "Comp Sci", "2.15", "4");
        
        // ACT
        Item gotBack = testDao.addItem(toStore, itemID);
        Item gottenItem = testDao.getAnItem(itemID);
        
        // ASSERT
        // Check that the item that we tried to store, and the one we got back
        // from the dao after we stored it look identical!!!
        Assertions.assertEquals(gottenItem.getSlotId(), toStore.getSlotId(), "Item Ids should match");
        Assertions.assertEquals(gottenItem.getItemName(), toStore.getItemName(), "Item Names should match");
        Assertions.assertEquals(gottenItem.getItemCost(), toStore.getItemCost(), "Item Costs should match");
        Assertions.assertEquals(gottenItem.getItemQuantity(), toStore.getItemQuantity(), "Item Quantities should match");
       
        
        Assertions.assertNull(gotBack, "There should have been an item in there, yo.");
    }
    
    @Test
    public void addGetAllItemTest() {
        // ARRANGE - already know we have a blank DAO
        // we also need an item, and an id
        String itemID = "001";
        Item toStore =  new Item(itemID, "Comp Sci", "2.15", "3");
        
        String itemID2 = "002";
        Item toStore2 = new Item(itemID2, "Fine Arts", "0.25", "1");
        
        // ACT
        Item gotBackFirst = testDao.addItem(toStore, itemID);
        Item gotBackSecond = testDao.addItem(toStore2, itemID2);
        
        List<Item> allDaItems = testDao.getAllItems();
        
        // ASSERT
        // Check that each item we got back are null.
        // And then also, that our list is not null, contains 2
        // and those two are both our items we stored
        
        Assertions.assertNotNull(allDaItems, "our item list should not be null");
        Assertions.assertEquals(2, allDaItems.size(), "there should be 2 items in the list.");
        Assertions.assertTrue(allDaItems.contains(toStore), "Item list should have the first item stored.");
        Assertions.assertTrue(allDaItems.contains(toStore2), "Item list should have the second item stored.");
        
        Assertions.assertNull(gotBackFirst,"There shouildn't be an item returned when storing in an empty dao.");
        Assertions.assertNull(gotBackSecond,"There shouildn't be an item returned when storing in an empty dao.");
        
    }
    
    @Test
    public void addRemoveItemTest() {
        // ARRANGE
        // we also need an item, and an id
        String itemID = "001";
        Item toStore =  new Item(itemID, "Comp Sci", "2.15", "3");
        
        // ACT
        testDao.addItem(toStore, itemID);
        Item removed = testDao.removeAnItem(itemID);
        Item shouldBeNullBecauseItWasRemoved = testDao.getAnItem(itemID);
        
        // ASSERT
        // AS LONG AS YOU HAVE OVERRIDDEN EQUALS YOU CAN SKIP THE PARTS
        // AND GO STRAIGHT TO COMPARING WHOLE OBJECTS
        Assertions.assertEquals(toStore, removed, "Stored item and removed item should match");
        Assertions.assertNull(shouldBeNullBecauseItWasRemoved, "Item should no longer be in DAO");
    }
    
    @Test
    public void addUpdateItemTest() {
        String itemID = "001";
        Item firstItem =  new Item(itemID, "Comp Sci", "2.15", "3");
        Item secondItem = new Item(itemID, "Fine Arts", "2.00", "4");
        
        // ACT
        // first add the item
        // then replace the item
        // then get the item, make sure its the second one
        // and that getAll only has one item, the second
        testDao.addItem(firstItem, itemID);
        testDao.updateAnItem(itemID, secondItem);
        
        Item retrieved = testDao.getAnItem(itemID);
        List<Item> allDaItems = testDao.getAllItems();
        
        // ASSERT
        // check that retrieved is equal to second
        // check that list is size 1, and has second
        Assertions.assertEquals(secondItem, retrieved, "Item should have been replaced by second");
        Assertions.assertEquals(1, allDaItems.size(), "There should only be one item");
        Assertions.assertTrue(allDaItems.contains(secondItem), "Only item left should be second one.");

    }
    
    @Test
    public void emptyDaoTest()  {
        // ARRANGE - done in constructor
        
        // ACT - just check that it's empty
        List<Item> emptyItems = testDao.getAllItems();
        
        // ASSERT - prove it
        Assertions.assertNotNull(emptyItems, "Should be empty list, not null");
        Assertions.assertEquals(0, emptyItems.size(), "Should be an empty list.");
    }
    
}
