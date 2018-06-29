package ingsw.observers;

import ingsw.model.DraftPool;
import ingsw.model.ViewData;

/**
 * This is the DraftPool observer
 */

public class DraftPoolObserver implements Observer{

    private DraftPool draftPool;

    /**
     * This method updates ViewData when a change of the DraftPool takes place
     * @param subject it is the object that calls the update
     * @param viewData it is the instance that has to be updated
     */
    @Override
    public void update(Object subject, ViewData viewData) {
        draftPool = (DraftPool) subject;
        for (int i=0; i<draftPool.getDiceListSize(); i++){
            viewData.addDPDie(i,draftPool.getDie(i).toString());
        }
    }
}
