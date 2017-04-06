package ttt.opiskelijalounas.models;

import java.util.List;

/**
 * Created by Teemu on 16.12.2016.
 *
 */

@SuppressWarnings("unused")
public class SodexoJSONResponse {

    private List<Course> courses;

    public SodexoJSONResponse() {

    }

    public List<Course> getCourses() {
        return courses;
    }
}
