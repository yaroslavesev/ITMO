package attacks;

import ru.ifmo.se.pokemon.*;

public class DreamEater extends SpecialMove {
    int HealthDrain;
    public DreamEater() {
        super(Type.PSYCHIC, 100, 100);
    }

    @Override
    public void applyOppDamage(Pokemon p, double damage) {
        if (p.getCondition().equals(Status.SLEEP)) {
            p.setMod(Stat.HP, (int) Math.round(damage));
            this.HealthDrain = (int) Math.round(0.5 * damage);
        } else {
            p.setMod(Stat.HP, 0);
            this.HealthDrain = 0;
        }
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.HP, -HealthDrain);
    }
    @Override
    protected String describe() {
        return ("Восстанавливает " + HealthDrain + "Здоровья");
    }
}



