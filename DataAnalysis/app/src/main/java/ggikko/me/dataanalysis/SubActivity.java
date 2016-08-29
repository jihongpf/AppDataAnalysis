package ggikko.me.dataanalysis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class SubActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sub);

    MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, "8c2af00ff44dee2feae5e25a486e4135");

    try {
      JSONObject props = new JSONObject();
      props.put("on page", "subActivity");
      mixpanel.track("Plan Selected", props);
    } catch (JSONException e) {
      Log.e("MYAPP", "Unable to add properties to JSONObject", e);
    }

  }
}
