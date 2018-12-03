package com.example.lenovo.newclassmate.Bean;

/**
 * @author Eskii
 *         题目的选项
 */

public class QusetionOptionBean extends BaseBean {

    private String name;
    private String details;
    private int value;

    public QusetionOptionBean() {
        super();
    }

    /**
     * @param name：选项的名字
     * @param details：选项的内容
     */
    public QusetionOptionBean(String name, String details, int value) {
        super();
        this.name = name;
        this.details = details;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "QuestionOption[name=" + getName() + ",details=" + getDetails() + ",value="+getValue()+"]";
    }
}
