package com.java.servlets.model;

/**
 * Created by proton2 on 15.01.2017.
 * тестовый класс. Для тестирования возможностей маппера конструировать иентити с тройной вложенностью
 * На выходе корректно выдает entity с тройной вложенностью:
 * класс WorkTask -> класс User -> класс Detail
 */
public class Detail extends Model{

    String notice;

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }


}
