package com.apps.ifaldyprayanda.moviecatalogue3.notification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.apps.ifaldyprayanda.moviecatalogue3.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreference sharedPreference;

    private SettingReceiver settingReceiver;

    private Switch switchDaily;
    private Switch switchNewRelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().setTitle(R.string.action_settings);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sharedPreference = new SharedPreference(this);
        settingReceiver =  new SettingReceiver();

        switchDaily = findViewById(R.id.dailyReminder);
        switchNewRelease = findViewById(R.id.relaseReminder);

        switchDaily.setOnClickListener(this);
        switchNewRelease.setOnClickListener(this);

        checkReminder();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.dailyReminder:
                if (switchDaily.isChecked())
                {
                    sharedPreference.saveBoolean(SharedPreference.STATUS_DAILY_NOTIF, true);
                    settingReceiver.setTimeDailyNotif(this, "07:00", getString(R.string.status_daily));
                    Toast.makeText(this, getString(R.string.daily_notif_enabled), Toast.LENGTH_SHORT).show();
                }else
                {
                    sharedPreference.saveBoolean(SharedPreference.STATUS_DAILY_NOTIF, false);
                    settingReceiver.cancelDailyNotif(this);
                    Toast.makeText(this, getString(R.string.daily_notif_disabled), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.relaseReminder:
                if (switchNewRelease.isChecked())
                {
                    sharedPreference.saveBoolean(SharedPreference.STATUS_NEW_RELEASE_NOTIF, true);
                    settingReceiver.setNewRelease(this, "08:00", settingReceiver.EXTRA_MESSAGE);
                    Toast.makeText(this, getString(R.string.new_release_enabled), Toast.LENGTH_SHORT).show();
                }else
                {
                    sharedPreference.saveBoolean(SharedPreference.STATUS_NEW_RELEASE_NOTIF, false);
                    settingReceiver.cancelNewReleaseNotif(this);
                    Toast.makeText(this, getString(R.string.new_release_disabled), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void checkReminder()
    {
        if (sharedPreference.getStatusDaily())
        {
            switchDaily.setChecked(true);
        }else
        {
            switchDaily.setChecked(false);
        }

        if (sharedPreference.getStatusNewRelease())
        {
            switchNewRelease.setChecked(true);
        }else
        {
            switchNewRelease.setChecked(false);
        }
    }
}
