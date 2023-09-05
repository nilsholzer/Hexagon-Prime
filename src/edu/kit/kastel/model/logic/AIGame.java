package edu.kit.kastel.model.logic;

import edu.kit.kastel.model.entity.Person;
import edu.kit.kastel.model.entity.Player;
import edu.kit.kastel.model.entity.Vector2D;

public class AIGame extends Game {
    public AIGame(final String name, final int size, final Person player1, final Player player2, final boolean autoPrint) {
        super(name, size, player1, player2, autoPrint);
    }

    @Override
    public String place(Vector2D coordinates) {
        return null;
    }
}
