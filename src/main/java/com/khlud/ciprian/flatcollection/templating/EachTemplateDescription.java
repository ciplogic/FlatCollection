package com.khlud.ciprian.flatcollection.templating;

import java.util.List;
import java.util.Map;

/**
 * Created by Ciprian on 4/27/2016.
 */
public class EachTemplateDescription  {
    TemplateDescription templateDescription = new TemplateDescription();
    private String _items;
    private String arguments;

    public void scan(String templateName){
        templateDescription = TemplateMaster.scanTemplate(templateName);
    }

    public String buildTemplates(String[] arguments, Map<String, Object> mappedArgs){
        StringBuilder stringBuilder = new StringBuilder();
        for(String arg: arguments){

        }

        return stringBuilder.toString();
    }

    public void setItems(String items) {
        _items = items;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public String getArguments() {
        return arguments;
    }
}
