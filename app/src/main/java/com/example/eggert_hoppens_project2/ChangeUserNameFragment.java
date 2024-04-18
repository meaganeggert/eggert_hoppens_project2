package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eggert_hoppens_project2.DB.AppRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangeUserNameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangeUserNameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ID_PARAM = "ID parameter";

    // TODO: Rename and change types of parameters
    private int mUserId;

    View view;
    AppRepository repository;
    ChangeUserNameFragment binding;
    Button submitUserName;
    EditText changeUserNameEditText;
    String mNewUserName = "";
    boolean goodName = false;

    TextView toolbar_displayNewName;

    public ChangeUserNameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId Active user's ID.
     * @return A new instance of fragment ChangeUserNameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangeUserNameFragment newInstance(int userId) {
        ChangeUserNameFragment fragment = new ChangeUserNameFragment();
        Bundle args = new Bundle();
        args.putInt(ID_PARAM, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getInt(ID_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        repository = AppRepository.getRepository(getActivity().getApplication());

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_change_user_name, container, false);

        // Bindings
        submitUserName = (Button) view.findViewById(R.id.changeUserNameSubmit_Button);
        changeUserNameEditText = view.findViewById(R.id.changeUserName_editText);
        toolbar_displayNewName = getActivity().findViewById(R.id.toolbarUsername);

        submitUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNewUserNameFromDisplay();
                checkNewUserName();

            }
        });

        return view;
    }

    /** Grabs string where the user can type their new username. */
    private void getNewUserNameFromDisplay(){
        mNewUserName = changeUserNameEditText.getText().toString();
    }

    /** Checks if the new username is valid.
     * Cannot be empty, cannot already exits.
     * If it is neither, update the user's username.
     * */
    private void checkNewUserName(){
        if(mNewUserName.isEmpty()){
            Toast.makeText(getActivity(), "New username cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(repository.containsUserName(mNewUserName)){
            Toast.makeText(getActivity(), "Username already exists.", Toast.LENGTH_SHORT).show();
        }
        else{
            repository.updateUserName(mNewUserName, mUserId);

            SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(LandingActivity.SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
            sharedPrefEditor.putString(LandingActivity.SHARED_PREFERENCE_USERNAME_KEY, mNewUserName);
            sharedPrefEditor.apply();

            toolbar_displayNewName.setText(mNewUserName);

            Toast.makeText(getActivity(), "Username successfully changed.", Toast.LENGTH_SHORT).show();
        }
    }
}