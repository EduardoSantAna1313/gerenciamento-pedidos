package order.infra.rest.v1.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Page(

    @JsonProperty("page")
    val page: Int = 0,

    @JsonProperty("page_size")
    val pageSize: Int = 0,

    @JsonProperty("total_pages")
    val totalPages: Int = 0,

    @JsonProperty("total_elements")
    val totalElements: Long = 0,
)