package com.example.lab07;

import static android.app.ProgressDialog.show;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ShowHeadlines extends Activity {
    // Main category has already been selected by user: ‘World News’, Business’, ...
// ["urlCaption", "urlAddress"] comes in a bundle sent by main thread
// here we access RSS-feed and show corresponding headlines
    ArrayList<SingleItem> newsList = new ArrayList<SingleItem>();
    ListView myListView; String urlAddress = "", urlCaption = ""; int publisherLogo = 0;
    SingleItem selectedNewsItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myListView = (ListView)this.findViewById(R.id.myListView);
        Intent callingIntent = getIntent();
        Bundle myBundle = callingIntent.getExtras();
        urlAddress = myBundle.getString("urlAddress"); urlCaption = myBundle.getString("urlCaption"); publisherLogo = myBundle.getInt("publisherLogo");
        TextView titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setText(urlCaption);
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        myListView = (ListView)this.findViewById(R.id.myListView);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View v, int index, long id) {
                selectedNewsItem = newsList.get(index);
                showNiceDialogBox(selectedNewsItem, getApplicationContext());
            }});
// get stories for the selected news option
        DownloadRssFeed downloader = new DownloadRssFeed(ShowHeadlines.this);
        downloader.execute(urlAddress, urlCaption);
    }//onCreate
    public void showNiceDialogBox(SingleItem selectedStoryItem, Context context){
// make a nice-looking dialog box (story summary, btnClose, btnMore)
// CAUTION: (check)on occasions title and description are the same!
        String title = selectedStoryItem.getTitle();
        String description = selectedStoryItem.getDescription();
        if (title.toLowerCase().equals(description.toLowerCase())){ description = ""; }
        try {
//CAUTION: sometimes TITLE and DESCRIPTION include HTML markers
            final Uri storyLink = Uri.parse(selectedStoryItem.getLink());
            AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);
            myBuilder.setIcon(publisherLogo)
                    .setTitle(Html.fromHtml(urlCaption) )
                    .setMessage(title + "\n\n" + Html.fromHtml(description) + "\n")
.setPositiveButton("Close", null)
                    .setNegativeButton("More", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int whichOne) {
                            Intent browser = new Intent(Intent.ACTION_VIEW, storyLink);
                            startActivity(browser);
                        }}).show();

        }
        catch (Exception e) { Log.e("Error DialogBox"
                , e.getMessage() ); }
    }//showNiceDialogBox
}//ShowHeadlines