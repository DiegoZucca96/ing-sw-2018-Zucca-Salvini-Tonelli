package ingsw.model.publiccard;

import ingsw.model.Player;

/**
 * Author : Diego Zucca
 *
 * This interface is used to exploit the Strategy pattern
 * @see ingsw.model.PBObjectiveCard
 */
public interface PBStrategy {
    void doOp(Player p);
}
