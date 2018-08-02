package hajjcare.hajj.com.hajjcare.views.fragments;

import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

    /**
     * @return true >> the fragment is added not replace  && false>> the fragment is Replaced
     * getSupportFragmentManager().beginTransaction().[[[[(true>add)//(false>replace)]]]](R.id.fragment_content, fragment, fargmentTag);
     */
    public boolean isFragmentAdded() {
        return false;
    }

    /**
     *
     * @return true if the fragment has editViews , and you need to show [discard/stay] alert onBackPress.
     *         otherwise return false
     */
    public boolean hasEditableFields(){
        return false;
    }
}
