package pok;
import attacks.Confide;
import attacks.Nuzzle;
import attacks.Swagger;
import attacks.ThunderShock;
import ru.ifmo.se.pokemon.*;
public class Raichu extends Pikachu{
    public Raichu(String name, int level) {
        super(name, level);
        setStats(60, 90, 55, 90, 80, 110);
        setType(Type.ELECTRIC);
        setMove(new Confide(), new Swagger(), new Nuzzle(), new ThunderShock());
    }
}
