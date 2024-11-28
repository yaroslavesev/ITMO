package attacks;
import ru.ifmo.se.pokemon.*;
public class Thunder extends SpecialMove {
    public Thunder() {
        super(Type.ELECTRIC, 110, 70);
    }
    @Override
    public void applyOppEffects(Pokemon p) {
        if (Math.random() <= 0.3) Effect.paralyze(p);
    }
    @Override
    public String describe() {
        return "Имеет шанс 30% парализовать противника";
    }
}
