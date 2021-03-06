package com.choicemmed.ichoice.healthcheck.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

import pro.choicemmed.datalib.CFT308DataDao;
import pro.choicemmed.datalib.Cbp1k1DataDao;
import pro.choicemmed.datalib.DaoMaster;
import pro.choicemmed.datalib.DeviceDisplayDao;
import pro.choicemmed.datalib.DeviceInfoDao;
import pro.choicemmed.datalib.EcgDataDao;
import pro.choicemmed.datalib.OxRealTimeDataDao;
import pro.choicemmed.datalib.OxSpotDataDao;
import pro.choicemmed.datalib.UserProfileInfoDao;
import pro.choicemmed.datalib.W314B4DataDao;
import pro.choicemmed.datalib.W628DataDao;


public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, EcgDataDao.class, Cbp1k1DataDao.class, CFT308DataDao.class, DeviceDisplayDao.class, DeviceInfoDao.class, UserProfileInfoDao.class, W314B4DataDao.class, W628DataDao.class, OxRealTimeDataDao.class, OxSpotDataDao.class);
    }
}
