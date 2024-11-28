package entities;
import intfaces.Inspecting;
import entities.Heroes;
import entities.Room;

import java.util.Random;
public class Shorty extends Heroes implements Inspecting {
    public Shorty(String name, Room room, int token){
        super(name, room, token);
    }
    Random random = new Random();
    int typeOfFood = random.nextInt(3);
    @Override
    public void greeting(String name){
        System.out.println("Меня зовут " + name + "!");
    }
    @Override
    public void inspectingPlace() {
        System.out.println(this.getName() + " сказал: " + this.getRoom().getRoom());
    }
    public String foodType(){
        String SFood;
        if (typeOfFood == 0){
             SFood = "печённая в золе картошка";
        } else if (typeOfFood == 1) {
             SFood = "похлёбка";
        } else {
             SFood = "какие-то длинные, бесформенные коржи из теста";
        }
        return SFood;
    }
}
