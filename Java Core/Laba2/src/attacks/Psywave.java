package attacks;
import ru.ifmo.se.pokemon.*;
import java.lang.Math;
public class Psywave extends SpecialMove {
    public Psywave(int level) {
        super(Type.PSYCHIC, Math.random()*level + level/2 ,100);
    }
    @Override
    public void applyOppDamage(Pokemon p, double damage) {
        if (p.hasType(Type.DARK)) applyOppDamage(p, 0);
    }
}
