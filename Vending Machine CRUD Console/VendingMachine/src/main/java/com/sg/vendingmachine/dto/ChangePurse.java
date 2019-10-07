/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dto;

import java.math.BigDecimal;

/**
 *
 * @author EricR
 */
public class ChangePurse {

    public enum CoinType {
        PENNY, NICKEL, DIME, QUARTER
    }

    private int numPennies;
    private int numNickels;
    private int numDimes;
    private int numQuarters;
    private BigDecimal totalPennies;
    //private BigDecimal remainingBalance;

    public ChangePurse(BigDecimal totalPennies) {
        this.totalPennies = totalPennies;
    }

    public void generateCoins() {
        while (totalPennies.intValueExact() > 0) {
            while (totalPennies.intValueExact() >= 25) {
                addCoin(CoinType.QUARTER);
                totalPennies = totalPennies.subtract(new BigDecimal("25"));
            }
            while (totalPennies.intValueExact() >= 10) {
                addCoin(CoinType.DIME);
                totalPennies = totalPennies.subtract(new BigDecimal("10"));
            }
            while (totalPennies.intValueExact() >= 5) {
                addCoin(CoinType.NICKEL);
                totalPennies = totalPennies.subtract(new BigDecimal("5"));
            }
            while (totalPennies.intValueExact() >= 1) {
                addCoin(CoinType.PENNY);
                totalPennies = totalPennies.subtract(new BigDecimal("1"));
            }
        }
    }

    private void addCoin(CoinType type) {
        switch (type) {
            case QUARTER:
                numQuarters++;
                break;
            case DIME:
                numDimes++;
                break;
            case NICKEL:
                numNickels++;
                break;
            case PENNY:
                numPennies++;
                break;
        }
    }

    public BigDecimal getTotalPennies() {
        return totalPennies;
    }

    public int getNumPennies() {
        return numPennies;
    }

    public void setNumPennies(int numPennies) {
        this.numPennies = numPennies;
    }

    public int getNumNickels() {
        return numNickels;
    }

    public void setNumNickels(int numNickels) {
        this.numNickels = numNickels;
    }

    public int getNumDimes() {
        return numDimes;
    }

    public void setNumDimes(int numDimes) {
        this.numDimes = numDimes;
    }

    public int getNumQuarters() {
        return numQuarters;
    }

    public void setNumQuarters(int numQuarters) {
        this.numQuarters = numQuarters;
    }

}
