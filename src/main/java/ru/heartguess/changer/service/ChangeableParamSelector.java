package ru.heartguess.changer.service;

import ru.heartguess.changer.model.ChangeableParam;

import java.util.List;

public interface ChangeableParamSelector {
    ChangeableParam selectParam(List<ChangeableParam> params);
}
