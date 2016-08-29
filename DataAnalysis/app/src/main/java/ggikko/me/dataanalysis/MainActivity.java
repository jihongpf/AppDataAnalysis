package ggikko.me.dataanalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

  private MixpanelAPI mixpanel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    String projectToken = "";
    mixpanel = MixpanelAPI.getInstance(this, projectToken);

    try {
      JSONObject props = new JSONObject();
      props.put("Gender", "Female");
      props.put("Logged in", false);
      props.put("Plan", "Premium");

      mixpanel.track("Plan Selected", props);
      mixpanel.track("MainActivity - onCreate called", props);
    } catch (JSONException e) {
      Log.e("MYAPP", "Unable to add properties to JSONObject", e);
    }

    mixpanel.timeEvent("Image Upload");

    // stop the timer if the imageUpload() method returns true
    if (imageUpload()) {
      mixpanel.track("Image Upload");
    }

    try {
      JSONObject props = new JSONObject();
      props.put("User Type", "Paid");
      mixpanel.registerSuperProperties(props);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    mixpanel.identify("13793");

    mixpanel.alias("13793", null);

    // identify must be called before
    // people properties can be set
    mixpanel.getPeople().identify("13793");

    // Sets user 13793's "Plan" attribute to "Premium"
    mixpanel.getPeople().set("Plan", "Premium");

    mixpanel.getPeople().increment("points earned", 500);

    // Pass a Map to increment multiple properties
    Map<String, Integer> properties = new HashMap<String, Integer>();
    properties.put("dollars spent", 17);
    // Subtract by passing a negative value
    properties.put("credits remaining", -34);

    mixpanel.getPeople().increment(properties);

    // Make getPeople() identify has been
    // called before making revenue updates
    mixpanel.getPeople().identify("13793");

    // Tracks $100 in revenue for user 13793
    mixpanel.getPeople().trackCharge(100, null);

    // Refund this user 50 dollars
    mixpanel.getPeople().trackCharge(-50, null);

    // Tracks $25 in revenue for user 13793
    // on the 2nd of january
    try {
      JSONObject properties2 = new JSONObject();
      properties2.put("$time", "2012-01-02T00:00:00");
      mixpanel.getPeople().trackCharge(25, properties2);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        try {
          JSONObject props = new JSONObject();
          props.put("action", "sub");
          mixpanel.track("page changed", props);

          startActivity(new Intent(MainActivity.this, SubActivity.class));
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    });
  }

  public boolean imageUpload() {
    return true;
  }

  @Override
  protected void onDestroy() {
    mixpanel.flush();
    super.onDestroy();
  }
}
