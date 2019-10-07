/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dto;

import java.util.Objects;

/**
 *
 * @author EricR
 */
public class Item {
    
    private String slotId;
    private String itemName;
    private String itemCost;
    private String itemQuantity;

    public Item(String slotId) {
        this.slotId = slotId;
    }

    public Item(String slotId, String itemName, String itemCost, String itemQuantity) {
        this.slotId = slotId;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemQuantity = itemQuantity;
    }

    public Item() {
    }
    
    
    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCost() {
        return itemCost;
    }

    public void setItemCost(String itemCost) {
        this.itemCost = itemCost;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
    
    @Override
    public int hashCode() {
        int hash = 4;
        hash = 47 * hash + Objects.hashCode(this.slotId);
        hash = 47 * hash + Objects.hashCode(this.itemName);
        hash = 47 * hash + Objects.hashCode(this.itemCost);
        hash = 47 * hash + Objects.hashCode(this.itemQuantity);
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (!Objects.equals(this.slotId, other.slotId)) {
            return false;
        }
        if (!Objects.equals(this.itemName, other.itemName)) {
            return false;
        }
        if (!Objects.equals(this.itemCost, other.itemCost)) {
            return false;
        }
        if (!Objects.equals(this.itemQuantity, other.itemQuantity)) {
            return false;
        }

        return true;
    }
    
}
