package ingsw.model.publiccard;

import ingsw.model.windowpattern.WP1;
import ingsw.model.Player;
import ingsw.model.windowpattern.WindowPattern;

public interface PBStrategy {
    void doOp(Player p, WindowPattern window);
}
