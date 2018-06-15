package ingsw.observers;

import ingsw.model.ViewData;

public interface Observer {

    void update(Object subject, ViewData viewData);
}
