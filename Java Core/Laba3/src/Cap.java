public class Cap {
    private int firstToken;
    private int secondToken;
    public Cap(int token1, int token2){
        this.firstToken = token1;
        this.secondToken = token2;
    }
    public void setFirstToken(int token){
        this.firstToken = token;
    }
    public void setSecondTokenToken(int token){
        this.secondToken = token;
    }
    public void teleport(Heroes hero1, Heroes hero2){
        int sumOfTokens = firstToken + secondToken;
        if (sumOfTokens <= 100) {
        hero1.setRoom(Room.PRISON);
        hero2.setRoom(Room.PRISON);
        } else if (sumOfTokens > 100 && sumOfTokens <= 200) {
        hero1.setRoom(Room.TIGHT_PRISON);
        hero2.setRoom(Room.TIGHT_PRISON);
        } else if (sumOfTokens > 200 && sumOfTokens <= 300) {
        hero1.setRoom(Room.MOON_CAVE);
        hero2.setRoom(Room.MOON_CAVE);
        } else if (sumOfTokens > 300 && sumOfTokens <= 400) {
        hero1.setRoom(Room.EARTH_ROOM);
        hero2.setRoom(Room.EARTH_ROOM);
        } else {
        hero1.setRoom(Room.EXCHANGE_ROOM);
        hero2.setRoom(Room.EXCHANGE_ROOM);
        }
    }
}
