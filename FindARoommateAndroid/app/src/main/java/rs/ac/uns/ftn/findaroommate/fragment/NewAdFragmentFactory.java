package rs.ac.uns.ftn.findaroommate.fragment;

import rs.ac.uns.ftn.findaroommate.dto.AdDto;

public class NewAdFragmentFactory {

    public static NewAdFragmentAbstact getNewAdFragment(int position, AdDto ad){
        switch (position){
            case 1:
                return NewAdLocationFragment.newInstance(ad);
            case 2:
                return NewAdPropertyFragment.newInstance(ad);
            case 3:
                return NewAdAmenityFragment.newInstance(ad);
            case 4:
                return NewAdRoomFragment.newInstance(ad);
            case 5:
                return NewAdMateAttrFragment.newInstance(ad);
            case 6:
                return NewAdFinalFragment.newInstance(ad);
        }
        return null;
    }
}
