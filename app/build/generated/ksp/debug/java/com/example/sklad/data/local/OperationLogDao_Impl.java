package com.example.sklad.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class OperationLogDao_Impl implements OperationLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<OperationLogEntity> __insertionAdapterOfOperationLogEntity;

  public OperationLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfOperationLogEntity = new EntityInsertionAdapter<OperationLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `operation_logs` (`id`,`date`,`operationType`,`productId`,`quantity`,`fromLocationId`,`toLocationId`,`sum`,`comment`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final OperationLogEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDate());
        statement.bindString(3, entity.getOperationType());
        statement.bindLong(4, entity.getProductId());
        statement.bindDouble(5, entity.getQuantity());
        if (entity.getFromLocationId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getFromLocationId());
        }
        if (entity.getToLocationId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getToLocationId());
        }
        if (entity.getSum() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getSum());
        }
        statement.bindString(9, entity.getComment());
      }
    };
  }

  @Override
  public Object insert(final OperationLogEntity item,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfOperationLogEntity.insert(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<OperationLogEntity>> getAll() {
    final String _sql = "SELECT * FROM operation_logs ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"operation_logs"}, new Callable<List<OperationLogEntity>>() {
      @Override
      @NonNull
      public List<OperationLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfOperationType = CursorUtil.getColumnIndexOrThrow(_cursor, "operationType");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfFromLocationId = CursorUtil.getColumnIndexOrThrow(_cursor, "fromLocationId");
          final int _cursorIndexOfToLocationId = CursorUtil.getColumnIndexOrThrow(_cursor, "toLocationId");
          final int _cursorIndexOfSum = CursorUtil.getColumnIndexOrThrow(_cursor, "sum");
          final int _cursorIndexOfComment = CursorUtil.getColumnIndexOrThrow(_cursor, "comment");
          final List<OperationLogEntity> _result = new ArrayList<OperationLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final OperationLogEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpOperationType;
            _tmpOperationType = _cursor.getString(_cursorIndexOfOperationType);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            final double _tmpQuantity;
            _tmpQuantity = _cursor.getDouble(_cursorIndexOfQuantity);
            final Long _tmpFromLocationId;
            if (_cursor.isNull(_cursorIndexOfFromLocationId)) {
              _tmpFromLocationId = null;
            } else {
              _tmpFromLocationId = _cursor.getLong(_cursorIndexOfFromLocationId);
            }
            final Long _tmpToLocationId;
            if (_cursor.isNull(_cursorIndexOfToLocationId)) {
              _tmpToLocationId = null;
            } else {
              _tmpToLocationId = _cursor.getLong(_cursorIndexOfToLocationId);
            }
            final Double _tmpSum;
            if (_cursor.isNull(_cursorIndexOfSum)) {
              _tmpSum = null;
            } else {
              _tmpSum = _cursor.getDouble(_cursorIndexOfSum);
            }
            final String _tmpComment;
            _tmpComment = _cursor.getString(_cursorIndexOfComment);
            _item = new OperationLogEntity(_tmpId,_tmpDate,_tmpOperationType,_tmpProductId,_tmpQuantity,_tmpFromLocationId,_tmpToLocationId,_tmpSum,_tmpComment);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
