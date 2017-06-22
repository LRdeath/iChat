package io.github.weizc.italker.factory.net;

import android.text.format.DateFormat;
import android.util.Log;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.util.Date;

import io.github.weizc.itakler.common.utils.HashUtil;
import io.github.weizc.italker.factory.Factory;

/**
 * @author Vzc
 * @version 1.0.0
 * @Date 2017/6/22.
 */

public class UploadHelper {
    private static final String ENDPOINT = "oss-cn-beijing.aliyuncs.com";
    private static final String BUCKETNAME = "vzer";
    private static final String TAG = UploadHelper.class.getSimpleName();

    private static final   OSS getOssClient() {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAI7rn5227Q7aGB", "PNHGOPpP4tFTXJsbnaJ97wsGRJdsEk");
        return new OSSClient(Factory.app(), ENDPOINT, credentialProvider);
    }

    private static String upLoadFile(String objectKey, String uploadFilePath) {
        PutObjectRequest put = new PutObjectRequest(BUCKETNAME, objectKey, uploadFilePath);
        try {
            OSS oss = getOssClient();
            PutObjectResult putResult = oss.putObject(put);
            Log.d("PutObject", "UploadSuccess");
            Log.d("RequestId", putResult.getRequestId());
            String url = oss.presignPublicObjectURL(BUCKETNAME,objectKey);
            Log.d(TAG, String.format("PublicObjectURL:%s", url));
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 普通图片的上传
     * @param uploadFilePath 上传图片的路径
     * @return 服务器地址
     */
    public static String upLoadImage(String uploadFilePath) {
        String fileMD5 = HashUtil.getMD5String(uploadFilePath);
        String path = String.format("image/%s/%s.jpg", getDatePath(), fileMD5);
        return upLoadFile(path,uploadFilePath);
    }

    /**
     * 头像的上传
     * @param uploadFilePath 上传头像的路径
     * @return 服务器地址
     */
    public static String upLoadPortrait(String uploadFilePath) {
        String fileMD5 = HashUtil.getMD5String(uploadFilePath);
        String path = String.format("portrait/%s/%s.jpg", getDatePath(), fileMD5);
        return upLoadFile(path,uploadFilePath);
    }

    /**
     * 语音的上传
     * @param uploadFilePath 上传语音的路径
     * @return 服务器地址
     */
    public static String upLoadAudio(String uploadFilePath) {
        String fileMD5 = HashUtil.getMD5String(uploadFilePath);
        String path = String.format("portrait/%s/%s.mps", getDatePath(), fileMD5);
        return upLoadFile(path,uploadFilePath);
    }

    /**
     * 得到当前月份的字符串格式
     *
     * @return
     */
    private static String getDatePath() {
        return DateFormat.format("yyyyMM", new Date()).toString();
    }
}
