package com.flatwater.candysurvey.produce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flatwater.candysurvey.MainActivity
import com.flatwater.candysurvey.R
import com.flatwater.candysurvey.adapter.CommonAdapter
import com.flatwater.candysurvey.databinding.ActivityProduceBinding
import com.flatwater.candysurvey.setting.MyPageActivity
import com.flatwater.candysurvey.template.TitleTemplateModel
import com.flatwater.candysurvey.template.TitleTemplateRVAdapter
import com.flatwater.candysurvey.templatedata.CommonItem
import com.flatwater.candysurvey.templatedata.viewobject.LongAnswerViewObject
import com.flatwater.candysurvey.templatedata.viewobject.ShortAnswerViewObject
import com.flatwater.candysurvey.templatedata.viewobject.TitleViewObject
import com.flatwater.candysurvey.templatedata.viewobject.ViewObject
import com.flatwater.candysurvey.viewholder.TitleViewHolder

class ProduceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProduceBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_produce)

        // MainActivity에서 SurveyTitle Data 받아오는 코드
        val surveyTitle = intent.getStringExtra("surveyTitle")
        binding.surveyTitle.text = surveyTitle

        // 뒤로가기 버튼 연결
        binding.closeBtn.setOnClickListener{
            showBackDialog()
        }

        binding.saveBtn.setOnClickListener {
            showSaveDialog()
        }

        // 초기 제목 템플릿 자동으로 연결
        val rv_default = findViewById<RecyclerView>(R.id.surveyArea)
        val itemList = ArrayList<TitleTemplateModel>()

        itemList.add(TitleTemplateModel("$surveyTitle", ""))

        val templateTitleAdapter = TitleTemplateRVAdapter(itemList)
        templateTitleAdapter.notifyDataSetChanged()

        rv_default.adapter = templateTitleAdapter
        rv_default.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // 메인페이지 네비게이션 연결
        binding.navigateMainPage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 마이페이지 네비게이션 연결
        binding.navigateMyPage.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 메뉴 버튼 dialog 연결
        val menuDialog = MenuDialog(this)
        binding.plusBtn.setOnClickListener {
            menuDialog.showDialog()
        }

        // 공유하기 버튼 연결
        binding.shareBtn.setOnClickListener {
            // 공유하기 템플릿 만드는 로직이 들어가야 합니다.
            Toast.makeText(this, "공유하기 템플릿 띄워야함..", Toast.LENGTH_SHORT).show()
        }

        // 설문 문항 영역 템플릿 데이터 적용되는지 체크
        val adapter = CommonAdapter (
            arrayOf(
                CommonItem(
                    "TITLE",
                    TitleViewObject(null, null )
                ), CommonItem(
                    "SHORT_ANS",
                    ShortAnswerViewObject(null)
                )
            )
        )
        binding.surveyArea.adapter = adapter
    }

    // 저장 버튼 터치시 나타다는 다이얼로그
    private fun showSaveDialog(){

        val saveDialogView = LayoutInflater.from(this).inflate(R.layout.save_check_dialog, null)
        val saveBuilder = AlertDialog.Builder(this)
            .setView(saveDialogView)

        val alertSaveDialog = saveBuilder.show()

        alertSaveDialog.findViewById<Button>(R.id.yesBtn)?.setOnClickListener {
            Toast.makeText(this,"저장되었습니다!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        alertSaveDialog.findViewById<Button>(R.id.noBtn)?.setOnClickListener {
            // 원래 작업하던 페이지로 돌아가게 구현해야함!
            Toast.makeText(this,"원래대로 돌아갑니다!", Toast.LENGTH_SHORT).show()
            alertSaveDialog.dismiss()
        }
    }

    // 뒤로가기 버튼 터치시 나타나는 다이얼로그
    private fun showBackDialog(){

        val backDialogView = LayoutInflater.from(this).inflate(R.layout.back_check_dialog, null)
        val backBuilder = AlertDialog.Builder(this)
            .setView(backDialogView)

        val alertBackDialog = backBuilder.show()

        alertBackDialog.findViewById<Button>(R.id.yesBtn)?.setOnClickListener {
            Toast.makeText(this, "저장하지 않고 메인페이지로 되돌아갑니다!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        alertBackDialog.findViewById<Button>(R.id.noBtn)?.setOnClickListener {
            Toast.makeText(this, "다시 작업하던 화면으로 되돌아갑니다!", Toast.LENGTH_SHORT).show()
            alertBackDialog.dismiss()
        }
    }

}