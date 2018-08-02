package hajjcare.hajj.com.hajjcare.control;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import hajjcare.hajj.com.hajjcare.R;
import hajjcare.hajj.com.hajjcare.models.exceptios.ApplicationException;
import hajjcare.hajj.com.hajjcare.views.activity.BaseActivity;

/**
 * Created by amra on 2/2/2018.
 */

public class UIController {
    private static UIController instance;
    DisplayMetrics mDisplayMetrics;
    private ImageLoader imageLoader;


    public static UIController getInstance() {
        if (instance == null) {
            instance = new UIController();
        }
        return instance;
    }

    private UIController() {
        mDisplayMetrics = new DisplayMetrics();
        mDisplayMetrics = HajjCareApplicationClass.getStarSmilesAppContext().getResources()
                .getDisplayMetrics();
        imageLoader = ImageLoader.getInstance();
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder
                (HajjCareApplicationClass.getStarSmilesAppContext());
        builder.defaultDisplayImageOptions(defaultOptions);
        imageLoader.init(builder.build());


    }

    public static void adjustDialogWidth(Context context, Dialog dialog) {
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int dialogWidth = (int) (screenWidth * .9f);
        dialog.getWindow().setLayout(dialogWidth, dialog.getWindow().getAttributes().height);
    }

    public static void handleActivtyResponseError(Throwable error,
                                                  final Activity activity, final boolean closeCurrentActivity, boolean showErrordialog) {

        if (showErrordialog) {
            String message;
            if (error instanceof ApplicationException) {
                message = ((ApplicationException) error).getUserMessage();
            } else {
                message = HajjCareApplicationClass.getInstance().getCurrentShowenActivity().getString(R.string.str_connection_error);
            }
            displayAlert(/*HajjCareApplicationClass.getInstance().getCurrentShowenActivity().getString(R.string.str_error) +
                            HajjCareApplicationClass.getInstance().getCurrentShowenActivity().getString(R.string.str_error_from)
                            + */"\n"+HajjCareApplicationClass.getInstance().getCurrentShowenActivity().getString(R.string.str_error)  + message, HajjCareApplicationClass.getInstance().getCurrentShowenActivity(),
                    HajjCareApplicationClass.getInstance().getApplicationContext().getString(R.string.btn_ok_txt), "", true);

        }
    }

    public static void displayAlert(String message, Context context, String yesBtnLabel, String noBtnLabel, final boolean dismissDialoginYesButton) {
        displayAlert(message, null,
                null, context, yesBtnLabel, null, true, false);

    }

    public static void displayAlert(String message, final Runnable yesAction,
                                    final Runnable noAction, Context context, String yesBtnLabel,
                                    String noBtnLabel, final boolean dismissDialoginYesButton, final boolean dismissDialoginNoButton) {

        final Dialog alert = new Dialog(context);
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.setContentView(R.layout.custom_dialog_layout);
        adjustDialogWidth(context, alert);
        TextView title = (TextView) alert
                .findViewById(R.id.custom_dialog_title_id);
        title.setText(R.string.app_name);
        TextView messageTXT = (TextView) alert
                .findViewById(R.id.custom_dialog_message_id);
        messageTXT.setText(message);


        alert.setCancelable(false);

        Button ok_btn = (Button) alert
                .findViewById(R.id.custom_dialog_okBtn_id);
        ok_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (yesAction != null)
                    yesAction.run();
                if (dismissDialoginYesButton) {
                    alert.dismiss();
                }

            }
        });

        Button cancel_btn = (Button) alert
                .findViewById(R.id.custom_dialog_cancelBtn_id);

        if (noBtnLabel == null) {
            cancel_btn.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ok_btn
                    .getLayoutParams();
            params.width = 100;
            ok_btn.setLayoutParams(params);
        }
        cancel_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (noAction != null)
                    noAction.run();
                if (dismissDialoginNoButton) {
                    alert.dismiss();
                }
            }
        });

        ok_btn.setAllCaps(true);
        cancel_btn.setAllCaps(true);

        alert.show();

    }

    public void showLogOutDialog(final Activity activity, final boolean closeActivity) {

        final Dialog innerAlertDialog = new Dialog(activity);
        innerAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        innerAlertDialog.setContentView(R.layout.custom_dialog_layout);
        adjustDialogWidth(activity, innerAlertDialog);
        TextView title = (TextView) innerAlertDialog
                .findViewById(R.id.custom_dialog_title_id);
        title.setText(R.string.app_name);
        TextView messageTXT = (TextView) innerAlertDialog
                .findViewById(R.id.custom_dialog_message_id);
        messageTXT.setText(activity.getString(R.string.logout_message));
        innerAlertDialog.setCancelable(false);
        Button ok_btn = (Button) innerAlertDialog
                .findViewById(R.id.custom_dialog_okBtn_id);
        ok_btn.setText(R.string.logout);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


//                Controller.getInstance().logout();
                if (closeActivity) {
                    activity.finish();
                }
            }

        });

        Button cancel_btn = (Button) innerAlertDialog
                .findViewById(R.id.custom_dialog_cancelBtn_id);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                innerAlertDialog.dismiss();


            }
        });

        ok_btn.setAllCaps(true);
        cancel_btn.setAllCaps(true);

        innerAlertDialog.show();

    }


    public void showChangLanguageDialog(final BaseActivity
                                                activity, final boolean closeActivity) {

        final Dialog innerAlertDialog = new Dialog(activity);
        innerAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        innerAlertDialog.setContentView(R.layout.custom_dialog_layout);
        adjustDialogWidth(activity, innerAlertDialog);
        TextView title = (TextView) innerAlertDialog
                .findViewById(R.id.custom_dialog_title_id);
        title.setText(R.string.app_name);
        TextView messageTXT = (TextView) innerAlertDialog
                .findViewById(R.id.custom_dialog_message_id);
        messageTXT.setText(activity.getString(R.string.setting_message));
        innerAlertDialog.setCancelable(false);
        Button ok_btn = (Button) innerAlertDialog
                .findViewById(R.id.custom_dialog_okBtn_id);
        ok_btn.setText(R.string.btn_ok_txt);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (closeActivity) {
//                    Controller.getInstance().changeLanguage(activity);
                    innerAlertDialog.dismiss();
                }
            }

        });

        Button cancel_btn = (Button) innerAlertDialog
                .findViewById(R.id.custom_dialog_cancelBtn_id);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                innerAlertDialog.dismiss();


            }
        });

        ok_btn.setAllCaps(true);
        cancel_btn.setAllCaps(true);

        innerAlertDialog.show();

    }

    @SuppressLint("MissingPermission")
    public void doCall(BaseActivity activity) {
        String number = "+920006776";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse( "tel:" + number));
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            activity.startActivity(intent);

        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);
        }

    }


}
