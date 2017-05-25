package com.adrienne.cookbook_app.RecipeDetail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrienne.cookbook_app.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ApiDetaiRecipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ApiDetaiRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApiDetaiRecipeFragment extends Fragment {

    public static final String TAG = "API COOKBOOK FRAG";

    private OnFragmentInteractionListener mApiCookbookListener;

    public ApiDetaiRecipeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ApiDetaiRecipeFragment newInstance(Bundle bundle) {
        ApiDetaiRecipeFragment fragment = new ApiDetaiRecipeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


   }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_api_detai_recipe, container, false);




    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mApiCookbookListener != null) {
            mApiCookbookListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mApiCookbookListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mApiCookbookListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
