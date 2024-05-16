package bocardo.fernando.socialbattery

import java.util.Date

data class Emocion(
    var nombre: String,
    var intensidad: Int,
    var descripcion: String,
    var fecha: Long,
    var usuario: String?
) {
    constructor() : this("", 0, "", System.currentTimeMillis(), "")

}


