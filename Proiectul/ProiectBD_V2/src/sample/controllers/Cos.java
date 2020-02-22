package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;

public class Cos {

    private static ObservableList<ModelTableApp> coslist = FXCollections.observableArrayList();

    Cos(){ }
    Cos (ObservableList<ModelTableApp> coslistNew) {
        if (coslistNew != null) {
            coslist.addAll(coslistNew);
        } else {
            coslist = null;
        }
    }

    public ObservableList<ModelTableApp> getCoslist() {
        return coslist;
    }

    public void setCos(ObservableList<ModelTableApp> cos) {
        Cos.coslist = cos;
    }
}
