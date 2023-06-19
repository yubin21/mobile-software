package dduwcom.mobile.finalreport

// 과제명: 하루 일기(일기 앱)
// 분반: 02 분반
// 학번: 20210767 성명: 김유빈
// 제출일: 2023년 6월 20일

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import dduwcom.mobile.finalreport.data.DiaryDao
import dduwcom.mobile.finalreport.data.DiaryDto
import dduwcom.mobile.finalreport.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    val REQ_ADD = 100
    val REQ_UPDATE = 200

    lateinit var binding : ActivityMainBinding
    lateinit var adapter : DiaryAdapter
    lateinit var diaries : ArrayList<DiaryDto>
    lateinit var diaryDao : DiaryDao

    lateinit var textView2: TextView
    lateinit var monthView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textView2 = findViewById(R.id.textView2)
        monthView = findViewById(R.id.monthView)

        // 현재 날짜 가져오기
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)

        // 오늘의 달 설정하기
        val dateFormat = SimpleDateFormat("MMMM", Locale.ENGLISH)
        val monthString = dateFormat.format(calendar.time)

        /*RecyclerView 의 layoutManager 지정*/
        binding.rvDiaries.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        // TextView2에 오늘의 년도 설정하기
        textView2.text = year.toString()
        monthView.text = monthString

        diaryDao = DiaryDao(this)

        diaries = diaryDao.getAllFoods()               // DB 에서 모든 diary를 가져옴
        adapter = DiaryAdapter(diaries)        // adapter 에 데이터 설정
        binding.rvDiaries.adapter = adapter   // RecylcerView 에 adapter 설정
        /*
        binding.btnAdd.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, REQ_ADD)
        }*/

        // 검색 구현
        val searchView: SearchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                performSearch(newText)
                return true
            }
        })

        val clickObj = object: DiaryAdapter.OnItemClickListener { // adapter.setOnItemClickListener(object : FoodAdapter.OnItemClickListener
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("dto", diaries.get(position) )   // 클릭한 RecyclerView 항목 위치의 dto 전달
                startActivityForResult(intent, REQ_UPDATE)      // 수정결과를 받아오도록 Activity 호출
            }
        }

        adapter.setOnItemClickListener(clickObj)


        val longClickObj = object: DiaryAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int) {
                val diary = diaries[position]

                AlertDialog.Builder(this@MainActivity).run {
                    setTitle("삭제")
                    setMessage("정말 [${diary.title}] 일기를 삭제할까요?")
                    setPositiveButton("확인"
                    ) { p0, p1 ->
                        // position 위치의 dto id를 사용하여 db 삭제
                        deleteFood(diaries.get(position).id)
                        // RecyclerView 갱신작업
                        refreshList(RESULT_OK)
                    }
                    setNegativeButton("취소", null)
                    show()
                }
            }
        }

        adapter.setOnItemLongClickListener(longClickObj)
    }

    // AddEmotionFragment에서 호출될 메소드
    fun openAddActivity(emotion: Int) {
        val intent = Intent(this, AddActivity::class.java)
        intent.putExtra("emotion", emotion)
        startActivityForResult(intent, REQ_ADD)
    }

    private fun performSearch(query: String) {

        // 검색 결과를 사용하여 어댑터에 데이터 갱신 및 리사이클러뷰 갱신 로직 추가
        // 예시 코드:
        diaries.clear()                       // 기존 항목 제거
        diaries.addAll(diaryDao.DBSearch(query))         // 항목 추가
        adapter.notifyDataSetChanged()      // RecyclerView 갱신
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.addDiary -> {
                val fragment = AddEmotionFragment.newInstance()
                fragment.show(supportFragmentManager, "addEmotionFragment")
            }
            R.id.introDev -> {
                val intent = Intent(this, IntroActivity::class.java)
                startActivityForResult(intent, REQ_ADD)
            }
            R.id.finish -> {
                AlertDialog.Builder(this@MainActivity).run {
                    setTitle("앱 종료")
                    setMessage("앱을 종료할까요?")
                    setPositiveButton("확인"
                    ) { p0, p1 ->
                        finish()
                    }
                    setNegativeButton("취소", null)
                    show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()   // 액티비티가 보일 때마다 RecyclerView 를 갱신하고자 할 경우
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_UPDATE -> {
                refreshList(resultCode)
            }
            REQ_ADD -> {
                refreshList(resultCode)
            }
        }
    }

    private fun refreshList(resultCode: Int) {
        if (resultCode == RESULT_OK) {
            diaries.clear()                       // 기존 항목 제거
            diaries.addAll(diaryDao.getAllFoods())         // 항목 추가
            adapter.notifyDataSetChanged()      // RecyclerView 갱신
        } else {
            Toast.makeText(this@MainActivity, "취소됨", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteFood(position: Long) {
        diaryDao.deleteFood(position) // DB에서 해당 객체를 삭제
        adapter.notifyDataSetChanged()
        refreshList(RESULT_OK)
    }

}