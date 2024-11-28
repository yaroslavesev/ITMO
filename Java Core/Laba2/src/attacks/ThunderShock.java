package attacks;

import ru.ifmo.se.pokemon.*;

public class ThunderShock extends SpecialMove {
    public ThunderShock() {
        super(Type.ELECTRIC, 40, 100);
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
