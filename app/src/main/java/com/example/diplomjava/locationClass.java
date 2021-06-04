package com.example.diplomjava;
import java.util.ArrayList;
import java.util.Random;
public class locationClass {
    String name;
    ArrayList<Object> loot;
    ArrayList<MainActivity.event> events;
    ArrayList<enemy> enemy_in_location;

    public locationClass(String name, ArrayList<Object> loot, ArrayList<MainActivity.event> events, ArrayList<enemy> enemy_in_location) {
        this.name = name;
        this.loot = loot;
        this.events = events;
        this.enemy_in_location = enemy_in_location;
    }

     Object exploration(avatar hero) {
        int chance_exploration = new Random().nextInt(11);
        MainActivity.ritual_counter--;
        if(chance_exploration<2){
            return giving_out_loot(hero);
        } else if(3<chance_exploration && chance_exploration<6 && events != null){
            return events.get(new Random().nextInt(events.size()));
        } else {
            return enemy_in_location.get(new Random().nextInt(enemy_in_location.size()));
        }
    }

    String giving_out_loot(avatar hero) {
        String lootOne;
        String lootTwo = "Всякий мусор";
        Object Added_item = loot.get(new Random().nextInt(loot.size()));
        if (Added_item instanceof item) {
            hero.inventory.add((item) Added_item);
            lootOne = ((item) Added_item).name;
        } else {
            hero.available_equipment.add(Added_item);
            if (Added_item instanceof weapon) {
                lootOne = ((weapon) Added_item).name;
            } else
                lootOne = ((clothes) Added_item).name;
        }
        if (new Random().nextBoolean()) {
            Object Added_item_two = loot.get(new Random().nextInt(loot.size()));
            if (Added_item_two instanceof item) {
                hero.inventory.add((item) Added_item_two);
                lootTwo = ((item) Added_item_two).name;
            } else {
                hero.available_equipment.add(Added_item_two);
                if (Added_item_two instanceof weapon) {
                    lootTwo = ((weapon) Added_item_two).name;
                } else
                    lootTwo = ((clothes) Added_item_two).name;
            }
        }
        return ("Вы порылись в ящиках и нашли некоторые вещи:\n" + lootOne + " и " + lootTwo);
    }
}