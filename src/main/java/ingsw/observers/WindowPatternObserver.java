package ingsw.observers;

import ingsw.model.ViewData;
import ingsw.model.windowpattern.WindowPattern;

/**
 * Author : Diego Zucca - Elio Salvini
 *
 * This is the Window observer
 */
public class WindowPatternObserver implements Observer {

    private WindowPattern wp;

    /**
     * This method updates ViewData when a change of the WindowPattern takes place
     * @param subject it is the object that calls the update
     * @param viewData it is the instance that has to be updated
     */
    @Override
    public void update(Object subject, ViewData viewData) {
        wp = (WindowPattern) subject;
        viewData.addWp(wp.toViewWP());
    }
}
