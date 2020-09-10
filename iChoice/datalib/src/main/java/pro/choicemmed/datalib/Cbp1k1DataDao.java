package pro.choicemmed.datalib;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CBP1K1_DATA".
*/
public class Cbp1k1DataDao extends AbstractDao<Cbp1k1Data, String> {

    public static final String TABLENAME = "CBP1K1_DATA";

    /**
     * Properties of entity Cbp1k1Data.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "Id", true, "ID");
        public final static Property DeviceName = new Property(1, String.class, "deviceName", false, "DEVICE_NAME");
        public final static Property UserId = new Property(2, String.class, "userId", false, "USER_ID");
        public final static Property ServerId = new Property(3, String.class, "serverId", false, "SERVER_ID");
        public final static Property MeasureDateTime = new Property(4, String.class, "measureDateTime", false, "MEASURE_DATE_TIME");
        public final static Property LastUpdateTime = new Property(5, String.class, "lastUpdateTime", false, "LAST_UPDATE_TIME");
        public final static Property LastUploadTime = new Property(6, String.class, "lastUploadTime", false, "LAST_UPLOAD_TIME");
        public final static Property Systolic = new Property(7, Integer.class, "systolic", false, "SYSTOLIC");
        public final static Property Diastolic = new Property(8, Integer.class, "diastolic", false, "DIASTOLIC");
        public final static Property PulseRate = new Property(9, Integer.class, "pulseRate", false, "PULSE_RATE");
        public final static Property DeviceId = new Property(10, String.class, "deviceId", false, "DEVICE_ID");
        public final static Property SyncState = new Property(11, Integer.class, "syncState", false, "SYNC_STATE");
        public final static Property CreateTime = new Property(12, String.class, "createTime", false, "CREATE_TIME");
        public final static Property LogDateTime = new Property(13, String.class, "logDateTime", false, "LOG_DATE_TIME");
    }


    public Cbp1k1DataDao(DaoConfig config) {
        super(config);
    }
    
    public Cbp1k1DataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CBP1K1_DATA\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: Id
                "\"DEVICE_NAME\" TEXT," + // 1: deviceName
                "\"USER_ID\" TEXT," + // 2: userId
                "\"SERVER_ID\" TEXT," + // 3: serverId
                "\"MEASURE_DATE_TIME\" TEXT," + // 4: measureDateTime
                "\"LAST_UPDATE_TIME\" TEXT," + // 5: lastUpdateTime
                "\"LAST_UPLOAD_TIME\" TEXT," + // 6: lastUploadTime
                "\"SYSTOLIC\" INTEGER," + // 7: systolic
                "\"DIASTOLIC\" INTEGER," + // 8: diastolic
                "\"PULSE_RATE\" INTEGER," + // 9: pulseRate
                "\"DEVICE_ID\" TEXT," + // 10: deviceId
                "\"SYNC_STATE\" INTEGER," + // 11: syncState
                "\"CREATE_TIME\" TEXT," + // 12: createTime
                "\"LOG_DATE_TIME\" TEXT);"); // 13: logDateTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CBP1K1_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Cbp1k1Data entity) {
        stmt.clearBindings();
 
        String Id = entity.getId();
        if (Id != null) {
            stmt.bindString(1, Id);
        }
 
        String deviceName = entity.getDeviceName();
        if (deviceName != null) {
            stmt.bindString(2, deviceName);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(3, userId);
        }
 
        String serverId = entity.getServerId();
        if (serverId != null) {
            stmt.bindString(4, serverId);
        }
 
        String measureDateTime = entity.getMeasureDateTime();
        if (measureDateTime != null) {
            stmt.bindString(5, measureDateTime);
        }
 
        String lastUpdateTime = entity.getLastUpdateTime();
        if (lastUpdateTime != null) {
            stmt.bindString(6, lastUpdateTime);
        }
 
        String lastUploadTime = entity.getLastUploadTime();
        if (lastUploadTime != null) {
            stmt.bindString(7, lastUploadTime);
        }
 
        Integer systolic = entity.getSystolic();
        if (systolic != null) {
            stmt.bindLong(8, systolic);
        }
 
        Integer diastolic = entity.getDiastolic();
        if (diastolic != null) {
            stmt.bindLong(9, diastolic);
        }
 
        Integer pulseRate = entity.getPulseRate();
        if (pulseRate != null) {
            stmt.bindLong(10, pulseRate);
        }
 
        String deviceId = entity.getDeviceId();
        if (deviceId != null) {
            stmt.bindString(11, deviceId);
        }
 
        Integer syncState = entity.getSyncState();
        if (syncState != null) {
            stmt.bindLong(12, syncState);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(13, createTime);
        }
 
        String logDateTime = entity.getLogDateTime();
        if (logDateTime != null) {
            stmt.bindString(14, logDateTime);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Cbp1k1Data entity) {
        stmt.clearBindings();
 
        String Id = entity.getId();
        if (Id != null) {
            stmt.bindString(1, Id);
        }
 
        String deviceName = entity.getDeviceName();
        if (deviceName != null) {
            stmt.bindString(2, deviceName);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(3, userId);
        }
 
        String serverId = entity.getServerId();
        if (serverId != null) {
            stmt.bindString(4, serverId);
        }
 
        String measureDateTime = entity.getMeasureDateTime();
        if (measureDateTime != null) {
            stmt.bindString(5, measureDateTime);
        }
 
        String lastUpdateTime = entity.getLastUpdateTime();
        if (lastUpdateTime != null) {
            stmt.bindString(6, lastUpdateTime);
        }
 
        String lastUploadTime = entity.getLastUploadTime();
        if (lastUploadTime != null) {
            stmt.bindString(7, lastUploadTime);
        }
 
        Integer systolic = entity.getSystolic();
        if (systolic != null) {
            stmt.bindLong(8, systolic);
        }
 
        Integer diastolic = entity.getDiastolic();
        if (diastolic != null) {
            stmt.bindLong(9, diastolic);
        }
 
        Integer pulseRate = entity.getPulseRate();
        if (pulseRate != null) {
            stmt.bindLong(10, pulseRate);
        }
 
        String deviceId = entity.getDeviceId();
        if (deviceId != null) {
            stmt.bindString(11, deviceId);
        }
 
        Integer syncState = entity.getSyncState();
        if (syncState != null) {
            stmt.bindLong(12, syncState);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(13, createTime);
        }
 
        String logDateTime = entity.getLogDateTime();
        if (logDateTime != null) {
            stmt.bindString(14, logDateTime);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public Cbp1k1Data readEntity(Cursor cursor, int offset) {
        Cbp1k1Data entity = new Cbp1k1Data( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // Id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // deviceName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // serverId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // measureDateTime
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // lastUpdateTime
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // lastUploadTime
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // systolic
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // diastolic
            cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // pulseRate
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // deviceId
            cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11), // syncState
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // createTime
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13) // logDateTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Cbp1k1Data entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setDeviceName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setServerId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMeasureDateTime(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLastUpdateTime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLastUploadTime(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSystolic(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setDiastolic(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setPulseRate(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setDeviceId(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setSyncState(cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11));
        entity.setCreateTime(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setLogDateTime(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
     }
    
    @Override
    protected final String updateKeyAfterInsert(Cbp1k1Data entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(Cbp1k1Data entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Cbp1k1Data entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}