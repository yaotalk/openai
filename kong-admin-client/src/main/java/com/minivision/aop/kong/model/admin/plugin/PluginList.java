package com.minivision.aop.kong.model.admin.plugin;

import lombok.Data;

import java.util.List;

import com.minivision.aop.kong.model.common.AbstractEntityList;

/**
 * Created by vaibhav on 13/06/17.
 */
@Data
public class PluginList extends AbstractEntityList {
    Long total;
    String next;
    List<Plugin> data;
}
