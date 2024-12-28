package ru.heartguess.changer.presentation;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.heartguess.changer.ChangeableParamType;
import ru.heartguess.changer.presentation.root.ChangedParam;
import ru.heartguess.models.RarityId;

import java.util.List;

@Data
@AllArgsConstructor
public class ChangedRarityParam implements ChangedParam {

    private RarityId originalValue;

    private List<RarityId> options;

    private ChangeableParamType paramType;
}
