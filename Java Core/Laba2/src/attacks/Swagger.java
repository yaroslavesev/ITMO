package attacks;
import ru.ifmo.se.pokemon.*;
public class Swagger extends StatusMove{
    public Swagger(){
        super(Type.NORMAL, 0, 85);
    }
    @Override
    public void applyOppEffects(Pokemon p){
        p.setMod(Stat.ATTACK, 2);
        Effect.confuse(p);
    }
    @Override
    public String describe() {
        return "Сбивает противника с толку и повышает его атаку на 2";
    }
}

