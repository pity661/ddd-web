package com.wenky.provider.framework.elasticsearch.entity;

import java.time.LocalDateTime;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author 克林
 * @date 2023/7/13 20:03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "customer")
public class ESCustomer {
    @Id
    @Field(store = true, type = FieldType.Keyword) // keyword表示不用分词
    private String id;

    @Field(index = false, store = true, type = FieldType.Long)
    private Long sid;

    @Field(store = true, type = FieldType.Text, analyzer = "ik_smart")
    private String name;

    @Field(index = false, store = true, type = FieldType.Integer)
    private Integer age;

    @Field(store = true, type = FieldType.Text, analyzer = "ik_smart")
    private String phone;

    @Field(index = false, store = true, type = FieldType.Date, format = DateFormat.basic_date_time)
    private LocalDateTime createdAt;

    @Field(index = false, store = true, type = FieldType.Date, format = DateFormat.basic_date_time)
    private LocalDateTime updatedAt;
}
