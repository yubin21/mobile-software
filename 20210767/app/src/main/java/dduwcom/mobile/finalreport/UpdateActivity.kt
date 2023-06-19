package dduwcom.mobile.finalreport

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import dduwcom.mobile.finalreport.data.DiaryDBHelper
import dduwcom.mobile.finalreport.data.DiaryDto
import dduwcom.mobile.finalreport.databinding.ActivityUpdateBinding
import java.util.Calendar

class UpdateActivity : AppCompatActivity() {

    lateinit var updateBinding : ActivityUpdateBinding
    // 기존에 선택한 감정 값을 저장할 변수
    private var previousEmotion: Int = 0
    var selectedEmotion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // 타이틀 바 숨기기
        updateBinding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(updateBinding.root)

        /*RecyclerView 에서 선택하여 전달한 dto 를 확인*/
        val dto = intent.getSerializableExtra("dto") as DiaryDto

        // emotion 값을 전달받음
        previousEmotion =  dto.emotion

        updateBinding.etUpdateId.setText(dto.id.toString())     // XML 속성에서 편집금지로 지정하였음
        updateBinding.etUpdateTitle.setText(dto.title)
        updateBinding.etDateView.setDate(dto.date.timeInMillis)
        updateBinding.etUpdateContext.setText(dto.context)

        updateBinding.etDateView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            dto.date = calendar
        }

        updateBinding.btnUpdateFood.setOnClickListener{
            dto.title = updateBinding.etUpdateTitle.text.toString()
            dto.context = updateBinding.etUpdateContext.text.toString()

            val dto = intent.getSerializableExtra("dto") as DiaryDto

            // update 화면에서 선택한 감정이 없을 경우에만 이전 감정으로 설정
            if (selectedEmotion == 0) {
                setEmotionImage(previousEmotion)
                selectedEmotion = previousEmotion
            }

            if (updateFood(dto) > 0) {
                setResult(RESULT_OK)
            } else {
                setResult(RESULT_CANCELED)
            }
            finish()
        }

        updateBinding.btnUpdateCancel.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }

        updateBinding.imageButton1.setOnClickListener { toggleEmotionImage(R.mipmap.emotion1) }
        updateBinding.imageButton2.setOnClickListener { toggleEmotionImage(R.mipmap.emotion2) }
        updateBinding.imageButton3.setOnClickListener { toggleEmotionImage(R.mipmap.emotion3) }
        updateBinding.imageButton4.setOnClickListener { toggleEmotionImage(R.mipmap.emotion4) }
        updateBinding.imageButton5.setOnClickListener { toggleEmotionImage(R.mipmap.emotion5) }
        updateBinding.imageButton6.setOnClickListener { toggleEmotionImage(R.mipmap.emotion6) }
    }

    /*ID 에 해당하는 레코드를 찾아 dto 의 내용으로 수정*/
    fun updateFood(dto: DiaryDto): Int {
        val helper = DiaryDBHelper(this)
        val db = helper.writableDatabase

        dto.emotion = selectedEmotion // 감정 값을 업데이트

        val updateValue = ContentValues()
        updateValue.put(DiaryDBHelper.COL_TITLE, dto.title)
        updateValue.put(DiaryDBHelper.COL_CONTENT, dto.context)
        updateValue.put(DiaryDBHelper.COL_DATE, dto.date.timeInMillis)
        updateValue.put(DiaryDBHelper.COL_EMOTION, dto.emotion)

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        val resultCount =  db.update(
            DiaryDBHelper.TABLE_NAME,
            updateValue, whereClause, whereArgs)

        helper.close()
        return resultCount
    }

    private fun toggleEmotionImage(emotion: Int) {
        if (selectedEmotion == emotion) {
            selectedEmotion = 0
            clearEmotionImages()
        } else {
            selectedEmotion = emotion
            setEmotionImage(emotion) // 변경된 부분: 선택한 감정 이미지로 설정
        }
    }

    private fun setEmotionImage(emotion: Int) {
        clearEmotionImages()
        selectedEmotion = emotion
        when (emotion) {
            R.mipmap.emotion1 -> updateBinding.imageButton1.setImageResource(emotion)
            R.mipmap.emotion2 -> updateBinding.imageButton2.setImageResource(emotion)
            R.mipmap.emotion3 -> updateBinding.imageButton3.setImageResource(emotion)
            R.mipmap.emotion4 -> updateBinding.imageButton4.setImageResource(emotion)
            R.mipmap.emotion5 -> updateBinding.imageButton5.setImageResource(emotion)
            R.mipmap.emotion6 -> updateBinding.imageButton6.setImageResource(emotion)
        }
    }

    private fun clearEmotionImages() {
        with(updateBinding) {
            imageButton1.setImageResource(R.mipmap.emotion1_unselected)
            imageButton2.setImageResource(R.mipmap.emotion2_unselected)
            imageButton3.setImageResource(R.mipmap.emotion3_unselected)
            imageButton4.setImageResource(R.mipmap.emotion4_unselected)
            imageButton5.setImageResource(R.mipmap.emotion5_unselected)
            imageButton6.setImageResource(R.mipmap.emotion6_unselected)
        }
    }


}