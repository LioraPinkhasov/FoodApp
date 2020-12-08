package com.example.foodapp;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecipeDetails
{


    // Memebrs as defined in FbDb

    // A : id
    private   String A;

    // B : the products used in the recipe

    private String B;

    // C : Units to use for each ingredient in B

    private String C;

    // D : Creation date

    private String D;
    // E : Update date
    private String E;

    // Default constructor
    public RecipeDetails() {}

    // More ditailed constructor
    public RecipeDetails( String id ,String ingredients , String units , String create_date , String update_date )
    {
        this.A = id;
        this.B = ingredients;
        this.C = units;
        this.D = create_date;
        this.E = update_date;
    }

    /**
     * getA = getID the name is for DB
     * @return id
     */
    public String getA()
    {
        return A;
    }

    /**
     * setA = setID
     * @param A
     */
    public void setA(String id)
    {
        this.A = id;
    }

    /**
     * SetB = setName
     * @param
     */
    public String getB()
    {
        return B;
    }
    public void setB(String name)
    {
        this.B = name;
    }
    public String getC()
    {
        return C;
    }
    public void setC(String units)
    {
        this.C = units;
    }
    public  String getD()
    {
        return D;
    }
    public void setD(String create_date)
    {
        this.D = create_date;
    }

    public String getE() {
        return E;
    }

    public void setE(String e) {
        E = e;
    }
    // This next part is a constructor by id ,  where it will find the proper recipe from out DB and fill the details according to it.

    public RecipeDetails (String id)
    // Need constructor to build from DB
    {


    }


}
