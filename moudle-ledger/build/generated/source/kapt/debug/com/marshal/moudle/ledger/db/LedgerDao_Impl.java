package com.marshal.moudle.ledger.db;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class LedgerDao_Impl implements LedgerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LedgerBean> __insertionAdapterOfLedgerBean;

  private final EntityDeletionOrUpdateAdapter<LedgerBean> __deletionAdapterOfLedgerBean;

  public LedgerDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLedgerBean = new EntityInsertionAdapter<LedgerBean>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ledger` (`id`,`amount`,`category`,`remark`,`createTime`,`type`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LedgerBean value) {
        stmt.bindLong(1, value.getId());
        stmt.bindDouble(2, value.getAmount());
        if (value.getCategory() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCategory());
        }
        if (value.getRemark() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getRemark());
        }
        stmt.bindLong(5, value.getCreateTime());
        stmt.bindLong(6, value.getType());
      }
    };
    this.__deletionAdapterOfLedgerBean = new EntityDeletionOrUpdateAdapter<LedgerBean>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ledger` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LedgerBean value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public long insert(final LedgerBean bean) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfLedgerBean.insertAndReturnId(bean);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final LedgerBean bean) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfLedgerBean.handle(bean);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<LedgerBean> queryAll() {
    final String _sql = "SELECT * FROM ledger ORDER BY createTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfRemark = CursorUtil.getColumnIndexOrThrow(_cursor, "remark");
      final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final List<LedgerBean> _result = new ArrayList<LedgerBean>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final LedgerBean _item;
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final double _tmpAmount;
        _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        final String _tmpRemark;
        if (_cursor.isNull(_cursorIndexOfRemark)) {
          _tmpRemark = null;
        } else {
          _tmpRemark = _cursor.getString(_cursorIndexOfRemark);
        }
        final long _tmpCreateTime;
        _tmpCreateTime = _cursor.getLong(_cursorIndexOfCreateTime);
        final int _tmpType;
        _tmpType = _cursor.getInt(_cursorIndexOfType);
        _item = new LedgerBean(_tmpId,_tmpAmount,_tmpCategory,_tmpRemark,_tmpCreateTime,_tmpType);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<LedgerBean> queryByRange(final long start, final long end) {
    final String _sql = "SELECT * FROM ledger WHERE createTime BETWEEN ? AND ? ORDER BY createTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, start);
    _argIndex = 2;
    _statement.bindLong(_argIndex, end);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfRemark = CursorUtil.getColumnIndexOrThrow(_cursor, "remark");
      final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final List<LedgerBean> _result = new ArrayList<LedgerBean>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final LedgerBean _item;
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final double _tmpAmount;
        _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        final String _tmpRemark;
        if (_cursor.isNull(_cursorIndexOfRemark)) {
          _tmpRemark = null;
        } else {
          _tmpRemark = _cursor.getString(_cursorIndexOfRemark);
        }
        final long _tmpCreateTime;
        _tmpCreateTime = _cursor.getLong(_cursorIndexOfCreateTime);
        final int _tmpType;
        _tmpType = _cursor.getInt(_cursorIndexOfType);
        _item = new LedgerBean(_tmpId,_tmpAmount,_tmpCategory,_tmpRemark,_tmpCreateTime,_tmpType);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public double sumByRange(final int type, final long start, final long end) {
    final String _sql = "SELECT IFNULL(SUM(amount),0) FROM ledger WHERE type = ? AND createTime BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, type);
    _argIndex = 2;
    _statement.bindLong(_argIndex, start);
    _argIndex = 3;
    _statement.bindLong(_argIndex, end);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final double _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getDouble(0);
      } else {
        _result = 0.0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
