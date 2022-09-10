package ru.mironov.drawpathonmaptesttask

object GeoJson {

    fun getJsonString(): String {

        return "{\n" +
                "  \"type\": \"FeatureCollection\",\n" +
                "  \"features\": [\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"properties\": {\"scalerank\":0,\"featurecla\":\"Admin-0 country\",\"labelrank\":2,\"sovereignt\":\"Russia\",\"sov_a3\":\"RUS\",\"adm0_dif\":0,\"level\":2,\"type\":\"Sovereign country\",\"admin\":\"Russia\",\"adm0_a3\":\"RUS\",\"geou_dif\":0,\"geounit\":\"Russia\",\"gu_a3\":\"RUS\",\"su_dif\":0,\"subunit\":\"Russia\",\"su_a3\":\"RUS\",\"brk_diff\":0,\"name\":\"Russia\",\"name_long\":\"Russian Federation\",\"brk_a3\":\"RUS\",\"brk_name\":\"Russia\",\"brk_group\":null,\"abbrev\":\"Rus.\",\"postal\":\"RUS\",\"formal_en\":\"Russian Federation\",\"formal_fr\":null,\"note_adm0\":null,\"note_brk\":null,\"name_sort\":\"Russian Federation\",\"name_alt\":null,\"mapcolor7\":2,\"mapcolor8\":5,\"mapcolor9\":7,\"mapcolor13\":7,\"pop_est\":140041247,\"gdp_md_est\":2266000,\"pop_year\":-99,\"lastcensus\":2010,\"gdp_year\":-99,\"economy\":\"3. Emerging region: BRIC\",\"income_grp\":\"3. Upper middle income\",\"wikipedia\":-99,\"fips_10_\":\"RS\",\"iso_a2\":\"RU\",\"iso_a3\":\"RUS\",\"iso_n3\":\"643\",\"un_a3\":\"643\",\"wb_a2\":\"RU\",\"wb_a3\":\"RUS\",\"woe_id\":23424936,\"woe_id_eh\":23424936,\"woe_note\":\"Exact WOE match as country\",\"adm0_a3_is\":\"RUS\",\"adm0_a3_us\":\"RUS\",\"adm0_a3_un\":-99,\"adm0_a3_wb\":-99,\"continent\":\"Europe\",\"region_un\":\"Europe\",\"subregion\":\"Eastern Europe\",\"region_wb\":\"Europe & Central Asia\",\"name_len\":6,\"long_len\":18,\"abbrev_len\":4,\"tiny\":-99,\"homepart\":1,\"filename\":\"RUS.geojson\"},\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"MultiPolygon\",\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}\n"
    }
}