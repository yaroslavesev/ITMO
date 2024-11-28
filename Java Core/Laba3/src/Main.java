public class Main implements GettingTokens{
    public static void whoIsGettingToken(Dunno hero1, Goat hero2) {
        if (hero1.getToken() >= hero2.getToken()){
            System.out.println(hero1.getName() + " взял жетон у " + hero2.getName() + " и положил оба жетона в волшебную шапку, дабы переместиться в волшебную комнату");
            hero1.inspectingPlace();
        } else {
            System.out.println(hero2.getName() + " взял жетон у " + hero1.getName() + " и положил оба жетона в волшебную шапку, дабы переместиться в волшебную комнату");
            hero2.inspectingPlace();
        }
    }
    public static void main(String[] args) {
//        var o = new Object();
//        Object o1 = o.clone();

        Money moneyOfHero1 = new Money();
        Money moneyOfHero2 = new Money();
        Token token1 = new Token(moneyOfHero1.getAmOfMoney());
        Token token2 = new Token(moneyOfHero2.getAmOfMoney());
        Dunno hero1 = new Dunno("Dunno", Room.START_ROOM, token1.getToken());
        hero1.greeting(hero1.getName());
        moneyOfHero1.messageAboutMoney(hero1);
        Goat hero2 = new Goat("Goat", Room.START_ROOM, token2.getToken());
        hero2.greeting(hero2.getName());
        moneyOfHero2.messageAboutMoney(hero2);
        Cap cap = new Cap(hero1.getToken(), hero2.getToken());
        cap.teleport(hero1, hero2);
        Stairs.messageAboutStairs();
        whoIsGettingToken(hero1, hero2);
        if (hero1.getRoom() == Room.PRISON){
            Shelves shelves = new Shelves("Пластмассовые", 2);
        }
        if (hero1.getRoom() == Room.TIGHT_PRISON){
            Shelves shelves = new Shelves("Деревянные", 1);
        } else {
            Shelves shelves = new Shelves("Металлические", 2);
        }

    }
}














































































//import java.util.Scanner;

//        System.out.println("Введите количество съеденных груш Незнайкой: ");
//                Scanner scanner = new Scanner(System.in);
//                int amountOfPears = scanner.nextInt();
//
//                Dunno pers1 = new Dunno("Dunno", "Пострадавший", "Ел груши", amountOfPears, Status.NOSTATUS);
//                Pilulkin pers2 = new Pilulkin("Pilulkin","Доктор", "Не ел груши");
//
//                String name1 = pers1.getName();
//                String name2 = pers2.getName();
//
//                if (pers1.getAmtOfEatenPears() > 0){
//                switch (pers1.getAmtOfEatenPears()){
//                case 0:
//                pers1.setStatus(Status.HUNGRY);
//                break;
//                case 1,2,3,4:
//                pers1.setStatus(Status.NORMAL);
//                break;
//                case 5,6,7:
//                pers1.setStatus(Status.CLOSEYES);
//                break;
//                case 8,9,10,11:
//                pers1.setStatus(Status.WHITEFACE);
//                break;
//default:
//        pers1.setStatus(Status.DEATH);
//        break;
//        }
//        }
//        else{
//        System.out.println("О нет! Ты кушаешь негативные груши!");
//        }
//
//
//        switch (pers1.getStatus()){
//        case HUNGRY:
//        System.out.println(Status.HUNGRY.getStringStatus());
//        break;
//        case NORMAL:
//        System.out.println(Status.NORMAL.getStringStatus());
//        break;
//        case CLOSEYES:
//        System.out.println(Status.CLOSEYES.getStringStatus());
//        pers2.shakingVictim(name2, name1);
//        System.out.println(Status.WAKEUP.getStringStatus());
//        break;
//        case WHITEFACE:
//        System.out.println(Status.CLOSEYES.getStringStatus());
//        pers2.shakingVictim(name2, name1);
//        System.out.println(Status.WHITEFACE.getStringStatus());
//        pers2.grabVictimHand(name2, name1);
//        pers2.checkingVictimPulse();
//        System.out.println(Status.WEAKPULSE.getStringStatus());
//        pers2.listenVictimHeartbeat(name2, name1);
//        System.out.println(Status.NOHEARTBEAT.getStringStatus());
//        pers2.giveToSniffToVictim(name2, name1);
//        pers1.victimSniffing(name1);
//        System.out.println(Status.WAKEUP.getStringStatus());
//        break;
//        case DEATH:
//        System.out.println(Status.CLOSEYES.getStringStatus());
//        pers2.shakingVictim(name2, name1);
//        System.out.println(Status.WHITEFACE.getStringStatus());
//        pers2.grabVictimHand(name2, name1);
//        pers2.checkingVictimPulse();
//        System.out.println(Status.WEAKPULSE.getStringStatus());
//        pers2.listenVictimHeartbeat(name2, name1);
//        System.out.println(Status.NOHEARTBEAT.getStringStatus());
//        pers2.giveToSniffToVictim(name2, name1);
//        pers1.victimSniffing(name1);
//        System.out.println(Status.DEATH.getStringStatus());
//
//        }