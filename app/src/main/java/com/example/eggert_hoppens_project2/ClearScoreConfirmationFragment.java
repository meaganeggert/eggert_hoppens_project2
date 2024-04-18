package com.example.eggert_hoppens_project2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.eggert_hoppens_project2.DB.AppRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClearScoreConfirmationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClearScoreConfirmationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;

    AppRepository repository;
    Button yesButton;
    Button noButton;

    public ClearScoreConfirmationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClearScoreConfirmationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClearScoreConfirmationFragment newInstance(String param1, String param2) {
        ClearScoreConfirmationFragment fragment = new ClearScoreConfirmationFragment();
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
    public void onDetach() {

        super.onDetach();

        //Toast.makeText(getActivity(), "fragment detached", Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        repository = AppRepository.getRepository(getActivity().getApplication());
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_clear_score_confirmation, container, false);
        // Bindings
        yesButton = (Button) view.findViewById(R.id.yes_Button);
        noButton = (Button) view.findViewById(R.id.no_Button);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.clearScoreboard();
                Toast.makeText(getActivity(), "Scoreboard has been cleared.", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().beginTransaction().replace(getActivity().findViewById(R.id.frag_ContainerView).getId(), new BlankFragment()).commit();

            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Scoreboard is unchanged.", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().beginTransaction().replace(getActivity().findViewById(R.id.frag_ContainerView).getId(), new BlankFragment()).commit();
            }
        });
        return view;
    }
}