package com.github.pipiloveslife.converter.impl.base;

import com.github.pipiloveslife.converter.interfaces.FieldCommand;

/**
 * @author by pipiloveslife on 2018/8/23.
 */
public class EmptyExecutor implements FieldCommand {

    public EmptyExecutor(Object object) { }

    @Override
    public void execute(Object object) { }
}
