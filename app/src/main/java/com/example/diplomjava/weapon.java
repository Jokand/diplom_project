package com.example.diplomjava;

import android.annotation.SuppressLint;

public class weapon {
    String description, name;
    int bonus_damage;
    public weapon(String name, int bonus_damage, String description) {
        this.name = name;
        this.bonus_damage = bonus_damage;
        this.description = description;
    }
    @SuppressLint("SetTextI18n")
    String weapon_review(){
        return ("Название: " + name + "\n" + "Бонусный урон +" + bonus_damage+ "\n" + "Описание " + description);
    }
}
