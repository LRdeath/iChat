package io.github.weizc.italker.push.frags.assist;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.weizc.itakler.common.app.Application;
import io.github.weizc.italker.push.R;
import io.github.weizc.italker.push.frags.media.GalleryFragment;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class PermissionsFragment extends BottomSheetDialogFragment implements EasyPermissions.PermissionCallbacks {

    private final int RC_CAMERA_AND_LOCATION = 0x001;
    public PermissionsFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new GalleryFragment.TransStatusBottomSheetDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_permissions, container, false);
        refreshView(root);
        root.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requiresAllPermission();
            }
        });
        return root;
    }

    /**
     * 刷新权限获取状态
     *
     * @param root 根布局
     */
    private void refreshView(View root) {
        if(root==null) return;
        Context context = getContext();
        root.findViewById(R.id.im_state_permission_network)
                .setVisibility(hasNetworkPermission(context) ? View.VISIBLE : View.GONE);

        root.findViewById(R.id.im_state_permission_read)
                .setVisibility(hasReadPermission(context) ? View.VISIBLE : View.GONE);

        root.findViewById(R.id.im_state_permission_write)
                .setVisibility(hasWritePermission(context) ? View.VISIBLE : View.GONE);

        root.findViewById(R.id.im_state_permission_record_audio)
                .setVisibility(hasAudioPermission(context) ? View.VISIBLE : View.GONE);
    }

    /**
     * 获取是否有外部存储写入权限
     *
     * @param context 上下文
     * @return True为有
     */
    private static boolean hasWritePermission(Context context) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 获取是否有外部读取权限
     *
     * @param context 上下文
     * @return True为有
     */
    private static boolean hasReadPermission(Context context) {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE
        };

        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 获取是否有网络权限
     *
     * @param context 上下文
     * @return True为有
     */
    private static boolean hasNetworkPermission(Context context) {
        String[] perms = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE
        };

        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 获取是否有录音权限
     *
     * @param context 上下文
     * @return True为有
     */
    private static boolean hasAudioPermission(Context context) {
        String[] perms = {Manifest.permission.RECORD_AUDIO};

        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 静态方法，外部调用
     * 判断是否显示权限申请界面
     * @param context
     * @param fragmentManager
     * @return
     */
    public static boolean hadAllPermission(Context context, FragmentManager fragmentManager) {
        boolean hasAll = hasAudioPermission(context) &&
                hasNetworkPermission(context)
                && hasReadPermission(context)
                && hasWritePermission(context);
        if (!hasAll){
            show(fragmentManager);
        }
        return hasAll;
    }


    /**
     * 申请所有权限
     */
    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void requiresAllPermission() {
        String[] perms = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };
        Context context  = getContext();
        if (EasyPermissions.hasPermissions(context, perms)) {
            Application.showToast(R.string.label_permission_ok);
            //Fragment 中调用getView得到根布局，前提是在onCreateView方法后
            refreshView(getView());
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.title_assist_permissions),
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }
    //私有的show方法
    private static void show(FragmentManager fragmentManager) {
        //调用BottomoSheetDialogFragment以及准备好的显示方法
        new PermissionsFragment()
                .show(fragmentManager,PermissionsFragment.class.getName());
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //如果有权限没有申请成功的存在，则弹出Dialog框，用户点击后去到设置界面自己打开权限
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            new AppSettingsDialog
                    .Builder(this)
                    .build()
                    .show();
        }
    }

    /**
     * 权限申请的时候回调的方法，在这个方法中把对应的权限申请状态交给EasyPermissions框架
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 传递对应的参数，并且告知接收权限的处理者是我自己
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
