package org.berlinvegan.generators;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.HelpFormatter;

import com.google.gdata.util.AuthenticationException;
import com.google.gson.Gson;

public class JsonGenerator extends WebsiteGenerator {
    
    public JsonGenerator() throws AuthenticationException {
        super();
    }
    
    public static void main(String[] args) throws Exception {
        if (args.length == 6) {  // 3 options with 1 value
            parseOptions(args);
            JsonGenerator generator = new JsonGenerator();
            generator.generate();
        } else {
            final HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("generatefactsheets", constructOptions());
        }
    }

    private void generate() throws Exception {
        final List<Restaurant> restaurants = getRestaurantsFromServer();
        augmentWithReviews(restaurants);
        String json = new Gson().toJson(restaurants);
        writeTextToFile(json, outputDir + File.separator + "Locations.json");
    }
    
    private void augmentWithReviews(List<Restaurant> restaurants) throws IOException {
        for (Restaurant restaurant : restaurants) {
            augmentWithReview(restaurant);
        }
    }
    
    private void augmentWithReview(Restaurant restaurant) throws IOException {
        
        String reviewURL = restaurant.getReviewURL();
        
        if (reviewURL != null && !reviewURL.isEmpty()) {
            String review = getLocationTextFromWebsite(REVIEW_DE_BASE_URL + reviewURL);
            review = textEncode(review);
            review = hyphenate(review, LANG_DE);
            restaurant.setComment(review);
        }
    }
}