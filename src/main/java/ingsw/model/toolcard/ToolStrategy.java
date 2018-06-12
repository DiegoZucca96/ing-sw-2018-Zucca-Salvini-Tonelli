package ingsw.model.toolcard;


import ingsw.model.ObjectiveTool;

public interface ToolStrategy {
    boolean doOp(ObjectiveTool object);
    boolean isAlreadyUsed();
    int getIdCard();
    void setAlreadyUsed(boolean alreadyUsed);
    void setNumTokenUsed(int token);
    int getNumTokenUsed();
}
