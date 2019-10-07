/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
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
public class VendingMachineDAOFileImplTest {
    
    VendingMachineDAOFileImpl testDao;
    
    public VendingMachineDAOFileImplTest() {
        testDao = new VendingMachineDAOFileImpl();
    }
    
    @Test
    public void unMarshallItemTest() {
        // ARRANGE
        String itemLine = "001::Comp Sci::2.00::2";
        
        // ACT
        Item fromLine = testDao.unmarshallItem(itemLine);
        
        // ASSERT
        Assertions.assertEquals("001", fromLine.getSlotId(), "Id should be 001");
        Assertions.assertEquals("Comp Sci", fromLine.getItemName(), "Item Name should be Comp Sci");
        Assertions.assertEquals("2.00", fromLine.getItemCost(), "Cost should be 2.00");
        Assertions.assertEquals("2", fromLine.getItemQuantity(), "Item Quantity should be 2");
        
    }
    
    @Test
    public void marshallItemTest(){
        // ARRANGE
        Item toTextify = new Item("001", "Comp Sci", "2.00", "2");
        
        // ACT
        String itemAsText = testDao.marshallItem(toTextify);
        
        // ASSERT
        Assertions.assertEquals("001::Comp Sci::2.00::2", itemAsText, "Lines should match!");
        
    }
    
    @Test
    public void circleOfMarshallingTest() {
        // ARRANGE
        Item toTextify = new Item("001", "Comp Sci", "2.00", "2");
        
        // ACT
        String tempItemText = testDao.marshallItem(toTextify);
        Item shouldLookLikeOriginal = testDao.unmarshallItem(tempItemText);
        
        // ASSERT
        Assertions.assertEquals(toTextify, shouldLookLikeOriginal, "Items should match");
    }
    
}
