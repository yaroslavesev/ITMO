import pok.*;
import ru.ifmo.se.pokemon.*;

public class BattleGround {
    public static void main(String[] args) {
        Battle field = new Battle();
        field.addAlly(new Volbeat("Volbeat", 1));
        field.addAlly(new MimeJr("Mime Jr.", 1));
        field.addAlly(new MrMime("Mr. Mime", 2));
        field.addFoe(new Pichu("Pichu", 1));
        field.addFoe(new Pikachu("Pikachu", 2));
        field.addFoe(new Raichu("Raichu", 3));
        field.go();
    }
}
