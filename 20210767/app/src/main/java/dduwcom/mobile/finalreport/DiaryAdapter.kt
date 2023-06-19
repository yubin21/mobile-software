package dduwcom.mobile.finalreport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dduwcom.mobile.finalreport.data.DiaryDto
import dduwcom.mobile.finalreport.databinding.ListItemBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DiaryAdapter (val diaries : ArrayList<DiaryDto>)
    : RecyclerView.Adapter<DiaryAdapter.FoodViewHolder>() {
    val TAG = "FoodAdapter"

    /*재정의 필수 - 데이터의 개수 확인이 필요할 때 호출*/
    override fun getItemCount(): Int = diaries.size

    /*재정의 필수 - 각 item view 의 view holder 생성 시 호출*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(itemBinding, listener, lcListener)
    }

    /*재정의 필수 - 각 item view 의 항목에 데이터 결합 시 호출*/
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.itemBinding.ivPhoto.setImageResource( diaries[position].emotion ) // diaryDao 에서 `foods.add~` 했던 부분의 객체의 값.
        holder.itemBinding.tvTitle.text = diaries[position].title
        val dateFormat = SimpleDateFormat("MM월 dd일", Locale.getDefault())
        val formattedDate: String = dateFormat.format(diaries[position].date.time)
        holder.itemBinding.tvMonthDate.text = formattedDate


        val calendar = diaries[position].date

        val dayOfWeek = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "일요일"
            Calendar.MONDAY -> "월요일"
            Calendar.TUESDAY -> "화요일"
            Calendar.WEDNESDAY -> "수요일"
            Calendar.THURSDAY -> "목요일"
            Calendar.FRIDAY -> "금요일"
            Calendar.SATURDAY -> "토요일"
            else -> "?요일"
        }

        holder.itemBinding.tvDay.text = dayOfWeek
    }


    class FoodViewHolder(val itemBinding: ListItemBinding,
                         listener: OnItemClickListener?,
                         liListener: OnItemLongClickListener?,
    )
        : RecyclerView.ViewHolder(itemBinding.root) {
            init {
                /*RecyclerView 항목 클릭 시 외부 click 이벤트 리스너 호출*/
                itemBinding.root.setOnClickListener{
                    listener?.onItemClick(it, adapterPosition)  // RecyclerView 항목 클릭 시 외부에서 지정한 리스너 호출
                }
                itemBinding.root.setOnLongClickListener{
                    liListener?.onItemLongClick(it, adapterPosition)
                    true
                }
            }
        }

    /*사용자 정의 외부 long click 이벤트 리스너 설정 */
    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }

    var lcListener: OnItemLongClickListener? = null

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        this.lcListener = listener
    }

    /*사용자 정의 외부 click 이벤트 리스너 설정 */
    var listener : OnItemClickListener? = null  // listener 를 사용하지 않을 때도 있으므로 null

    interface OnItemClickListener {
        fun onItemClick(view : View, position : Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }
}



