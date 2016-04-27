/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khlud.ciprian.flatcollection.templating;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ciprian
 */
public class TemplateMaster {

    public void fillTemplate(String arrayListSection, Object[] objects) {
            TemplateDescription template = scanTemplate(arrayListSection);
            System.out.println("Template: " + template);

    }

    public static List<String> readResourceFile(String resourceName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(resourceName);
        List<String> result = new ArrayList<>();
        if (inputStream == null) {
            return result;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream/*, "UTF-8"*/));

        try {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                result.add(line);
            }
        } catch (IOException ignore) {
        }

        return result;
    }

    public static TemplateDescription scanTemplate(String templateName) {

        List<String> message = readResourceFile(templateName + ".mustache");

        TemplateDescription templateDescription = new TemplateDescription();
        templateDescription.fillData(message);

        return templateDescription;
    }/*

        Map<String, Object> scopes = new HashMap<>();
        scopes.put("name", "Mustache");
        scopes.put("feature", new Feature("Perfect!"));

        String templateContent = "{{name}}, {{feature.description}}!";
        String result = TemplateDescription.renderTemplate(templateContent, scopes);
        System.out.println("Result:" + result);

        return templateDescription;
    }
    */

    void parseMustache() throws IOException {
        Map<String, Object> scopes = new HashMap<>();
        scopes.put("name", "Mustache");
        scopes.put("feature", new Feature("Perfect!"));

        String result = TemplateDescription.renderTemplate("{{name}}, {{feature.description}}!", scopes);
        System.out.println("Result:" + result);
    }
}
