package dtos

import java.time.LocalDate

class ChatDto(
    val uid: String="",
    val username:String="",
    val status:String?="",
    val message:String="",
    val profile:Boolean=false,
    val createdate:Int=0,
    val person:Int =1,
    val time:String?=""
) {
}