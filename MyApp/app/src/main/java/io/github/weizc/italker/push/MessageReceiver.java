package io.github.weizc.italker.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;

import io.github.weizc.italker.factory.Factory;
import io.github.weizc.italker.factory.data.helper.AccountHelper;
import io.github.weizc.italker.factory.persistence.Account;

/**
 * Created by Vzer.
 * Date: 2017/7/23.
 */

public class MessageReceiver extends BroadcastReceiver{
    private static final String TAG = MessageReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent==null)return;

        Bundle bundle = intent.getExtras();
        switch (bundle.getInt(PushConsts.CMD_ACTION)){
            case PushConsts.GET_CLIENTID:{
                Log.i(TAG, "GET_CLIENTID:" + bundle.toString());
                //当Id初始化的时候
                //获取设备Id
                onClientInit(bundle.getString("clientid"));
                break;
            }
            //常规消息送达的时候
            case PushConsts.GET_MSG_DATA:{
                byte[] payload = bundle.getByteArray("payload");
                if (payload!=null){
                    String message = new String(payload);
                    Log.i(TAG,"GET_MSG_DATA"+message);
                    onMessageArrived(message);
                }
                break;
            }
            default:
                Log.i(TAG,"OTHER"+bundle.toString());
                break;
        }
    }

    //推送消息的处理
    private void onMessageArrived(String message) {
        //交给Factory处理
        Factory.diapatchPush(message);
    }

    private void onClientInit(String clientid) {
        //设置设备的Id
        Account.setPushId(clientid);
        if (Account.isLogin()){
            AccountHelper.bindPush(null);
        }
    }
}
