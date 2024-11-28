import entities.*;
import exceptions.AmountOfMoneyException;
import intfaces.GettingTokens;
import intfaces.Inspecting;

import java.util.Scanner;
public class Main implements GettingTokens {
    public static void whoIsGettingToken(Dunno hero1, Goat hero2) {
        if (hero1.getToken() >= hero2.getToken()) {
            System.out.println(hero1.getName() + " взял жетон у " + hero2.getName() + " и положил оба жетона в волшебную шапку, дабы переместиться в волшебную комнату");
            Inspecting inspecting_dunno = new Inspecting() {
                {
                    inspectingPlace();
                }
                @Override
                public void inspectingPlace() {
                    System.out.println(hero1.getName() + " осмотрелся и громко заплакал ");
                }
            };
        } else {
            System.out.println(hero2.getName() + " взял жетон у " + hero1.getName() + " и положил оба жетона в волшебную шапку, дабы переместиться в волшебную комнату");
            hero2.inspectingPlace();
        }
    }
    public static void main(String[] args){
        boolean success = false;
        while (!success) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Введите сумму денег для первого героя:");
                String s1 = scanner.nextLine();
                System.out.println("Введите сумму денег для второго героя:");
                String s2 = scanner.nextLine();
                int m1 = Integer.parseInt(s1);
                int m2 = Integer.parseInt(s2);
                if ((m1 <= 0 || m2 <= 0) || (m1 > 250 || m2 > 250)){
                    throw new AmountOfMoneyException("Жетонов такого номинала не существует! Попробуйте ещё раз!");
                }
                AmountOfMoneyException.Money moneyOfHero1 = new AmountOfMoneyException.Money(m1);
                AmountOfMoneyException.Money moneyOfHero2 = new AmountOfMoneyException.Money(m2);
                Token token1 = new Token(moneyOfHero1.getAmOfMoney());
                Token token2 = new Token(moneyOfHero2.getAmOfMoney());
                Dunno hero1 = new Dunno("entities.Dunno", Room.START_ROOM, token1.getToken());
                hero1.greeting(hero1.getName());
                moneyOfHero1.messageAboutMoney(hero1);
                Goat hero2 = new Goat("entities.Goat", Room.START_ROOM, token2.getToken());
                hero2.greeting(hero2.getName());
                moneyOfHero2.messageAboutMoney(hero2);
                Cap cap = new Cap(hero1.getToken(), hero2.getToken());
                cap.teleport(hero1, hero2);
                Stairs stairs1 = new Stairs(true, "мраморная");
                stairs1.messageAbStairs(hero1);
                Stairs stairs2 = new Stairs(false, "деревянная");
                stairs2.messageAbStairs(hero2);
                whoIsGettingToken(hero1, hero2);
                String material;
                String purity;
                int dist;
                if (hero1.getRoom() == Room.PRISON) {
                    material = "пластмассовые";
                    purity = "достаточно грязно, совсем неуютно(";
                    dist = 2;
                }
                if (hero1.getRoom() == Room.TIGHT_PRISON) {
                    material = "деревянные";
                    purity = "чище чем в старой каталажке, однако всё равно очень грязно";
                    dist = 1;
                } else {
                    material = "металлические";
                    purity = "царит чистота";
                    dist = 2;
                }
                Shelves shelves = new Shelves(material, dist);
                Shelves.Dust dust = shelves.new Dust(purity);
                dust.mesAbDust();
                Shorty shorty = new Shorty("Коротышка", Room.PRISON, 0);
                shorty.foodType();
                System.out.println(shorty.getName() + " приготовил такое блюдо, как " + shorty.foodType());
                hero1.reactionToFood(shorty);
                hero1.helpHero(hero2);
                Dunno.Rat gray_rat = new Dunno.Rat("Лариса", "жирная", "серая");
                gray_rat.ratTalking();
                gray_rat.ratTricking();
                success = true;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Вы ввели не число. Пожалуйста, попробуйте ещё раз.");
            } catch (AmountOfMoneyException e) {
                System.out.println("Жетонов такого номинала не существует! Попробуйте ещё раз!");
            }

        }
    }
}