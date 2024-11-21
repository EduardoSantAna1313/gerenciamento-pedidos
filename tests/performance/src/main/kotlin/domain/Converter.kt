package domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper

object Converter {

    val mapper: ObjectMapper = JsonMapper.builder()
        .findAndAddModules()
        .build()

}