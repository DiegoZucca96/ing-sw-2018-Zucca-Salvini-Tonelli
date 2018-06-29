package ingsw.observers;

import ingsw.model.ToolCard;
import ingsw.model.ViewData;

/**
 * This is the ToolCard observer
 */
public class ToolCardsObserver implements Observer {

    private ToolCard toolCard;

    /**
     * This method updates ViewData when a change of the ToolCard takes place
     * @param subject it is the object that calls the update
     * @param viewData it is the instance that has to be updated
     */
    @Override
    public void update(Object subject, ViewData viewData) {
        toolCard = (ToolCard) subject;
        viewData.setToolCard(toolCard.toString());
    }
}
