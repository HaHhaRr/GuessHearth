package ru.heartguess.changer.service;

import org.springframework.stereotype.Component;
import ru.heartguess.changer.model.ChangeableParam;

import java.util.List;
import java.util.Random;

@Component
public class RandomChangeableParamSelector implements ChangeableParamSelector {

    private Random random = new Random();

    @Override
    public ChangeableParam selectParam(List<ChangeableParam> changeableParams) {
        return changeableParams.get(
                random.nextInt(
                        changeableParams.size()));
    }
}
