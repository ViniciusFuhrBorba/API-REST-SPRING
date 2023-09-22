package br.com.unisenai.apprest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {

    private Integer id;
    private String name;

}
