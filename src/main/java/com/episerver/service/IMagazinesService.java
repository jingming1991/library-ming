package com.episerver.service;

import com.episerver.entity.Magazine;

import java.util.List;

public interface IMagazinesService {

    List<Magazine> getFromFile(String fileUrl);
}
