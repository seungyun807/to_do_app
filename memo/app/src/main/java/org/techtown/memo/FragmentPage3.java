package org.techtown.memo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.techtown.memo.R;

public class FragmentPage3 extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceStace){
        super.onCreate(savedInstanceStace);



        Intent intent = new Intent(getActivity(), Youtube.class);
        startActivity(intent);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(FragmentPage3.this).commit();
        fragmentManager.popBackStack();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_page_3, container, false);
    }


}

