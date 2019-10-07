/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDAO;
import com.sg.vendingmachine.dto.ChangePurse;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.stubs.VendingMachineAuditDAOStubImpl;
import com.sg.vendingmachine.service.stubs.VendingMachineDAOStubImpl;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author EricR
 */
public class VendingMachineServiceTest {

    VendingMachineServiceImpl testService;
    Item testItemOne;
    Item testItemTwo;

    public VendingMachineServiceTest() {
//        testItemOne = new Item("010", "Party Animal", "3.00", "0");
//        testItemTwo = new Item("011", "General Studies", "4.00", "5");
//
//        VendingMachineDAOStubImpl daoStub = new VendingMachineDAOStubImpl(testItemOne, testItemTwo);
//        VendingMachineAuditDAO auditDaoStub = new VendingMachineAuditDAOStubImpl();
//
//        testService = new VendingMachineServiceImpl(daoStub, auditDaoStub);

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testService = ctx.getBean("serviceLayer", VendingMachineServiceImpl.class);
        testItemOne = ctx.getBean("itemOne", Item.class);
        testItemTwo = ctx.getBean("itemTwo", Item.class);
    }

    @Test
    public void testGetAllItemsInMachine() {
        Assertions.assertEquals(2, testService.getAllItemsInMachine().size(), "list sizes should equal 2");
    }

    /**
     * Test of getOneItem method, of class VendingMachineService.
     */
    @Test
    public void testGetOneItem() {
        Item item = testService.getOneItem("011");
        Assertions.assertNotNull(item);
        item = testService.getOneItem("999");
        Assertions.assertNull(item);
    }

    @Test
    public void testNoQuantity() throws Exception {

        BigDecimal moneyHolder = new BigDecimal("5.00");

        try {
            testService.purchaseItem("010", moneyHolder); //if this doesn't throw an exceeption, fail
            fail("Expected VendingMachineNoItemInventoryException");
        } catch (VendingMachineNoItemInventoryException e) {

        }
    }

    @Test
    void testQuantityUpdate() throws Exception {
        BigDecimal moneyHolder = new BigDecimal("5.00");

        String expectedQuantityBefore = "5";
        String expectedQuantityAfter = "4";
        String actualQuantityBefore = testItemTwo.getItemQuantity();

        Assertions.assertEquals(expectedQuantityBefore, actualQuantityBefore, "Both quantites should be same");

        testService.purchaseItem("011", moneyHolder); // upon succesful purchase will update quantity
        String actualQuantityAfter = testItemTwo.getItemQuantity();

        Assertions.assertEquals(expectedQuantityAfter, actualQuantityAfter, "Both quantities should be same");
    }

    @Test
    public void testNoMoneyInserted() throws Exception {
        //
        BigDecimal moneyHolder = new BigDecimal("0.00");

        try {
            testService.purchaseItem("011", moneyHolder); //if this doesn't throw an exceeption, fail
            fail("Expected VendingMachineInsufficientFundsException");
        } catch (VendingMachineInsufficientFundsException e) {

        }
    }

    @Test
    public void testNotEnoughMoneyInserted() throws Exception {
        //
        BigDecimal moneyHolder = new BigDecimal("1.00");

        try {
            testService.purchaseItem("011", moneyHolder); //if this doesn't throw an exceeption, fail
            fail("Expected VendingMachineInsufficientFundsException");
        } catch (VendingMachineInsufficientFundsException e) {

        }
    }

    @Test
    public void testEqualAmountOfFundsAsPrice() throws Exception {

        BigDecimal moneyHolder = new BigDecimal("4.00");

        ChangePurse change = testService.purchaseItem("011", moneyHolder);
        int expectedQuarters = 0;
        int expectedDimes = 0;
        int expectedNickels = 0;
        int expectedPennies = 0;
        BigDecimal expectedBalance = new BigDecimal("0.00");

        Assertions.assertEquals(expectedQuarters, change.getNumQuarters(), "Quarters should both be 0");
        Assertions.assertEquals(expectedDimes, change.getNumDimes(), "Dimes should both be 0");
        Assertions.assertEquals(expectedNickels, change.getNumNickels(), "Nickels should both be 0");
        Assertions.assertEquals(expectedPennies, change.getNumPennies(), "Pennies should both be 0");
        Assertions.assertEquals(expectedBalance, testService.getTotalBalance(), "Both balances should be 0.00");
    }

    @Test
    public void testSufficientFunds() throws Exception {

        BigDecimal moneyHolder = new BigDecimal("7.89");

        ChangePurse change = testService.purchaseItem("011", moneyHolder);
        // Change should be $3.89
        int expectedQuarters = 15;
        int expectedDimes = 1;
        int expectedNickels = 0;
        int expectedPennies = 4;

        Assertions.assertEquals(expectedQuarters, change.getNumQuarters(), "Quarters should both be 15");
        Assertions.assertEquals(expectedDimes, change.getNumDimes(), "Dimes should both be 1");
        Assertions.assertEquals(expectedNickels, change.getNumNickels(), "Nickels should both be 0");
        Assertions.assertEquals(expectedPennies, change.getNumPennies(), "Pennies should both be 4");
    }

}
