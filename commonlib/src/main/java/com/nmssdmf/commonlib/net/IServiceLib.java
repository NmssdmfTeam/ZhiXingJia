package com.nmssdmf.commonlib.net;

import com.nmssdmf.commonlib.bean.Upload;
import com.nmssdmf.commonlib.bean.UploadImage;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author huscarter@163.com
 * @title
 * @description
 * @date 7/10/17
 */

public interface IServiceLib {
    /**
     * 上传图片
     *
     * @param body
     * @return
     */
    @Multipart
    @POST("/image/upload")
    Observable<Upload> uploadImage(@Part MultipartBody.Part body);
}
