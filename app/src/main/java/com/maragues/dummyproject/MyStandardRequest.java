package com.maragues.dummyproject;

import android.net.Uri;

import org.apache.commons.io.IOUtils;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by miguel on 12/02/14.
 */
public class MyStandardRequest {
  public static final String TAG = MyStandardRequest.class.getSimpleName();

  private String word;

  public MyStandardRequest(String word) {
    this.word = word;
  }

  public String loadDataFromNetwork() throws Exception {
    // With Uri.Builder class we can build our url is a safe manner
    Uri.Builder uriBuilder = Uri.parse("http://robospice-sample.appspot.com/reverse").buildUpon();
    uriBuilder.appendQueryParameter("word", word);

    String url = uriBuilder.build().toString();

    HttpURLConnection urlConnection = (HttpURLConnection) new URL(url)
      .openConnection();
    String result = IOUtils.toString(urlConnection.getInputStream());
    urlConnection.disconnect();

    return result;
  }
}
