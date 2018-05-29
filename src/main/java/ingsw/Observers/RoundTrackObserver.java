package ingsw.Observers;

import ingsw.model.RoundTrack;
import ingsw.model.ViewData;

public class RoundTrackObserver implements Observer {

    private RoundTrack roundTrack;

    @Override
    public void update(Object subject, ViewData viewData) {
        roundTrack = (RoundTrack) subject;
            viewData.setRoundTrack(roundTrack.toString());
    }
}
