public class Dunno extends Heroes implements Inspecting{
    Dunno(String name, Room room, int token){
        super(name, room, token);
    }
    @Override
    public void greeting(String name){
        System.out.println("Я ничего не знаю( Меня зовут " + name + "!");
    }
    @Override
    public void inspectingPlace(){
        System.out.println(this.getName() + " постоял и подумал: " + this.getRoom().getRoom());
    }
}
