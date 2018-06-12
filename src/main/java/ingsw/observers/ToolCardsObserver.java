package ingsw.observers;

import ingsw.model.ToolCard;
import ingsw.model.ViewData;

public class ToolCardsObserver implements Observer {

    private ToolCard toolCard;

    @Override
    public void update(Object subject, ViewData viewData) {
        toolCard = (ToolCard) subject;
        viewData.setToolCard(toolCard.toString());
    }
}
