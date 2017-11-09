package com.minivision.aop.kong.model.admin.consumer;

import lombok.Data;

import java.util.List;

import com.minivision.aop.kong.model.common.AbstractEntityList;

/**
 * Created by vaibhav on 13/06/17.
 */
@Data
public class ConsumerList extends AbstractEntityList {
    Long total;
    String next;
    List<Consumer> data;
}
