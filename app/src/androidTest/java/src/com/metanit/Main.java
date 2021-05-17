package src.com.metanit;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int ritual_counter = 15;
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        ArrayList<location> locations = new ArrayList<>();
        location living_spaces = new location("Жилые помещения", "a"), kitchen  = new location("Кухня", "б"), hospital = new location("Больница", "в"), engine_room = new location("Машинное отделение", "г"),  warehouse = new location("Склад", "д");
        locations.add(living_spaces);
        locations.add(kitchen);
        locations.add(hospital);
        locations.add(engine_room);
        locations.add(warehouse);
        avatar hero = new avatar(20, 30, 10, 10, 4, null, null, null, null);

        String logo = "Приветствие игрока, ввод в курс дела. После приветствия перед игроком описывается база и концепция вылазок глазами главного героя";
        System.out.println(logo + "\n");
        while (true){
            System.out.println("Вы находитесь на своей базе, выберите куда вы пойдёте сейчас:");
            for (int i = 0; i < locations.size(); i++) {
                System.out.println((i + 1) + ". " + locations.get(i).name);
            }
            System.out.println(locations.size()+1 + ". Выбор брони и оружия перед вылазкой" + "\n" + "0. Сидеть и ждать");
            int location_vote = cin.nextInt() - 1;
            if (location_vote == -1) {
                ritual_counter--;
                hero.avatar_healing_xp(5);
                hero.avatar_healing_mind(2);
            }else if(locations.size()==location_vote){
                hero.const_armor = 10 + hero.head_clothes.armor_class + hero.body_clothes.armor_class + hero.legs_clothes.armor_class;
                hero.const_armor_mind = 10 + hero.head_clothes.mind_armor_class + hero.body_clothes.mind_armor_class + hero.legs_clothes.mind_armor_class;
                hero.avatar_review();
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
                            clothes choose = null;
                            switch (((clothes) hero.available_equipment.get(inventory_vote)).place_of_protection) {
                                case 1 -> {
                                    clothes leg = hero.legs_clothes;
                                    hero.legs_clothes = (clothes) hero.available_equipment.get(inventory_vote);
                                    choose = (clothes) hero.available_equipment.get(inventory_vote);
                                    hero.available_equipment.remove(inventory_vote);
                                    hero.available_equipment.add(leg);
                                }
                                case 2 -> {
                                    clothes body = hero.body_clothes;
                                    hero.body_clothes = (clothes) hero.available_equipment.get(inventory_vote);
                                    choose = (clothes) hero.available_equipment.get(inventory_vote);
                                    hero.available_equipment.remove(inventory_vote);
                                    hero.available_equipment.add(body);
                                }
                                case 3 -> {
                                    clothes head = hero.head_clothes;
                                    hero.head_clothes = (clothes) hero.available_equipment.get(inventory_vote);
                                    choose = (clothes) hero.available_equipment.get(inventory_vote);
                                    hero.available_equipment.remove(inventory_vote);
                                    hero.available_equipment.add(head);
                                }
                            }
                            hero.const_armor = 10 + hero.head_clothes.armor_class + hero.body_clothes.armor_class + hero.legs_clothes.armor_class;
                            hero.const_armor_mind = 10 + hero.head_clothes.mind_armor_class + hero.body_clothes.mind_armor_class + hero.legs_clothes.mind_armor_class;
                            assert choose != null;
                            System.out.println("Вы надели на себя " + choose.name + ". Теперь ваша физическая защита равна " + hero.const_armor + ", а ваша ментальная защита стала равна " + hero.const_armor_mind);
                        } catch (Exception ex) {
                            System.out.println("Неккоректный ввод");
                        }
                    } else {
                        try {
                            weapon chose;
                            weapon Weapon = hero.weapon;
                            hero.weapon = (weapon) hero.available_equipment.get(inventory_vote);
                            chose = (weapon) hero.available_equipment.get(inventory_vote);
                            hero.available_equipment.remove(inventory_vote);
                            hero.available_equipment.add(Weapon);
                            assert chose != null;
                            System.out.println("Вы надели на себя " + chose.name + ". Теперь ваш модификатор урона равен " + hero.weapon.bonus_damage);
                        } catch (Exception ex) {
                            System.out.println("Неккоректный ввод");
                        }
                    }
                }
            }
            else if(location_vote<locations.size()){
                if(locations.get(location_vote).exploration(hero)){
                    break;
                }
            }
        }
    }

    public static class location{
        String name;
        String description;
        ArrayList<Object> loot;
        ArrayList<event> event;
        ArrayList<enemy> enemy_in_location;
        Scanner cin = new Scanner(System.in);
        public location(String name, String description) {
            this.name = name;
            this.description = description;
            this.loot = new ArrayList<>();
            this.event = new ArrayList<>();
            this.enemy_in_location = new ArrayList<>();
        }
        boolean exploration(avatar hero){
            int chance_exploration = new Random().nextInt(11);
            if(chance_exploration<3){
                giving_out_loot(hero);
            } else if(3<chance_exploration && chance_exploration<6){
                event this_event = event.get(new Random().nextInt(event.size()));
                this_event.issuing_an_event(hero);
            } else{
                enemy this_enemy = enemy_in_location.get(new Random().nextInt(enemy_in_location.size()));
                int result_duel = fight(this_enemy, hero);//1 - поражение 2 - победа 3 - побег
                if(result_duel==1){
                    return true;
                }else if(result_duel==2){
                    System.out.println("Вы нашли на его теле эти вещи:");
                    giving_out_loot(hero);
                }else System.out.println("Вы сбежали от противника");
            }
            return false;
        }

        void giving_out_loot(avatar hero){
            System.out.println("Вы порылись в ящиках и нашли некоторые вещи:");
            for (int i = 0; i < Math.random() * 2; i++) {
                Object Added_item = loot.get(new Random().nextInt(loot.size()));
                if (Added_item instanceof item) {
                    hero.inventory.add((item) Added_item);
                    System.out.println(((item) Added_item).name);
                } else {
                    hero.available_equipment.add(Added_item);
                    if (Added_item instanceof weapon) {
                        System.out.println(((weapon) Added_item).name);
                    } else
                        System.out.println(((clothes) Added_item).name);
                }
            }
        }

        public class event{
            String description;
            String true_answer;
            ArrayList<String> false_answer;
            public event(String true_answer, ArrayList<String> false_answer, String description) {
                this.true_answer = true_answer;
                this.false_answer = false_answer;
                this.description = description;
            }
            void issuing_an_event(avatar hero){
                Scanner cin = new Scanner(System.in);
                int position_of_the_true_answer = new Random().nextInt(false_answer.size());
                System.out.println(description);
                false_answer.add(position_of_the_true_answer, true_answer);
                for(int i = 0; i < false_answer.size()+1; i++) {
                    System.out.println((i + 1) + ". " + false_answer.get(i));
                }
                if(position_of_the_true_answer==cin.nextInt()){
                    false_answer.remove(position_of_the_true_answer);
                    System.out.println("Вы правильно решили задачу");
                    giving_out_loot(hero);
                }else System.out.println("Вы неправильно решили задачу и довольно сильно нашумели. Теперь вам нужно быстро убегать");
            }
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
                        System.out.println("Выберите что будете защищать:" + "\n" + "1. Повысить защиту тела" + "\n" + "2. Повысить защиту разума");

                        int defence_vote = cin.nextInt();
                        switch (defence_vote) {
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
                    }
                    case 4 -> hero.avatar_review();
                    case 5 -> {
                        System.out.println("Вы сбежали с поля боя!");
                        result_defeat = 3;
                    }
                    default -> System.out.println("Ошибка");
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

    public static class enemy {
        String name;
        int bodyDamagePercent, mindDamagePercent, defencePercent;
        int xp, armor, damage, damage_mind, MAX_xp, const_armor, attack_modifier;

        public enemy(String name, int xp, int armor, int damage, int attack_modifier, int damage_mind, int bodyDamagePercent, int mindDamagePercent, int defencePercent) {
            this.name = name;
            this.xp = xp;
            this.armor = armor;
            this.damage = damage;
            this.attack_modifier = attack_modifier;
            this.damage_mind = damage_mind;
            this.MAX_xp = xp;
            this.bodyDamagePercent = bodyDamagePercent;
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

        void stop_effect_defense() {
            if (const_armor != armor) {
                armor = const_armor;
            }
        }

        int enemy_hit() {
            return new Random().nextInt(19) + 1 + attack_modifier;
        }
    }

    public static class avatar {
        int xp, mind, armor, mind_armor, damage, MAX_xp, MAX_mind, const_armor, const_armor_mind;
        ArrayList<item> inventory;
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

        void avatar_review(){
            System.out.println("Физическое здоровье: " + xp + ", Ментальное здоровье: " + mind + "\n" + "Защита тела равна: " + const_armor + ", Защита разума равна: " + const_armor_mind + "\n" + "Экипированное снаряжение: " + "\n" + "Экипированное оружие:");
            weapon.weapon_review();
            System.out.println("Экипированная броня:");
            head_clothes.clothes_review();
            body_clothes.clothes_review();
            legs_clothes.clothes_review();
        }

        void avatar_healing_xp(int heal) {
            int difference = MAX_xp - xp;
            if (difference > heal) {
                xp = xp + heal;
                System.out.println("Вы восстановили " + heal + " едениц здоровья " + xp + "/" + MAX_xp);
            } else {
                xp = MAX_xp;
                System.out.println("Вы полностью восстановили здоровье " + xp + "/" + MAX_xp);
            }
        }

        void avatar_healing_mind(int mind_heal) {
            int difference = MAX_mind - mind;
            if (difference > mind_heal) {
                mind = mind + mind_heal;
                System.out.println("Вы восстановили " + mind_heal + " едениц ментального здоровья " + mind + "/" + MAX_mind);
            } else {
                mind = MAX_mind;
                System.out.println("Вы полностью восстановили ментальное здоровье " + mind + "/" + MAX_mind);
            }
        }
    }

    static class item{
        String name;
        int amount_of_treatment, what_heals; //what_heals 1-xp, 2-mind 3 - damageEnemy
        public item(String name, int amount_of_treatment, int what_heals) {
            this.name = name;
            this.amount_of_treatment = amount_of_treatment;
            this.what_heals = what_heals;
        }
    }

    public static class weapon {
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

    public static class clothes {
        String description, name;
        int armor_class, mind_armor_class, place_of_protection; // place_of_protection 1 - leg, 2 - body, 3 - head

        public clothes(String name, String description, int armor_class, int mind_armor_class, int place_of_protection) {
            this.place_of_protection = place_of_protection;
            this.name = name;
            this.armor_class = armor_class;
            this.mind_armor_class = mind_armor_class;
            this.description = description;
        }
        void clothes_review(){
            System.out.println("Название: " + name + "\n" + "Физическая защита +" + armor_class + " Ментальная защита +" + mind_armor_class + "\n" + "Описание " + description);
        }
    }
}
//цикл вылазки состоит из 1. выбора места вылазки, 2. определения опасности(или их отсутствие(мини-квест или головоломка)) 3.выбор контестного действия для решения проблемы. 4. получения награды за свои действия
// Основные цели: Добыть оружие, броню, информацию о месте проведения и о том, как правильно прервать ритуал. Так же нужно как то запилить взрыв баллонов воздуха