package ru.heartguess.changer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.heartguess.changer.ChangeableParamType;
import ru.heartguess.changer.model.root.ChangeableParam;

@Data
@AllArgsConstructor
public class NumericChangeableParam implements ChangeableParam {

    private int value;

    private ChangeableParamType changeableParamType;
}
