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

    public List<TemplateDescription> eachTemplates = new ArrayList<>();

    public List<String> getArgs() {
        return args;
    }


    public Map<String, Object> templateMap = new HashMap<>();


    public void fillData(List<String> message) {
        String firstRow = message.get(0);
        List<String> paramsComponent = StringUtils.split(firstRow, ':', false);
        List<String> params = StringUtils.split(paramsComponent.get(1), ',', true);
        args = params.stream().map(String::trim).collect(Collectors.toList());
        message.remove(0);

        for (int i = 0; i < message.size(); i++) {
            String row = message.get(i);
            if (!row.startsWith("%each"))
                continue;
            setupEachTemplate(i, row);

        }

        lines.addAll(message);
    }

    public void put(String key, Object obj) {
        templateMap.put(key, obj);
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

    }
}
