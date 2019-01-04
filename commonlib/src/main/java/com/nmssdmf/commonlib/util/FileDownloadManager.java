package com.nmssdmf.commonlib.util;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.nmssdmf.commonlib.util.UpdateManager.DOWNLOAD_TYPE_APP;

/**
 * @author huscarter@163.com.
 * @title 权限处理类
 * @description 本类是针对android6.0以上的版本, 实施的权限处理.
 * 尤其针对华为android6.0的系统,因为他的文件读写权限不能获取.
 * @date 2015/9/7.
 */
public class FileDownloadManager {
    private final static String TAG = FileDownloadManager.class.getSimpleName();

    private final static Pattern url_pattern = Pattern.compile("^[a-zA-z]+:\\/\\/[\\w\\.\\/\\-\\/@]+\\.[\\w]+$");

    private static FileDownloadManager instance;
    private ProgressDialog progressbar;
    private ScheduledExecutorService scheduled;
    private Future<?> future;
    private DownloadReceiver receiver;

    private int and_fix_count = 0;//下载热修复文件的数目,因为热修复文件要下载两个

    private FileDownloadManager() {
        //
    }


    public static FileDownloadManager getInstance() {
        if (instance == null) {
            synchronized (FileDownloadManager.class) {
                if (instance == null) {
                    instance = new FileDownloadManager();
                }
            }
        }
        return instance;
    }

    /**
     * @param context
     * @param url
     * @param show_progressbar 是否显示进度条
     * @param need_open
     * @param type 文件下载的类型
     * @param downOverListener 下载完成之后的回调,目前只有热修复需要用到
     * @return
     */
    public long download(Context context, String url, boolean show_progressbar, boolean need_open, int type, UpdateManager.DownOverListener downOverListener) {
        if (null == url || "".equals(url) || "null".equals(null)) {
            return -1L;
        }
        String name;
        if (url_pattern.matcher(url).matches()) { // 是正规的url，自动识别下载的文件名
            // suffix
            String suffix = url.substring(url.lastIndexOf("."), url.length());
            name = url.substring(url.lastIndexOf("/") + 1, url.length() - suffix.length()) + suffix;
        } else {
            name = System.currentTimeMillis() + ".temp";
        }
        return download(context, Uri.parse(url), name, show_progressbar, need_open, type, downOverListener);
    }

    /**
     * 下载的方法</br>
     * 传入url系统自动下载，会根据url自动识别所下载的文件名称，也可以自己手动指定文件名称</br>
     * 下载保存路径为sdcard的Download文件夹下</br>
     *
     * @param context
     * @param uri              下载的uri
     * @param name             下载文件保存的名称（包含后缀），如test.apk
     * @param show_progressbar 是否展示下载dialog
     * @return 下载文件的id
     */
    public long download(Context context, Uri uri, String name, boolean show_progressbar, boolean need_open, int type, UpdateManager.DownOverListener downOverListener) {
        JLog.i(TAG, "name:" + name);
        /**
         * 华为H60手机可能会报Unknown URL content错误
         */
        String title = "下载";
        String content = "";
        String dir_path = Environment.DIRECTORY_DOWNLOADS;
        if (type == DOWNLOAD_TYPE_APP) {//app文件下载
            content = "版本更新";
            dir_path = Environment.DIRECTORY_DOWNLOADS;
        }

        long download_id = -1;
        try {
            // Download文件夹判断
            File dir = Environment.getExternalStoragePublicDirectory(dir_path);
            File file = new File(Environment.getExternalStoragePublicDirectory(dir_path) + "/" + name);

            if (!dir.isDirectory() || !dir.exists()) {
                dir.mkdirs();
            }
            // 删除已有的文件
            if (file.exists()) {
                file.delete();
            }

            DownloadManager download_manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(uri);

            // Set the name of the file you will download.
            //表示设置下载地址为sd卡的(参数一)文件夹，文件名为参数二
            request.setDestinationInExternalPublicDir(dir_path, name);
            // Set the title of download component.
            request.setTitle(title);
            // Set the content of download component.
            request.setDescription(content);
            /**
             * 表示下载允许的网络类型，默认在任何网络下都允许下载,
             * NETWORK_MOBILE、NETWORK_WIFI、NETWORK_BLUETOOTH,
             * 如果只允许wifi下载，而当前网络为3g，则下载会等待
             */
            //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
            //表示允许MediaScanner扫描到这个文件，默认不允许
            request.allowScanningByMediaScanner();

            setMineType(request, name);

            if (show_progressbar) {
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
                request.setVisibleInDownloadsUi(false);
                showProgressBar(context);
            } else {
                // 在状态栏是否显示,默认只显示下载中通知
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                request.setVisibleInDownloadsUi(true);
                if (type == DOWNLOAD_TYPE_APP) {//app文件下载
                    Toast.makeText(context, "正在下载,请稍后", Toast.LENGTH_LONG).show();
                }
            }

            // 下载文件在系统里的id，用于文件读取和删除操作
            download_id = download_manager.enqueue(request);
            // 监听用于展示进度
            registerScheduled(context, download_id);
            // 监听下载成功广播
            registerReceiver(Environment.getExternalStoragePublicDirectory(dir_path) + "/" + name, context, need_open, type, downOverListener);
        } catch (Exception e) {
            e.printStackTrace();
            if (type == DOWNLOAD_TYPE_APP) {//app文件下载
                Toast.makeText(context, "抱歉下载失败了，请到应用市场上更新", Toast.LENGTH_LONG).show();
            }
        } finally {
            return download_id;
        }
    }

    /**
     * 设置下载文件的mineType,
     * 因为下载管理Ui中点击某个已下载完成文件及下载完成点击通知栏提示都会根据mimeType去打开文件，
     * 所以我们可以利用这个属性。比如上面设置了mimeType为application/package.name，
     * 我们可以同时设置某个Activity的intent-filter为application/package.name，用于响应点击的打开文件
     */
    private void setMineType(DownloadManager.Request request, String name) {
        if (!name.isEmpty()) {
            if (name.endsWith(".apk")) {
                request.setMimeType("application/vnd.android.package-archive");
            } else if (name.endsWith(".xls")) {
                //
            }
        }
    }

    /**
     * 监听用于展示进度
     *
     * @param context
     * @param download_id
     */
    private void registerScheduled(final Context context, long download_id) {
        unregisterScheduled();
        scheduled = Executors.newScheduledThreadPool(1);
        /**
         * Runnable command,long initialDelay, long period, TimeUnit unit
         */
        future = scheduled.scheduleAtFixedRate(new DownloadScheduled(new DownloadHandler(), context, download_id), 100, 200, TimeUnit.MILLISECONDS);
    }

    /**
     * 取消进度监
     */
    private void unregisterScheduled() {
        if (future != null && !future.isCancelled())
            future.cancel(true);

        if (scheduled != null && !scheduled.isShutdown()) {
            scheduled.shutdown();
        }
    }

    private void registerReceiver(String file_path, Context context, boolean need_open, int type, UpdateManager.DownOverListener downOverListener) {
        JLog.i(TAG, "registerReceiver");
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        receiver = new DownloadReceiver(file_path, need_open, type, downOverListener);
        context.registerReceiver(receiver, filter);
    }

    private void unregisterReceiver(Context context) {
        JLog.i(TAG, "unregisterReceiver");
        try {
            if (receiver != null) {
                context.unregisterReceiver(receiver);
                receiver = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示进度条
     *
     * @param context
     */
    private void showProgressBar(Context context) {
        progressbar = new ProgressDialog(context);
        progressbar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressbar.setCancelable(false);

        progressbar.setTitle("正在下载,请稍后");
        progressbar.setMax(100);
        progressbar.setProgress(0);
        progressbar.show();
    }

    /**
     * 用来监听下载进度
     */
    private class DownloadScheduled implements Runnable {
        private Handler handler;
        private DownloadManager download_manager;
        private DownloadManager.Query download_query;
        private Cursor cursor;

        public DownloadScheduled(Handler handler, Context context, long download_id) {
            this.handler = handler;
            download_manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            download_query = new DownloadManager.Query().setFilterById(download_id);
        }

        @Override
        public void run() {


            cursor = download_manager.query(download_query);
            cursor.moveToFirst();
            int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            int progress = (bytes_downloaded * 100) / bytes_total;
            JLog.i(TAG, "run progress = " + progress + "\nbytes_total = " + bytes_total);
            if (handler != null) {
                handler.sendEmptyMessage(progress);
            }
            cursor.close();
        }
    }

    /**
     * 进度更新handler
     */
    private class DownloadHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if (progressbar != null) {
                progressbar.setProgress(msg.what);
            }
        }
    }

    /**
     * 下载事件监听
     */
    private class DownloadReceiver extends BroadcastReceiver {
        private boolean need_open = false;
        private String file_path;
        private int type;
        private UpdateManager.DownOverListener down_over_listener;

        public DownloadReceiver(String file_path, boolean need_open, int type, UpdateManager.DownOverListener downOverListener) {
            this.need_open = need_open;
            this.file_path = file_path;
            this.type = type;
            this.down_over_listener = downOverListener;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            JLog.i(TAG, "onReceive file_path:" + file_path + ",need_open:" + need_open);
            switch (intent.getAction()) {
                case DownloadManager.ACTION_DOWNLOAD_COMPLETE:
                    if (progressbar != null) {
                        progressbar.dismiss();
                        progressbar = null;
                    }

                    unregisterScheduled();
                    unregisterReceiver(context);

                    if (need_open) {
                        try {
//                        long download_id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//                        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                            if (type == DOWNLOAD_TYPE_APP) {//下载apk
                                and_fix_count = 0;
                                installApk(new File(file_path), context);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    break;
            }

        }
    }

    /**
     * 安装apk
     */
    private void installApk(File file, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//兼容安卓7.0版本
            Uri uri = FileProvider.getUriForFile(context, "com.zhihangjia.project.FileProvider", file); //修改  downloadFile 来源于上面下载文件时保存下来的
            //  BuildConfig.APPLICATION_ID + ".fileprovider" 是在manifest中 Provider里的authorities属性定义的值
            Intent installIntent = new Intent(Intent.ACTION_VIEW);
            installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //临时授权
            installIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(installIntent);
        } else {
            Intent intent = new Intent();
            //执行动作
            intent.setAction(Intent.ACTION_VIEW);
            //执行的数据类型
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
    }
}
