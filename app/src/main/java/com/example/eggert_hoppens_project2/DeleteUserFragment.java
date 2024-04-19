package com.example.eggert_hoppens_project2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteUserFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    AppRepository repository;

    EditText userId_ET;

    Button viewUser_B;
    Button confirmDelete_B;
    Button cancelDelete_B;

    TextView viewUsername_TV;
    TextView viewHighScore_TV;

    LinearLayout userView_L;
    ScrollView scroller;

    private final String BLANK = "BLANK";
    private int userIdToDelete = -1;
    private String mUsername = BLANK;
    private int mHighScore = 0;

    public DeleteUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeleteQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteUserFragment newInstance(String param1, String param2) {
        DeleteUserFragment fragment = new DeleteUserFragment();
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
        repository = AppRepository.getRepository(getActivity().getApplication());
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_delete_user, container, false);

        // Bindings
        userId_ET = view.findViewById(R.id.userId_editText);
        viewUser_B = view.findViewById(R.id.viewUser_Button );
        confirmDelete_B = view.findViewById(R.id.usersConfirmDelete_Button);
        cancelDelete_B = view.findViewById(R.id.usersCancelDelete_Button);
        viewUsername_TV = view.findViewById(R.id.userDisplaySectionUsername_textView);
        viewHighScore_TV = view.findViewById(R.id.userDisplaySectionHS_textView);

        userView_L = view.findViewById(R.id.viewUser_Layout);
        scroller = view.findViewById(R.id.deleteUser_scroller);

        // View User to Delete
        viewUser_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userIdToDelete = Integer.parseInt(userId_ET.getText().toString());
//                Toast.makeText(getActivity(), userId_ET.getText().toString(), Toast.LENGTH_SHORT).show();
                if (repository.doesContainUserId(userIdToDelete)) {
                    userView_L.setVisibility(View.VISIBLE);
                    retrieveUserInfo();
                }
                else {
                    Toast.makeText(getActivity(), "No such user.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Confirm Delete
        confirmDelete_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.deleteUserById(userIdToDelete);
                userView_L.setVisibility(View.INVISIBLE);
                scroller.smoothScrollTo(0,0);
                Toast.makeText(getActivity(), "User #" + userIdToDelete + " has been deleted.", Toast.LENGTH_SHORT).show();
            }
        });

        // Cancel Delete Question and Clear Fragment
        cancelDelete_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userView_L.setVisibility(View.INVISIBLE);
                scroller.smoothScrollTo(0,0);
            }
        });

        return view;
    }

    private void retrieveUserInfo() {
        LiveData<UserInfo> userObserver = repository.getUserByUserId(userIdToDelete);
        userObserver.observe(this, thisUser -> {
            if (thisUser != null) {
                mUsername = thisUser.getUserName();
                try {
                    mHighScore = repository.getScoreByUserId(userIdToDelete).getValue().getUserScore();
                }
                catch (NullPointerException e) {
                    mHighScore = 0;
                }

                viewUsername_TV.setText(mUsername);
                viewHighScore_TV.setText(String.valueOf(mHighScore));
            }
        });
    }
}