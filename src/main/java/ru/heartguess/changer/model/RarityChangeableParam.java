package ru.heartguess.changer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.heartguess.changer.ChangeableParamType;
import ru.heartguess.models.RarityId;

@Data
@AllArgsConstructor
public class RarityChangeableParam implements ChangeableParam {

    private RarityId rarityId;

    private ChangeableParamType changeableParamType;
}
