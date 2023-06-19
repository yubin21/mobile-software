package dduwcom.mobile.finalreport.data

import java.io.Serializable
import java.util.Calendar

data class DiaryDto(val id: Long, var emotion: Int, var title: String, var date : Calendar, var context: String) : Serializable {
    override fun toString() = "$emotion - $title ( $date )"
}