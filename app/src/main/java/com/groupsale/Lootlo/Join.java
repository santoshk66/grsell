package com.groupsale.Lootlo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.groupsale.Lootlo.models.customer;
import com.groupsale.Lootlo.models.deal;
import com.groupsale.Lootlo.viewHolder.dealHolder;
import com.squareup.picasso.Picasso;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Join#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Join extends Fragment {

    RecyclerView mrecyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass> userList;
    Adapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Join() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Join.
     */
    // TODO: Rename and change types and number of parameters
    public static Join newInstance(String param1, String param2) {
        Join fragment = new Join();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_join, container, false);
        mrecyclerView = v.findViewById(R.id.RecyclerView1);
        //initData();
        initRecyclerView();
        return v;

    }

    public void initRecyclerView() {
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mrecyclerView.setLayoutManager(layoutManager);
        //adapter = new Adapter(userList);
       // mrecyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();


    }


    public void onStart() {

        super.onStart();

        final DatabaseReference DealRef;
        DealRef = FirebaseDatabase.getInstance().getReference().child("customer").child("currentDeal");
        FirebaseRecyclerOptions<deal> options = new FirebaseRecyclerOptions.Builder<deal>().setQuery(DealRef, deal.class).build();

        FirebaseRecyclerAdapter<deal, dealHolder> adapter = new FirebaseRecyclerAdapter<deal, dealHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull dealHolder holder, int position, @NonNull deal model) {
                //holder.textView.setText(model.getDummyName());


               // holder.textView2.setText(model.getDummyBelowText());
                //holder.textview3.setText(model.getDummyTime());
                //Picasso.with(getContext()).load(model.getDummyImageURL()).into(holder.imageView);

            }



            @NonNull
            @Override
            public dealHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent,false);
                dealHolder holder = new dealHolder(view);
                return holder;
            }
        };
        mrecyclerView.setAdapter(adapter);
        adapter.startListening();






    }

    private void initData() {
        userList = new ArrayList<>();

        userList.add(new ModelClass(R.drawable.appicon, "Anjali", "2 KG APPLES @ RS 20", "10:45 am"));

        userList.add(new ModelClass(R.drawable.appicon, "Brijesh", "I am fine", "15:08 pm"));

        userList.add(new ModelClass(R.drawable.appicon, "Sam", "You Know?", "1:02 am"));

    }
}