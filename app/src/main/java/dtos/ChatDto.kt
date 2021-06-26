package dtos

class ChatDto(
    val uid: String,
    val username:String,
    val message:String?,
    val profile:Boolean,
    val createdate:String
) {
}