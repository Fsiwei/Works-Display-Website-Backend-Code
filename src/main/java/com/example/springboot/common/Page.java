package com.example.springboot.common;

import lombok.Data;

import java.util.List;

/**
 * @author : siwei.fan
 * @date : 2024/2/26 0:32
 * @modyified By :
 */

@Data
public class Page<T> {
    private Integer total;
    private List<T> list;
}
