package com.example.colorblindtest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.colorblindtest.databinding.FragmentQuestionBinding;
import com.example.colorblindtest.databinding.FragmentWinBinding;

import java.util.List;


public class WinFragment extends Fragment {

    MyViewModel myViewModel;
    TextView mTextView,warningText;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
        myViewModel=new ViewModelProvider(requireActivity(),new SavedStateViewModelFactory(getActivity().getApplication(),requireActivity())).get(MyViewModel.class);
        FragmentWinBinding binding;
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_win,container,false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        binding.buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navigate back to home screen
                Navigation.findNavController(view).navigate(R.id.action_winFragment_to_homeFragment);
                //verify current score higher than highest score or not
                if (myViewModel.getCurrentScore().getValue()>myViewModel.getHighScore().getValue()){
                    myViewModel.getHighScore().setValue(myViewModel.getCurrentScore().getValue());
                    myViewModel.save();
                }
        }});
        //navigate false list screen
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_winFragment_to_falseAnswerListFragment);

            }
        });

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MyViewModel myViewModel;
        myViewModel=new ViewModelProvider(requireActivity(),new SavedStateViewModelFactory(getActivity().getApplication(),requireActivity())).get(MyViewModel.class);
        mTextView=view.findViewById(R.id.textView8);
        mTextView.setText(myViewModel.getUptime().getValue());
        warningText=view.findViewById(R.id.textView10);
        //show warning text only if user score is lower than 15
        if (myViewModel.getCurrentScore().getValue() >= 15) {
            warningText.setVisibility(view.GONE);
        }
    }
}