package pok;
import attacks.ChargeBeam;
import attacks.Confide;
import attacks.TailGlow;
import attacks.Thunder;
import ru.ifmo.se.pokemon.*;
public class Volbeat extends Pokemon{
    public Volbeat(String name, int level) {
        super(name, level);
        setStats(65, 73, 75, 47, 85, 85);
        setType(Type.BUG);
        setMove(new Thunder(), new ChargeBeam(), new TailGlow(), new Confide());
    }
}
