package attacks;
import ru.ifmo.se.pokemon.*;
public class Psybeam extends SpecialMove {
    public Psybeam() { super(Type.PSYCHIC, 65, 100);
    }
    @Override
    public void applyOppEffects(Pokemon p) {
        if (Math.random() <= 0.1) Effect.confuse(p);
    }
    @Override
    public String describe() {
        return "Имеет шанс 10% сбить с толку противника";
    }
}
