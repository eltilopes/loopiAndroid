package br.com.aio.view;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RelativeLayout;

import br.com.aio.utils.ViewUtils;

/**
 * Created by elton on 11/10/2017.
 */

public class ProgressDialogAsyncTask extends AsyncTask<Boolean, Integer, Void> {

    public interface IProgressActivity {

        void executaProgressoDialog();
        boolean isAddedValidation();
        void onPostExecute();

    }

    Activity activity;
    RelativeLayout layout;
    IProgressActivity progressActivity;

    public ProgressDialogAsyncTask(Activity activity, RelativeLayout layout, IProgressActivity progressActivity) {
        super();
        this.activity = activity;
        this.progressActivity = progressActivity;
        this.layout = layout;
    }

    public ProgressDialogAsyncTask(Activity activity, IProgressActivity progressActivity) {
        super();
        this.activity = activity;
        this.progressActivity = progressActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(this.activity != null)
            ViewUtils.escondeTeclado(this.activity);
        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        if (this.layout != null)
            this.layout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(Boolean... params) {
        if(progressActivity != null)
            progressActivity.executaProgressoDialog();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        boolean viewAtual = progressActivity.isAddedValidation();
        if(this.activity != null)
            this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        if(viewAtual && layout != null)
            layout.setVisibility(View.GONE);
        if(progressActivity != null)
            progressActivity.onPostExecute();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
