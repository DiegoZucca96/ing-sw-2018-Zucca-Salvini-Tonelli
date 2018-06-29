package ingsw.observers;

import ingsw.model.ViewData;

/**
 * This interface is used to implements the pattern Observer
 * @see DraftPoolObserver
 * @see RoundTrackObserver
 * @see ToolCardsObserver
 * @see WindowPatternObserver
 */
public interface Observer {

    void update(Object subject, ViewData viewData);
}
