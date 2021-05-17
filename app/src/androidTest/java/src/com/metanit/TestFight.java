package src.com.metanit;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TestFight {

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        items heal_body = new items("Аптечка", 5, 1);
        items heal_mind = new items("Книга", 5, 2);
        items damage_enemy= new items("Обрез", 10, 3);

        weapon arms = new weapon("кулаки", 1, "Тупа кулаки");
        weapon knife = new weapon("Нож", 3, "Ножик делает вжик вжик");
        clothes cap = new clothes("ШапОчка", "Щапка", 0, 1,3);
        clothes T_shirt = new clothes("Футболочка", "Тряпка", 0, 1,2);
        clothes shorts = new clothes("Шортики", "Короткие сексуальные шортики", 1, 0,1);
        clothes helmet = new clothes("щлем", "железный шлем", 1, 2,3);
        clothes bulletproof_vest = new clothes("Броник", "Непробиваемая штука", 4, 1,2);
        clothes trousers = new clothes("Штаны", "Штаны из берёзовой коры", 2, 1,1);

        avatar hero = new avatar(20, 30,10, 10, 4, arms, cap, T_shirt, shorts);

        hero.available_equipment.add(knife);
        hero.available_equipment.add(helmet);
        hero.available_equipment.add(bulletproof_vest);
        hero.available_equipment.add(trousers);


        //выбор снаряжения в базе перед боем
        System.out.println("Выбор оружия и брони перед вылазкой");
        while (true) {
            for (int i = 0; i < hero.available_equipment.size(); i++) {
                if (hero.available_equipment.get(i) instanceof weapon) {
                    System.out.println((i + 1) + ". " + ((weapon) hero.available_equipment.get(i)).name);
                } else {
                    System.out.println((i + 1) + ". " + ((clothes) hero.available_equipment.get(i)).name);
                }
            }
            System.out.println("0. Прекратить выбирать снаряжение");
            int inventory_vote = cin.nextInt() - 1;
            if (inventory_vote == -1) {
                break;
            }
            boolean category_clothes = hero.available_equipment.get(inventory_vote) instanceof clothes;
            if (category_clothes) {
                try {
                    clothes choosed = null;
                    switch (((clothes) hero.available_equipment.get(inventory_vote)).place_of_protection) {
                        case 1 -> {
                            clothes leg = hero.legs_clothes;
                            hero.legs_clothes = (clothes) hero.available_equipment.get(inventory_vote);
                            choosed = (clothes) hero.available_equipment.get(inventory_vote);
                            hero.available_equipment.remove(inventory_vote);
                            hero.available_equipment.add(leg);
                        }
                        case 2 -> {
                            clothes body = hero.body_clothes;
                            hero.body_clothes = (clothes) hero.available_equipment.get(inventory_vote);
                            choosed = (clothes) hero.available_equipment.get(inventory_vote);
                            hero.available_equipment.remove(inventory_vote);
                            hero.available_equipment.add(body);
                        }
                        case 3 -> {
                            clothes head = hero.head_clothes;
                            hero.head_clothes = (clothes) hero.available_equipment.get(inventory_vote);
                            choosed = (clothes) hero.available_equipment.get(inventory_vote);
                            hero.available_equipment.remove(inventory_vote);
                            hero.available_equipment.add(head);
                        }
                    }
                    hero.const_armor = 10 + hero.head_clothes.armor_class + hero.body_clothes.armor_class + hero.legs_clothes.armor_class;
                    hero.const_armor_mind = 10 + hero.head_clothes.mind_armor_class + hero.body_clothes.mind_armor_class + hero.legs_clothes.mind_armor_class;
                    assert choosed != null;
                    System.out.println("Вы надели на себя " + choosed.name + ". Теперь ваша физическая защита равна " + hero.const_armor + ", а ваша ментальная защита стала равна " + hero.const_armor_mind);
                } catch (Exception ex) {
                    System.out.println("Неккоректный ввод1");
                }
            } else {
                try {
                    weapon choosed;
                    weapon Weapon = hero.weapon;
                    hero.weapon = (weapon) hero.available_equipment.get(inventory_vote);
                    choosed = (weapon) hero.available_equipment.get(inventory_vote);
                    hero.available_equipment.remove(inventory_vote);
                    hero.available_equipment.add(Weapon);
                    assert choosed != null;
                    System.out.println("Вы надели на себя " + choosed.name + ". Теперь ваш модификатор урона равен " + hero.weapon.bonus_damage);
                } catch (Exception ex) {
                    System.out.println("Неккоректный ввод2");
                }
            }
        }

        hero.inventory.add(heal_body);
        hero.inventory.add(heal_body);
        hero.inventory.add(heal_mind);
        hero.inventory.add(heal_mind);
        hero.inventory.add(damage_enemy);
        hero.inventory.add(damage_enemy);
        enemy.EnemySteps steps = new enemy.EnemySteps(100, 70, 20);
        enemy Enemy_1 = new enemy("Культист", 15, 12, 4, 6, 100, 60, 10,5 , steps);

        //начинается метод боёвки
        enemy Enemy = Enemy_1;
        boolean upgraded = false, const_defence_avatar = false, const_defence_mind_avatar = false, const_defence_enemy = false;
        int count_defence_avatar = 0, count_defence_mind_avatar = 0, count_defence_enemy = 0;
        hero.armor = hero.const_armor;  hero.mind_armor = hero.const_armor_mind;
        while (Enemy.xp > 0 && hero.xp > 0 && hero.mind > 0) {
            System.out.println("1) Атаковать");
            System.out.println("2) Защищаться");
            System.out.println("3) Убежать");
            System.out.println("4) Использовать предмет");
            if(count_defence_avatar==2){
                hero.stop_effect_defense();
                count_defence_avatar = 0;
                const_defence_avatar = false;
            } else if(const_defence_avatar)count_defence_avatar++;
            if(count_defence_mind_avatar==2){
                hero.stop_effect_defense_mind();
                count_defence_mind_avatar = 0;
                const_defence_mind_avatar = false;
            } else if(const_defence_mind_avatar)count_defence_mind_avatar++;
            int vote = cin.nextInt();
            switch (vote) {
                case 1 -> {
                    int hit = hero.avatar_hit();
                    if ((hit > Enemy.armor) && (Enemy.xp > 0)) {
                        Enemy.xp -= hero.avatar_attack();
                        System.out.println("Вы ранили " + Enemy.name + ". Теперь у него осталось " + Enemy.xp + "/" + Enemy.MAX_xp);
                    } else if (hit <= Enemy.armor) {
                        System.out.println("Вы не попали по " + Enemy.name + ". " + Enemy.xp + "/" + Enemy.MAX_xp);
                    }
                }
                case 2 -> {
                    System.out.println("Выберите что будете защищать:");
                    System.out.println("1. Повысить защиту тела");
                    System.out.println("2. Повысить защиту разума");
                    int defence_vote = cin.nextInt();
                    switch (defence_vote){
                        case 1 -> {
                            hero.defense();
                            const_defence_avatar = true;
                            System.out.println("Вы встали в защитную стойку. Ваша защита была повышена на 2 еденицы. Ваша защита теперь равна " + hero.armor + ".");
                        }
                        case 2 -> {
                            hero.defense_mind();
                            const_defence_mind_avatar = true;
                            System.out.println("Вы отчистили свой разум. Ваша ментальная защита была повышена на 2 еденицы. Ваша ментальная защита теперь равна " + hero.mind_armor + ".");
                        }
                    }
                }
                case 3 -> {
                    System.out.println("Ну ты и курица!");
                    hero.avatar_run();
                    return;
                }
                case 4 -> {
                    if (hero.inventory.size()==0){
                        System.out.println("Инвентарь пуст");
                        break;
                    }
                    for(int i = 0; i < hero.inventory.size(); i++){
                        System.out.println((i+1) + ". " + hero.inventory.get(i).name);
                    }
                    int inventory_vote = cin.nextInt()-1;
                    try{
                        if(hero.inventory.get(inventory_vote).what_heals==1){
                            hero.avatar_healing_xp(hero.inventory.get(inventory_vote).amount_of_treatment);
                            System.out.println("Вы использовали " + hero.inventory.get(inventory_vote+1).name + " и восстановили " + hero.inventory.get(inventory_vote).amount_of_treatment + " xp");
                        }else if(hero.inventory.get(inventory_vote).what_heals==2){
                            hero.avatar_healing_mind(hero.inventory.get(inventory_vote).amount_of_treatment);
                            System.out.println("Вы использовали " + hero.inventory.get(inventory_vote+1).name + " и восстановили " + hero.inventory.get(inventory_vote).amount_of_treatment + " очков ментального здоровья");
                        } else{
                            Enemy.xp -= hero.inventory.get(inventory_vote).amount_of_treatment;
                            System.out.println("Вы использовали " + hero.inventory.get(inventory_vote+1).name + " ранили своего противника на  " + hero.inventory.get(inventory_vote).amount_of_treatment + " урона.");
                        }
                        hero.inventory.remove(inventory_vote);
                    }
                    catch(Exception ex){
                        System.out.println("Некорректный ввод");
                    }
                }
                default -> System.out.println("Ошибка");
            }
            if (Enemy.xp < Enemy.hpSteps && !upgraded) {
                Enemy.bodyDamagePercent = Enemy.steps.newBodyPercent;
                Enemy.mindDamagePercent = Enemy.steps.newMindPercent;
                Enemy.defencePercent = Enemy.steps.newDefencePercent;
                System.out.println("Теперь у босса вторая стадия!");
                upgraded = true;
            }
            if(Enemy.xp <=0){
                System.out.println("Вы убили своего противника " + Enemy.name + ".");
                break;
            }
            int enemyAttack = new Random().nextInt(101);
            if(count_defence_enemy==2){
                Enemy.stop_effect_defense();
                count_defence_enemy = 0;
                const_defence_enemy = false;
            } else if(const_defence_enemy)count_defence_enemy++;
            if (enemyAttack < Enemy.bodyDamagePercent  && enemyAttack > Enemy.mindDamagePercent){
                int hit = Enemy.enemy_hit();
                if((hit>hero.armor) && (hero.xp > 0)){
                    hero.xp -= Enemy.attack();
                    System.out.println("Вас ранил " + Enemy.name + ". Теперь у вас осталось " + hero.xp + "/" + hero.MAX_xp);
                }
                else if(hit<=hero.armor){
                    System.out.println("Меня ударили, но не пробили броню." + hero.xp + "/" + hero.MAX_xp);
                } else {
                    System.out.println("Вас убил противник.");
                    System.out.println("GAME OVER");
                    return;
                }
            } else if (enemyAttack < Enemy.bodyDamagePercent && enemyAttack > Enemy.defencePercent){
                int hit = Enemy.enemy_hit();
                if((hit>hero.mind_armor) && (hero.mind > 0)){
                    hero.mind -= Enemy.attack_mind();
                    System.out.println("Ваш разум повредил " + Enemy.name + ". Теперь у вас осталось " + hero.mind + "/" + hero.MAX_mind);
                }
                else if(hit<=hero.mind_armor){
                    System.out.println("Меня ударили, но не пробили ментальную броню." + hero.mind + "/" + hero.MAX_mind);
                }
            } else {
                Enemy.defense();
                const_defence_enemy = true;
                System.out.println(Enemy.name + " усилил защиту " + Enemy.armor);
            }
        }
    }
    public static class enemy{
        String name;
        int bodyDamagePercent, mindDamagePercent, defencePercent;
        int xp, armor, damage, damage_mind, hpSteps, MAX_xp, const_armor;
        EnemySteps steps;
        public enemy(String name, int xp, int armor, int damage, int damage_mind, int bodyDamagePercent, int mindDamagePercent , int defencePercent, int hpSteps, EnemySteps steps) {
            this.name = name;
            this.xp = xp;
            this.armor = armor;
            this.damage = damage;
            this.damage_mind = damage_mind;
            this.MAX_xp = xp;
            this.hpSteps = hpSteps;
            this.steps = steps;
            this.bodyDamagePercent = bodyDamagePercent;
            this.mindDamagePercent = mindDamagePercent;
            this.defencePercent = defencePercent;
        }

        static class EnemySteps {
            int newBodyPercent, newMindPercent, newDefencePercent;
            public EnemySteps(int newBodyPercent, int newMindPercent, int newDefencePercent) {
                this.newBodyPercent = newBodyPercent;
                this.newMindPercent = newMindPercent;
                this.newDefencePercent = newDefencePercent;
            }
        }

        int attack(){
            return (int) (damage * Math.random()+1);
        }

        int attack_mind(){
            return (int) (damage_mind * Math.random()+1);
        }
        void defense(){
            armor =  armor + 2;
        }
        void stop_effect_defense(){
            if(const_armor != armor) {
                armor =  const_armor;
            }
        }
        int enemy_hit(){
            return new Random().nextInt(19)+1;
        }
    }

    public static class avatar{
        int xp, mind, armor, mind_armor, damage, MAX_xp, MAX_mind, const_armor, const_armor_mind;
        ArrayList<items> inventory;
        ArrayList<Object> available_equipment;
        weapon weapon;
        clothes head_clothes, body_clothes, legs_clothes;

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
            this.body_clothes =  body_clothes;
            this.legs_clothes = legs_clothes;
        }
        int avatar_attack(){
            return (int) (damage * Math.random()+1+weapon.bonus_damage);
        }
        void defense(){
            armor =  armor + 2;
        }
        void stop_effect_defense(){
            if(const_armor != armor) {
                armor =  const_armor;
            }
        }
        void defense_mind(){
            mind_armor =  mind_armor + 2;
        }
        void stop_effect_defense_mind(){
            if(const_armor_mind != mind_armor) {
                mind_armor =  const_armor_mind;
            }
        }
        int avatar_hit(){
            return new Random().nextInt(21);
        }
        void avatar_run(){
            //сделать побег от противника
        }
        void avatar_healing_xp(int heal){
            int difference = MAX_xp - xp;
            if(difference>heal){
                xp = xp + heal;
                System.out.println("Вы восстановили " + heal + " едениц здоровья " + xp + "/" + MAX_xp);
            }
            else{
                xp = MAX_xp;
                System.out.println("Вы полностью восстановили здоровье " + xp + "/" + MAX_xp);}
        }
        void avatar_healing_mind(int mind_heal){
            int difference = MAX_mind - mind;
            if(difference>mind_heal){
                mind = mind + mind_heal;
                System.out.println("Вы восстановили " + mind_heal + " едениц ментального здоровья " + mind + "/" + MAX_mind);
            }
            else{
                mind = MAX_mind;
                System.out.println("Вы полностью восстановили ментальное здоровье " + mind + "/" + MAX_mind);}
        }
    }
    static class items{
        String name;
        int amount_of_treatment, what_heals; //what_heals 1-xp, 2-mind 3 - damageEnemy
        public items(String name, int amount_of_treatment, int what_heals) {
            this.name = name;
            this.amount_of_treatment = amount_of_treatment;
            this.what_heals = what_heals;
        }
    }
    public static class weapon{
        String description, name;
        int bonus_damage;
        public weapon(String name, int bonus_damage, String description) {
            this.name = name;
            this.bonus_damage = bonus_damage;
            this.description = description;
        }
    }

    public static class clothes{
        String description, name;
        int armor_class, mind_armor_class, place_of_protection; // place_of_protection 1 - leg, 2 - body, 3 - head
        public clothes( String name, String description, int armor_class, int mind_armor_class, int place_of_protection) {
            this.place_of_protection = place_of_protection;
            this.name = name;
            this.armor_class = armor_class;
            this.mind_armor_class = mind_armor_class;
            this.description = description;
        }
    }
}