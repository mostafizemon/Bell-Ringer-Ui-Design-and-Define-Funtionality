package com.mostafiz.bellringer;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    ListView listView;
    DatabaseHelper databaseHelper;
    ArrayList<HashMap<String,String>> arrayList;
    HashMap<String,String> hashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab=findViewById(R.id.fab);
        listView=findViewById(R.id.listview);
        databaseHelper=new DatabaseHelper(this);

        loaddata();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddActivity.class));
            }
        });





    }

    public void loaddata(){
        Cursor cursor=databaseHelper.show();

        if (cursor!=null && cursor.getCount()>0){

            arrayList=new ArrayList<>();
            while (cursor.moveToNext()){
                int id=cursor.getInt(0);
                String room=cursor.getString(1);

                hashMap=new HashMap<>();
                hashMap.put("id", String.valueOf(id));
                hashMap.put("room",room);
                arrayList.add(hashMap);
            }

            listView.setAdapter(new Myadapter());
        }
        else {

        }
    }




    public class Myadapter extends BaseAdapter {

        private int selectedItem = -1; // To track the selected item

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return arrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater layoutInflater = getLayoutInflater();
                convertView = layoutInflater.inflate(R.layout.listview, viewGroup, false);
                holder = new ViewHolder();
                holder.listText = convertView.findViewById(R.id.listview_text);
                holder.deleteButton = convertView.findViewById(R.id.delete);
                holder.secondaryOptions = convertView.findViewById(R.id.secondary_options);
                holder.optionOn = convertView.findViewById(R.id.option_on);
                holder.optionOff = convertView.findViewById(R.id.option_off);
                holder.optionSetTimer = convertView.findViewById(R.id.option_set_timer);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            hashMap = arrayList.get(i);
            String id = hashMap.get("id");
            String name = hashMap.get("room");
            holder.listText.setText(name);

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseHelper.delete(id);
                    loaddata();
                }
            });

            holder.listText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedItem == i) {
                        selectedItem = -1; // Deselect the item
                    } else {
                        selectedItem = i; // Select the new item
                    }
                    notifyDataSetChanged();
                }
            });

            if (selectedItem == i) {
                holder.secondaryOptions.setVisibility(View.VISIBLE);
            } else {
                holder.secondaryOptions.setVisibility(View.GONE);
            }

            holder.optionOn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle On option click
                }
            });

            holder.optionOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle Off option click
                }
            });

            holder.optionSetTimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTimePickerDialog();
                }
            });

            return convertView;
        }

        private class ViewHolder {
            TextView listText;
            ImageButton deleteButton;
            LinearLayout secondaryOptions;
            TextView optionOn;
            TextView optionOff;
            TextView optionSetTimer;
        }
    }

    private void showTimePickerDialog() {
        MinuteSecondPickerDialog dialog = new MinuteSecondPickerDialog(this, new MinuteSecondPickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(int minutes, int seconds) {
                // Handle the selected minutes and seconds here
               // selectedOptionTextView.setText("Timer set to: " + minutes + " minutes and " + seconds + " seconds");
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loaddata();
    }



}
