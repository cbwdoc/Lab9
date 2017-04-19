package edu.temple.lab9;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.net.URL;
import java.util.Map;


public class TabFragment extends Fragment {

    String url;
    private WebView page;
    public TabFragment() {

    }


    public String getUrl() { return url; }

    public void loadPageAt(String address) {
        // pass the desired url with protocol
        url = address;

        //
        page.setWebViewClient(new WebViewClient());
        // enable JavaScript, in practice the user should be able to disable as well
        // finally, load the url to display
        page.loadUrl(url);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        page = (WebView) view.findViewById(R.id.web_view);

        page.getSettings().setJavaScriptEnabled(true);
        page.setWebViewClient(new WebViewClient());

        page.loadUrl(url);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}