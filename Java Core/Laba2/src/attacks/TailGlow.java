package attacks;
import ru.ifmo.se.pokemon.*;
public class TailGlow extends StatusMove {
    public TailGlow() {
        super(Type.BUG, 0, 0);
    }
    @Override
    public void applySelfEffects(Pokemon p) {
        p.setMod(Stat.SPECIAL_ATTACK, 3);
    }
    @Override
    public String describe() {
        return "Повышает Специальную атаку на 3";
    }
}
