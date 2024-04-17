package com.example.eggert_hoppens_project2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.Question;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddQuestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    AppRepository repository;
    AddQuestionFragment binding;
    Button submitQuestion;
    EditText questionEditText;
    EditText wrong1EditText;
    EditText wrong2EditText;
    EditText wrong3EditText;
    EditText correctAnswerEditText;
    RadioButton easyRadioButton;
    RadioButton mediumRadioButton;
    RadioButton hardRadioButton;


    private String mNewQuestionText;
    private String mNewCorrectAns, mWrongAns1, mWrongAns2, mWrongAns3;

    public AddQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddQuestionFragment newInstance(String param1, String param2) {
        AddQuestionFragment fragment = new AddQuestionFragment();
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
        view = inflater.inflate(R.layout.fragment_add_question, container, false);

        // Bindings
        submitQuestion = (Button) view.findViewById(R.id.addQuestionSubmit_Button);
        questionEditText = view.findViewById(R.id.addQuestion_EditText);
        wrong1EditText = view.findViewById(R.id.wrong1_editText);
        wrong2EditText = view.findViewById(R.id.wrong2_editText);
        wrong3EditText = view.findViewById(R.id.wrong3_editText);
        correctAnswerEditText = view.findViewById(R.id.correctAnswer_editText);
        easyRadioButton = view.findViewById(R.id.easy_Radio);
        mediumRadioButton = view.findViewById(R.id.medium_Radio);
        hardRadioButton = view.findViewById(R.id.hard_Radio);

        submitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewQuestionText = questionEditText.getText().toString();
                mWrongAns1 = wrong1EditText.getText().toString();
                mWrongAns2 = wrong2EditText.getText().toString();
                mWrongAns3 = wrong3EditText.getText().toString();
                mNewCorrectAns = correctAnswerEditText.getText().toString();
                Question newQuestion = new Question("multiple", "easy", "Video Games", mNewQuestionText, mNewCorrectAns, mWrongAns1, mWrongAns2, mWrongAns3);
                repository.insertQuestion(newQuestion);
                Toast.makeText(getActivity(), "Question Added To The Database", Toast.LENGTH_LONG).show();
                getParentFragmentManager().beginTransaction().replace(getActivity().findViewById(R.id.updateQuest_FragmentContainer).getId(), new BlankFragment()).commit();
            }
        });
        return view;
    }


}