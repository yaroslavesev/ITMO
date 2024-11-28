package entities;

public abstract class Heroes {
    private String name;
    private Room room;
    private int token;

    public Heroes(String name, Room room, int token) {
        this.name = name;
        this.room = room;
        this.token = token;
    }

    public String getName() {
        return this.name;
    }

    public Room getRoom() {
        return this.room;
    }

    public int getToken() {
        return this.token;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setToken(int token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "Name '" + name + '\'' +
                ", entities.Room '" + room + '\'' +
                ", entities.Token '" + token + '\'' +
                "}";
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + token;
        result = 31 * result + room.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Heroes shorty = (Heroes) obj;
        return this.token == shorty.token;
    }
    public void helpHero(Heroes hero2){
        System.out.println(hero2.getName() + " тут же пришёл на помощь, ведь " + this.getName() + " нуждается в ней");
    }
    public void reactionToFood(Shorty shorty){
        if (shorty.foodType() == "печённая в золе картошка"){
            System.out.println(this.getName() + " увидел, что на полке находится " + shorty.foodType() + " и захотел съесть это");
        } else if (shorty.foodType() == "похлёбка") {
            System.out.println(this.getName() + " почувствовал нехороший запах, это была " + shorty.foodType());
        } else {
            System.out.println(this.getName() + " почувствовал себя плохо от всего этого жарящегося, варящегося, пекущегося, сохнущего и просто чадящего в помещении стоял такой удушливый запах, что у него перехватило дыхание и помутилось в глазах. Почувствовав головокружение, он зашатался и принялся хвататься руками за стенку.");
        }
    }
    public abstract void greeting(String name);

    public abstract void inspectingPlace();
}