public abstract class Heroes{
    private String name;
    private Room room;
    private int token;
    public Heroes(String name, Room room, int token){
        this.name = name;
        this.room = room;
        this.token = token;
    }
    public String getName(){
        return this.name;
    }
    public Room getRoom(){
        return this.room;
    }
    public int getToken(){return this.token;}
    public void setRoom(Room room){
        this.room = room;
    }
    public void  setName(String name){this.name = name;}
    public void  setToken(int token){this.token = token;}
    @Override
    public String toString(){
        return  "Hero{" +
                "Name '" + name + '\'' +
                ", Room '" + room + '\'' +
                ", Token '" + token + '\'' +
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
    public abstract void greeting(String name);
}
