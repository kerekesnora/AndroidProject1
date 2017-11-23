package sapiadvertiser.sapiadvertiser;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.FocusFinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowId;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import static android.view.View.GONE;


public class ListFragment extends Fragment {
    private ArrayList<ModelList> feedsList;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private Button add;
    private TextView first;
    private int flag=2;
    private static final String TAG = "ListFragment";
    private EditText searchtexte;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedsList = new ArrayList<>();
        flag = this.getArguments().getInt("flag");
        Log.d("teszt", flag + " ez a flag");

        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }



    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference modelRef = ref.child("newroad");
        adapter = new MyRecyclerViewAdapter(getActivity(), feedsList);

        modelRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                feedsList.clear();
               // Map<String, Object> objectMap = (HashMap<String, Object>)
                 //       snapshot.getValue();
                ArrayList<ModelList> objectMap = (ArrayList<ModelList>) snapshot.getValue();
                if(objectMap!=null){
                for (Object obj : objectMap) {

                    Map<String, Object> mapObj = (Map<String, Object>) obj;
                    ModelList match = new ModelList();
                    match.setStart((String) mapObj.get("start"));
                    match.setFinish((String) mapObj.get("finish"));
                    match.setDate((String) mapObj.get("date"));
                    match.setMessage((String) mapObj.get("message"));
                    match.setPhone((String) mapObj.get("phone"));
                    match.setClock((String) mapObj.get("clock"));
                    feedsList.add(match);
                    Log.d("teszt",objectMap.size()+"" );
                    adapter.setStringSearch("");
                    adapter.notifyDataSetChanged();
                }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                    Log.d("teszt","1");

            }

        });



        final View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(adapter);
        add = (Button) rootView.findViewById(R.id.add);

        searchtexte =(EditText)rootView.findViewById(R.id.search);
        if(flag==1){
            add.setVisibility(View.VISIBLE);
        }else{
            add.setVisibility(View.INVISIBLE);

        }
       // first=(TextView) rootView.findViewById(R.id.maintext) ;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                first.setText("Uj lista elem");
                Fragment newFragment = new NewWay();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.list, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        searchtexte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("teszt4"," sziasakjdsbf1:"+charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("teszt4"," sziasakjdsbf2:"+charSequence.toString());

                    adapter.setStringSearch(charSequence.toString());



            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("teszt4"," sziasakjdsbf3:"+editable.toString());
            }

        });



        return rootView;

    }



}

