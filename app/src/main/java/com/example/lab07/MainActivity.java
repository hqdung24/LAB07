package com.example.lab07;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // Main GUI - A NEWS application based on National Public Radio RSS material
    ArrayAdapter<String> adapterMainSubjects;
    ListView myMainListView;
    Context context;
    SingleItem selectedNewsItem;

    HashMap<String, String[][]> publishers;
    String[][] myUrlCaptionMenu;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_main);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        Intent intent = getIntent();
        String publisherName = intent.getStringExtra("publisherName");
        int publisherLogo = intent.getIntExtra("publisherLogo", 0);

        publishers = new HashMap<>();
        publishers.put("VnExpress", new String[][]{
                {"https://vnexpress.net/rss/the-gioi.rss", "Thế giới"},
                {"https://vnexpress.net/rss/thoi-su.rss", "Thời sự"},
                {"https://vnexpress.net/rss/giai-tri.rss", "Giải trí"},
                {"https://vnexpress.net/rss/phap-luat.rss", "Pháp luật"},
                {"https://vnexpress.net/rss/giao-duc.rss", "Giáo dục"},
                {"https://vnexpress.net/rss/suc-khoe.rss", "Sức khoẻ"},
                {"https://vnexpress.net/rss/cong-nghe.rss", "Công nghệ"}
        });
        publishers.put("Dân Trí", new String[][]{
                {"https://dantri.com.vn/rss/tam-diem.rss", "Tâm điểm"},
                {"https://dantri.com.vn/rss/the-gioi.rss", "Thế giới"},
                {"https://dantri.com.vn/rss/giao-duc-khuyen-hoc.rss", "Giáo dục"},
                {"https://dantri.com.vn/rss/suc-khoe.rss", "Sức khỏe"},
                {"https://dantri.com.vn/rss/lao-dong-viec-lam.rss", "Việc làm"},
                {"https://dantri.com.vn/rss/khoa-hoc.rss", "Khoa học"},
                {"https://dantri.com.vn/rss/phap-luat.rss", "Pháp luật"}

        });
        publishers.put("Tuổi Trẻ", new String[][]{
                {"https://tuoitre.vn/rss/thoi-su.rss", "Thời sự"},
                {"https://tuoitre.vn/rss/the-gioi.rss", "Thế giới"},
                {"https://tuoitre.vn/rss/kinh-doanh.rss", "Kinh doanh"},
                {"https://tuoitre.vn/rss/giao-duc.rss", "Giáo dục"},
                {"https://tuoitre.vn/rss/suc-khoe.rss", "Sức khỏe"},
                {"https://tuoitre.vn/rss/cong-nghe.rss", "Công nghệ"}
        });
        publishers.put("Thanh Niên", new String[][]{
                {"https://thanhnien.vn/rss/thoi-su.rss", "Thời sự"},
                {"https://thanhnien.vn/rss/the-gioi.rss", "Thế giới"},
                {"https://thanhnien.vn/rss/doi-song.rss", "Đời sống"},
                {"https://thanhnien.vn/rss/giao-duc.rss", "Giáo dục"},
                {"https://thanhnien.vn/rss/cong-nghe.rss", "Công nghệ"},
                {"https://thanhnien.vn/rss/suc-khoe.rss", "Sức khỏe"}
        });

        // Get URL-Caption array for the selected publisher
        myUrlCaptionMenu = publishers.getOrDefault(publisherName, new String[][]{});
        String[] myUrlCaption = new String[myUrlCaptionMenu.length];
        String[] myUrlAddress = new String[myUrlCaptionMenu.length];


        for (int i=0; i<myUrlAddress.length; i++) {
            myUrlAddress[i] = myUrlCaptionMenu[i][0]; myUrlCaption[i] = myUrlCaptionMenu[i][1];
        }
        context = getApplicationContext();
            TextView titleTextView = findViewById(R.id.titleTextView);
            titleTextView.setText( publisherName + " " +  niceDate()); // Change this to whatever title you want
        // user will tap on a ListView’s row to request category’s headlines

        myMainListView = (ListView)this.findViewById(R.id.myListView);
        myMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> _av, View _v, int _index, long _id) {
                String urlAddress = myUrlAddress[_index], urlCaption = myUrlCaption[_index];
                //create an Intent to talk to activity: ShowHeadlines
                Intent callShowHeadlines = new Intent(MainActivity.this, ShowHeadlines.class);
                //prepare a Bundle and add the input arguments: url & caption
                Bundle myData = new Bundle();
                myData.putString("urlAddress", urlAddress); myData.putString("urlCaption", urlCaption); myData.putInt("publisherLogo", publisherLogo);

                callShowHeadlines.putExtras(myData);
                startActivity(callShowHeadlines);
            }
        });
        // fill up the Main-GUI’s ListView with main news categories
        adapterMainSubjects = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myUrlCaption);
        myMainListView.setAdapter(adapterMainSubjects);
    }//onCreate
    public static String niceDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM d, yyyy", Locale.US);
        return sdf.format(new Date()); //Monday Apr 7, 2014
    }

} // Ma