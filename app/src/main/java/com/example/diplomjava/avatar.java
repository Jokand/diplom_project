package com.example.diplomjava;

import android.annotation.SuppressLint;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class avatar {
    int xp, mind, armor, mind_armor, damage, MAX_xp, MAX_mind, const_armor, const_armor_mind;
    ArrayList<item> inventory;
    ArrayList<Object> available_equipment;
    weapon weapon;
    clothes head_clothes, body_clothes, legs_clothes;

    LinearLayout textsLayout, buttonsLayout;
    TextView someTextHelper;


    public avatar(int xp, int mind, int armor, int mind_armor, int damage, weapon weapon, clothes head_clothes, clothes body_clothes, clothes legs_clothes) {
        this.xp = xp;
        this.mind = mind;
        this.armor = armor;
        this.const_armor = armor;
        this.mind_armor = mind_armor;
        this.const_armor_mind = mind_armor;
        this.damage = damage;
        this.MAX_xp = xp;
        this.MAX_mind = mind;
        this.inventory = new ArrayList<>();
        this.available_equipment = new ArrayList<>();
        this.weapon = weapon;
        this.head_clothes = head_clothes;
        this.body_clothes = body_clothes;
        this.legs_clothes = legs_clothes;
    }

    int avatar_attack() {
        return (int) (damage * Math.random() + 1 + weapon.bonus_damage);
    }

    void defense() {
        armor = armor + 2;
    }

    void stop_effect_defense() {
        if (const_armor != armor) {
            armor = const_armor;
        }
    }

    void defense_mind() {
        mind_armor = mind_armor + 2;
    }

    void stop_effect_defense_mind() {
        if (const_armor_mind != mind_armor) {
            mind_armor = const_armor_mind;
        }
    }

    int avatar_hit() {
        return new Random().nextInt(21)+ weapon.bonus_damage;
    }

    @SuppressLint("SetTextI18n")
    String avatar_review(){
        return ("\n Физическое здоровье: " + xp + ", Ментальное здоровье: " + mind + "\n" +
                "Защита тела равна: " + const_armor + ", Защита разума равна: " + const_armor_mind + "\n" +
                "\n" + "Экипированное оружие:" + "\n" + weapon.weapon_review() + "\n" + "\n" +  "Экипированная броня:" + "\n"
                + head_clothes.clothes_review()+ "\n" + "\n" + body_clothes.clothes_review() + "\n" + "\n" + legs_clothes.clothes_review());
    }

    String avatar_healing_xp(int heal) {
        int difference = MAX_xp - xp;
        if (difference > heal) {
            xp = xp + heal;
            return new String("Вы восстановили " + heal + " едениц здоровья " + xp + "/" + MAX_xp);
        } else {
            xp = MAX_xp;
            return new String("Вы полностью восстановили здоровье " + xp + "/" + MAX_xp);
        }
    }
    String avatar_healing_mind(int mind_heal) {
        int difference = MAX_mind - mind;
        if (difference > mind_heal) {
            mind = mind + mind_heal;
            return new String("Вы восстановили " + mind_heal + " едениц ментального здоровья " + mind + "/" + MAX_mind);
        } else {
            xp = MAX_xp;
            return new String("Вы полностью восстановили ментальное здоровье " + mind + "/" + MAX_mind);
        }
    }
}
