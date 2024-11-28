package pok;
import attacks.DreamEater;
import attacks.Psybeam;
import attacks.Psywave;
import attacks.Thunderbolt;
import ru.ifmo.se.pokemon.*;
public class MrMime extends MimeJr {
    public MrMime(String name, int level) {
        super(name, level);
        setStats(40, 45, 65, 100, 120, 90);
        setType(Type.PSYCHIC, Type.FAIRY);
        setMove(new DreamEater(), new Psybeam(), new Thunderbolt(), new Psywave(level));
    }
}
