package com.example.lenovo.newclassmate.Bean;

import java.util.List;

/**
 * @author Eskii
 *         每道题的Bean
 */

public class QuestionBean extends BaseBean {

    private String details;
    private String type;
    private String ID;
    private List<QusetionOptionBean> qusetionOptionBeanList;


    public QuestionBean() {
        super();
    }

    /**
     * @param details：题目的内容
     * @param type：题目的类型（多选或单选）
     * @param ID：题目ID（题号）
     * @param qusetionOptionBeanList：题目的选项队列
     */
    public QuestionBean(String details, String type, String ID, List<QusetionOptionBean> qusetionOptionBeanList) {
        super();
        this.details = details;
        this.type = type;
        this.ID = ID;
        this.qusetionOptionBeanList = qusetionOptionBeanList;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<QusetionOptionBean> getQusetionOptionBeanList() {
        return qusetionOptionBeanList;
    }

    public void setQusetionOptionBeanList(List<QusetionOptionBean> qusetionOptionBeanList) {
        this.qusetionOptionBeanList = qusetionOptionBeanList;
    }

    @Override
    public String toString() {
        return "Question[details=" + getDetails() + ",type=" + getType() + ",ID=" + getID() + ",qusetionOptionBeanList="
                + getQusetionOptionBeanList() + "]";
    }
}
