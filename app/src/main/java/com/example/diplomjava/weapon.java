package com.example.diplomjava;

public class weapon {
    String description, name;
    int bonus_damage;

    public weapon(String name, int bonus_damage, String description) {
        this.name = name;
        this.bonus_damage = bonus_damage;
        this.description = description;
    }
    void weapon_review(){
        System.out.println("Название: " + name + "\n" + "Бонусный урон +" + bonus_damage+ "\n" + "Описание " + description);
    }
}
