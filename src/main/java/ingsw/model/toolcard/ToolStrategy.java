package ingsw.model.toolcard;

import ingsw.model.Die;

public interface ToolStrategy {
    void doOp(Die die);
    boolean isAlreadyUsed();
}
