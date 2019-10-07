/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.ChangePurse;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sg.vendingmachine.service.VendingMachineNoItemInventoryException;
import com.sg.vendingmachine.service.VendingMachineNoSuchItemException;
import com.sg.vendingmachine.service.VendingMachineService;
import com.sg.vendingmachine.service.VendingMachineServiceImpl;
import com.sg.vendingmachine.ui.VendingMachineUserIO;
import com.sg.vendingmachine.ui.VendingMachineUserIOConsoleImpl;
import com.sg.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author EricR
 */
public class VendingMachineController {

    VendingMachineView view;
    VendingMachineServiceImpl service;

    public VendingMachineController(VendingMachineServiceImpl service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {

        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            service.loadMachine();
            while (keepGoing) {
                //service.loadMachine();
                listItems();
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        service.setBalance(insertMoney());
                        break;
                    case 2:
                        purchaseItem();
                        break;
                    case 3:
                        keepGoing = false;
                        saveOnExit();
                        break;
                    default:
                        unknownCommand();
                        break;
                }
            }
            exitMessage();
        } catch (VendingMachinePersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private int getMenuSelection() {

        return view.printMenuAndGetSelection(service.getTotalBalance());

    }

    private void saveOnExit() throws VendingMachinePersistenceException {
        service.saveMachine();
    }

    private BigDecimal insertMoney() {
        return view.addMoney();
    }

    private void listItems() {
        List<Item> itemList = service.getAllItemsInMachineWithoutZeroQuantity();
        view.displayItemList(itemList);
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private void purchaseItem() throws VendingMachinePersistenceException {

        boolean hasErrors = false;

        try {
            String slotId = view.getSlotIdChoice();

            ChangePurse change = service.purchaseItem(slotId, service.getTotalBalance());
            view.displayChange(change, service.getOneItem(slotId), service.getBalance());
            hasErrors = false;
        } catch (VendingMachineInsufficientFundsException | VendingMachineNoItemInventoryException e) {
            hasErrors = true;
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

}
