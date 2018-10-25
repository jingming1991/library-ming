package com.episerver.fileReader;


import com.episerver.entity.NormalBook;

import java.util.List;

public interface INormalBookReader {

    List<NormalBook> convertMagazineFile(String fileUrl);
}
