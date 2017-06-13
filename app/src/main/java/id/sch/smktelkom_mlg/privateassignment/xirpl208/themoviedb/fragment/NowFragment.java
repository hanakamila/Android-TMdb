package id.sch.smktelkom_mlg.privateassignment.xirpl208.themoviedb.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;

import id.sch.smktelkom_mlg.privateassignment.xirpl208.themoviedb.R;
import id.sch.smktelkom_mlg.privateassignment.xirpl208.themoviedb.adapter.NowAdapter;
import id.sch.smktelkom_mlg.privateassignment.xirpl208.themoviedb.model.Result;
import id.sch.smktelkom_mlg.privateassignment.xirpl208.themoviedb.model.SourcesResponse;
import id.sch.smktelkom_mlg.privateassignment.xirpl208.themoviedb.service.GsonGetRequest;
import id.sch.smktelkom_mlg.privateassignment.xirpl208.themoviedb.service.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowFragment extends Fragment {

    ArrayList<Result> mList = new ArrayList<>();
    NowAdapter mAdapter;

    public NowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new NowAdapter(this.getActivity(), mList);
        recyclerView.setAdapter(mAdapter);

        downloadDataSources();
    }

    private void downloadDataSources() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=257d0b7507984b4e2c320ce924b61d4e";

        GsonGetRequest<SourcesResponse> myRequest = new GsonGetRequest<SourcesResponse>
                (url, SourcesResponse.class, null, new Response.Listener<SourcesResponse>() {

                    @Override
                    public void onResponse(SourcesResponse response) {
                        Log.d("FLOW", "onResponse: " + (new Gson().toJson(response)));
                        mList.addAll(response.results);
                        mAdapter.notifyDataSetChanged();
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("FLOW", "onErrorResponse: ", error);
                    }
                });
        VolleySingleton.getInstance(this.getActivity()).addToRequestQueue(myRequest);
    }

}
