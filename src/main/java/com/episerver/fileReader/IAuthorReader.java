package com.episerver.fileReader;

import com.episerver.entity.Author;

import java.util.List;

public interface IAuthorReader {

    List<Author> convertAuthorFile(String fileUrl);
}
