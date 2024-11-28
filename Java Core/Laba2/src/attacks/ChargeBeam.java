package attacks;

import ru.ifmo.se.pokemon.*;

public class ChargeBeam extends SpecialMove {
    public ChargeBeam() {
        super(Type.ELECTRIC, 50, 90);
    }
    @Override
    public void applySelfEffects(Pokemon p) {
        if (Math.random() <= 0.7) p.setMod(Stat.SPECIAL_ATTACK, +1);
    }
    @Override
    public String describe() {
        return "Имеет 70% вероятность повысить Специальную Атаку на 1";
    }

}
