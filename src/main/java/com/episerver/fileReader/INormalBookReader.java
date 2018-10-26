package com.episerver.fileReader;


import com.episerver.entity.NormalBook;

import java.util.List;

public interface INormalBookReader {

    List<NormalBook> convertNormalBookFile(String fileUrl);
}
