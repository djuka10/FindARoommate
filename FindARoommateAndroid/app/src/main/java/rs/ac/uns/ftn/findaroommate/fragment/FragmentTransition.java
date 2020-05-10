package rs.ac.uns.ftn.findaroommate.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;

public class FragmentTransition {

    public static void to(Fragment newFragment, FragmentActivity activity)
    {
        to(newFragment, activity, true);
    }

    public static void to(Fragment newFragment, FragmentActivity activity, boolean addToBackstack)
    {
        FragmentTransaction transaction = activity.getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.mainContent, newFragment);
        if(addToBackstack)
            transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void to(Fragment newFragment, FragmentActivity activity, boolean addToBackstack, String tag)
    {
        FragmentTransaction transaction = activity.getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.mainContent, newFragment, tag);
        if(addToBackstack)
            transaction.addToBackStack(tag);
        transaction.commit();
    }

    public static void remove(Fragment fragment, FragmentActivity activity) // TODO izbaciti fragment parametar
    {
        fragment.getArguments().putString("key", "value");
        activity.getSupportFragmentManager().popBackStack();
    }

    public static Fragment findActiveFragment(FragmentActivity activity){
        return activity.getSupportFragmentManager().findFragmentById(R.id.mainContent);
    }
}
