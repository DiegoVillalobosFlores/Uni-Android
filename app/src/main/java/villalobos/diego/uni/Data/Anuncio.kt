package villalobos.diego.uni.Data

import java.io.Serializable

data class Anuncio (val fecha:String = "",
                    val id:String = "",
                    val titulo:String = "",
                    val cuerpo:String = "",
                    val photo:String = "",
                    val coordinacion:String = "") : Serializable