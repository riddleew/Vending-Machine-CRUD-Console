/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service.stubs;

import com.sg.vendingmachine.dao.VendingMachineDAO;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Item;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author EricR
 */
public class VendingMachineDAOStubImpl implements VendingMachineDAO {

    Item itemOne;
    Item itemTwo;
    List<Item> itemList = new ArrayList<>();

    public VendingMachineDAOStubImpl(Item itemOne, Item itemTwo) {
        this.itemOne = itemOne;
        this.itemTwo = itemTwo;
    }

    @Override
    public void loadAllItems() throws VendingMachinePersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveAllChanges() throws VendingMachinePersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Item addItem(Item anItem, String slotId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> theStubItems = new ArrayList<>();
        theStubItems.add(itemOne);
        theStubItems.add(itemTwo);
        return theStubItems;
    }

    @Override
    public Item getAnItem(String slotId) {
        if (slotId.equals(itemOne.getSlotId())) {
            return itemOne;
        } else if (slotId.equals(itemTwo.getSlotId())) {
            return itemTwo;
        } else {
            return null;
        }
    }

    @Override
    public void updateAnItem(String slotId, Item changedItem) {
        itemList.remove(getAnItem(slotId));
        itemList.add(changedItem);
    }

    @Override
    public Item removeAnItem(String slotId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
