package ru.heartguess.changer.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.heartguess.changer.ChangeableParamType;
import ru.heartguess.changer.example.root.ChangeableParam;

@Data
@AllArgsConstructor
public class NumericChangeableParam implements ChangeableParam {

    private int value;

    private ChangeableParamType changeableParamType;
}
