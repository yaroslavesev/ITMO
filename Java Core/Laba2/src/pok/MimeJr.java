package pok;

import attacks.DreamEater;
import attacks.Psybeam;
import attacks.Thunderbolt;
import ru.ifmo.se.pokemon.*;

public class MimeJr extends Pokemon{
    public MimeJr(String name, int level) {
        super(name, level);
        setStats(20, 25, 45, 70, 90, 60);
        setType(Type.PSYCHIC, Type.FAIRY);
        setMove(new DreamEater(), new Psybeam(), new Thunderbolt());
    }
}