/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDAO;
import com.sg.vendingmachine.dao.VendingMachineDAO;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.ChangePurse;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author EricR
 */
public class VendingMachineServiceImpl implements VendingMachineService {

    private VendingMachineDAO dao;
    private VendingMachineAuditDAO auditDao;
    private BigDecimal currentBalance;
    private BigDecimal totalBalance = new BigDecimal("0.00");
    private BigDecimal zeroBalance = new BigDecimal("0.00");

    public VendingMachineServiceImpl(VendingMachineDAO dao, VendingMachineAuditDAO auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public void loadMachine() throws VendingMachinePersistenceException {
        dao.loadAllItems();
    }

    public void saveMachine() throws VendingMachinePersistenceException {
        dao.saveAllChanges();
    }

    @Override
    public List<Item> getAllItemsInMachine() {
        return dao.getAllItems();
        //return dao.getAllItems();
    }
    
    public List<Item> getAllItemsInMachineWithoutZeroQuantity() {
        List<Item> fullList = dao.getAllItems();
        List<Item> listWithoutZeroQuantity = new ArrayList<>();

        for (Item currentItem : fullList) {
            int quantityInt = Integer.parseInt(currentItem.getItemQuantity());
            //if (currentItem.getItemQuantity().equalsIgnoreCase("0")) {
            if (quantityInt > 0) {
                //dao.removeAnItem(currentItem.getSlotId());
                listWithoutZeroQuantity.add(currentItem);
            }
        }
        return listWithoutZeroQuantity;
    }

    @Override
    public Item getOneItem(String vendingSlot) {
        return dao.getAnItem(vendingSlot);
    }

    @Override
    public ChangePurse purchaseItem(String vendingSlot, BigDecimal money)
            throws VendingMachineInsufficientFundsException, 
            VendingMachineNoItemInventoryException,
            VendingMachinePersistenceException {

        Item currentItem = getOneItem(vendingSlot);
        if (currentItem == null) {
            throw new VendingMachineNoItemInventoryException("No such item in inventory.");
        }
        int quantityInt = Integer.parseInt(currentItem.getItemQuantity());
        BigDecimal itemCostBigDecimal = getItemCostInBigDecimal(currentItem);

        if (quantityInt > 0) {
            int compareBigDecimal = compareBalanceWithItemCost(money, itemCostBigDecimal); // take advantage of BigDecimal.compareTo()
            if (compareBigDecimal == 0 || compareBigDecimal == 1) {
                money = priceVersusBalance(compareBigDecimal, money, itemCostBigDecimal);
                BigDecimal moneyInPennies = money.multiply(new BigDecimal("100"));

                // instantiate and populate values for changePurse
                ChangePurse change = new ChangePurse(moneyInPennies);
                change.generateCoins();

                // Update quantity, reset balance after change is issued, update dao map, write to audit file
                setBalance(money);
                quantityInt -= 1;
                currentItem.setItemQuantity(String.valueOf(quantityInt));
                setTotalBalance(new BigDecimal("0.00"));
                dao.updateAnItem(vendingSlot, currentItem);
                auditDao.writeAuditEntry("Item " + currentItem.getItemName() + " PURCHASED");

                return change;
            } else {
                throw new VendingMachineInsufficientFundsException("Insufficient funds! Only inserted $" + money + " when item costs $" + currentItem.getItemCost());

            }
        } else {
            throw new VendingMachineNoItemInventoryException("No such item in inventory.");
        }
    }

    private BigDecimal getItemCostInBigDecimal(Item item) {
        BigDecimal itemCostBigDecimal = new BigDecimal(item.getItemCost());
        return itemCostBigDecimal;
    }

    private int compareBalanceWithItemCost(BigDecimal money, BigDecimal itemCostBigDecimal) {
        return money.compareTo(itemCostBigDecimal); // compareTo returns -1 if < val, 1 if > val, 0 if equal
    }

    private BigDecimal priceVersusBalance(int compareValue, BigDecimal money, BigDecimal itemCost) {
        switch (compareValue) {
            case 1:
                money = money.subtract(itemCost);
                break;
            case 0:
                money = zeroBalance;
                break;
        }
        return money;
    }

    public void setTotalBalance(BigDecimal totalBal) {
        this.totalBalance = totalBal;
    }

    public void setBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
        totalBalance = currentBalance.add(totalBalance);
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public BigDecimal getBalance() {
        return currentBalance;
    }
}
