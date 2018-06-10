package com.example.wkddu.android_project_mcm;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class ToClipboardListener extends Service implements ClipboardManager.OnPrimaryClipChangedListener{
    ClipboardManager clipboardManager;
    @Override
    public void onCreate() {
        super.onCreate();
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(this);
        Log.v("clipboard ready","ready");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onPrimaryClipChanged() {
        Log.v("clipboard", "clip data call");
        if (clipboardManager != null && clipboardManager.getPrimaryClip() != null) {
            ClipData data = clipboardManager.getPrimaryClip();

            // 한번의 복사로 복수 데이터를 넣었을 수 있으므로, 모든 데이터를 가져온다.
            int dataCount = data.getItemCount();
            for (int i = 0 ; i < dataCount ; i++) {
                Log.v("clipboard", "clip data - item : "+ i + "번째 " + data.getItemAt(i).coerceToText(this));
            }
        } else {
            Log.e("Test", "No Manager or No Clip data");
        }
    }


}
