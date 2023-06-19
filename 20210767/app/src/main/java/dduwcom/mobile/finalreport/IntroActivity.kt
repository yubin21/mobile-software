package dduwcom.mobile.finalreport

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dduwcom.mobile.finalreport.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    lateinit var introBinding : ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // 타이틀 바 숨기기
        introBinding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(introBinding.root)

        introBinding.btnAddCancel.setOnClickListener{
            finish()
        }

        introBinding.imageButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse("https://github.com/yubin21"));
            startActivity(intent);
        }
    }


}