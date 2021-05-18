package com.example.diplomjava;

public class item{
    String name;
    int amount_of_treatment, what_heals; //what_heals 1-xp, 2-mind 3 - damageEnemy
    public item(String name, int amount_of_treatment, int what_heals) {
        this.name = name;
        this.amount_of_treatment = amount_of_treatment;
        this.what_heals = what_heals;
    }
}
