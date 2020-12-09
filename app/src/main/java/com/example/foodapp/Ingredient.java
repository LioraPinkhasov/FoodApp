package com.example.foodapp;


/**
 * This class represents a ingridient named as product in the DB
 */
public class Ingredient {
    public String A,B,C,D,E,F;


    public Ingredient(){}


    public Ingredient(String A, String B, String C, String D, String E, String F) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.E = E;
        this.F = F;


    }

    public String getA() {
        return A;
    }

    public String getB() {
        return B;
    }

    public String getC() {
        return C;
    }

    public String getD() {
        return D;
    }

    public String getE() {
        return E;
    }

    public String getF() {
        return F;
    }

    public void setA(String A) {
        this.A = A;
    }

    public void getB(String B) {
        this.B = B;
    }

    public void getC(String C) {
        this.C = C;
    }

    public void getD(String D) {
        this.D = D;
    }

    public void getE(String E) {
        this.E = E;
    }

    public void getF(String F) {
        this.F = F;
    }


    public String toString() {
        return "ID: " + A + ", hebrew name: " +B+ ", english name" + C +", measure units: " + D + "\n " +
                "adding date: " + E + "changing date: " +  F;
    }

}