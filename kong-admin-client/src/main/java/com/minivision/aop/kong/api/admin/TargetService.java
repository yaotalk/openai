package com.minivision.aop.kong.api.admin;

import com.minivision.aop.kong.model.admin.target.Target;
import com.minivision.aop.kong.model.admin.target.TargetList;

/**
 * Created by vaibhav on 13/06/17.
 */
public interface TargetService {
    Target createTarget(String upstreamNameOrId, Target request);
    Target deleteTarget(String upstreamNameOrId, String target);
    TargetList listTargets(String upstreamNameOrId, String id, Integer weight, String target, Long size, String offset);
    TargetList listActiveTargets(String upstreamNameOrId);
}
