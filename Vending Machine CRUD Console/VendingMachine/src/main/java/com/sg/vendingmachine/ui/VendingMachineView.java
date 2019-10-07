/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.ChangePurse;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author EricR
 */
public class VendingMachineView {

    private VendingMachineUserIO io;

    public VendingMachineView(VendingMachineUserIO io) {
        this.io = io;
    }
    
    public int printMenuAndGetSelection(BigDecimal balance) {

        io.print("\nBalance: $" + balance);
        io.print("1. Insert Money");
        io.print("2. Purchase Item");
        io.print("3. Exit");

        return io.readInt("Please select from the above choices.", 1, 3);
    }

    public void displayItemList(List<Item> itemList) {

        io.print(" id\t Degree\t\tPrice\tQty"); //tab formatting
        for (Item currentItem : itemList) {
            io.print(currentItem.getSlotId() + ":\t"
                    + currentItem.getItemName() + "\t$"
                    + currentItem.getItemCost() + "\t"
                    + currentItem.getItemQuantity());
        }
    }

    public BigDecimal addMoney() {
        return new BigDecimal(io.readString("Please enter an amount: "));
    }

    public String getSlotIdChoice() {
        return io.readString("Please enter the slot id of the item you want to purchase.");
    }

    public void itemSuccessfullyPurchased(Item item) {
        io.print(item + " puchased successfully!");
    }

    public void displayChange(ChangePurse change, Item item, BigDecimal changeAmnt) {
        if (change != null) {
            io.print("\nThank you for your purchase of your " + item.getItemName() + " diploma");    
            io.print("Your change is $" + changeAmnt);
            io.print("Here are your coins");
            io.print("Quarters: " + change.getNumQuarters());
            io.print("Dimes: " + change.getNumDimes());
            io.print("Nickels: " + change.getNumNickels());
            io.print("Pennies: " + change.getNumPennies());
        } else {
            System.out.println("Out of item");
        }
        io.readString("\nPress enter to continue.");

    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
        io.readString("Press enter to continue.");
    }
    
    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
}
