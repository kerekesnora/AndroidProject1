package sapiadvertiser.sapiadvertiser;

import android.util.Log;
import android.widget.ListAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Elod on 11/29/2017.
 */

public class GetAllRows   {
    private MyRecyclerViewAdapter ap;
    private ArrayList<ModelList> list;
    private DatabaseReference ref;
    private DatabaseReference modelRef;

    public GetAllRows(ArrayList<ModelList> list) {
        this.list = list;
        conf();
    }
    public GetAllRows() {
        conf();
    }

    public GetAllRows(MyRecyclerViewAdapter ap, ArrayList<ModelList> list) {
        this.ap = ap;
        this.list = list;
        conf();
    }
    public void get(){

        modelRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ModelList elem = postSnapshot.getValue(ModelList.class);
                    elem.setId(postSnapshot.getKey());
                    list.add(elem);
                    if (ap != null) {
                        ap.setStringSearch("");
                        ap.notifyDataSetChanged();
                    }
                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    public void delete(String elem){
        modelRef.child(elem).getRef().child("flag").setValue(0);
    }
    public void update(String elem,ModelList el){
        modelRef.child(elem).setValue(el);
    }
    public void conf(){
        this.ref = FirebaseDatabase.getInstance().getReference();
        this.modelRef = ref.child("newroad");
    }
}
