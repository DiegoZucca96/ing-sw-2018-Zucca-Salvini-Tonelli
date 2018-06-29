package ingsw.model.toolcard;

import ingsw.model.ObjectiveTool;

/**
 * This interface is used to exploit the Strategy pattern.
 * To see the description of "doOp" method go to toolcard package, because it is different for every tool card
 * @see ingsw.model.ToolCard
 */
public interface ToolStrategy {

    boolean doOp(ObjectiveTool object);

    /**
     * Simply getter method
     * @return true if the tool card is already used, otherwise false
     */
    boolean isAlreadyUsed();

    /**
     * Simply getter method
     * @return the number of this tool card
     */
    int getIdCard();

    /**
     * Simply setter method
     * @param alreadyUsed it is a boolean value to assign
     */
    void setAlreadyUsed(boolean alreadyUsed);

    /**
     * Simply setter method
     * @param token it is the number of tokens to assign
     */
    void setNumTokenUsed(int token);

    /**
     * Simply getter method
     * @return the number of tokens used on this tool card
     */
    int getNumTokenUsed();
}
