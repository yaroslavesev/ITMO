import java.util.Random;
public class Money {
    Random random = new Random();
    private int amOfMoney = random.nextInt(250) + 1;
    public int getAmOfMoney() {
        return amOfMoney;
    }
    public void messageAboutMoney(Heroes hero){
        System.out.println("У " + hero.getName() + " " + getAmOfMoney() + " баксов");
    }
}
