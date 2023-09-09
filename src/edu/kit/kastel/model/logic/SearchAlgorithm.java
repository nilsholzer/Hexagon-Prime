package edu.kit.kastel.model.logic;

import edu.kit.kastel.model.entity.Hexagon;
import edu.kit.kastel.model.entity.Vector2D;

import java.util.List;

/**
 * Todo.
 * @author uhquw
 * @version 1.0.0
 */
public interface SearchAlgorithm {
    /**
     * Todo.
     * @param root todo.
     * @param token todo.
     * @return  todo.
     */
    List<Vector2D> search(Vector2D root, Hexagon token);
}
