package com.minivision.aop.kong.model.plugin.authentication.oauth2;

import lombok.Data;

import java.util.List;

import com.minivision.aop.kong.model.common.AbstractEntityList;

/**
 * Created by vaibhav on 15/06/17.
 */
@Data
public class ApplicationList extends AbstractEntityList {
    Long total;
    List<Application> data;
}
