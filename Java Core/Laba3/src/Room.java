public enum Room {
    START_ROOM("В самом центре комнаты на столе стоит волшебная шляпа"),
    PRISON("Мы опять оказались в старой каталажке..."),
    TIGHT_PRISON("Ого!!! Похоже на старую каталажку, только здесь более грязно и тесно("),
    MOON_CAVE("Не может быть! Мы оказались в лунной пещере!"),
    EARTH_ROOM("Мы вернулись домой, в родной Цветочный город"),
    EXCHANGE_ROOM("Кажется, мы попали в Давилон, в самый центр биржи");

    private String room;
    Room (String room){
        this.room = room;
    }
    public String getRoom(){
        return room;
    }
}

