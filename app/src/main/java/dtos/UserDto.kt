package dtos

class UserDto(
    val uid:String,
    val role:String,
    val username:String,
    val useremail:String,
    val status:String?,
    val userListDto: List<UserListDto>?
) {
}