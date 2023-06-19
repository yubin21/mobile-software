package dduwcom.mobile.finalreport.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log



class DiaryDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 4) {
    val TAG = "FoodDBHelper"

    companion object {
        const val DB_NAME = "diary_db"
        const val TABLE_NAME = "diary_table"
        const val COL_TITLE = "title"
        const val COL_EMOTION = "emotion"
        const val COL_DATE = "date"
        const val COL_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ( " +
                "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_TITLE TEXT, " +
                "$COL_DATE TEXT, " +
                "$COL_EMOTION INTEGER, " +
                "$COL_CONTENT TEXT)"
        Log.d(TAG, CREATE_TABLE)
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

}


