/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khlud.ciprian.flatcollection.templating;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ciprian
 */
public class TemplateMaster {

    public static TemplateDescription loadTemplate(String templateName) {
        List<String> message = readResourceFile(templateName + ".mustache");

        TemplateDescription templateDescription = new TemplateDescription();
        templateDescription.fillData(message);

        return templateDescription;

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

}
