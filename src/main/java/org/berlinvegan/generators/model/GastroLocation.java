package org.berlinvegan.generators.model;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;

public class GastroLocation extends Location {
    public static final String OPEN_TIME_ONE_DAY = "<b>%s</b> %s<br/>";
    public static final String OPEN_TIME_MORE_DAYS = "<b>%s-%s</b> %s<br/>";
    public static final int TYPE_VEGAN = 5;
    private String reviewURL = "";
    private String district = "";
    private String publicTransport = "";
    private String openComment = "";
    private String email = "";
    private int handicappedAccessible;
    private int handicappedAccessibleWc;
    private int dog;
    private int childChair;
    private int catering;
    private int delivery;
    private int organic;
    private int seatsOutdoor;
    private int seatsIndoor;
    private int wlan;
    private int glutenFree;
    private String[] tags;
    private List<String> districts;
    private List<Picture> pictures;

    public GastroLocation(String name, String district, double latCoord, double longCoord, int vegan) {
        this.name = name;
        this.district = district;
        this.latCoord = latCoord;
        this.longCoord = longCoord;
        this.vegan = vegan;
    }

    public GastroLocation(ListEntry entry) {
        super(entry);
        final CustomElementCollection elements = entry.getCustomElements();
        reviewURL = elements.getValue("reviewurl");
        district = elements.getValue("stadtbezirk");
        publicTransport = elements.getValue("bvganfahrt");
        openComment = getTrimmedString(elements, "oeffnungszeitenkommentar");
        email = elements.getValue("e-mail");
        handicappedAccessible = getTriStateBooleanAsInt(elements, "restaurantrollstuhlgeeignet");
        handicappedAccessibleWc = getTriStateBooleanAsInt(elements, "wcrollstuhlgeignet");
        dog = getTriStateBooleanAsInt(elements, "hunde");
        childChair = getTriStateBooleanAsInt(elements, "kindersitz");
        catering = getTriStateBooleanAsInt(elements, "catering");
        delivery = getTriStateBooleanAsInt(elements, "lieferservice");
        organic = getTriStateBooleanAsInt(elements, "bio");
        seatsIndoor = getTriStateBooleanAsInt(elements, "sitzplaetzeinnen");
        seatsOutdoor = getTriStateBooleanAsInt(elements, "sitzplaetzeaussen");
        tags = getTags(elements);
        wlan = getTriStateBooleanAsInt(elements, "wlan");
        glutenFree = getTriStateBooleanAsInt(elements, "glutenfrei");
    }
    
    private static String[] getTags(CustomElementCollection elements) {
        final String value = elements.getValue("tagsbittenurimbisscaferestaurantundeiscafeverwenden");
        if (value == null) {
            return null;
        }
        List<String> tags =
            Arrays.stream(value.split(","))
                .map(String::trim)
                .map(tag -> tag.equals("Café") ? "Cafe" : tag)
                .map(tag -> tag.equals("Eiscafé") ? "Eiscafe" : tag)
                .collect(toList());
        List<String> allowedTags = Arrays.asList("Imbiss", "Cafe", "Restaurant", "Eiscafe");
        for (String tag : tags) {
            if (!allowedTags.contains(tag)) {
                throw new RuntimeException("Found illegal tag '" + tag + "'.");
            }
        }
        return tags.toArray(new String[0]);
    }

    public String getReviewURL() {
        return reviewURL;
    }

    public void setReviewURL(String reviewURL) {
        this.reviewURL = reviewURL;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPublicTransport() {
        return publicTransport;
    }

    public void setPublicTransport(String publicTransport) {
        this.publicTransport = publicTransport;
    }

    public String getOpenComment() {
        return openComment;
    }

    public void setOpenComment(String openComment) {
        this.openComment = openComment;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHandicappedAccessible() {
        return handicappedAccessible;
    }

    public void setHandicappedAccessible(int handicappedAccessible) {
        this.handicappedAccessible = handicappedAccessible;
    }

    public int getHandicappedAccessibleWc() {
        return handicappedAccessibleWc;
    }

    public void setHandicappedAccessibleWc(int handicappedAccessibleWc) {
        this.handicappedAccessibleWc = handicappedAccessibleWc;
    }

    public int getDog() {
        return dog;
    }

    public void setDog(int dog) {
        this.dog = dog;
    }

    public int getChildChair() {
        return childChair;
    }

    public void setChildChair(int childChair) {
        this.childChair = childChair;
    }

    public int getCatering() {
        return catering;
    }

    public void setCatering(int catering) {
        this.catering = catering;
    }

    public int getDelivery() {
        return delivery;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public int getOrganic() {
        return organic;
    }

    public void setOrganic(int organic) {
        this.organic = organic;
    }

    public int getSeatsOutdoor() {
        return seatsOutdoor;
    }

    public void setSeatsOutdoor(int seatsOutdoor) {
        this.seatsOutdoor = seatsOutdoor;
    }

    public int getSeatsIndoor() {
        return seatsIndoor;
    }

    public void setSeatsIndoor(int seatsIndoor) {
        this.seatsIndoor = seatsIndoor;
    }

    public int getWlan() {
        return wlan;
    }

    public void setWlan(int wlan) {
        this.wlan = wlan;
    }

    public int getGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(int glutenFree) {
        this.glutenFree = glutenFree;
    }

    public List<String> getDistricts() {
        return districts;
    }

    public void setDistricts(List<String> districts) {
        this.districts = districts;
    }

    public String getVeganHTML(String language) {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n", new Locale(language));
        if (vegan == TYPE_VEGAN) {
            return bundle.getString("vegan");
        } else if (vegan == 4) {
            return bundle.getString("vegetarian");
        }
        return bundle.getString("omnivore");
    }
    
    public String getOpenTimesHTML(String language) {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n", new Locale(language));
        String[] weekdaysNames = new String[7];
        weekdaysNames[0] = bundle.getString("mon");
        weekdaysNames[1] = bundle.getString("tue");
        weekdaysNames[2] = bundle.getString("wed");
        weekdaysNames[3] = bundle.getString("thu");
        weekdaysNames[4] = bundle.getString("fri");
        weekdaysNames[5] = bundle.getString("sat");
        weekdaysNames[6] = bundle.getString("sun");

        String[] openTimes = new String[7];
        openTimes[0] = otMon;
        openTimes[1] = otTue;
        openTimes[2] = otWed;
        openTimes[3] = otThu;
        openTimes[4] = otFri;
        openTimes[5] = otSat;
        openTimes[6] = otSun;
        StringBuilder result = new StringBuilder();
        
        boolean openTimesAvailable = 
            !openTimes[0].isEmpty() 
            || !openTimes[1].isEmpty() 
            || !openTimes[2].isEmpty() 
            || !openTimes[3].isEmpty() 
            || !openTimes[4].isEmpty() 
            || !openTimes[5].isEmpty()
            || !openTimes[6].isEmpty();
        
        if (openTimesAvailable) {
            int equalStart = -1;
            for (int i = 0; i <= 6; i++) {
                if (i < 6 && openTimes[i].equalsIgnoreCase(openTimes[i + 1])) { 
                    // nachfolger identisch, mach weiter
                    if (equalStart == -1) {
                        equalStart = i;
                    }
                } else {
                    if (equalStart == -1) { 
                        // aktueller Tag ist einmalig
                        if (openTimes[i].isEmpty()) { 
                            // geschlossen
                            String str = String.format(OPEN_TIME_ONE_DAY, weekdaysNames[i], "geschlossen");
                            result.append(str);
                        } else {
                            String str = 
                                String.format(OPEN_TIME_ONE_DAY, weekdaysNames[i], openTimes[i] + " Uhr");
                            result.append(str);
                        }
                    } else { 
                        // es gibt zusammenhaengende Tage
                        String openTimesText = 
                            openTimes[i].isEmpty() ? "geschlossen" : openTimes[i] + " Uhr";
                        String str = 
                            String.format(
                                OPEN_TIME_MORE_DAYS, 
                                weekdaysNames[equalStart], 
                                weekdaysNames[i], 
                                openTimesText);
                        result.append(str);
                        equalStart = -1;
                    }
                }
            }

        }
        return result.toString();
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", reviewURL='" + reviewURL + '\'' +
                ", street='" + street + '\'' +
                ", cityCode=" + cityCode +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", latCoord=" + latCoord +
                ", longCoord=" + longCoord +
                ", publicTransport='" + publicTransport + '\'' +
                ", telephone='" + telephone + '\'' +
                ", openComment='" + openComment + '\'' +
                ", website='" + website + '\'' +
                ", email='" + email + '\'' +
                ", otMon='" + otMon + '\'' +
                ", otTue='" + otTue + '\'' +
                ", otWed='" + otWed + '\'' +
                ", otThu='" + otThu + '\'' +
                ", otFri='" + otFri + '\'' +
                ", otSat='" + otSat + '\'' +
                ", otSun='" + otSun + '\'' +
                ", vegan=" + vegan +
                ", handicappedAccessible=" + handicappedAccessible +
                ", handicappedAccessibleWc=" + handicappedAccessibleWc +
                ", dog=" + dog +
                ", childChair=" + childChair +
                ", catering=" + catering +
                ", delivery=" + delivery +
                ", organic=" + organic +
                ", seatsOutdoor=" + seatsOutdoor +
                ", seatsIndoor=" + seatsIndoor +
                ", comment='" + comment + '\'' +
                ", commentEnglish='" + commentEnglish + '\'' +
                ", wlan=" + wlan +
                ", glutenFree=" + glutenFree +
                ", tags=" + Arrays.toString(tags) +
                ", districts=" + districts +
                ", pictures=" + pictures +
                '}';
    }
}
