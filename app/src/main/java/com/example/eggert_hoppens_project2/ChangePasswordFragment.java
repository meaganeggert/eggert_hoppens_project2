package com.example.eggert_hoppens_project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {

    private static final String ID_PARAM = "ID parameter";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // TODO: Rename parameter arguments, choose names that match

    // TODO: Rename and change types of parameters
    private int mUserId;

    View view;
    AppRepository repository;
    ChangeUserNameFragment binding;
    Button submitPassword;
    EditText oldPassword_ET;
    EditText newPassword_ET;
    EditText newRepeatPassword_ET;
    String mUsername = "";
    String mOldPassword = "";
    String mNewPassword = "";
    String mNewRepeatPassword = "";
    boolean goodPassword = false;

    TextView toolbar_Display;

    public ChangePasswordFragment() {
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
    public static ChangePasswordFragment newInstance(int userId) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        view = inflater.inflate(R.layout.fragment_change_password, container, false);

        // Bindings
        submitPassword = (Button) view.findViewById(R.id.changePasswordSubmit_Button);
        oldPassword_ET = view.findViewById(R.id.currentPassword_editText);
        newPassword_ET = view.findViewById(R.id.newPassword_editText);
        newRepeatPassword_ET = view.findViewById(R.id.repeatNewPassword_editText);
        toolbar_Display = getActivity().findViewById(R.id.toolbarUsername);

        mUsername = toolbar_Display.getText().toString();
//        Toast.makeText(getActivity(), mUsername, Toast.LENGTH_SHORT).show();

        submitPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                validatePassword();
            }
        });

        return view;
    }

    /**
     * Grabs string where the user can type their new username.
     */
    private void getInformationFromDisplay() {
        mOldPassword = oldPassword_ET.getText().toString();
        mNewPassword = newPassword_ET.getText().toString();
        mNewRepeatPassword = newRepeatPassword_ET.getText().toString();
    }

    /**
     * Checks if the new username is valid.
     * Cannot be empty, cannot already exits.
     * If it is neither, update the user's username.
     */
    private void validatePassword() {
        LiveData<UserInfo> userObserver = repository.getUserByUserName(mUsername);
        userObserver.observe(this, userInfo -> {
            if (userInfo != null) {
                if (mOldPassword.isEmpty() || mNewPassword.isEmpty() || mNewRepeatPassword.isEmpty()) {
                    Toast.makeText(getActivity(), "No fields can be blank.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!(mOldPassword.equals(userInfo.getUserPassword()))) {
                        Toast.makeText(getActivity(), "Current password is incorrect.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (!(mNewPassword.equals(mNewRepeatPassword))) {
                            Toast.makeText(getActivity(), "Passwords don't match.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            repository.updatePassword(mNewPassword, mUserId);
                            getParentFragmentManager().beginTransaction().replace(getActivity().findViewById(R.id.profile_FragmentContainer).getId(), new BlankFragment()).commit();
                            Toast.makeText(getActivity(), "Password changed successfully.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
    }
}
