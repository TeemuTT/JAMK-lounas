package ttt.opiskelijalounas.models;

/**
 * Created by Teemu on 16.12.2016.
 *
 */

@SuppressWarnings("unused")
public class Course {

    private String title_fi;
    private String title_en;
    private String category;
    private String price;
    private String properties;
    private String desc_fi;
    private String desc_en;

    public Course() {

    }

    public String getTitle_fi() {
        return title_fi;
    }

    public String getTitle_en() {
        return title_en;
    }

    public String getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }

    public String getProperties() {
        return properties;
    }

    public String getDesc_fi() {
        return desc_fi;
    }

    public String getDesc_en() {
        return desc_en;
    }
}
