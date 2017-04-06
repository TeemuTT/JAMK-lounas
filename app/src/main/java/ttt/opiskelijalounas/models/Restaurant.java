package ttt.opiskelijalounas.models;

/**
 * Created by Teemu on 16.12.2016.
 *
 */

public class Restaurant {

    private final String title;
    private final String subtitle;
    private final String servingTimes;
    private final String sodexoID;

    public Restaurant(String title, String subtitle, String servingTimes, String sodexoID) {
        this.title = title;
        this.subtitle = subtitle;
        this.servingTimes = servingTimes;
        this.sodexoID = sodexoID;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getServingTimes() {
        return servingTimes;
    }

    public String getSodexoID() {
        return sodexoID;
    }
}
