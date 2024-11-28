package exceptions;

import entities.Heroes;

public class AmountOfMoneyException extends RuntimeException{
    public AmountOfMoneyException(String message){
        super(message);
    }

    public static class Money {
        private int amOfMoney;
    public Money(int amOfMoney){
        this.amOfMoney = amOfMoney;
    }
        public int getAmOfMoney() {
            return amOfMoney;
        }
        public void messageAboutMoney(Heroes hero){
            System.out.println("У " + hero.getName() + " " + getAmOfMoney() + " баксов");
        }
    }
}
