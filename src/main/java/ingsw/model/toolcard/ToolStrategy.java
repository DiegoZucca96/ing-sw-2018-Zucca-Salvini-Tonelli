package ingsw.model.toolcard;


import ingsw.model.ObjectiveTool;

public interface ToolStrategy {
    void doOp(ObjectiveTool object);
    boolean isAlreadyUsed();
    int getIdCard();
    void setAlreadyUsed(boolean alreadyUsed);
}
