package com.khlud.ciprian.flatcollection.templating;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.khlud.ciprian.flatcollection.utils.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Ciprian on 4/27/2016.
 */
public class TemplateDescription {
    private List<String> args = new ArrayList<>();
    private List<String> lines = new ArrayList<>();

    public Map<String, EachTemplateDescription> eachTemplates = new HashMap<>();

    public List<String> getArgs() {
        return args;
    }




    public void fillData(List<String> message) {
        String firstRow = message.get(0);
        List<String> paramsComponent = StringUtils.split(firstRow, ':', false);
        List<String> params = StringUtils.split(paramsComponent.get(1), ',', true);
        args = params.stream().map(String::trim).collect(Collectors.toList());
        message.remove(0);

        lines.addAll(message);
        for (int i = 0; i < message.size(); i++) {
            String row = message.get(i).trim();
            if (!row.startsWith("%each"))
                continue;
            setupEachTemplate(i, row);
        }

    }

    @NotNull
    public static String renderTemplate(String templateContent, Map<String, Object> scopes) {
        MustacheFactory mf = new DefaultMustacheFactory();
        StringReader reader = new StringReader(templateContent);
        Mustache mustache = mf.compile(reader, "scopes");
        StringWriter writer = new StringWriter();
        mustache.execute(writer, scopes);
        writer.flush();
        return writer.getBuffer().toString();
    }

    public String renderObjectList(List<Object> arguments){
        Map<String, Object> templateArgs = buildArgumentsMap(arguments, this.args);
        for (String eachTemplatKey:
                this.eachTemplates.keySet()) {
            EachTemplateDescription eachTemplateDescription = eachTemplates.get(eachTemplatKey);
            String itemsKey = eachTemplateDescription.getItems();
            List<String> itemsNames = (List<String>)templateArgs.get(itemsKey);

            Map<String, Object> eachArgumentsMap = new HashMap<>();
            List<String> eachArgments = eachTemplateDescription.getArguments();
            eachArgments.stream().forEach(
                    eachArgment->{
                        eachArgumentsMap.put(eachArgment, templateArgs.get(eachArgment));
                    }
            );

            String eachInnerText = eachTemplateDescription.buildTemplates(itemsNames, eachArgumentsMap );
            templateArgs.put(eachTemplatKey, eachInnerText);
        }
        return renderTemplateMap(templateArgs);
    }

    @NotNull
    public static Map<String, Object> buildArgumentsMap(List<Object> arguments, List<String> argumentNames) {
        Map<String, Object> templateArgs = new HashMap<>();
        int argsSize = argumentNames.size();
        for (int i = 0; i < argsSize; i++) {
            String key = argumentNames.get(i);
            Object value =arguments.get(i);
            templateArgs.put(key,value);

        }
        return templateArgs;
    }

    public void setupEachTemplate(int i, String row) {
        System.out.println(row);
        Map<String, String> dict = new HashMap<>();
        StringUtils.split(row, ';', false).stream()
                .forEach(s -> {
                    List<String> keyVal = StringUtils.split(s, ':', false);
                    dict.put(keyVal.get(0).trim(), keyVal.get(1).trim());
                });
        String templateEach = dict.get("%each");
        EachTemplateDescription eachTemplateDescription = new EachTemplateDescription();
        eachTemplateDescription.scan(templateEach);

        eachTemplateDescription.setItems(dict.get("items"));
        eachTemplateDescription.setArguments(dict.get("args"));
        String templateName= "each_template_"+eachTemplates.size();
        lines.set(i,"{{{"+ templateName+"}}}");
        eachTemplates.put(templateName, eachTemplateDescription);
    }

    private String buildContent() {
        StringBuilder resultBuilder = new StringBuilder();
        for(String str:lines){
            resultBuilder.append(str).append("\n\r");
        }
        return resultBuilder.toString();
    }

    public String renderTemplateMap(Map<String, Object> perTemplateMap) {
        String content = buildContent();
        return renderTemplate(content, perTemplateMap);
    }
}
