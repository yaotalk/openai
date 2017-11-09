package com.minivision.aop.kong.model.admin.target;

import lombok.Data;

import java.util.List;

import com.minivision.aop.kong.model.common.AbstractEntityList;

/**
 * Created by vaibhav on 13/06/17.
 */
@Data
public class TargetList extends AbstractEntityList {
    Long total;
    String next;
    List<Target> data;
}
