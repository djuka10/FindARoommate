package rs.ac.uns.ftn.findaroommate.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.NewAdActivity;
import rs.ac.uns.ftn.findaroommate.dto.AdDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;

public class NewAdRoomFragment extends NewAdFragmentAbstact {

    DatePickerDialog datePicker;
    TextInputEditText stayFromEditText;
    TextInputEditText stayToEditText;

    TextInputEditText depositEditText;
    CheckBox depositCheckbox;

    TextInputEditText roomSizeEditText;

    TextInputEditText costEditText;
    CheckBox billsCheckbox;

    Spinner minStaySpinner;
    Spinner maxStaySpinner;

    RadioGroup bedType;

    public NewAdRoomFragment(AdDto ad) {
        super(ad);
    }

    public static NewAdRoomFragment newInstance(AdDto ad) {
        NewAdRoomFragment mpf = new NewAdRoomFragment(ad);

        return mpf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_add_room, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            title = bundle.getString("title", "New ad");
            dialogNum = bundle.getInt("dialogNum", 1);
        }

        TextView titleText = (TextView) view.findViewById(R.id.dialog_title);
        titleText.setText(title + " (Room)");

        minStaySpinner = (Spinner) view.findViewById(R.id.ad_form_min_stay_spinner);
        ArrayAdapter<CharSequence> minStayAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.stay_range, android.R.layout.simple_spinner_item);
        minStayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minStaySpinner.setAdapter(minStayAdapter);

        maxStaySpinner = (Spinner) view.findViewById(R.id.ad_form_max_stay_spinner);
        ArrayAdapter<CharSequence> maxStayAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.stay_range, android.R.layout.simple_spinner_item);
        maxStayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maxStaySpinner.setAdapter(maxStayAdapter);

        stayFromEditText= (TextInputEditText) view.findViewById(R.id.ad_form_date_from);
        stayFromEditText.setInputType(InputType.TYPE_NULL);
        stayFromEditText.setOnFocusChangeListener(new FocusListener("FROM"));

        stayToEditText= (TextInputEditText) view.findViewById(R.id.ad_form_date_to);
        stayToEditText.setInputType(InputType.TYPE_NULL);
        stayToEditText.setOnFocusChangeListener(new FocusListener("TO"));

        depositEditText= (TextInputEditText) view.findViewById(R.id.ad_form_deposit);

        depositCheckbox = (CheckBox) view.findViewById(R.id.check_deposit_required);
        depositCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                depositEditText.setEnabled(!isChecked);
            }
        });

        roomSizeEditText= (TextInputEditText) view.findViewById(R.id.ad_form_room_size);

        costEditText = (TextInputEditText) view.findViewById(R.id.ad_form_cost);
        billsCheckbox = (CheckBox) view.findViewById(R.id.ad_form_bill_included);

        bedType = (RadioGroup) view.findViewById(R.id.ad_form_bed_type);
        // TODO add in model

        // value init
        if(ad.getAd().getRoomM2() != 0){
            roomSizeEditText.setText(String.valueOf(ad.getAd().getRoomM2()));
        }

        if(ad.getAd().isCostsIncluded()){
            billsCheckbox.setChecked(true);
        }

        if(ad.getAd().getPrice() != 0){
            costEditText.setText(String.valueOf(ad.getAd().getPrice()));
        }

        if(ad.getAd().getDeposit() != 0){
            depositCheckbox.setChecked(false);
            depositEditText.setText(String.valueOf(ad.getAd().getDeposit()));
        } else {
            depositCheckbox.setChecked(true);
        }

        if(ad.getAd().getMinDays() != null){
            int ind = minStayAdapter.getPosition(ad.getAd().getMinDays());
            minStaySpinner.setSelection(ind);
        }

        if(ad.getAd().getMaxDays() != null){
            int ind = maxStayAdapter.getPosition(ad.getAd().getMaxDays());
            maxStaySpinner.setSelection(ind);
        }

        if(ad.getAd().getAvailableFrom() != null){
            Calendar cldr = Calendar.getInstance();
            cldr.setTime(ad.getAd().getAvailableFrom());
            stayFromEditText.setText(cldr.get(Calendar.DAY_OF_MONTH) + "/" + (cldr.get(Calendar.MONTH) + 1) + "/" + cldr.get(Calendar.YEAR));
        }

        if(ad.getAd().getAvailableUntil() != null){
            Calendar cldr = Calendar.getInstance();
            cldr.setTime(ad.getAd().getAvailableUntil());
            stayToEditText.setText(cldr.get(Calendar.DAY_OF_MONTH) + "/" + (cldr.get(Calendar.MONTH) + 1) + "/" + cldr.get(Calendar.YEAR));
        }

        return view;
    }

    class FocusListener implements View.OnFocusChangeListener{

        String dateType;

        public FocusListener(String dateType){
            this.dateType = dateType;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(v == stayFromEditText || v == stayToEditText){
                if(hasFocus){
                    final EditText source = (EditText) v;

                    Date dateToView;
                    if(dateType.equals("FROM")){
                        dateToView = ad.getAd().getAvailableFrom();
                    } else {
                        dateToView = ad.getAd().getAvailableUntil();
                    }

                    final Calendar cldr = Calendar.getInstance();

                    if(dateToView != null) {
                        cldr.setTime(dateToView);
                    }

                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);

                    datePicker = new DatePickerDialog(getActivity(), null, year, month, day);
                    datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            source.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                            Calendar c = Calendar.getInstance();
                            c.set(year, month, dayOfMonth, 0, 0);

                            Date d = c.getTime();
                            if(dateType.equals("FROM")){
                                ad.getAd().setAvailableFrom(d);
                            } else {
                                ad.getAd().setAvailableUntil(d);
                            }
                        }
                    });
                    datePicker.show();
                }
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        NewAdActivity act = (NewAdActivity) getActivity();
        act.getToolbar().setTitle("New ad " + dialogNum + " of 6");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("title", "Gladiator");

    }

    @Override
    public void onDestroyView() {
        String roomSizeText = roomSizeEditText.getText().toString();
        String costText = costEditText.getText().toString();
        String depositText = depositEditText.getText().toString();

        if(!roomSizeText.isEmpty()){
            ad.getAd().setRoomM2(Float.parseFloat(roomSizeText));
        }

        if(!costText.isEmpty()){
            ad.getAd().setPrice(Float.parseFloat(costText));
        }

        if(!depositText.isEmpty() && !depositCheckbox.isChecked()){
            ad.getAd().setDeposit(Float.parseFloat(depositText));
        } else {
            ad.getAd().setDeposit(0f);
            depositEditText.setText("");
        }

        ad.getAd().setCostsIncluded(billsCheckbox.isChecked());

        ad.getAd().setMaxDays(maxStaySpinner.getSelectedItem().toString());
        ad.getAd().setMinDays(minStaySpinner.getSelectedItem().toString());

        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
