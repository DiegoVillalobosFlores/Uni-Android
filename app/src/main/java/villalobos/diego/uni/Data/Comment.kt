package villalobos.diego.uni.Data

import java.io.Serializable

data class Comment (var nombre:String = "",
                    var codigo:String = "",
                    var date:String = "",
                    var comment:String = "") : Serializable