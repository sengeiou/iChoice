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
 * DAO for table "ECG_DATA".
*/
public class EcgDataDao extends AbstractDao<EcgData, String> {

    public static final String TABLENAME = "ECG_DATA";

    /**
     * Properties of entity EcgData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Uuid = new Property(0, String.class, "uuid", true, "UUID");
        public final static Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public final static Property StartDate = new Property(2, String.class, "startDate", false, "START_DATE");
        public final static Property EndDate = new Property(3, String.class, "endDate", false, "END_DATE");
        public final static Property MeasureTime = new Property(4, String.class, "measureTime", false, "MEASURE_TIME");
        public final static Property UpLoadFlag = new Property(5, int.class, "upLoadFlag", false, "UP_LOAD_FLAG");
        public final static Property EcgTime = new Property(6, String.class, "ecgTime", false, "ECG_TIME");
        public final static Property EcgData = new Property(7, String.class, "ecgData", false, "ECG_DATA");
        public final static Property LastUpdateTime = new Property(8, String.class, "lastUpdateTime", false, "LAST_UPDATE_TIME");
        public final static Property DecodeBpm = new Property(9, int.class, "decodeBpm", false, "DECODE_BPM");
        public final static Property Locale = new Property(10, String.class, "locale", false, "LOCALE");
        public final static Property Analysisresult = new Property(11, String.class, "analysisresult", false, "ANALYSISRESULT");
        public final static Property EcgResult = new Property(12, int.class, "ecgResult", false, "ECG_RESULT");
    }


    public EcgDataDao(DaoConfig config) {
        super(config);
    }
    
    public EcgDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ECG_DATA\" (" + //
                "\"UUID\" TEXT PRIMARY KEY NOT NULL ," + // 0: uuid
                "\"USER_ID\" TEXT," + // 1: userId
                "\"START_DATE\" TEXT," + // 2: startDate
                "\"END_DATE\" TEXT," + // 3: endDate
                "\"MEASURE_TIME\" TEXT," + // 4: measureTime
                "\"UP_LOAD_FLAG\" INTEGER NOT NULL ," + // 5: upLoadFlag
                "\"ECG_TIME\" TEXT," + // 6: ecgTime
                "\"ECG_DATA\" TEXT," + // 7: ecgData
                "\"LAST_UPDATE_TIME\" TEXT," + // 8: lastUpdateTime
                "\"DECODE_BPM\" INTEGER NOT NULL ," + // 9: decodeBpm
                "\"LOCALE\" TEXT," + // 10: locale
                "\"ANALYSISRESULT\" TEXT," + // 11: analysisresult
                "\"ECG_RESULT\" INTEGER NOT NULL );"); // 12: ecgResult
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ECG_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, EcgData entity) {
        stmt.clearBindings();
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(1, uuid);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String startDate = entity.getStartDate();
        if (startDate != null) {
            stmt.bindString(3, startDate);
        }
 
        String endDate = entity.getEndDate();
        if (endDate != null) {
            stmt.bindString(4, endDate);
        }
 
        String measureTime = entity.getMeasureTime();
        if (measureTime != null) {
            stmt.bindString(5, measureTime);
        }
        stmt.bindLong(6, entity.getUpLoadFlag());
 
        String ecgTime = entity.getEcgTime();
        if (ecgTime != null) {
            stmt.bindString(7, ecgTime);
        }
 
        String ecgData = entity.getEcgData();
        if (ecgData != null) {
            stmt.bindString(8, ecgData);
        }
 
        String lastUpdateTime = entity.getLastUpdateTime();
        if (lastUpdateTime != null) {
            stmt.bindString(9, lastUpdateTime);
        }
        stmt.bindLong(10, entity.getDecodeBpm());
 
        String locale = entity.getLocale();
        if (locale != null) {
            stmt.bindString(11, locale);
        }
 
        String analysisresult = entity.getAnalysisresult();
        if (analysisresult != null) {
            stmt.bindString(12, analysisresult);
        }
        stmt.bindLong(13, entity.getEcgResult());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, EcgData entity) {
        stmt.clearBindings();
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(1, uuid);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String startDate = entity.getStartDate();
        if (startDate != null) {
            stmt.bindString(3, startDate);
        }
 
        String endDate = entity.getEndDate();
        if (endDate != null) {
            stmt.bindString(4, endDate);
        }
 
        String measureTime = entity.getMeasureTime();
        if (measureTime != null) {
            stmt.bindString(5, measureTime);
        }
        stmt.bindLong(6, entity.getUpLoadFlag());
 
        String ecgTime = entity.getEcgTime();
        if (ecgTime != null) {
            stmt.bindString(7, ecgTime);
        }
 
        String ecgData = entity.getEcgData();
        if (ecgData != null) {
            stmt.bindString(8, ecgData);
        }
 
        String lastUpdateTime = entity.getLastUpdateTime();
        if (lastUpdateTime != null) {
            stmt.bindString(9, lastUpdateTime);
        }
        stmt.bindLong(10, entity.getDecodeBpm());
 
        String locale = entity.getLocale();
        if (locale != null) {
            stmt.bindString(11, locale);
        }
 
        String analysisresult = entity.getAnalysisresult();
        if (analysisresult != null) {
            stmt.bindString(12, analysisresult);
        }
        stmt.bindLong(13, entity.getEcgResult());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public EcgData readEntity(Cursor cursor, int offset) {
        EcgData entity = new EcgData( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // uuid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // startDate
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // endDate
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // measureTime
            cursor.getInt(offset + 5), // upLoadFlag
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // ecgTime
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // ecgData
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // lastUpdateTime
            cursor.getInt(offset + 9), // decodeBpm
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // locale
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // analysisresult
            cursor.getInt(offset + 12) // ecgResult
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, EcgData entity, int offset) {
        entity.setUuid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setStartDate(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setEndDate(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMeasureTime(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUpLoadFlag(cursor.getInt(offset + 5));
        entity.setEcgTime(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setEcgData(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setLastUpdateTime(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setDecodeBpm(cursor.getInt(offset + 9));
        entity.setLocale(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setAnalysisresult(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setEcgResult(cursor.getInt(offset + 12));
     }
    
    @Override
    protected final String updateKeyAfterInsert(EcgData entity, long rowId) {
        return entity.getUuid();
    }
    
    @Override
    public String getKey(EcgData entity) {
        if(entity != null) {
            return entity.getUuid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(EcgData entity) {
        return entity.getUuid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}