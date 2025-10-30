package com.orange.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult {
    private Long total;//用long不用Long包装类肯是因为long默认值为0;
    private List records;
}
