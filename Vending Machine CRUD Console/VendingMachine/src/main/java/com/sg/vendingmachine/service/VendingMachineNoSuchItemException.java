/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

/**
 *
 * @author EricR
 */
public class VendingMachineNoSuchItemException extends Exception {
    public VendingMachineNoSuchItemException(String message) {
        super(message);
    }
    
    public VendingMachineNoSuchItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
