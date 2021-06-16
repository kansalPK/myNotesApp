package model;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mynotesapp.R;
import note.node_details;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<String> titles;
    List<String> content;

    public Adapter(List<String> titles, List<String> content) {
        this.titles = titles;
        this.content = content;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ic_custom_layout, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.noteTitle.setText(titles.get(position));
        holder.noteContent.setText(content.get(position));
        Integer colorCode = getRandomColor();
        holder.cardView.setBackgroundColor(holder.view.getResources().getColor(colorCode, null));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), node_details.class);
                i.putExtra("title", titles.get(position));
                i.putExtra("content", content.get(position));
                i.putExtra("colorCode", colorCode);
                v.getContext().startActivity(i);
            }
        });
    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.skyBlue);
        colorCode.add(R.color.red);
        colorCode.add(R.color.gray);
        colorCode.add(R.color.greenLyt);
        colorCode.add(R.color.lytGreen);
        colorCode.add(R.color.notGreen);
        colorCode.add(R.color.blue);
        colorCode.add(R.color.white);
        colorCode.add(R.color.purple_200);
        colorCode.add(R.color.pink);
        colorCode.add(R.color.yellow);
        colorCode.add(R.color.summer);
        colorCode.add(R.color.sunSet);
        colorCode.add(R.color.winter);
        colorCode.add(R.color.gold);
        colorCode.add(R.color.darkPink);
        colorCode.add(R.color.vintage);
        colorCode.add(R.color.xMas);
        colorCode.add(R.color.wedding);
        colorCode.add(R.color.retro);
        colorCode.add(R.color.pastel);
        colorCode.add(R.color.neon);
        colorCode.add(R.color.brightPurple);
        colorCode.add(R.color.halloween);
        colorCode.add(R.color.brown);
        colorCode.add(R.color.autumn);
        colorCode.add(R.color.cold);

        Random random = new Random();
        int num = random.nextInt(colorCode.size());
        return colorCode.get(num);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView noteTitle, noteContent;
        View view;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.titles);
            noteContent = itemView.findViewById(R.id.content);
            cardView = itemView.findViewById(R.id.noteCard);
            view = itemView;
        }
    }
}
