package com.github.pipiloveslife.converter;

/**
 * @author by pipiloveslife on 2018/8/24.
 */
public interface DataHelper {

    String[] PAPER_TYPE_ARRAY = new String[] {"考试", "问卷"};
    String[] QUESTION_TYPE_ARRAY = new String[] {"单选", "多选", "填空"};
    String[] OPTION_TYPE_ARRAY = new String[] {"A.可以", "B.不错", "C.很好"};
    String[][] ARRAY = new String[][] {PAPER_TYPE_ARRAY, QUESTION_TYPE_ARRAY, OPTION_TYPE_ARRAY};
}
