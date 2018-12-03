package com.example.lenovo.newclassmate.DAO;

import com.example.lenovo.newclassmate.Bean.QuestionBean;
import com.example.lenovo.newclassmate.Bean.QusetionOptionBean;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Eskii
 *         加载问题数据
 *         用Dom4j解析xml文件
 */

public class LoadQuestionDataDao {

//    public static List<QuestionBean> questionBeanList = new LinkedList<>();
    private QuestionBean questionBean;
    private QusetionOptionBean qusetionOptionBean;
    private List<QusetionOptionBean> qusetionOptionBeanList = new LinkedList<>();
    private InputStream inputStream;

    public LoadQuestionDataDao(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<QuestionBean> setQuestionBeanList() {

        List<QuestionBean> questionBeanList = new LinkedList<>();

        SAXReader saxReader = new SAXReader();

        try {
        Document document = saxReader.read(inputStream);
        Element dorElement = document.getRootElement();
        Iterator iterator = dorElement.elementIterator();
        questionBeanList = new ArrayList<>();

        while (iterator.hasNext()){
            questionBean = new QuestionBean();
            Element questionElement = (Element) iterator.next();
            List<Attribute> list = questionElement.attributes();

            for (Attribute attribute : list){
                if (attribute.getName().equals("id")){
                    questionBean.setID(attribute.getValue());
                }
            }

            Iterator iterator1 = questionElement.elementIterator();
            qusetionOptionBeanList = new ArrayList<>();

            while (iterator1.hasNext()){
                qusetionOptionBean = new QusetionOptionBean();
                Element child = (Element) iterator1.next();
                String nodeName = child.getName();

                switch (nodeName){
                    case "detail":
                        String s = child.getStringValue();
                        questionBean.setDetails(s);
                        break;
                    case "type":
                        String s1 = child.getStringValue();
                        questionBean.setType(s1);
                        break;
                    case "o1":
                        String s2 = child.getStringValue();
                        String v1 = getOptionValue(child.attributes());
                        if (v1 != null) {
                            qusetionOptionBean.setValue(Integer.parseInt(v1));
                        }
                        qusetionOptionBean.setDetails(s2);
                        qusetionOptionBean.setName("A");
                        break;
                    case "o2":
                        String s3 = child.getStringValue();
                        String v2 = getOptionValue(child.attributes());
                        if (v2 != null) {
                            qusetionOptionBean.setValue(Integer.parseInt(v2));
                        }
                        qusetionOptionBean.setDetails(s3);
                        qusetionOptionBean.setName("B");
                        break;
                    case "o3":
                        String s4 = child.getStringValue();
                        String v3 = getOptionValue(child.attributes());
                        if (v3 != null) {
                            qusetionOptionBean.setValue(Integer.parseInt(v3));
                        }
                        qusetionOptionBean.setDetails(s4);
                        qusetionOptionBean.setName("C");
                        break;
                    case "o4":
                        String s5 = child.getStringValue();
                        String v4 = getOptionValue(child.attributes());
                        if (v4 != null) {
                            qusetionOptionBean.setValue(Integer.parseInt(v4));
                        }
                        qusetionOptionBean.setDetails(s5);
                        qusetionOptionBean.setName("D");
                        break;
                    case "o5":
                        String s6 = child.getStringValue();
                        String v5 = getOptionValue(child.attributes());
                        if (v5 != null) {
                            qusetionOptionBean.setValue(Integer.parseInt(v5));
                        }
                        qusetionOptionBean.setDetails(s6);
                        qusetionOptionBean.setName("E");
                        break;
                    case "o6":
                        String s7 = child.getStringValue();
                        String v6 = getOptionValue(child.attributes());
                        if (v6 != null) {
                            qusetionOptionBean.setValue(Integer.parseInt(v6));
                        }
                        qusetionOptionBean.setDetails(s7);
                        qusetionOptionBean.setName("F");
                        break;
                }

                if (qusetionOptionBean.getDetails() != null){
                    qusetionOptionBeanList.add(qusetionOptionBean);
                }
            }

            questionBean.setQusetionOptionBeanList(qusetionOptionBeanList);
            questionBeanList.add(questionBean);
        }

    } catch (DocumentException e) {
        e.printStackTrace();
    }
    return questionBeanList;
    }

    private String getOptionValue(List<Attribute> attributes) {

        String value = null;

        for (Attribute attribute : attributes) {

            if (attribute.getName().equals("value")) {
                value = attribute.getValue();
                break;
            }
        }

        return value;
    }

}
