package ir.hhadanooo.persianshare.setting;



import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

import ir.hhadanooo.persianshare.R;


public class settingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_screen_setting);
    }
}
