package com.khlud.ciprian.flatcollection.templating;

import com.khlud.ciprian.flatcollection.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Ciprian on 4/27/2016.
 */
public class EachTemplateDescription  {
    TemplateDescription templateDescription = new TemplateDescription();
    private String _items;
    private List<String> arguments;

    public void scan(String templateName){
        templateDescription = TemplateMaster.loadTemplate(templateName);
    }

    public String buildTemplates(List<String> arguments, Map<String, Object> mappedArgs){
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < arguments.size(); i++) {
            String arg = arguments.get(i);
            Map<String, Object> perTemplateMap = cloneMap(mappedArgs);
            perTemplateMap.put("itemName", arg);
            perTemplateMap.put("index", i);
            String renderedTemplate = templateDescription.renderTemplateMap(perTemplateMap);
            stringBuilder.append(renderedTemplate);
        }

        return stringBuilder.toString();
    }

    public static Map<String, Object> cloneMap(Map<String, Object> mappedArgs) {

        Map<String,Object> result = new HashMap<>();
        for(String key:mappedArgs.keySet()){
            result.put(key, mappedArgs.get(key));
        }
        return result;
    }

    public String getItems() {
        return _items;
    }

    public void setItems(String items) {
        _items = items;
    }

    public void setArguments(String arguments) {
        this.arguments = StringUtils.split(arguments, ',', true)
                .stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public List<String> getArguments() {
        return arguments;
    }
}
