package com.episerver.fileReader;


import com.episerver.entity.Magazine;

import java.util.List;

public interface IMagazineReader {

    List<Magazine> convertMagazineFile(String fileUrl);
}
