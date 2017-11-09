package com.minivision.aop.kong.model.admin.sni;

import lombok.Data;

import java.util.List;

import com.minivision.aop.kong.model.common.AbstractEntityList;

/**
 * Created by vaibhav on 13/06/17.
 */
@Data
public class SniList extends AbstractEntityList {
    Long total;
    List<Sni> data;
}
