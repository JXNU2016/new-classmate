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
 *         加载问题数据
 *         用Dom4j解析xml文件
 */

public class LoadQuestionDataDao {

    private QuestionBean questionBean;
    private QusetionOptionBean qusetionOptionBean;
    private List<QusetionOptionBean> qusetionOptionBeanList = new LinkedList<>();
    private InputStream inputStream;
    //构造函数
    public LoadQuestionDataDao(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<QuestionBean> setQuestionBeanList() {

        List<QuestionBean> questionBeanList = new LinkedList<>();

        SAXReader saxReader = new SAXReader();

        try {
            Document document = saxReader.read(inputStream);//读取输入流
            Element dorElement = document.getRootElement();//获取xml文件的根节点 Question
            Iterator iterator = dorElement.elementIterator();//得到根节点的迭代器
            questionBeanList = new ArrayList<>();

            while (iterator.hasNext()){
                questionBean = new QuestionBean();
                Element questionElement = (Element) iterator.next();//获取下一个根节点 question
                List<Attribute> list = questionElement.attributes();//获取根节点里面的所有属性，如id, 只能用attributes 方法

                for (Attribute attribute : list){
                    if (attribute.getName().equals("id")){   // 遍历获取根节点里所有属性存放的list，当名字有id时，将id的值获取并set给QuestionBean
                        questionBean.setID(attribute.getValue());//atttibute.getValue() 获取id的值
                    }
                }

                Iterator iterator1 = questionElement.elementIterator();// 获取一个question 的迭代器，用while循环遍历每一个question下面的内容
                qusetionOptionBeanList = new ArrayList<>();

                while (iterator1.hasNext()){
                    qusetionOptionBean = new QusetionOptionBean();//  实例化一个问题里面的内容的对象
                    Element child = (Element) iterator1.next();
                    String nodeName = child.getName();

                    switch (nodeName){
                        case "detail":
                            String s = child.getStringValue(); //获取detail 里面的值
                            questionBean.setDetails(s);  // 将获取到的值赋予给问题对象，questionBean
                            break;
                        case "type":
                            String s1 = child.getStringValue();
                            questionBean.setType(s1);
                            break;
                        case "o1":
                            String s2 = child.getStringValue();
                            String v1 = getOptionValue(child.attributes());//child.atttibutes()遍历 o1 里面的内容，如Values，attributes方法获得的就是一个list
                            if (v1 != null) {                              //这里的V1 其实就是在这道题下（这个id下）的每个选项的分值，是getOptionValue方法返回的value
                                qusetionOptionBean.setValue(Integer.parseInt(v1));  //如果分值不为0，就把分值实例化给questionOption对象
                            }
                            qusetionOptionBean.setDetails(s2);
                            break;
                        case "o2":
                            String s3 = child.getStringValue();
                            String v2 = getOptionValue(child.attributes());
                            if (v2 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v2));
                            }
                            qusetionOptionBean.setDetails(s3);
                            break;
                        case "o3":
                            String s4 = child.getStringValue();
                            String v3 = getOptionValue(child.attributes());
                            if (v3 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v3));
                            }
                            qusetionOptionBean.setDetails(s4);
                            break;
                        case "o4":
                            String s5 = child.getStringValue();
                            String v4 = getOptionValue(child.attributes());
                            if (v4 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v4));
                            }
                            qusetionOptionBean.setDetails(s5);
                            break;
                        case "o5":
                            String s6 = child.getStringValue();
                            String v5 = getOptionValue(child.attributes());
                            if (v5 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v5));
                            }
                            qusetionOptionBean.setDetails(s6);
                            break;
                        case "o6":
                            String s7 = child.getStringValue();
                            String v6 = getOptionValue(child.attributes());
                            if (v6 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v6));
                            }
                            qusetionOptionBean.setDetails(s7);
                            break;
                        case "o7":
                            String s8 = child.getStringValue();
                            String v7 = getOptionValue(child.attributes());
                            if (v7 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v7));
                            }
                            qusetionOptionBean.setDetails(s8);
                            break;
                        case "o8":
                            String s9 = child.getStringValue();
                            String v8 = getOptionValue(child.attributes());
                            if (v8 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v8));
                            }
                            qusetionOptionBean.setDetails(s9);
                            break;
                        case "o9":
                            String s10 = child.getStringValue();
                            String v9 = getOptionValue(child.attributes());
                            if (v9 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v9));
                            }
                            qusetionOptionBean.setDetails(s10);
                            break;
                        case "o10":
                            String s11 = child.getStringValue();
                            String v10 = getOptionValue(child.attributes());
                            if (v10 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v10));
                            }
                            qusetionOptionBean.setDetails(s11);
                            break;
                        case "o11":
                            String s12 = child.getStringValue();
                            String v11 = getOptionValue(child.attributes());
                            if (v11 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v11));
                            }
                            qusetionOptionBean.setDetails(s12);
                            break;
                        case "o13":
                            String s13 = child.getStringValue();
                            String v12 = getOptionValue(child.attributes());
                            if (v12 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v12));
                            }
                            qusetionOptionBean.setDetails(s13);
                            break;
                        case "o14":
                            String s14 = child.getStringValue();
                            String v13 = getOptionValue(child.attributes());
                            if (v13 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v13));
                            }
                            qusetionOptionBean.setDetails(s14);
                            break;
                        case "o15":
                            String s15 = child.getStringValue();
                            String v14 = getOptionValue(child.attributes());
                            if (v14 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v14));
                            }
                            qusetionOptionBean.setDetails(s15);
                            break;
                        case "o16":
                            String s16 = child.getStringValue();
                            String v15 = getOptionValue(child.attributes());
                            if (v15 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v15));
                            }
                            qusetionOptionBean.setDetails(s16);
                            break;
                        case "o17":
                            String s17 = child.getStringValue();
                            String v16 = getOptionValue(child.attributes());
                            if (v16 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v16));
                            }
                            qusetionOptionBean.setDetails(s17);
                            break;
                        case "o18":
                            String s18 = child.getStringValue();
                            String v17 = getOptionValue(child.attributes());
                            if (v17 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v17));
                            }
                            qusetionOptionBean.setDetails(s18);
                            break;
                        case "o19":
                            String s19 = child.getStringValue();
                            String v18 = getOptionValue(child.attributes());
                            if (v18 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v18));
                            }
                            qusetionOptionBean.setDetails(s19);
                            break;
                        case "o20":
                            String s20 = child.getStringValue();
                            String v19 = getOptionValue(child.attributes());
                            if (v19 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v19));
                            }
                            qusetionOptionBean.setDetails(s20);
                            break;
                        case "o21":
                            String s21 = child.getStringValue();
                            String v20 = getOptionValue(child.attributes());
                            if (v20 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v20));
                            }
                            qusetionOptionBean.setDetails(s21);
                            break;
                        case "o22":
                            String s22 = child.getStringValue();
                            String v21 = getOptionValue(child.attributes());
                            if (v21 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v21));
                            }
                            qusetionOptionBean.setDetails(s22);
                            break;
                        case "o23":
                            String s23 = child.getStringValue();
                            String v22 = getOptionValue(child.attributes());
                            if (v22 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v22));
                            }
                            qusetionOptionBean.setDetails(s23);
                            break;
                        case "o24":
                            String s24 = child.getStringValue();
                            String v23 = getOptionValue(child.attributes());
                            if (v23 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v23));
                            }
                            qusetionOptionBean.setDetails(s24);
                            break;
                        case "o25":
                            String s25 = child.getStringValue();
                            String v24 = getOptionValue(child.attributes());
                            if (v24 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v24));
                            }
                            qusetionOptionBean.setDetails(s25);
                            break;
                        case "o26":
                            String s26 = child.getStringValue();
                            String v25 = getOptionValue(child.attributes());
                            if (v25 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v25));
                            }
                            qusetionOptionBean.setDetails(s26);
                            break;
                        case "o27":
                            String s27 = child.getStringValue();
                            String v26 = getOptionValue(child.attributes());
                            if (v26 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v26));
                            }
                            qusetionOptionBean.setDetails(s27);
                            break;
                        case "o28":
                            String s28 = child.getStringValue();
                            String v27 = getOptionValue(child.attributes());
                            if (v27 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v27));
                            }
                            qusetionOptionBean.setDetails(s28);
                            break;
                        case "o29":
                            String s29 = child.getStringValue();
                            String v28 = getOptionValue(child.attributes());
                            if (v28 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v28));
                            }
                            qusetionOptionBean.setDetails(s29);
                            break;
                        case "o30":
                            String s30 = child.getStringValue();
                            String v29 = getOptionValue(child.attributes());
                            if (v29 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v29));
                            }
                            qusetionOptionBean.setDetails(s30);
                            break;
                        case "o31":
                            String s31 = child.getStringValue();
                            String v30 = getOptionValue(child.attributes());
                            if (v30 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v30));
                            }
                            qusetionOptionBean.setDetails(s31);
                            break;
                        case "o32":
                            String s32 = child.getStringValue();
                            String v31 = getOptionValue(child.attributes());
                            if (v31 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v31));
                            }
                            qusetionOptionBean.setDetails(s32);
                            break;
                        case "o33":
                            String s33 = child.getStringValue();
                            String v32 = getOptionValue(child.attributes());
                            if (v32 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v32));
                            }
                            qusetionOptionBean.setDetails(s33);
                            break;
                        case "o34":
                            String s34 = child.getStringValue();
                            String v33 = getOptionValue(child.attributes());
                            if (v33 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v33));
                            }
                            qusetionOptionBean.setDetails(s34);
                            break;
                        case "o35":
                            String s35 = child.getStringValue();
                            String v34 = getOptionValue(child.attributes());
                            if (v34 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v34));
                            }
                            qusetionOptionBean.setDetails(s35);
                            break;
                        case "o36":
                            String s36 = child.getStringValue();
                            String v35 = getOptionValue(child.attributes());
                            if (v35 != null) {
                                qusetionOptionBean.setValue(Integer.parseInt(v35));
                            }
                            qusetionOptionBean.setDetails(s36);
                            break;

                    }

                    if (qusetionOptionBean.getDetails() != null){
                        qusetionOptionBeanList.add(qusetionOptionBean);
                        //将解析得到的问题选项内容放在问题选项列表里面，选项列表qusetionOptionBeanList包含的是每个选项的内容details,分值values，名字name
                    }
                }

                questionBean.setQusetionOptionBeanList(qusetionOptionBeanList);
                questionBeanList.add(questionBean);

                //将问题选项列表放到问题列表中，根据questionBean可知,questionBeanList中包含了问题的details、type、Id和问题选项列表questionOptionBeanList里面的内容（选项的details、values、name)
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return questionBeanList;
    }

    private String getOptionValue(List<Attribute> attributes) {

        String value = null;

        for (Attribute attribute : attributes) {

            if (attribute.getName().equals("value")) {  //每个子节点遍历的内容中如果名字时"value”，则将其值赋给value并返回
                value = attribute.getValue();
                break;
            }
        }

        return value;
    }

}
