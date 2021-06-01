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
    String exploration(avatar hero) {
        int chance_exploration = new Random().nextInt(11);
        MainActivity.ritual_counter--;

//        if(chance_exploration<3){
//            giving_out_loot(hero);
//        } else if(3<chance_exploration && chance_exploration<6){

        event this_event = events.get(new Random().nextInt(events.size()));
        this_event.issuing_an_event(hero);
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


    int fight(enemy Enemy, avatar hero){
        boolean const_defence_avatar = false, const_defence_mind_avatar = false, const_defence_enemy = false;
        int count_defence_avatar = 0, count_defence_mind_avatar = 0, count_defence_enemy = 0, result_defeat = 0;
        hero.armor = hero.const_armor;
        hero.mind_armor = hero.const_armor_mind;
        while (Enemy.xp > 0 && hero.xp > 0 && hero.mind > 0) {
            System.out.println("1) Атаковать" + "\n" + "2) Защищаться" + "\n" + "3) Использовать предмет" + "\n" + "4) Оценка своего состояния" + "\n" + "5) Убежать");
            if (count_defence_avatar == 2) {
                hero.stop_effect_defense();
                count_defence_avatar = 0;
                const_defence_avatar = false;
            } else if (const_defence_avatar) count_defence_avatar++;
            if (count_defence_mind_avatar == 2) {
                hero.stop_effect_defense_mind();
                count_defence_mind_avatar = 0;
                const_defence_mind_avatar = false;
            } else if (const_defence_mind_avatar) count_defence_mind_avatar++;
            int vote = cin.nextInt();
            if(vote==1){
                int hit = hero.avatar_hit();
                if ((hit > Enemy.armor) && (Enemy.xp > 0)) {
                    Enemy.xp -= hero.avatar_attack();
                    System.out.println("Вы ранили " + Enemy.name + ". Теперь у него осталось " + Enemy.xp + "/" + Enemy.MAX_xp);
                } else if (hit <= Enemy.armor) {
                    System.out.println("Вы не попали по " + Enemy.name + ". " + Enemy.xp + "/" + Enemy.MAX_xp);
                }
            }else if(vote==2) {
                System.out.println("Выберите что будете защищать:" + "\n" + "1. Повысить защиту тела" + "\n" + "2. Повысить защиту разума");

                int defence_vote = cin.nextInt();
                if(defence_vote==1){
                    hero.defense();
                    const_defence_avatar = true;
                    System.out.println("Вы встали в защитную стойку. Ваша защита была повышена на 2 еденицы. Ваша защита теперь равна " + hero.armor + ".");

                }else if(defence_vote==2) {
                    hero.defense_mind();
                    const_defence_mind_avatar = true;
                    System.out.println("Вы отчистили свой разум. Ваша ментальная защита была повышена на 2 еденицы. Ваша ментальная защита теперь равна " + hero.mind_armor + ".");

                }
            }else if(vote==3){
                if (hero.inventory.size() == 0) {
                    System.out.println("Инвентарь пуст");
                    break;
                }
                for (int i = 0; i < hero.inventory.size(); i++) {
                    System.out.println((i + 1) + ". " + hero.inventory.get(i).name);
                }
                int inventory_vote = cin.nextInt() - 1;
                try {
                    if (hero.inventory.get(inventory_vote).what_heals == 1) {
                        hero.avatar_healing_xp(hero.inventory.get(inventory_vote).amount_of_treatment);
                        System.out.println("Вы использовали " + hero.inventory.get(inventory_vote + 1).name + " и восстановили " + hero.inventory.get(inventory_vote).amount_of_treatment + " xp");
                    } else if (hero.inventory.get(inventory_vote).what_heals == 2) {
                        hero.avatar_healing_mind(hero.inventory.get(inventory_vote).amount_of_treatment);
                        System.out.println("Вы использовали " + hero.inventory.get(inventory_vote + 1).name + " и восстановили " + hero.inventory.get(inventory_vote).amount_of_treatment + " очков ментального здоровья");
                    } else {
                        Enemy.xp -= hero.inventory.get(inventory_vote).amount_of_treatment;
                        System.out.println("Вы использовали " + hero.inventory.get(inventory_vote + 1).name + " ранили своего противника на  " + hero.inventory.get(inventory_vote).amount_of_treatment + " урона.");
                    }
                    hero.inventory.remove(inventory_vote);
                } catch (Exception ex) {
                    System.out.println("Некорректный ввод");
                }
            }else if(vote==4){
                hero.avatar_review();
            }else if(vote==5){
                System.out.println("Вы сбежали с поля боя!");
                result_defeat = 3;
            }
            if (Enemy.xp <= 0) {
                System.out.println("Вы убили своего противника " + Enemy.name + ".");
                result_defeat = 2;
            }
            int enemyAttack = new Random().nextInt(101);
            if (count_defence_enemy == 2) {
                Enemy.stop_effect_defense();
                count_defence_enemy = 0;
                const_defence_enemy = false;
            } else if (const_defence_enemy) count_defence_enemy++;
            if (enemyAttack < Enemy.bodyDamagePercent && enemyAttack > Enemy.mindDamagePercent) {
                int hit = Enemy.enemy_hit();
                if ((hit > hero.armor) && (hero.xp > 0)) {
                    hero.xp -= Enemy.attack();
                    System.out.println("Вас ранил " + Enemy.name + ". Теперь у вас осталось " + hero.xp + "/" + hero.MAX_xp);
                } else if (hit <= hero.armor) {
                    System.out.println("Меня ударили, но не пробили броню." + hero.xp + "/" + hero.MAX_xp);
                } else {
                    System.out.println("Вас убил противник.");
                    System.out.println("GAME OVER");
                    result_defeat = 1;
                }
            } else if (enemyAttack < Enemy.bodyDamagePercent && enemyAttack > Enemy.defencePercent) {
                int hit = Enemy.enemy_hit();
                if ((hit > hero.mind_armor) && (hero.mind > 0)) {
                    hero.mind -= Enemy.attack_mind();
                    System.out.println("Ваш разум повредил " + Enemy.name + ". Теперь у вас осталось " + hero.mind + "/" + hero.MAX_mind);
                } else if (hit <= hero.mind_armor) {
                    System.out.println("Меня ударили, но не пробили ментальную броню." + hero.mind + "/" + hero.MAX_mind);
                }else {
                    System.out.println("Вас свёл с ума противник.");
                    System.out.println("GAME OVER");
                    result_defeat = 1;

                }

            } else {
                Enemy.defense();
                const_defence_enemy = true;
                System.out.println(Enemy.name + " усилил защиту " + Enemy.armor);
            }
        }
        return result_defeat;//1 - поражение 2 - победа 3 - побег
    }
}
