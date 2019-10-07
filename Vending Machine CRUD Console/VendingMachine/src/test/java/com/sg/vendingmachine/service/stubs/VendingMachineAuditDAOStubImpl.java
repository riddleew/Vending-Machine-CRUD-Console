/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service.stubs;

import com.sg.vendingmachine.dao.VendingMachineAuditDAO;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;

/**
 *
 * @author EricR
 */
public class VendingMachineAuditDAOStubImpl implements VendingMachineAuditDAO {

    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        // do nothing...
    }

}
