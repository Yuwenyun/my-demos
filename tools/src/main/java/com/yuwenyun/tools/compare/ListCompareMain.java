package com.yuwenyun.tools.compare;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-12-04 15:29
 */
public class ListCompareMain {

    private static final String txt_1 = "/compare/list_1.txt";
    private static final String txt_2 = "/compare/list_2.txt";
    private static final String txt_name_1 = "platform";
    private static final String txt_name_2 = "routing";

    public static void main(String[] args) throws IOException {

        URL url_1 = ListCompareMain.class.getResource(txt_1);
        URL url_2 = ListCompareMain.class.getResource(txt_2);
        List<String> list_1 = FileUtils.readLines(new File(url_1.getPath()));
        List<String> list_2 = FileUtils.readLines(new File(url_2.getPath()));

        Collections.sort(list_1);
        Collections.sort(list_2);

        List<String> in_1_not_2 = new LinkedList<>();
        List<String> in_2_not_1 = new LinkedList<>();
        for (String element : list_1) {
            if (!list_2.contains(element) && !element.contains("cost") && !element.contains("rate")) {
                in_1_not_2.add(element);
            }
        }
        for (String element : list_2) {
            if (!list_1.contains(element) && !element.contains("cost") && !element.contains("rate")) {
                in_2_not_1.add(element);
            }
        }

        System.out.println("In " + txt_name_1 + "but not in " + txt_name_2 + ": ");
        for (String element : in_1_not_2) {
            System.out.println(element);
        }

        System.out.println("In " + txt_name_2 + "but not in " + txt_name_1 + ": ");
        for (String element : in_2_not_1) {
            System.out.println(element);
        }
    }
}
