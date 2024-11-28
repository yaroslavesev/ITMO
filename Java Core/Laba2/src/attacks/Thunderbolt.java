package attacks;
import ru.ifmo.se.pokemon.*;
public class Thunderbolt extends SpecialMove {
    public Thunderbolt() {
        super(Type.ELECTRIC, 90, 100);
    }
    @Override
    public void applyOppEffects(Pokemon p) {
        if (Math.random() <= 0.1) Effect.paralyze(p);
    }
    @Override
    public String describe() {
        return "Имеет шанс 10% парализовать противника";
    }
}
