package org.berlinvegan.generators;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.Writer;
import java.util.*;

public class FactsheetGenerator extends WebsiteGenerator {
    public FactsheetGenerator() throws Exception {
        super();
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 6) {  // 3 options with 1 value
            parseOptions(args);
            FactsheetGenerator generator = new FactsheetGenerator();
            generator.generateFactSheets("de");
        } else {
            final HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("generatefactsheets", constructOptions());
        }
    }

    private void generateFactSheets(String language) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n", new Locale(language));
        final List<Restaurant> restaurants = getRestaurantsFromServer();
        if (!StringUtils.isEmpty(outputDir)) {
            generateFactSheets(language, bundle, restaurants);
        }


    }

    private void generateFactSheets(String language, ResourceBundle bundle, List<Restaurant> restaurants) {
        // Configuration
        Writer fileWriter = null;
        Configuration cfg = new Configuration();
        try {
            // Set Directory for templates
            cfg.setClassForTemplateLoading(FactsheetGenerator.class, "");
            // load template
            Template template = cfg.getTemplate("factsheet_v2.ftl", "ISO-8859-1");
            template.setOutputEncoding("UTF-8");
            // data-model
            Map<String, Object> input = new HashMap<String, Object>();
            input.put("i18n", bundle);
            input.put("language", language);
            HashSet<String> restaurantsDone = new HashSet<String>();
            for (Restaurant restaurant : restaurants) {
                String reviewURL = restaurant.getReviewURL();
                if (!StringUtils.isEmpty(reviewURL) && !restaurantsDone.contains(reviewURL)) {
                    List<Restaurant> restaurantBranches = getBranches(reviewURL, restaurants);
                    input.put("branches", restaurantBranches);
                    // File output
                    fileWriter = getUTF8Writer(outputDir + File.separator + reviewURL + ".html");
                    template.process(input, fileWriter);
                    fileWriter.flush();
                    restaurantsDone.add(reviewURL);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (Exception ignored) {
                }
            }
        }

    }


    /**
     * get branches(filialen) for restaurant
     *
     * @param reviewURL
     * @param restaurants
     * @return
     */
    private List<Restaurant> getBranches(String reviewURL, List<Restaurant> restaurants) {
        ArrayList<Restaurant> list = new ArrayList<Restaurant>();
        for (Restaurant restaurant : restaurants) {
            String url = restaurant.getReviewURL();
            if (StringUtils.isNotEmpty(url) && url.equals(reviewURL)) {
                list.add(restaurant);
            }
        }
        return list;
    }


}
