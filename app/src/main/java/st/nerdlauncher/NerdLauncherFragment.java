package st.nerdlauncher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by tengsun on 2/26/16.
 */
public class NerdLauncherFragment extends ListFragment {

    private static final String TAG = "NerdLauncherFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(startupIntent, 0);

        Log.i(TAG, "I have found " + activities.size() + " activities.");

        // set activities by label
        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo lhs, ResolveInfo rhs) {
                PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER
                        .compare(lhs.loadLabel(pm).toString(), rhs.loadLabel(pm).toString());
            }
        });

        // setup array adapter
        ArrayAdapter<ResolveInfo> adapter = new ArrayAdapter<ResolveInfo>(
                getActivity(), android.R.layout.simple_list_item_1, activities) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                PackageManager pm = getActivity().getPackageManager();
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view;
                ResolveInfo info = getItem(position);
                text.setText(info.loadLabel(pm));
                return view;
            }
        };

        setListAdapter(adapter);
    }
}
