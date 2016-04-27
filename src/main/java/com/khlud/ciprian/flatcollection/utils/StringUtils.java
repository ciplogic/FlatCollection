/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khlud.ciprian.flatcollection.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ciprian
 */
public class StringUtils {

    public static final String substringBeforeLast(String _this, String delimiter) {
        int index = _this.lastIndexOf(delimiter);
        if (index == -1) {
            return _this;
        }
        return _this.substring(0, index);
    }

    public static List<String> split(String _this, char split, boolean removeEmpty){

        char[] _thisData = _this.toCharArray();
        int start = 0;
        List<String> resultList = new ArrayList<>();

        for(int cursor = 1; cursor<_thisData.length;cursor++){
            char ch = _thisData[cursor];
            if(ch ==split){
                String itemString = new String(Arrays.copyOfRange(_thisData, start, cursor)) ;
                resultList.add(itemString);
                start = cursor+1;
            }
        }
        String itemString = new String(Arrays.copyOfRange(_thisData, start, _thisData.length)) ;
        resultList.add(itemString);

        if(!removeEmpty)
            return resultList;
        return resultList.stream()
                .filter(it->!it.isEmpty())
                .collect(Collectors.toList());

    }

    public static final String substringAfterLast(String _this, String delimiter) {
        int index = _this.lastIndexOf(delimiter);
        if (index == -1) {
            return _this;
        }
        return _this.substring(index + 1);
    }

    public static String removePrefix(String _this, String prefix) {
        if (_this.startsWith(prefix)) {
            return _this.substring(prefix.length());
        }
        return _this;
    }

    public static String removeAfterLastSeparator(String _this, String regexSeparator) {
        String[] tokens = _this.split(regexSeparator);
        String result = tokens[tokens.length - 1];
        return result;
    }

    public static String removeSuffix(String _this, String suffix) {
        if (_this.endsWith(suffix)) {
            return _this.substring(0, _this.length() - suffix.length());
        }
        return _this;
    }

    public static final String join(String separator, Collection<String> items) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String item : items) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(separator);
            }
            sb.append(item);
        }

        return sb.toString();
    }

    public static final String indent(String text) {
        String startString = text.substring(0, 1).toUpperCase();
        String endString = text.substring(1, text.length());
        return startString + endString;
    }

}
