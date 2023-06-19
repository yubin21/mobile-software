package dduwcom.mobile.finalreport.data

import android.annotation.SuppressLint
import android.content.Context
import android.provider.BaseColumns
import java.util.Calendar


class DiaryDao(val context: Context) {

    @SuppressLint("Range")
    fun getAllFoods() : ArrayList<DiaryDto> {
        val helper = DiaryDBHelper(context)
        val db = helper.readableDatabase
//        val cursor = db.rawQuery("SELECT * FROM ${FoodDBHelper.TABLE_NAME}", null)
        val cursor = db.query(DiaryDBHelper.TABLE_NAME, null, null, null, null, null, null)

        val diaries = arrayListOf<DiaryDto>()
        with (cursor) {
            while (moveToNext()) {
                val id = getLong( getColumnIndex(BaseColumns._ID) )
                val title = getString ( getColumnIndex(DiaryDBHelper.COL_TITLE) )
//                val date = getString ( getColumnIndex(FoodDBHelper.COL_COUNTRY) )
                val dateInMillis = getLong(getColumnIndex(DiaryDBHelper.COL_DATE))
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = dateInMillis
                val emotion = getInt( getColumnIndex(DiaryDBHelper.COL_EMOTION) )
                val context = getString ( getColumnIndex(DiaryDBHelper.COL_CONTENT) )

                val dto = DiaryDto(id, emotion , title, calendar, context)
                diaries.add(dto)
            }
        }
        return diaries
    }

    fun deleteFood(diaryId: Long): Int {
        val helper = DiaryDBHelper(context)
        val db = helper.writableDatabase
        val whereClause = "_id=?"
        val whereArgs = arrayOf(diaryId.toString())
        val result = db.delete(DiaryDBHelper.TABLE_NAME, whereClause, whereArgs)
        helper.close()

        return result
    }

    @SuppressLint("Range")
    // "SELECT * FROM title"
    fun DBSearch(searchTitle: String): ArrayList<DiaryDto> {
        val helper = DiaryDBHelper(context)
        val db = helper.readableDatabase

        val tableName = DiaryDBHelper.TABLE_NAME
        val selection = "${DiaryDBHelper.COL_TITLE} LIKE ?"
        val selectionArgs = arrayOf("%$searchTitle%")

        val cursor = db.query(tableName, null, selection, selectionArgs, null, null, null)
        val searchResults = arrayListOf<DiaryDto>()

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndex(BaseColumns._ID))
                val title = getString(getColumnIndex(DiaryDBHelper.COL_TITLE))
                val dateInMillis = getLong(getColumnIndex(DiaryDBHelper.COL_DATE))
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = dateInMillis
                }
                val emotion = getInt(getColumnIndex(DiaryDBHelper.COL_EMOTION))
                val context = getString(getColumnIndex(DiaryDBHelper.COL_CONTENT))

                // 여기 뭔가 이상
                val dto = DiaryDto(id, emotion, title, calendar, context)
                searchResults.add(dto)
            }
        }

        cursor.close()
        return searchResults
    }
}