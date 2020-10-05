package com.example.barter;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;


public class Signup extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    Button bttn_next;
    TextInputLayout edt_fname;
    TextInputLayout edt_mname;
    TextInputLayout edt_lname;
    TextInputLayout edt_address;
    EditText edt_dob;

    TextView tv_dob;

    public Signup() {

    }

    public static Signup newInstance(String param1, String param2) {
        Signup fragment = new Signup();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_signup, container, false);

       //START INITIALIZE VIEWS
        edt_fname = view.findViewById(R.id.edt_firstname);
        edt_mname = view.findViewById(R.id.edt_middleName);
        edt_lname = view.findViewById(R.id.edt_lastname);
        edt_address = view.findViewById(R.id.edt_address);
        edt_dob = view.findViewById(R.id.edt_dob);
        bttn_next = view.findViewById(R.id.bttn_signup_next);
        //END INITIALIZE VIEWS

        // START initial animation
        YoYo.with(Techniques.BounceInUp)
                .delay(150)
                .duration(1500)
                .playOn(view);
        // END initial animation

        // START Para sa back button sa phone
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {

                   // getFragmentManager()
                          //  .beginTransaction()
                           // .replace(R.id.frame_mainDisplay, new Signin())
                           // .addToBackStack(null)
                           // .commit();

                   getFragmentManager().popBackStack("signIn", 0);


                    return true;
                }
                return false;
            }
        } );
        // END Para sa back button sa phone

        //START date of birth picker
        edt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = String.valueOf(year) +"-"+String.valueOf(month)
                                +"-"+String.valueOf(dayOfMonth);
                        edt_dob.setText(date);
                    }
                }, yy,mm,dd);
                datePickerDialog.show();
            }
        });
        //END date of birth picker


        //START next button
        bttn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateFname() | !validateMname() | !validateLname() | !validateAddress() | !validateDOB()){
                    return;
                }
                SignupSubmitInfo signupSubmitInfo = new SignupSubmitInfo();

                Bundle bundle = new Bundle();
                User newUser = new User(
                        edt_fname.getEditText().getText().toString(),
                        edt_mname.getEditText().getText().toString(),
                        edt_lname.getEditText().getText().toString(),
                        edt_dob.getText().toString(),
                        edt_address.getEditText().getText().toString()
                );
                bundle.putSerializable("newUser", newUser);
                signupSubmitInfo.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_mainDisplay, signupSubmitInfo)
                        .addToBackStack("signUp")
                        .commit();

            }
        });

        //END next button

       return view;

    }
    public boolean validateFname(){
        if(edt_fname.getEditText().getText().toString().isEmpty()){
            edt_fname.setError("Field Must not be Empty");
            return false;
        }else{
            edt_fname.setError(null);
            return  true;
        }
    }
    public boolean validateMname(){
        if(edt_mname.getEditText().getText().toString().isEmpty()){
            edt_mname.setError("Field Must not be Empty");
            return false;
        }else{
            edt_mname.setError(null);
            return  true;
        }
    }
    public boolean validateLname(){
        if(edt_lname.getEditText().getText().toString().isEmpty()){
            edt_lname.setError("Field Must not be Empty");
            return false;
        }else{
            edt_lname.setError(null);
            return  true;
        }
    }
    public boolean validateAddress(){
        if(edt_address.getEditText().getText().toString().isEmpty()){
            edt_address.setError("Field Must not be Empty");
            return false;
        }else{
            edt_address.setError(null);
            return  true;
        }
    }
    public boolean validateDOB(){
        if(edt_dob.getText().toString().isEmpty()){
            edt_dob.setError("Field Must not be Empty");
            return false;
        }else{
            edt_dob.setError(null);
            return  true;
        }
    }

}