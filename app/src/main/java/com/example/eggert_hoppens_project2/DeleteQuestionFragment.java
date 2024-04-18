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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eggert_hoppens_project2.DB.AppRepository;
import com.example.eggert_hoppens_project2.DB.entities.Question;
import com.example.eggert_hoppens_project2.DB.entities.UserInfo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteQuestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    AppRepository repository;

    EditText questionID_editText;

    Button viewQuestion_button;
    Button confirmDelete_button;
    Button cancelDelete_button;

    TextView viewQuestion_TV;
    TextView viewCorrectAns_TV;
    TextView viewWrong1_TV;
    TextView viewWrong2_TV;
    TextView viewWrong3_TV;
    TextView viewCategory_TV;

    LinearLayout questionLayout;
    ScrollView scroller;
    private final String BLANK = "BLANK";
    private int mQuestionId = -1;
    private String mQuestionText = BLANK;
    private String mCorrectAns = BLANK;
    private String mWrongAns1 = BLANK;
    private String mWrongAns2 = BLANK;
    private String mWrongAns3 = BLANK;
    private String mCategory = BLANK;

    Question mQuestion;

    public DeleteQuestionFragment() {
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
    public static DeleteQuestionFragment newInstance(String param1, String param2) {
        DeleteQuestionFragment fragment = new DeleteQuestionFragment();
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
        view = inflater.inflate(R.layout.fragment_delete_question, container, false);

        // Bindings
        questionID_editText = view.findViewById(R.id.questionId_editText);
        viewQuestion_button = view.findViewById(R.id.viewQuestion_button);
        confirmDelete_button = view.findViewById(R.id.confirmDelete_Button);
        cancelDelete_button = view.findViewById(R.id.cancelDelete_Button);
        viewCategory_TV = view.findViewById(R.id.displaySection_categoryTV);
        viewQuestion_TV = view.findViewById(R.id.displaySection_questTV);
        viewCorrectAns_TV = view.findViewById(R.id.displaySection_correctAnsTV);
        viewWrong1_TV = view.findViewById(R.id.displaySection_wrong1TV);
        viewWrong2_TV = view.findViewById(R.id.displaySection_wrong2TV);
        viewWrong3_TV = view.findViewById(R.id.displaySection_wrong3TV);
        questionLayout = view.findViewById(R.id.viewQuestion_Layout);
        scroller = view.findViewById(R.id.deleteQuest_scroller);

        // View Question to Delete
        viewQuestion_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionId = Integer.parseInt(questionID_editText.getText().toString());
                //Toast.makeText(getActivity(), questionID_editText.getText().toString(), Toast.LENGTH_SHORT).show();
                if (repository.doesContainQuestionId(mQuestionId)) {
                     questionLayout.setVisibility(View.VISIBLE);
                    retrieveQuestionInfo();
                }
                else {
                    Toast.makeText(getActivity(), "No such question ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Confirm Delete
        confirmDelete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.deleteQuestionById(mQuestionId);
                questionLayout.setVisibility(View.INVISIBLE);
                scroller.smoothScrollTo(0,0);
                Toast.makeText(getActivity(), "Question #" + mQuestionId + " has been deleted.", Toast.LENGTH_SHORT).show();
            }
        });

        // Cancel Delete Question and Clear Fragment
        cancelDelete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionLayout.setVisibility(View.INVISIBLE);
                scroller.smoothScrollTo(0,0);
            }
        });

        return view;
    }

    private void retrieveQuestionInfo() {
        LiveData<Question> questionObserver = repository.getQuestionById(mQuestionId);
        questionObserver.observe(this, thisQuest -> {
            if (thisQuest != null) {
                mQuestionText = thisQuest.getQuestion();
                mCategory = thisQuest.getCategory();
                mCorrectAns = thisQuest.getCorrectAnswer();
                mWrongAns1 = thisQuest.getIncorrectAnswer1();
                mWrongAns2 = thisQuest.getIncorrectAnswer2();
                mWrongAns3 = thisQuest.getIncorrectAnswer3();

                viewCategory_TV.setText(mCategory);
                viewQuestion_TV.setText(mQuestionText);
                viewCorrectAns_TV.setText(mCorrectAns);
                viewWrong1_TV.setText(mWrongAns1);
                viewWrong2_TV.setText(mWrongAns2);
                viewWrong3_TV.setText(mWrongAns3);
            }
        });
    }
}