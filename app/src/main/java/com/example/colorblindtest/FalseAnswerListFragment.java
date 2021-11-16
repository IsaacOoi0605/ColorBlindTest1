package com.example.colorblindtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FalseAnswerListFragment extends Fragment {
    RecyclerView mRecyclerView;
    QuestionAdapter mMyAdapter;
    List<Question> mQuestions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.false_answer_result,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        MyViewModel myViewModel;
        myViewModel=new ViewModelProvider(requireActivity(),new SavedStateViewModelFactory(getActivity().getApplication(),requireActivity())).get(MyViewModel.class);
        mQuestions=myViewModel.getFalseAnswer();
        if(mMyAdapter==null){
            mMyAdapter=new QuestionAdapter(mQuestions);
        mRecyclerView=view.findViewById(R.id.false_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mMyAdapter);}
        else{
        mMyAdapter.notifyDataSetChanged();}
    }

    private class QuestionHolder extends RecyclerView.ViewHolder{
        TextView trueAnswer,inputAnswer;
        ImageView questionImage;
        private Question mQuestion;
        public QuestionHolder(View itemView){
            super(itemView);

            trueAnswer=itemView.findViewById(R.id.textView11);
            inputAnswer=itemView.findViewById(R.id.textView7);
            questionImage=itemView.findViewById(R.id.imageView3);
        }

        public void bindQuestion(Question question){
            mQuestion=question;
            trueAnswer.setText("True Answer:"+question.getAnswer());
            inputAnswer.setText("Answer have been entered:"+question.getUserInput());
            questionImage.setImageResource(question.getImage());
        }


    }


    private class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder>{
        private List<Question> mQuestions;
        public QuestionAdapter(List<Question> questions){
            mQuestions=questions;
        }
        @NonNull
        @Override
        public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.cell_normal,parent,false);
            return new QuestionHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
            Question question=mQuestions.get(position);
            holder.bindQuestion(question);
        }


        @Override
        public int getItemCount() {
            return mQuestions.size();
        }}
}
