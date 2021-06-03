package com.example.diplomjava;

public class clothes {
    String description, name;
    int armor_class, mind_armor_class, place_of_protection; // place_of_protection 1 - leg, 2 - body, 3 - head

    public clothes(String name, String description, int armor_class, int mind_armor_class, int place_of_protection) {
        this.place_of_protection = place_of_protection;
        this.name = name;
        this.armor_class = armor_class;
        this.mind_armor_class = mind_armor_class;
        this.description = description;
    }
    String clothes_review(){
        return ("Название: " + name + "\n" + "Физическая защита +" + armor_class + " Ментальная защита +" + mind_armor_class + "\n" + "Описание " + description);
    }
}
