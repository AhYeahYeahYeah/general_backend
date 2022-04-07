package com.workflow.general_backend.service;

import com.workflow.general_backend.entity.Guidance;

public interface GuidanceService {
    Guidance findById(String id);
}
