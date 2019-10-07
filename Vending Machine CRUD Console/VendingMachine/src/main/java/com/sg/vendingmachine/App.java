/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine;

import com.sg.vendingmachine.controller.VendingMachineController;
import com.sg.vendingmachine.dao.VendingMachineAuditDAO;
import com.sg.vendingmachine.dao.VendingMachineAuditDAOFileImpl;
import com.sg.vendingmachine.dao.VendingMachineDAO;
import com.sg.vendingmachine.dao.VendingMachineDAOFileImpl;
import com.sg.vendingmachine.service.VendingMachineService;
import com.sg.vendingmachine.service.VendingMachineServiceImpl;
import com.sg.vendingmachine.ui.VendingMachineUserIO;
import com.sg.vendingmachine.ui.VendingMachineUserIOConsoleImpl;
import com.sg.vendingmachine.ui.VendingMachineView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author EricR
 */
public class App {
    public static void main(String[] args) {
        
//        VendingMachineUserIO myIo = new VendingMachineUserIOConsoleImpl();
//        VendingMachineView myView = new VendingMachineView(myIo);
//        VendingMachineDAO myDao = new VendingMachineDAOFileImpl();
//        VendingMachineAuditDAO myAuditDao = new VendingMachineAuditDAOFileImpl();
//        VendingMachineServiceImpl myService = new VendingMachineServiceImpl(myDao, myAuditDao);
//        VendingMachineController controller = new VendingMachineController(myService, myView);
//        
//        controller.run();

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);
        controller.run();
    }
}
