package entities;
import intfaces.Inspecting;

public class Goat extends Heroes implements Inspecting {
    public Goat(String name, Room room, int token){
        super(name, room, token);
    }
    @Override
    public void greeting(String name){
        System.out.println("Мееее, Меня зовут " + name + "!");
    }
    @Override
    public void inspectingPlace(){
        System.out.println(this.getName() + " сказал: " + this.getRoom().getRoom());
    }
}
