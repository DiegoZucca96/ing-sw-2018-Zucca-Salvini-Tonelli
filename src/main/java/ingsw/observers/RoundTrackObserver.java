package ingsw.observers;

import ingsw.model.RoundTrack;
import ingsw.model.ViewData;

/**
 * Author : Diego Zucca - Elio Salvini
 *
 * This is the RoundTrack observer
 */
public class RoundTrackObserver implements Observer {

    private RoundTrack roundTrack;

    /**
     * This method updates ViewData when a change of the RoundTrack takes place
     * @param subject it is the object that calls the update
     * @param viewData it is the instance that has to be updated
     */
    @Override
    public void update(Object subject, ViewData viewData) {
        roundTrack = (RoundTrack) subject;
        viewData.setRoundTrack(roundTrack.toArrayString());
    }
}
