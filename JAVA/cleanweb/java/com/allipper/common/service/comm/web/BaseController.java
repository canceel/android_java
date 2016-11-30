/**
 *
 */
package com.allipper.common.service.comm.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.allipper.common.service.comm.utils.JsonService;




@Controller
public class BaseController {
    @Autowired
    protected JsonService jsonService;

    public JsonService getJsonService() {
        return jsonService;
    }

    public void setJsonService(JsonService jsonService) {
        this.jsonService = jsonService;
    }
}
