package com.adrienne.cookbook_app.My_cookbook;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adrienne.cookbook_app.MainActivity;
import com.adrienne.cookbook_app.My_cookbook.db_cookbook.RecipeSQLiteOpenHelper;
import com.adrienne.cookbook_app.R;

import java.util.ArrayList;
import java.util.List;

import static com.adrienne.cookbook_app.DetailViewofRecipe.SavedAPIRecipe.ApiDetaiRecipeFragment.TAG;


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
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private String mQueryCookbook;
    private EditText mQueryCookbookEditText;
    private ImageView mSort, mBookmark;
    List<MyRecipe> myRecipes;


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
        myRecipes = new ArrayList<>();
        mDBHelper = RecipeSQLiteOpenHelper.getInstance(getContext());

        if(mDBHelper.getAllRecipes() != null) {
            myRecipes = mDBHelper.getAllRecipes();
        }
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

        /* The fragment displays the collection of recipes on the home screen ie the cookbook.*/

        List<MyRecipe> myRecipes = new ArrayList<>();

        mQueryCookbookEditText = (EditText) view.findViewById(R.id.cookbook_search_query);

        mSort = (ImageView) view.findViewById(R.id.cookbook_sortaz);
        mBookmark = (ImageView) view.findViewById(R.id.cookbook_bookmark);

        mDBHelper = RecipeSQLiteOpenHelper.getInstance(getContext());

        if(mDBHelper.getAllRecipes() != null) {
            myRecipes = mDBHelper.getAllRecipes();
            Log.d(TAG, "onViewCreated: all recipes" + myRecipes);
        }



        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mycookbook_layout_recyclerview);
        mCookbookRecyclerViewAdapter = new CookbookRecyclerViewAdapter(myRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(mCookbookRecyclerViewAdapter);

        //search through the recipes that the user has selected for the cookbook
        //Search through categories and title
        mQueryCookbookEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                List<MyRecipe> myRecipes ;
                boolean search = false;
                if(actionId == EditorInfo.IME_ACTION_GO){
                    mQueryCookbook = mQueryCookbookEditText.getText().toString();
                    Log.d(TAG, "onEditorAction: mquerycookbook " + mQueryCookbook);
                    myRecipes = mDBHelper.searchByTitleOrCategory(mQueryCookbook);
                    Log.d(TAG, "onEditorAction: results " + myRecipes);
                    recyclerView.setAdapter(new CookbookRecyclerViewAdapter(myRecipes));
                    search = true;
                }
                return search;
            }
        });


        // sort items in the bookmark by A to Z in the title
        mSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MyRecipe> myRecipes ;
                Toast.makeText(getContext(), "clicked on sort", Toast.LENGTH_SHORT).show();
                myRecipes = mDBHelper.sortByTitle();
                Log.d(TAG, "onClick: mSORT " + myRecipes);
                recyclerView.setAdapter(new CookbookRecyclerViewAdapter(myRecipes));

            }
        });
        // displays items that the user marked with bookmark
        mBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MyRecipe> myRecipes = new ArrayList<>();
                if(mDBHelper.getBookmarkItems() != null){
                    myRecipes = mDBHelper.getBookmarkItems();
                    Log.d(TAG, "onClick: mBookmark" + myRecipes);
                } else {
                    Toast.makeText(getContext(), "No items have been bookmarked.", Toast.LENGTH_LONG)
                            .show();
                }

                recyclerView.setAdapter(new CookbookRecyclerViewAdapter(myRecipes));
            }
        });

        //swipe refresh for cookbook items
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.cookbook_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List<MyRecipe> myRecipes ;
                myRecipes = mDBHelper.getAllRecipes();
                Log.d(TAG, "onRefresh: swiperefresh" + myRecipes);
                recyclerView.setAdapter(new CookbookRecyclerViewAdapter(myRecipes));
                Log.d(TAG, "onRefresh: " + mCookbookRecyclerViewAdapter);
                mSwipeRefreshLayout.setRefreshing(false);
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
