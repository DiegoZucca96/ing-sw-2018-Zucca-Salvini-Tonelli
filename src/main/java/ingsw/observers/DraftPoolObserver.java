package ingsw.observers;

import ingsw.model.DraftPool;
import ingsw.model.ViewData;

public class DraftPoolObserver implements Observer{

    private DraftPool draftPool;

    @Override
    public void update(Object subject, ViewData viewData) {
        draftPool = (DraftPool) subject;
        for (int i=0; i<draftPool.getDiceListSize(); i++){
            viewData.addDPDie(draftPool.getDie(i).toString());
        }
    }
}
