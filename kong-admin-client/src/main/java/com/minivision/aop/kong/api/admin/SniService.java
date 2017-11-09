package com.minivision.aop.kong.api.admin;

import com.minivision.aop.kong.model.admin.sni.Sni;
import com.minivision.aop.kong.model.admin.sni.SniList;

/**
 * Created by vaibhav on 13/06/17.
 */
public interface SniService {
    Sni createSni(Sni request);
    Sni getSni(String name);
    Sni updateSni(String name, Sni request);
    Sni createOrUpdateSni(Sni request);
    Sni deleteSni(String name);
    SniList listSnis();
}
