package com.example.diplomjava;

import com.example.diplomjava.MainActivity.event;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class location{
    String name;
    String description;
    static ArrayList<Object> loot;
    ArrayList<MainActivity.event> events;
    ArrayList<enemy> enemy_in_location;
    Scanner cin = new Scanner(System.in);
    public location(String name, String description, ArrayList<Object> loot, ArrayList<MainActivity.event> event, ArrayList<enemy> enemy_in_location) {
        this.name = name;
        this.description = description;
        this.loot = loot;
        this.events = events;
        this.enemy_in_location = enemy_in_location;
    }
    static String exploration(avatar hero) {
        int chance_exploration = new Random().nextInt(11);
        MainActivity.ritual_counter--;

//        if(chance_exploration<3){
//            giving_out_loot(hero);
//        } else if(3<chance_exploration && chance_exploration<6){

//        event this_event = events.get(new Random().nextInt(events.size()));
//        this_event.issuing_an_event(hero);
        return giving_out_loot(hero);
//        } else {
//           enemy this_enemy = enemy_in_location.get(new Random().nextInt(enemy_in_location.size()));
//            int result_duel = fight(this_enemy, hero);//1 - поражение 2 - победа 3 - побег
//            if (result_duel == 1) {
//                return true;
//            } else if (result_duel == 2)
//                giving_out_loot(hero);
//        }
    }


    static String giving_out_loot(avatar hero){
        String lootOne = "";
        String lootTwo = "Всякий мусор";
        Object Added_item = loot.get(new Random().nextInt(loot.size()));
        if (Added_item instanceof item) {
            hero.inventory.add((item) Added_item);
            lootOne =((item) Added_item).name;
        } else {
            hero.available_equipment.add(Added_item);
            if (Added_item instanceof weapon) {
                lootOne =((weapon) Added_item).name;
            } else
                lootOne =((clothes) Added_item).name;
        }
        if(new Random().nextBoolean()){
            Object Added_item_two = loot.get(new Random().nextInt(loot.size()));
            if (Added_item_two instanceof item) {
                hero.inventory.add((item) Added_item_two);
                lootTwo =((item) Added_item_two).name;
            } else {
                hero.available_equipment.add(Added_item_two);
                if (Added_item_two instanceof weapon) {
                    lootTwo =((weapon) Added_item_two).name;
                } else
                    lootTwo =((clothes) Added_item_two).name;
            }
        }
        return("Вы порылись в ящиках и нашли некоторые вещи:\n" + lootOne + " и "+ lootTwo);
    }
}
