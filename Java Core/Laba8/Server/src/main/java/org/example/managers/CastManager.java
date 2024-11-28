package org.example.managers;

import org.example.models.MovieGenre;

public class CastManager {
    public static MovieGenre castToDragonType(String str){
        for (MovieGenre value: MovieGenre.values()){
            if (value.name().equalsIgnoreCase(str)){
                return value;
            }
        }
        return MovieGenre.MUSICAL;
    }
}
