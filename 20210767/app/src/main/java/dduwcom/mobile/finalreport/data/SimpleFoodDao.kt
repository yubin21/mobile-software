package dduwcom.mobile.finalreport.data

import java.util.Calendar

class SimpleFoodDao {
    private val diaries = ArrayList<DiaryDto>()
    private val currentDate = Calendar.getInstance()

    init {
//        foods.add (FoodDto(1, R.drawable.cat10_heart, "치즈", currentDate))
//        foods.add (FoodDto(2, R.drawable.cat11_sleep, "치킨", currentDate))
//        foods.add (FoodDto(3, R.drawable.cat2_angry, "도넛", currentDate))
//        foods.add (FoodDto(4, R.drawable.cat1_smile, "사과", currentDate))
//        foods.add (FoodDto(5, R.drawable.cat7_shock, "핫도그", currentDate))
//        foods.add (FoodDto(6, R.drawable.cat9_question, "파스타", currentDate))
//        foods.add (FoodDto(7, R.drawable.cat3_cry, "아이스크림", currentDate))
    }

    fun getAllFoods() : ArrayList<DiaryDto> {
        return diaries
    }

    fun addNewFood(newDiaryDto : DiaryDto) {
        diaries.add(newDiaryDto)
    }

    fun modifyFood(pos: Int, modifyDiaryDto : DiaryDto) {
        diaries.set(pos, modifyDiaryDto)
    }

    fun removeFood(removeDiaryDto : DiaryDto) {
        val index = diaries.indexOf(removeDiaryDto)
        diaries.removeAt(index)
    }

    fun getFoodByPos(pos : Int) = diaries.get(pos)
}