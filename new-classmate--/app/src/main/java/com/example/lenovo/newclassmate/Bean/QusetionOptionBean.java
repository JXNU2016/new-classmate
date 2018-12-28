package com.example.lenovo.newclassmate.Bean;

/**
 * @author Eskii
 *         题目的选项
 */

public class QusetionOptionBean extends BaseBean {


    private String details;
    private int value;

    public QusetionOptionBean() {
        super();
    }

    /**
     * @param details：选项的内容
     */
    public QusetionOptionBean( String details, int value) {
        super();
        this.details = details;
        this.value = value;
    }


    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "QuestionOption[details=" + getDetails() + ",value="+getValue()+"]";
    }
}
