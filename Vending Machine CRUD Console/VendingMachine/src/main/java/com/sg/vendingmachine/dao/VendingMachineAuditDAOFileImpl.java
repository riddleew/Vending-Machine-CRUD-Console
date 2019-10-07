/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author EricR
 */
public class VendingMachineAuditDAOFileImpl implements VendingMachineAuditDAO {

    public static final String AUDIT_FILE = "audit.txt";
    
    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        PrintWriter out;
        
        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException ex) {
            throw new VendingMachinePersistenceException("Could not persist audit information.", ex);
        }
        
        LocalDateTime timestamp = LocalDateTime.now();
        
        //timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        out.println(timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")) + " : " + entry);
        //TO-DO if time allows
        //Load - item, qty
        //Added Money - amnt
        //Vended - item, price
        //Change dispensed - amnt
        //Exceptions
        //Save
        
        out.flush();
    }
    
}
