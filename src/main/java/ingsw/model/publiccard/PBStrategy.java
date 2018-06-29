package ingsw.model.publiccard;

import ingsw.model.Player;

/**
 * This interface is used to exploit the Strategy pattern
 * @see ingsw.model.PBObjectiveCard
 */
public interface PBStrategy {
    void doOp(Player p);
}
