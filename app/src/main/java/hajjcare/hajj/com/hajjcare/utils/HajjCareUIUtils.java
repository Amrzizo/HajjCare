package hajjcare.hajj.com.hajjcare.utils;

import android.app.ProgressDialog;
import android.content.Context;

import hajjcare.hajj.com.hajjcare.R;
import hajjcare.hajj.com.hajjcare.control.HajjCareApplicationClass;
import hajjcare.hajj.com.hajjcare.control.UIController;

/**
 * Created by amra on 2/2/2018.
 */

public class HajjCareUIUtils {
    public  static ProgressDialog progressDialog;
    static Context context;
    private String title, message;
    private boolean cancellable;

    public static void hideLoading() {

        if(progressDialog != null)
            progressDialog.dismiss();
    }

    public static void showLoading( String title, String message, boolean cancellable) {

        if(progressDialog == null ||(progressDialog!= null && !progressDialog.isShowing())) {
            progressDialog = ProgressDialog.show(HajjCareApplicationClass.getInstance().getCurrentShowenActivity(), title, message, true, cancellable, null);
            progressDialog.setContentView(R.layout.loading_dialog_layout);
            progressDialog.setCanceledOnTouchOutside(false);
            UIController.adjustDialogWidth(HajjCareApplicationClass.getInstance().getCurrentShowenActivity(), progressDialog);

        }
    }


}
