package attacks;
import ru.ifmo.se.pokemon.*;
public class Nuzzle extends PhysicalMove {
    public Nuzzle() {
        super(Type.ELECTRIC, 20, 100);
    }
    @Override
    public void applyOppEffects(Pokemon p) {
        Effect.paralyze(p);
    }
    @Override
    public String describe() {
        return "Парализует противника";
    }
}
