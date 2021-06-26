package dtos

class ChatDto(
    val uid: String="",
    val username:String="",
    val message:String="",
    val profile:Boolean=false,
    val createdate:String="",
    val person:Int =1
) {
}