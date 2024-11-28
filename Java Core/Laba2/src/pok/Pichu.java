package pok;
import attacks.Confide;
import attacks.Swagger;
import ru.ifmo.se.pokemon.*;
public class Pichu extends Pokemon{
    public Pichu(String name, int level) {
        super(name, level);
        setStats(20, 40, 15, 35, 35, 60);
        setType(Type.ELECTRIC);
        setMove(new Confide(), new Swagger());
    }
}
