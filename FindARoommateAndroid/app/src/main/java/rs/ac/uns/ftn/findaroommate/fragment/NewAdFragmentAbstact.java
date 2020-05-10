package rs.ac.uns.ftn.findaroommate.fragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import rs.ac.uns.ftn.findaroommate.activity.NewAdActivity;
import rs.ac.uns.ftn.findaroommate.dto.AdDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;

public abstract class NewAdFragmentAbstact extends Fragment {

    protected String title;
    protected int dialogNum;
    protected AdDto ad;

    public String getTitle() {
        return title;
    }

    public int getDialogNum() {
        return dialogNum;
    }

    public NewAdFragmentAbstact(AdDto ad){
        this.ad = ad;
    }
}
