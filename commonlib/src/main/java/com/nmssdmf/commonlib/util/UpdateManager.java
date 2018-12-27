package com.nmssdmf.commonlib.util;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nmssdmf.commonlib.R;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.UpdateInfo;
import com.nmssdmf.commonlib.config.BaseConfig;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.net.IServiceLib;

import io.reactivex.disposables.CompositeDisposable;


/**
 * @author huscarter@163.com.
 * @title app更新工具类
 * @description
 * @date 2015/9/7.
 */
public class UpdateManager {
    private static final String TAG = UpdateManager.class.getSimpleName();
    private static UpdateManager instance;
    private UpdateInfo detail;
    private AlertDialog dialog = null;

    //以下区分下载的文件类型
    private int type = DOWNLOAD_TYPE_APP;
    /**
     * app下载
     */
    public static final int DOWNLOAD_TYPE_APP = 1;
    /**
     * 热修复文件下载
     */
    public static final int DOWNLOAD_TYPE_ANDFIX = 2;
    /**
     * 普通文件下载
     */
    public static final int DOWNLOAD_TYPE_FILE = 3;

    private DownOverListener down_over_listener;


    private UpdateManager() {
        //
    }

    public static UpdateManager getInstance() {
        if (instance == null) {
            synchronized (UpdateManager.class) {
                if (instance == null) {
                    instance = new UpdateManager();
                }
            }
        }
        return instance;
    }

    /**
     * 检查版本
     *
     * @param should_show 是否显示更新提示,比如在MainActivity他是不需要显示的
     * @param down_type   下载的类型,在UpdateManager中有常量 app文件下载,热修复文件下载,普通文件下载
     */
    public void checkVersion(Activity activity, final boolean should_show, int down_type) {
        type = down_type;
        JLog.d(TAG, "type = " + type);
        if (type == DOWNLOAD_TYPE_APP) {//app文件下载
            downloadAPkFile(activity, should_show);
        }

    }

    /**
     * apk文件下载
     */
    private void downloadAPkFile(final Activity activity, final boolean should_show) {
        if (CommonUtils.isEmpty(PreferenceUtil.getString(PrefrenceConfig.TOKEN, ""))) {
            return;
        }

        final String old_version = CommonUtils.getAppVersion(activity);
        if (CommonUtils.isEmpty(old_version)) {
            return;
        }
        final int lever_version_local = Integer.valueOf(old_version.replace(".", ""));
        try {
            HttpUtils.doHttp(new CompositeDisposable(),
                    RxRequest.create(IServiceLib.class, HttpVersionConfig.API_APP_UPDATE_INFO).getUpdateInfo("android"),
                    new ServiceCallback<BaseData<UpdateInfo>>() {
                        @Override
                        public void onError(Throwable error) {

                        }

                        @Override
                        public void onSuccess(BaseData<UpdateInfo> updateInfoBaseData) {

                            detail = updateInfoBaseData.getData();

                            if ("0".equals(detail.getIs_mandatory())) {// 推荐更新
                                PreferenceUtil.setStringValue(PrefrenceConfig.IS_VERSION_UPDATE, "0"); // 0说明之前检查过，不需要再检查。1需要再检查
                            }
                            PreferenceUtil.setLongValue(PrefrenceConfig.NOT_UPDATE_TIME, System.currentTimeMillis());// 每隔一段时间判断是否需要检查更新

                            if (detail.getVersion() != null) {
                                try {
                                    int lever_version_online = Integer.valueOf(detail.getVersion().replace(".", ""));

                                    //大于
                                    if (lever_version_online > lever_version_local) {
                                        showUpdateDialog(activity);
                                    } else {
                                        if (should_show) {
                                            if (activity == null) {
                                                JLog.i("UpdateManager", " activity is null");
                                            } else {
                                                Toast.makeText(activity, "已经是最新版,无需更新", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                } catch (Exception e) {

                                }
                            }
                        }

                        @Override
                        public void onDefeated(BaseData<UpdateInfo> updateInfoBaseData) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 普通文件下载
     */
//    private void downloadNomoreFile(Activity activity) {
//        FileDownloadManager.getInstance().download(activity, Config.XML_URL + "/file", false, true, type, down_over_listener);
//    }


    /**
     *
     */
    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    public boolean isForceUpdate() {
        return detail != null && detail.equals("1");
    }

    /**
     * 弹出对话框进行提示更新
     */
    private void showUpdateDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LinearLayout layout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.alert_dialog_updata_version, null);
        builder.setView(layout);
        dialog = builder.create();

        TextView left_update_version = (TextView) layout.findViewById(R.id.left_update_version);//暂不更新
        TextView right_update_version = (TextView) layout.findViewById(R.id.right_update_version);//立即跟新

        if (detail.getIs_mandatory().equals("1")) { // 1:强制更新,0:推荐更新
            left_update_version.setVisibility(View.GONE);
            dialog.setCancelable(false);
        } else { // 推荐更新
            left_update_version.setVisibility(View.VISIBLE);
        }

        TextView update_version = (TextView) layout.findViewById(R.id.update_version);
        update_version.setText("文件版本: " + detail.getVersion());
        TextView update_size = (TextView) layout.findViewById(R.id.update_size);
        update_size.setText("文件大小: " + detail.getFilesize());
        TextView update_content = (TextView) layout.findViewById(R.id.update_content);
        String s_updatecontent = detail.getUpdate_content();
//        String s_updatecontent = "";
//        for (int i = 0; i < list_updatecontent.size(); i++) {
//            s_updatecontent = s_updatecontent + list_updatecontent.get(i) + "\n";
//        }
        update_content.setText(s_updatecontent);

        left_update_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        right_update_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermissionCompat.getInstance().checkStoragePermission(activity)) {
                    return;
                }
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                //detail.getIs_mandatory().equals("1")
                FileDownloadManager.getInstance().download(activity, BaseConfig.XML_URL + "/zhihangjia.apk",
                        "1".equals(detail.getIs_mandatory()), true, type, down_over_listener);
            }
        });
        dialog.show();
    }

    /**
     *
     */
    public void releaseActivity() {
        //
    }

    /**
     * 文件下载成功接口
     */
    public interface DownOverListener {
        void downOver(int code);//1:下载成功  0:下载失败
    }

    public DownOverListener getDown_over_listener() {
        return down_over_listener;
    }

    public void setDown_over_listener(DownOverListener down_over_listener) {
        this.down_over_listener = down_over_listener;
    }
}
