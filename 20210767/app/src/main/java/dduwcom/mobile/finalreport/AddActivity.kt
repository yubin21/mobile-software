package dduwcom.mobile.finalreport

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import dduwcom.mobile.finalreport.databinding.ActivityAddBinding
import dduwcom.mobile.finalreport.data.DiaryDBHelper
import dduwcom.mobile.finalreport.data.DiaryDto
import java.text.SimpleDateFormat

import java.util.Calendar
import java.util.Locale

class AddActivity : AppCompatActivity() {

    lateinit var addBinding : ActivityAddBinding
    private var emotion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // 타이틀 바 숨기기
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        // MainActivity에서 emotion 값을 전달받음
        emotion = intent.getIntExtra("emotion", 0)

        val calendar = Calendar.getInstance()

        val monthAndDate : TextView = addBinding.monthAndDate
        val days : TextView = addBinding.days

        val monthAndDateText = SimpleDateFormat("MM월 dd일", Locale.getDefault()).format(calendar.time)
        val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.time)

        monthAndDate.text = monthAndDateText
        days.text = dayOfWeek

        // 이미지 뷰 변경
        val imageView = addBinding.imageView

        when (emotion) {
            R.mipmap.emotion1 -> imageView.setImageResource(R.mipmap.emotion1)
            R.mipmap.emotion2 -> imageView.setImageResource(R.mipmap.emotion2)
            R.mipmap.emotion3 -> imageView.setImageResource(R.mipmap.emotion3)
            R.mipmap.emotion4 -> imageView.setImageResource(R.mipmap.emotion4)
            R.mipmap.emotion5 -> imageView.setImageResource(R.mipmap.emotion5)
            else -> imageView.setImageResource(R.mipmap.emotion6)
        }

        /*EditText에서 값을 읽어와 DB에 저장*/
        addBinding.btnAddFood.setOnClickListener{
            val title = addBinding.etAddTitle.text.toString()

            val calendar = Calendar.getInstance()
            val today = Calendar.getInstance()

            calendar.set(Calendar.YEAR, today.get(Calendar.YEAR))
            calendar.set(Calendar.MONTH, today.get(Calendar.MONTH))
            calendar.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH))

            val selectedDateInMillis = calendar.timeInMillis

            fun getEmotionDrawableResId(emotion: Int): Int {
                return when (emotion) {
                    R.mipmap.emotion1 -> R.mipmap.emotion1
                    R.mipmap.emotion2 -> R.mipmap.emotion2
                    R.mipmap.emotion3 -> R.mipmap.emotion3
                    R.mipmap.emotion4 -> R.mipmap.emotion4
                    R.mipmap.emotion5 -> R.mipmap.emotion5
                    else -> R.mipmap.emotion6
                }
            }

            val context = addBinding.etAddContext.text.toString()
            val newDto = DiaryDto(0, getEmotionDrawableResId(emotion), title, calendar, context)

            if (addFood(newDto) > 0) {
                setResult(RESULT_OK)
                } else {
                    setResult(RESULT_CANCELED)
                }

            finish()
        }

        addBinding.btnAddCancel.setOnClickListener{
            finish()
        }

    }

    fun addFood(newDto : DiaryDto) : Long {
        val helper = DiaryDBHelper(this)
        val db = helper.writableDatabase

        val newValue = ContentValues()
        newValue.put(DiaryDBHelper.COL_TITLE, newDto.title)
        newValue.put(DiaryDBHelper.COL_EMOTION, newDto.emotion) // emotion 값을 DB에 저장
        newValue.put(DiaryDBHelper.COL_CONTENT, newDto.context)
        newValue.put(DiaryDBHelper.COL_DATE, newDto.date.timeInMillis)

        val result = db.insert(DiaryDBHelper.TABLE_NAME, null, newValue)

        helper.close()
        return result
    }

}