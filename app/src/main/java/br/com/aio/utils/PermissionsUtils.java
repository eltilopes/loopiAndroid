package br.com.aio.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import br.com.aio.R;

/**
 * Created by elton on 21/09/2017.
 */

public class PermissionsUtils {

    public static final int PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int PERMISSIONS_REQUEST_CAMERA_ID = 96;
    public static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_ID = 98;
    public static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_ID = 97;

    public static final int PICKFILE_RESULT_CODE = 1;

    public static final String ACESSO_CAMERA_NECESSARIO = "É necessário acesso a câmera.";
    public static final String ACESSO_CAMERA_PERMITIDO = "Acesso a câmera permitido";
    public static final String ACESSO_LOCALIZACAO_NECESSARIO = "É necessário acesso ao GPS.";
    public static final String ACESSO_LOCALIZACAO_PERMITIDO = "Acesso ao GPS permitido";
    public static final String ACESSO_LER_ARMAZENAMENTO_NECESSARIO = "É necessário acesso ao armazenamento.";
    public static final String ACESSO_LER_ARMAZENAMENTO_PERMITIDO = "Acesso ao armazenamento permitido";
    public static final String ACESSO_GRAVAR_ARMAZENAMENTO_NECESSARIO = "É necessário acesso para gravar no armazenamento.";
    public static final String ACESSO_GRAVAR_ARMAZENAMENTO_PERMITIDO = "Acesso para gravar no armazenamento permitido";

    private PermissionsUtils() {
    }

    private static String getMessageDialog(int idPermissao) {
        String tituloPermissao = "";
        switch (idPermissao){
            case PERMISSIONS_REQUEST_CAMERA_ID:
                tituloPermissao = ACESSO_CAMERA_NECESSARIO;
                break;
            case PERMISSIONS_REQUEST_LOCATION:
                tituloPermissao = ACESSO_LOCALIZACAO_NECESSARIO;
                break;
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_ID:
                tituloPermissao = ACESSO_GRAVAR_ARMAZENAMENTO_NECESSARIO;
                break;
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_ID:
                tituloPermissao = ACESSO_LER_ARMAZENAMENTO_NECESSARIO;
                break;
        }
        return tituloPermissao;
    }

    private static int getIconDialog(int idPermissao) {
        int iconDialog = R.drawable.ic_info;
        switch (idPermissao){
            case PERMISSIONS_REQUEST_CAMERA_ID:
                iconDialog = R.drawable.ic_camera;
                break;
            case PERMISSIONS_REQUEST_LOCATION:
                iconDialog = R.drawable.ic_edit_location;
                break;
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_ID:
                iconDialog = R.drawable.ic_storage;
                break;
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_ID:
                iconDialog = R.drawable.ic_attach_file_branco;
                break;
        }
        return iconDialog;
    }

    public static boolean checkPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (!checkPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isDeviceLocationGranted(Context context) {
        return checkPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public static boolean isDeviceReadExternalStorageGranted(Context context) {
        return checkPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public static boolean isDeviceWriteExternalStorageGranted(Context context) {
        return checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static boolean isDeviceCameraGranted(Context context) {
        return checkPermission(context, Manifest.permission.CAMERA);
    }

    public static void requestPermissions(Object o, final int permissionId, String permission, final String... permissions) {
        if (o instanceof Activity) {
            final Activity activity = (AppCompatActivity) o;
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                // Explain to the user why permission is required, then request again
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
                TextView title = new TextView(activity);
                title.setText("PERMITIR AO APP");
                title.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
                title.setPadding(30, 10, 10, 10);
                title.setCompoundDrawablesWithIntrinsicBounds(getIconDialog(permissionId), 0, 0, 0);
                title.setCompoundDrawablePadding(20);
                title.setGravity(Gravity.CENTER_HORIZONTAL);
                title.setTextColor(activity.getResources().getColor(R.color.textColorPrimary));
                title.setTextSize(20);
                builder.setMessage(getMessageDialog(permissionId))
                        .setCustomTitle(title)
                        .setCancelable(false)
                        .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(activity, permissions, permissionId);}});
                ;
                AlertDialog alert = builder.create();
                alert.show();

            } else {
                ActivityCompat.requestPermissions((AppCompatActivity) o, permissions, permissionId);
            }
        }
    }
}
