package com.example.easytutonotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.google.android.material.button.MaterialButton;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmResults;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    RealmResults<Note> notesList;




    public MyAdapter(Context context, RealmResults<Note> notesList) {

        this.notesList = notesList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false));
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder,int position) {


        Note note = notesList.get(position);
        holder.titleOutput.setText(note.getTitle());


        holder.descriptionOutput.setText(note.getDescription());


        String formatedTime = DateFormat.getDateTimeInstance().format(note.getCreatedTime());
        holder.timeOutput.setText(formatedTime);

        // holder.linearLayout.setBackground()



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", note.getTitle());
                bundle.putString("Description", note.getDescription());

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu menu = new PopupMenu(holder.context,v);
                menu.getMenu().add("DELETE");
                menu.getMenu().add("EDIT");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE")){
                            //delete the note
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(holder.context,"Note deleted",Toast.LENGTH_SHORT).show();
                        }
                        if(item.getTitle().equals("EDIT")){

                            holder.context.startActivity(new Intent(holder.context,AddNoteActivity.class));
                            //delete the note
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();

                        }

                        return true;
                    }



                });
                menu.show();


                return true;
            }
        });


    }

    private int getRandomColor() {
        List<Integer> colorcode=new ArrayList<>();
        colorcode.add(R.color.white);
        colorcode.add(R.color.teal_200);
        colorcode.add(R.color.PaleTurquoise);
        colorcode.add(R.color.silver);
        colorcode.add(R.color.SkyBlue);
        colorcode.add(R.color.Violet);
        colorcode.add(R.color.SpringGreen);

        Random random=new Random();
        int number=random.nextInt(colorcode.size());
        return number;

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleOutput;
        TextView descriptionOutput;
        TextView timeOutput;
        CardView cardView;
        LinearLayout linearLayout;
         Context context;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.titleoutput);
            descriptionOutput = itemView.findViewById(R.id.descriptionoutput);
            timeOutput = itemView.findViewById(R.id.timeoutput);
            context = itemView.getContext();
            cardView=itemView.findViewById(R.id.cardview);
            linearLayout=itemView.findViewById(R.id.linearlay);


        }
    }
}
