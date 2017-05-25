package com.adrienne.cookbook_app.My_cookbook;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.adrienne.cookbook_app.My_cookbook.db_cookbook.RecipeSQLiteOpenHelper;
import com.adrienne.cookbook_app.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CookbookOnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyCookbookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCookbookFragment extends Fragment {

    public CookbookRecyclerViewAdapter mCookbookRecyclerViewAdapter;
    private RecipeSQLiteOpenHelper mDBHelper;

    private String mQueryCookbook;
    private EditText mqueryCookbookEditText;
    private ImageView mSearch, mSort;
    List<MyRecipe> myRecipe;


    private CookbookOnFragmentInteractionListener mCookbookListener;


    public MyCookbookFragment() {
        // Required empty public constructor
    }

    public static MyCookbookFragment newInstance() {
        MyCookbookFragment fragment = new MyCookbookFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_my_cookbook, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mCookbookListener != null) {
            mCookbookListener.onFragmentInteraction();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myRecipe = new ArrayList<>();

        mqueryCookbookEditText = (EditText) view.findViewById(R.id.cookbook_search_query);
        mSearch = (ImageView) view.findViewById(R.id.cookbook_submit_query);
        mQueryCookbook = mqueryCookbookEditText.getText().toString();
        mSort = (ImageView) view.findViewById(R.id.cookbook_sortaz);

        mDBHelper = RecipeSQLiteOpenHelper.getInstance(getContext());

        if(mDBHelper.getAllRecipes() != null) {
            myRecipe = mDBHelper.getAllRecipes();
        }



        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mycookbook_layout_recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        mCookbookRecyclerViewAdapter = new CookbookRecyclerViewAdapter(myRecipe);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mCookbookRecyclerViewAdapter);





        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRecipe = mDBHelper.searchByTitleOrCategory(mQueryCookbook);
                mCookbookRecyclerViewAdapter = new CookbookRecyclerViewAdapter(myRecipe);
                recyclerView.setAdapter(mCookbookRecyclerViewAdapter);

            }
        });

        mSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRecipe = mDBHelper.sortByTitle();
                mCookbookRecyclerViewAdapter = new CookbookRecyclerViewAdapter(myRecipe);
                recyclerView.setAdapter(mCookbookRecyclerViewAdapter);

            }
        });



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CookbookOnFragmentInteractionListener) {
            mCookbookListener = (CookbookOnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SearchOnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCookbookListener = null;
    }


    public interface CookbookOnFragmentInteractionListener {

        void onFragmentInteraction();
    }
}
