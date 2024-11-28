package entities;
import intfaces.Inspecting;
public class Dunno extends Heroes {
    public Dunno(String name, Room room, int token){
        super(name, room, token);
    }
    static String master_name = "Незнайка";

    @Override
    public void greeting(String name){
        System.out.println("Я ничего не знаю( Меня зовут " + name + "!");
    }

    @Override
    public void inspectingPlace() {

    }

    public static class Rat {
        private String name_rat;
        private String size;
        private String color;
        public Rat(String name_rat, String size, String color){
            this.name_rat = name_rat;
            this.size = size;
            this.color = color;
        }
        public void ratTalking() {
            System.out.println("Хи-хи-хи!!!");
            System.out.println("Я " + size + " " + color + " крыса про имени " + name_rat);
        }
        public void ratTricking(){
            System.out.println(master_name + " почувствовал как его укусила " + name_rat);
        }

    }

}
