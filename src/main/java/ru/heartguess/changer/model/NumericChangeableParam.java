package ru.heartguess.changer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.heartguess.changer.ChangeableParamType;

@Data
@AllArgsConstructor
public class NumericChangeableParam implements ChangeableParam {

    private int value;

    private ChangeableParamType changeableParamType;
}
