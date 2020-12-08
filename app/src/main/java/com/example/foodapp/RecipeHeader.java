package com.example.foodapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecipeHeader
{
    // Members


    private String A,B,C,D,E;
    // A : recipe id; is the same as the ID in RecipeDetails
    // B : Hebrew name not implemented
    // C : English name
    //public String name;

    // Default constructor
    public RecipeHeader() {}
    // Explicit constructor
    public RecipeHeader(String a , String b , String c , String d , String e )
    {
        this.A = a;
        this.B = b;
        this.C = c;
        this.D = d;
        this.E = e;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getE() {
        return E;
    }

    public void setE(String e) {
        E = e;
    }
    /////////////// This constructor contacts the DB and pass the RecipeHeader info to create an instance of RecipeHeader \\\\\\\\\\\\\

    // Connect to FBDB
  // FirebaseDatabase database = FirebaseDatabase.getInstance() ;
   // DatabaseReference dbRHRef = database.getReference() ;



    // Init connection



    public RecipeHeader(String id)
    {

    }



}
