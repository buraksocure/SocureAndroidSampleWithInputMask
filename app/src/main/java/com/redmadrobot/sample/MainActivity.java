package com.redmadrobot.sample;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.redmadrobot.inputmask.MaskedTextChangedListener;
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy;
import com.socure.idplus.devicerisk.androidsdk.Interfaces;
import com.socure.idplus.devicerisk.androidsdk.model.*;
import com.socure.idplus.devicerisk.androidsdk.sensors.DeviceRiskManager;
import com.socure.idplus.devicerisk.androidsdk.sensors.SocureSigmaDevice;
import com.socure.idplus.devicerisk.androidsdk.uilts.SocureFingerPrintContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Home screen for the sample app.
 *
 * @author taflanidi
 */
public final class MainActivity extends AppCompatActivity implements SocureSigmaDevice.DataUploadCallback {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupPrefixSample();
        setupSuffixSample();
SocureFingerPrintContext gg = new SocureFingerPrintContext.Home();

        SocureSigmaDevice sigma = new SocureSigmaDevice();
        SocureSigmaDeviceConfig config  = new SocureSigmaDeviceConfig("6c5f08ad-6d12-4a79-b272-0abb26d9a053",false,"https://dvnfo.com",this);
        SocureFingerPrintOptions options  = new SocureFingerPrintOptions(false,gg,null);



        sigma.fingerPrint(config, options, this);



        }

    private void setupPrefixSample() {
        final EditText editText = findViewById(R.id.prefix_edit_text);
        final CheckBox checkBox = findViewById(R.id.prefix_check_box);
        final List<String> affineFormats = new ArrayList<>();
        affineFormats.add("8 ([000]) [000]-[00]-[00]");

        final MaskedTextChangedListener listener = MaskedTextChangedListener.Companion.installOn(
                editText,
                "+7 ([000]) [000]-[00]-[00]",
                affineFormats,
                AffinityCalculationStrategy.PREFIX,
                new MaskedTextChangedListener.ValueListener() {
                    @Override
                    public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue, @NonNull String formattedText) {
                        logValueListener(maskFilled, extractedValue, formattedText);
                        checkBox.setChecked(maskFilled);
                    }
                }
        );

        editText.setHint(listener.placeholder());
    }




    private void setupSuffixSample() {
        final EditText editText = findViewById(R.id.suffix_edit_text);
        final CheckBox checkBox = findViewById(R.id.suffix_check_box);
        final List<String> affineFormats = new ArrayList<>();
        affineFormats.add("+7 ([000]) [000]-[00]-[00]#[000]");

        final MaskedTextChangedListener listener = MaskedTextChangedListener.Companion.installOn(
                editText,
                "+7 ([000]) [000]-[00]-[00]",
                affineFormats,
                AffinityCalculationStrategy.WHOLE_STRING,
                new MaskedTextChangedListener.ValueListener() {
                    @Override
                    public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue, @NonNull String formattedText) {
                        logValueListener(maskFilled, extractedValue, formattedText);
                        checkBox.setChecked(maskFilled);
                    }
                }
        );

        editText.setHint(listener.placeholder());
    }

    private void logValueListener(boolean maskFilled, @NonNull String extractedValue, @NonNull String formattedText) {
        final String className = MainActivity.class.getSimpleName();
        Log.d(className, extractedValue);
        Log.d(className, String.valueOf(maskFilled));
        Log.d(className, formattedText);
    }

    @Override
    public void dataUploadFinished(@NonNull SocureFingerprintResult socureFingerprintResult) {
        Log.d("socure", socureFingerprintResult.toString());
    }

    @Override
    public void onError(@NonNull SocureSigmaDevice.SocureSigmaDeviceError socureSigmaDeviceError, @Nullable String s) {
        Log.d("socure", socureSigmaDeviceError.toString());

    }
}
