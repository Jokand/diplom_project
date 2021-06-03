package com.example.diplomjava;

import java.util.Random;

public class enemy {
    String name;
    int mindDamagePercent, defencePercent;
    int xp, armor, damage, damage_mind, MAX_xp, const_armor, attack_modifier;

    public enemy(String name, int xp, int armor, int damage, int attack_modifier, int damage_mind, int mindDamagePercent, int defencePercent) {
        this.name = name;
        this.xp = xp;
        this.armor = armor;
        this.damage = damage;
        this.attack_modifier = attack_modifier;
        this.damage_mind = damage_mind;
        this.MAX_xp = xp;
        this.mindDamagePercent = mindDamagePercent;
        this.defencePercent = defencePercent;
    }
    int attack() {
        return (int) (damage * Math.random() + 1);
    }

    int attack_mind() {
        return (int) (damage_mind * Math.random() + 1);
    }

    void defense() {
        armor = armor + 2;
    }

    int enemy_hit() {
        return new Random().nextInt(19) + 1 + attack_modifier;
    }

}
