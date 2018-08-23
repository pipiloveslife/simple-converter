package com.github.pipiloveslife.converter.impl;

import java.util.List;

import com.github.pipiloveslife.converter.enums.EnumManager.ConvertTiming;
import com.github.pipiloveslife.converter.impl.base.AbstractEngine;
import com.github.pipiloveslife.converter.interfaces.ConvertCommand;
import com.github.pipiloveslife.converter.local.LocalStorage;
import org.springframework.context.ApplicationContext;

/**
 * @author by pipiloveslife on 2018/8/23.
 */
public class ListEngine extends AbstractEngine {
    private List<?> target;

    private ListEngine(List<?> list) {
        this.target = list;
    }

    @Override
    public void execute() {
        LocalStorage.setListener();
        this.fieldExecutors.forEach(executor -> this.target.forEach(executor::execute));
        this.deepEngines.forEach(ConvertCommand::execute);
        LocalStorage.clearListener();
    }

    public static ConvertCommand instance(ApplicationContext context, List<?> list, ConvertTiming timing) {
        return new ListEngine(list).init(context, list.get(0).getClass(), null, list, list.size(), timing);
    }
}
