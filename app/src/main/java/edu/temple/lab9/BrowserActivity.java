package edu.temple.lab9;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.os.Handler;

import java.util.ArrayList;



public class BrowserActivity extends AppCompatActivity {

    int currentIndex = 0;
    ArrayList<TabFragment> tabs = new ArrayList<TabFragment>();

    TabFragment tab = new TabFragment();

    Button prev, next, goButton, openNew;
    EditText urlInput;


    private void addFragment (Fragment fragment, int containerId){
        FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
            .replace(containerId, fragment)
            .commit();
        fm.executePendingTransactions();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);



        // assign all Views to objects
        prev = (Button) findViewById(R.id.prev_tab);
        next = (Button) findViewById(R.id.next_tab);
        goButton = (Button) findViewById(R.id.go_button);
        openNew = (Button) findViewById(R.id.new_tab);
        urlInput = (EditText) findViewById(R.id.url_EditText);

        String url = getIntent().toString();

        addFragment(tab, R.id.tab_frame);
        tabs.add(currentIndex, tab);



        // send urlInput, trigger loadUrl in the fragment
        goButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String userUrl = urlInput.getText().toString();

                if(userUrl.equals(""))
                    return;

                // check if this is a domain
                if (userUrl.contains(".")) {

                    // make sure the URL has a protocol
                    if (!(userUrl.startsWith("http://") || userUrl.startsWith("https://"))) {
                        String temp = "http://";
                        temp += userUrl;
                        userUrl = temp;

                    }
                }

                // search the string as a literal
                else {
                    userUrl = "https://www.google.com/search?q=" + "\"" + userUrl + "\"";
                    // parsing and adding "+" is too much work
                    // search engine will probably give the option to change it anyway
                }

                // navigate to page
                tab.loadPageAt(userUrl);
            }
        });

        openNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabs.set(currentIndex, tab);
                currentIndex = tabs.size();
                tab = new TabFragment();
                addFragment(tab, R.id.tab_frame);
                tabs.add(currentIndex, tab);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabs.size() > 1) {
                    tabs.set(currentIndex, tab);

                    // account for underflow
                    if (currentIndex > 0)
                        tab = tabs.get(--currentIndex);
                    else {
                        currentIndex = tabs.size() - 1;
                        tab = tabs.get(currentIndex);
                    }

                    addFragment(tab, R.id.tab_frame);
                    urlInput.setText(tab.getUrl());
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabs.size() > 1) {
                    tabs.set(currentIndex, tab);

                    // account for overflow
                    if (currentIndex < tabs.size() - 1)
                        tab = tabs.get(++currentIndex);
                    else {
                        currentIndex = 0;
                        tab = tabs.get(currentIndex);
                    }

                    addFragment(tab, R.id.tab_frame);
                    urlInput.setText(tab.getUrl());
                }
            }
        });
    }
}