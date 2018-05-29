package ingsw.Observers;

import ingsw.model.ViewData;
import ingsw.model.windowpattern.WindowPattern;

public class WindowPatternObserver implements Observer {

    private WindowPattern wp;

    @Override
    public void update(Object subject, ViewData viewData) {
        wp = (WindowPattern) subject;
        viewData.addWp(wp.toViewWP());
    }
}
