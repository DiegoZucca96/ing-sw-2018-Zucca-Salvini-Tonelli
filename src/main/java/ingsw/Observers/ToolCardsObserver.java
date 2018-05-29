package ingsw.Observers;

import ingsw.model.ToolCard;
import ingsw.model.ViewData;

public class ToolCardsObserver implements Observer {

    private ToolCard toolCard;

    @Override
    public void update(Object subject, ViewData viewData) {
        toolCard = (ToolCard) subject;
        viewData.addToolCard(toolCard.toString());
    }
}
