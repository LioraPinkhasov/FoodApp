package com.example.foodapp;

public class Auser
{
  private  String email;

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Override
    public String toString() {
        return getEmail();
    }
}

