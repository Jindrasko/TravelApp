package cz.mendelu.xspacek6.va2.travelApp.models.places

data class Properties(
    val dist: Double,
    val kinds: String,
    val name: String,
    val osm: String?,
    val rate: Int,
    val wikidata: String?,
    val xid: String
)