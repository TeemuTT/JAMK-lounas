package ttt.opiskelijalounas.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ttt.opiskelijalounas.R;
import ttt.opiskelijalounas.models.Course;
import ttt.opiskelijalounas.models.SodexoJSONResponse;
import ttt.opiskelijalounas.retrofitservices.CourseService;

public class MenuFragment extends Fragment {

    private static final String ARG_SODEXOID = "sodexoid";
    private String sodexoID;
    private Retrofit retrofit;
    private MenuItemAdapter menuItemAdapter;
    private ProgressBar progressBar;
    private TextView message;
    private final List<Course> courses = new ArrayList<>();

    public MenuFragment() {

    }

    public static MenuFragment newInstance(String sodexoID) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SODEXOID, sodexoID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sodexoID = getArguments().getString(ARG_SODEXOID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progressSpinner);
        message = (TextView) v.findViewById(R.id.message);

        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        menuItemAdapter = new MenuItemAdapter(courses);
        mRecyclerView.setAdapter(menuItemAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getMenu();

        return v;
    }

    private String currentDateAsString() {
        ZonedDateTime now = ZonedDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        return String.format(Locale.US, "%d/%02d/%02d", year, month, day);
    }

    private void getMenu() {
        if (sodexoID == null) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        message.setVisibility(View.INVISIBLE);

        CourseService courseService = retrofit.create(CourseService.class);
        final Call<SodexoJSONResponse> call = courseService.courses(sodexoID, currentDateAsString());

        call.enqueue(new Callback<SodexoJSONResponse>() {
            @Override
            public void onResponse(Call<SodexoJSONResponse> call, Response<SodexoJSONResponse> response) {
                progressBar.setVisibility(View.INVISIBLE);

                if (response.body().getCourses().size() == 0) {
                    message.setText(getString(R.string.msg_no_menu));
                    message.setVisibility(View.VISIBLE);
                }

                courses.clear();
                courses.addAll(response.body().getCourses());
                menuItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SodexoJSONResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                if (courses.size() == 0) {
                    message.setText(getString(R.string.msg_fetch_menu_error));
                    message.setVisibility(View.VISIBLE);
                }
                Toast.makeText(getContext(), getString(R.string.msg_fetch_menu_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateMenu(String sodexoID) {
        this.sodexoID = sodexoID;
        getMenu();
    }

    public static class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder> {

        final List<Course> courses;

        MenuItemAdapter(List<Course> courses) {
            this.courses = courses;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View vh = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.menu_layout, parent, false);
            return new ViewHolder(vh);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Course course = courses.get(position);
            holder.courseTitle.setText(course.getTitle_fi());
            holder.courseCategory.setText(course.getCategory());
            holder.courseDesc.setText(course.getDesc_fi());
            holder.coursePrice.setText(course.getPrice());
            holder.courseProperties.setText(course.getProperties());
        }

        @Override
        public int getItemCount() {
            return courses.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            final TextView courseTitle;
            final TextView courseDesc;
            final TextView courseCategory;
            final TextView coursePrice;
            final TextView courseProperties;

            ViewHolder(View itemView) {
                super(itemView);
                courseTitle = (TextView) itemView.findViewById(R.id.courseName);
                courseDesc = (TextView) itemView.findViewById(R.id.courseDesc);
                courseCategory = (TextView) itemView.findViewById(R.id.courseCategory);
                coursePrice = (TextView) itemView.findViewById(R.id.coursePrice);
                courseProperties = (TextView) itemView.findViewById(R.id.courseProperties);
            }
        }
    }
}
