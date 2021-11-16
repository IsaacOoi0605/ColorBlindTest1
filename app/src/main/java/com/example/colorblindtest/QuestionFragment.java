package com.example.colorblindtest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.colorblindtest.databinding.FragmentQuestionBinding;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.Locale;


public class QuestionFragment extends Fragment {
    public Chronometer mChronometer;
    public ImageView mImageview;
    private static long elapsedTime;
    MyViewModel myViewModel;

    private Question[] mQuestionBank=new Question[]{
            new Question(R.drawable.question1,29),
            new Question(R.drawable.question2,97),
            new Question(R.drawable.question3,2),
            new Question(R.drawable.question4,10),
            new Question(R.drawable.question5,12),
            new Question(R.drawable.question6,6),
            new Question(R.drawable.question7,74),
            new Question(R.drawable.question8,7),
            new Question(R.drawable.question9,3),
            new Question(R.drawable.question10,15),
            new Question(R.drawable.question11,73),
            new Question(R.drawable.question12,56),
            new Question(R.drawable.question13,2),
            new Question(R.drawable.question14,26),
            new Question(R.drawable.question15,16),
            new Question(R.drawable.question16,5),
            new Question(R.drawable.question17,25),
            new Question(R.drawable.question18,42),
            new Question(R.drawable.question19,45),
            new Question(R.drawable.question20,35)


    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myViewModel=new ViewModelProvider(requireActivity(),new SavedStateViewModelFactory(getActivity().getApplication(),requireActivity())).get(MyViewModel.class);
        FragmentQuestionBinding binding;
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_question,container,false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        StringBuilder builder=new StringBuilder();
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.btn0:
                        builder.append("0");
                        break;
                    case R.id.btn1:
                        builder.append("1");
                        break;
                    case R.id.btn2:
                        builder.append("2");
                        break;
                    case R.id.btn3:
                        builder.append("3");
                        break;
                    case R.id.btn4:
                        builder.append("4");
                        break;
                    case R.id.btn5:
                        builder.append("5");
                        break;
                    case R.id.btn6:
                        builder.append("6");
                        break;
                    case R.id.btn7:
                        builder.append("7");
                        break;
                    case R.id.btn8:
                        builder.append("8");
                        break;
                    case R.id.btn9:
                        builder.append("9");
                        break;
                    case R.id.btnClear:
                        builder.setLength(0);
                        myViewModel.getAnswer().setValue(null);
                        break;
                }
                //if no input then set text to hint
                if (builder.length()==0){
                    binding.textView5.setText(getString(R.string.answer));
                }else{
                    binding.textView5.setText(builder.toString());
                    //save the answer input to avoid screen lost during rotation
                    myViewModel.getAnswer().setValue(binding.textView5.getText().toString());
                }
            }

        };
        binding.btn0.setOnClickListener(listener);
        binding.btn1.setOnClickListener(listener);
        binding.btn2.setOnClickListener(listener);
        binding.btn3.setOnClickListener(listener);
        binding.btn4.setOnClickListener(listener);
        binding.btn5.setOnClickListener(listener);
        binding.btn6.setOnClickListener(listener);
        binding.btn7.setOnClickListener(listener);
        binding.btn8.setOnClickListener(listener);
        binding.btn9.setOnClickListener(listener);
        binding.btnClear.setOnClickListener(listener);

        binding.btnSubmit.setOnClickListener(listener);

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verify input is empty or not
                if(builder.toString().isEmpty()){
                    binding.textView5.setText(R.string.answer_empty_message);
                    //save the answer input to avoid screen lost during rotation
                    myViewModel.getAnswer().setValue(binding.textView5.getText().toString());

                }
                else{
                    //if answer correct
                    if(Integer.valueOf(builder.toString()).intValue()==mQuestionBank[myViewModel.getIndex().getValue()].getAnswer()){
                        myViewModel.answerCorrect();
                        builder.setLength(0);
                        binding.textView5.setText(R.string.answer_correct_message);
                        //save the answer input to avoid screen lost during rotation
                        myViewModel.getAnswer().setValue(binding.textView5.getText().toString());
                    }
                    //if answer incorrect
                    else{
                        Question question=mQuestionBank[myViewModel.getIndex().getValue()];
                        question.setUserInput(Integer.valueOf(builder.toString()).intValue());
                        myViewModel.insertFalseAnswer(question);
                        myViewModel.answerWrong();
                        builder.setLength(0);
                        binding.textView5.setText(R.string.answer_wrong_message);
                        //save the answer input to avoid screen lost during rotation
                        myViewModel.getAnswer().setValue(binding.textView5.getText().toString());

                    }
                    //if reach end of the question then proceed to the result screen and verify database
                    if(myViewModel.getIndex().getValue()==mQuestionBank.length) {
                        NavController controller = Navigation.findNavController(view);
                        controller.navigate(R.id.action_questionFragment3_to_winFragment);
                        mChronometer.stop();
                        long time= SystemClock.elapsedRealtime()-mChronometer.getBase();
                        int h   = (int)(time /3600000);
                        int m = (int)(time - h*3600000)/60000;
                        int s= (int)(time - h*3600000- m*60000)/1000 ;
                        String hh = h < 10 ? "0"+h: h+"";
                        String mm = m < 10 ? "0"+m: m+"";
                        String ss = s < 10 ? "0"+s: s+"";
                        myViewModel.getUptime().setValue("Completion Time:"+hh+":"+mm+":"+ss);
                        myViewModel.getAnswer().setValue(null);
                        myViewModel.IS_STARTED=false;
                        String name=myViewModel.getKeyUsername().getValue();
                        int score=myViewModel.getCurrentScore().getValue();
                        verifyDatabase(Integer.valueOf(hh),Integer.valueOf(mm),Integer.valueOf(ss),name,score);
                        }
                    else{
                        updateQuestion();
                    }

                    }
            }
        });

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myViewModel=new ViewModelProvider(requireActivity(),new SavedStateViewModelFactory(getActivity().getApplication(),requireActivity())).get(MyViewModel.class);

        mImageview=view.findViewById(R.id.imageView2);
        mChronometer=view.findViewById(R.id.textView9);
        //set the timer start from 0
        if(myViewModel.IS_STARTED==false){
            elapsedTime=0;
            myViewModel.IS_STARTED=true;
        }
        updateQuestion();
        TextView textView=view.findViewById(R.id.textView5);
        if(myViewModel.getAnswer().getValue()!=null){
        textView.setText(myViewModel.getAnswer().getValue());}
    }


    private void updateQuestion(){
        MyViewModel myViewModel;
        myViewModel=new ViewModelProvider(requireActivity(),new SavedStateViewModelFactory(getActivity().getApplication(),requireActivity())).get(MyViewModel.class);
        int integer=mQuestionBank[myViewModel.getIndex().getValue()].getImage();
        mImageview.setImageResource(integer);
        }

    @Override
    public void onPause() {
        super.onPause();
        //save the time left when app is in background
        elapsedTime=SystemClock.elapsedRealtime()-mChronometer.getBase();
    }

    @Override
    public void onResume() {
        super.onResume();
        //get back the elapsed time when app is resumed
        mChronometer.setBase(SystemClock.elapsedRealtime()-elapsedTime);
        mChronometer.start();
    }

    private void verifyDatabase(int hour,int minute,int second,String name, int score){
        //if user already exist then update the data based on condition
        if (myViewModel.verifyUsers(name)==1){
            //if score is higher than overwrite the score and time
            if(score>myViewModel.verifyScore(name)){
                Rank user=new Rank(name,score,hour,minute,second);
                myViewModel.updateUsers(user);
            }
            //if score same then update the time only if the time is shorter
            else if(score== myViewModel.verifyScore(name)){
                if(hour== myViewModel.verifyHour(name)){
                    if(minute== myViewModel.verifyMinute(name)){
                        if(second<myViewModel.verifySecond(name)){
                            Rank user=new Rank(name,score,hour,minute,second);
                            myViewModel.updateUsers(user);
                        }
                    }
                    else if(minute<myViewModel.verifyMinute(name)){
                        Rank user=new Rank(name,score,hour,minute,second);
                        myViewModel.updateUsers(user);
                    }
                }
                else if(hour<myViewModel.verifyHour(name)){
                    Rank user=new Rank(name,score,hour,minute,second);
                    myViewModel.updateUsers(user);
                }
            }
        }
        //if not exist then direct insert
        else{
            Rank user=new Rank(name,score,hour,minute,second);
            myViewModel.insertUsers(user);
        }
    }
}
