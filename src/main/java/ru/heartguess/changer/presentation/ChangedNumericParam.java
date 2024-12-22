package ru.heartguess.changer.presentation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heartguess.changer.ChangeableParamType;
import ru.heartguess.changer.presentation.root.ChangedParam;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangedNumericParam implements ChangedParam {

    private int originalValue;

    private List<Integer> options;

    private ChangeableParamType paramType;
}
