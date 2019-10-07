/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.ChangePurse;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author EricR
 */
public interface VendingMachineService {
    
    public void loadMachine() throws VendingMachinePersistenceException;
    
    public List<Item> getAllItemsInMachine();
    public Item getOneItem(String vendingSlot);
    
    public ChangePurse purchaseItem(String vendingSlot, BigDecimal money)
            throws VendingMachineInsufficientFundsException,
            VendingMachinePersistenceException,
            VendingMachineNoItemInventoryException;
}
