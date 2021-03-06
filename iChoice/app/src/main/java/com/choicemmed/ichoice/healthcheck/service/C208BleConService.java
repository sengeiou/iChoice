package com.choicemmed.ichoice.healthcheck.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.choice.c208sdkblelibrary.base.DeviceType;
import com.choice.c208sdkblelibrary.cmd.callback.C208CancelConnectCallback;
import com.choice.c208sdkblelibrary.cmd.callback.C208ConnectCallback;
import com.choice.c208sdkblelibrary.cmd.callback.C208DisconnectCallBack;
import com.choice.c208sdkblelibrary.cmd.invoker.C208Invoker;
import com.choice.c208sdkblelibrary.device.C208;
import com.choice.c208sdkblelibrary.utils.ErrorCode;
import com.choicemmed.common.FormatUtils;
import com.choicemmed.common.LogUtils;
import com.choicemmed.common.ThreadManager;
import com.choicemmed.common.UuidUtils;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.healthcheck.db.DeviceOperation;
import com.choicemmed.ichoice.healthcheck.db.OxSpotOperation;
import com.choicemmed.ichoice.healthcheck.db.UserOperation;


import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pro.choicemmed.datalib.DeviceInfo;
import pro.choicemmed.datalib.OxSpotData;
import pro.choicemmed.datalib.UserProfileInfo;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class C208BleConService extends Service {
    public static final String TAG = "C208BleConService";
    private List<DeviceInfo> list;
    Subscription c208Subscription;
    C208 c208;
    C208Invoker c208Invoker;
    C208BleConService.ScanBleBinder binder = new ScanBleBinder();

    public C208BleConService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return binder;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        c208Invoker = new C208Invoker(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class ScanBleBinder extends Binder {
        ScanBleBinder getService() {
            Log.d(TAG, "getService");
            return ScanBleBinder.this;
        }

        public void starConnectBle() {
            LogUtils.d(TAG, "----startConnectBle-----");
            init();
        }

        public void cancelConnect() {
            LogUtils.d(TAG, "关闭Gatt,取消所有待连接！");
            c208Invoker.cancelConnectDevice(new C208CancelConnectCallback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(DeviceType deviceType, int errorMsg) {

                }
            });

        }

        public void disconnect() {
            LogUtils.d(TAG, "断开蓝牙连接");
            c208Invoker.disConnectDevice(new C208DisconnectCallBack() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(DeviceType deviceType, int errorMsg) {

                }
            });
        }
    }

    private void init() {
        list = new DeviceOperation(this).queryByDeviceType(IchoiceApplication.getAppData().userProfileInfo.getUserId(), DevicesType.PulseOximeter);
        if (list.isEmpty()) {
            LogUtils.d(TAG, "devices：没有血氧设备");
            return;
        }
        openBluetoothBle();
        LogUtils.d(TAG, "devices：" + list.toString());
        searchDevice(list);
        LogUtils.d(TAG, "结束了：searchDevice");
    }

    private void openBluetoothBle() {
        BluetoothManager bluetoothManager = (BluetoothManager) C208BleConService.this.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            LogUtils.d(TAG, "检测到蓝牙未打开：重新开启蓝牙！");
            bluetoothAdapter.enable();//不弹对话框直接开启蓝牙
        }
    }

    private void searchDevice(List<DeviceInfo> list) {
        if (list.size() != 0) {
            final DeviceInfo deviceInfo = list.get(0);
            ThreadManager.execute(new Runnable() {
                @Override
                public void run() {
                    c208 = new C208();
                    c208.setDeviceName(deviceInfo.getDeviceName());
                    c208.setMacAddress(deviceInfo.getBluetoothId());
                    LogUtils.d(TAG, "绑定设备" + deviceInfo.getDeviceName());
                    connectC208(c208);
                }
            });

        } else {
            LogUtils.d(TAG, "没有绑定设备");
        }
    }

    private void connectC208(final C208 c208) {
        c208Invoker.connectDevice(new C208ConnectCallback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccessC208: C208连接成功！");
            }

            @Override
            public void onMeasureResult(DeviceType deviceType, int ox, float pi, int pulseRate) {
                Log.d(TAG, "onMeasureResult: 收到测量数据");
                saveMeasurementData(ox, pulseRate, pi);
            }

            @Override
            public void onError(DeviceType deviceType, int errorMsg) {
                Log.d(TAG, "onErrorC208: " + errorMsg);
                switch (errorMsg) {
                    //未打开蓝牙
                    case ErrorCode.ERROR_BLUETOOTH_NOT_OPEN:
                        openBluetoothBle();
                        break;
                    case ErrorCode.ERROR_CONNECT_TIMEOUT://连接超时
                    case ErrorCode.ERROR_BLE_DISCONNECT://下位机主动断开连接
                    case ErrorCode.ERROR_GATT_EXCEPTION://gatt异常
                        connectDelayC208(1);
                        break;
                    default:
                }

            }
        }, c208.getMacAddress());

    }

    private void connectDelayC208(int delayTime) {
        c208Subscription = Observable.timer(delayTime, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d(TAG, "call: ");
                connectC208(c208);
            }
        });


    }

    private void saveMeasurementData(int ox, int pr, float pi) {
        LogUtils.d(TAG, "准备保存测量数据");
        OxSpotData oxSpotData = new OxSpotData();
        OxSpotOperation oxSpotOperation = new OxSpotOperation(this);
        UserOperation userOperation = new UserOperation(this);
        UserProfileInfo userProfileInfo = userOperation.queryByUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (userProfileInfo != null) {
            oxSpotData.setId(UuidUtils.getUuid());
            oxSpotData.setUserId(userProfileInfo.getUserId());
            oxSpotData.setDeviceName(c208.getDeviceName());
            oxSpotData.setLogDateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
            oxSpotData.setMeasureDateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
            oxSpotData.setCreateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
            oxSpotData.setLastUpdateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
            oxSpotData.setBloodOxygen(ox);
            oxSpotData.setPulseRate(pr);
            oxSpotData.setSyncState(0);
            oxSpotData.setPi(pi);
            oxSpotOperation.insertOxSpotByUser(oxSpotData);
            LogUtils.d(TAG, "保存测量数据" + ox + pr + pi);
            Intent intent = new Intent("onOxSpotMeasureResult");
            intent.putExtra("oxResult", true);
            sendBroadcast(intent);
        }

    }
}
