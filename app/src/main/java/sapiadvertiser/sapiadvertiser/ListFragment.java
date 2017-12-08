package sapiadvertiser.sapiadvertiser;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;


public class ListFragment extends Fragment {
    private ArrayList<ModelList> feedsList;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private Button add;
    private TextView first;
    private int login = 2;
    private EditText searchtexte;
    private GetAllRows fiar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedsList = new ArrayList<>();
        login = this.getArguments().getInt("flag");

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new MyRecyclerViewAdapter(getActivity(), feedsList);

        fiar=new GetAllRows(adapter, feedsList);
        fiar.get();

        final View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(adapter);
        add = (Button) rootView.findViewById(R.id.add);

        searchtexte = (EditText) rootView.findViewById(R.id.search);
        if (login == 1) {
            add.setVisibility(View.VISIBLE);
        } else {
            add.setVisibility(View.INVISIBLE);

        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.setStringSearch(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return rootView;

    }

}

