package ingsw.Observers;

import ingsw.model.ViewData;

public interface Observer {

    public void update(Object subject, ViewData viewData);
}
