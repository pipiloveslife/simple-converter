package com.github.pipiloveslife.converter.impl;

import com.github.pipiloveslife.converter.enums.EnumManager.ConvertTiming;
import com.github.pipiloveslife.converter.impl.base.AbstractEngine;
import com.github.pipiloveslife.converter.interfaces.ConvertCommand;
import com.github.pipiloveslife.converter.local.LocalStorage;
import org.springframework.context.ApplicationContext;

/**
 * @author by pipiloveslife on 2018/8/23.
 */
public class ObjectEngine extends AbstractEngine {
    private Object target;

    private ObjectEngine(Object target) {
        this.target = target;
    }

    @Override
    public void execute() {
        LocalStorage.setListener();
        this.fieldExecutors.forEach(executor -> executor.execute(this.target));
        this.deepEngines.forEach(ConvertCommand::execute);
        LocalStorage.clearListener();
    }

    public static ConvertCommand instance(ApplicationContext context, Object object, ConvertTiming timing) {
        return new ObjectEngine(object).init(context, object.getClass(), object, null, 1, timing);
    }
}
