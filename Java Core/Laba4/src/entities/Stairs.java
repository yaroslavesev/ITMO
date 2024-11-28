package entities;

import entities.Heroes;

import java.util.Random;
public class Stairs {
    private boolean slipperiness;
    private String material;

    public Stairs(boolean slipperiness, String material){
        this.slipperiness = slipperiness;
        this.material = material;
    }
    public void messageAbStairs(Heroes hero){
        class AmOfStairs{
            Random random = new Random();
            private int amOfStairs = random.nextInt(88) + 1;
            public void mesAbAmOfStairs(){
                System.out.println(hero.getName() + " спустилcя вниз на " + amOfStairs);
            }
        }
        AmOfStairs amount = new AmOfStairs();
        amount.mesAbAmOfStairs();
        if (slipperiness == true){
            System.out.println("На ступеньках было очень скользко, " + hero.getName() + " едва ли не упал, как никак " + material + " лестница");
        } else {
            System.out.println(hero.getName() + " удивился!!! " + "На лестнице даже не было скользко, несмотря на то, что лестница " + material );
        }
    }
}
