package ru.heart.guess.heartguess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PagesCountResponse {
    @JsonProperty("pageCount")
    public int pageCount;
}
