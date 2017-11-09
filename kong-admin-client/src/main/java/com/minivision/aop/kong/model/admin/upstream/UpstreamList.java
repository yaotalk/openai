package com.minivision.aop.kong.model.admin.upstream;

import lombok.Data;

import java.util.List;

import com.minivision.aop.kong.model.common.AbstractEntityList;

/**
 * Created by vaibhav on 13/06/17.
 */
@Data
public class UpstreamList extends AbstractEntityList {
    Long total;
    String next;
    List<Upstream> data;
}
