package com.example.eggert_hoppens_project2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangeUserNameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangeUserNameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mUserId;
    //private String mParam2;

    View view;
    AppRepository repository;
    ChangeUserNameFragment binding;
    Button submitUserName;
    EditText changeUserNameEditText;
    String mNewUserName = "";
    boolean goodName = false;

    public ChangeUserNameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangeUserNameFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static ChangeUserNameFragment newInstance(String param1, String param2) {
//        ChangeUserNameFragment fragment = new ChangeUserNameFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
    public static ChangeUserNameFragment newInstance(int userId) {
        ChangeUserNameFragment fragment = new ChangeUserNameFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getInt(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        repository = AppRepository.getRepository(getActivity().getApplication());

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_change_user_name, container, false);
        submitUserName = (Button) view.findViewById(R.id.changeUserNameSubmit_Button);
        changeUserNameEditText = view.findViewById(R.id.changeUserName_editText);

        submitUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNewUserFromDisplay();
                checkNewUserName();
            }
        });

        return view;
    }

    private void getNewUserFromDisplay(){
        mNewUserName = changeUserNameEditText.getText().toString();
    }
    private void checkNewUserName(){
        if(mNewUserName.equals("")){
            Toast.makeText(getActivity(), "New username cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(repository.containsUserName(mNewUserName)){
            Toast.makeText(getActivity(), "Username already taken.", Toast.LENGTH_SHORT).show();
        }
        else{
            repository.updateUserName(mNewUserName, mUserId);
            Toast.makeText(getActivity(), "Username successfully changed.", Toast.LENGTH_SHORT).show();
        }
    }
}