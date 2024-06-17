package ru.nsu.gurin.Task5.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Aircraft {

    @JsonProperty("en")
    private String modelEn;

    @JsonProperty("ru")
    private String modelRu;

    // Геттеры и сеттеры

    public String getModelEn() {
        return modelEn;
    }

    public void setModelEn(String modelEn) {
        this.modelEn = modelEn;
    }

    public String getModelRu() {
        return modelRu;
    }

    public void setModelRu(String modelRu) {
        this.modelRu = modelRu;
    }
}

