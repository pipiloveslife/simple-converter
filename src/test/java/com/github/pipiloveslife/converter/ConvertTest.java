package com.github.pipiloveslife.converter;

import java.util.List;

import com.github.pipiloveslife.converter.enums.EnumManager.ConvertTiming;
import com.github.pipiloveslife.converter.interfaces.ConvertCommand;
import com.github.pipiloveslife.converter.model.Option;
import com.github.pipiloveslife.converter.model.Paper;
import com.github.pipiloveslife.converter.model.Question;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

/**
 * @author by pipiloveslife on 2018/8/24.
 */
public class ConvertTest {

    private Paper paper;
    private List<Paper> paperList;

    @Before
    public void init() {
        paper = new Paper();
        paper.setTitle("test1").setType(0);
        List<Option> optionList1 = Lists.newArrayList(
            new Option("to11", 0, null, false),
            new Option("to12", 1, null, false),
            new Option("to13", 2, null, false)
        );
        List<Option> optionList2 = Lists.newArrayList(
            new Option("to21", 2, null, false),
            new Option("to22", 1, null, false),
            new Option("to23", 0, null, false)
        );
        List<Question> questionList = Lists.newArrayList(
            new Question("q1", 0, null, optionList1),
            new Question("q2", 2, null, optionList2)
        );
        paper.setQuestions(questionList);
        Paper paper2 = new Paper();
        paper2.setTitle("test2").setType(1);
        List<Option> optionList3 = Lists.newArrayList(
            new Option("to31", 0, null, false),
            new Option("to32", 2, null, false),
            new Option("to33", 1, null, false)
        );
        List<Option> optionList4 = Lists.newArrayList(
            new Option("to41", 1, null, false),
            new Option("to42", 2, null, false),
            new Option("to43", 0, null, false)
        );
        List<Question> questionList2 = Lists.newArrayList(
            new Question("q3", 1, null, optionList3),
            new Question("q4", 0, null, optionList4)
        );
        paper2.setQuestions(questionList2);
        paperList = Lists.newArrayList(paper2);
    }

    @Test
    public void testConvertObjectWhenNormal() {
        ConvertCommand command = EngineFactory.convertOne(null, paper);
        command.execute();
        System.out.println(paper);
    }

    @Test
    public void testConvertObjectWhenSometimes() {
        ConvertCommand command = EngineFactory.convertOneAtTime(null, paper, ConvertTiming.SOMETIMES);
        command.execute();
        System.out.println(paper);
    }

    @Test
    public void testConvertObjectWhenOnce() {
        ConvertCommand command = EngineFactory.convertOneAtTime(null, paper, ConvertTiming.ONCE);
        command.execute();
        System.out.println(paper);
    }

    @Test
    public void testConvertListWhenNormal() {
        ConvertCommand command = EngineFactory.convertList(null, paperList);
        command.execute();
        System.out.println(paperList);
    }

    @Test
    public void testConvertListWhenSometimes() {
        ConvertCommand command = EngineFactory.convertListAtTime(null, paperList, ConvertTiming.SOMETIMES);
        command.execute();
        System.out.println(paperList);
    }

    @Test
    public void testConvertListWhenOnce() {
        ConvertCommand command = EngineFactory.convertListAtTime(null, paperList, ConvertTiming.ONCE);
        command.execute();
        System.out.println(paperList);
    }
}
