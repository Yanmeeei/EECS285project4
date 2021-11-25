package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import android.app.Application;
import android.content.res.Configuration;
import android.widget.Toast;

public class StartupApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
        == Configuration.UI_MODE_NIGHT_NO) {
      Toast.makeText(this, R.string.toast_dark_mode_one, Toast.LENGTH_LONG).show();
      Toast.makeText(this, R.string.toast_dark_mode_two, Toast.LENGTH_LONG).show();
    }
  }
}
