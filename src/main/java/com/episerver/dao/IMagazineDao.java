package com.episerver.dao;

import com.episerver.entity.Magazine;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IMagazineDao extends PagingAndSortingRepository<Magazine, String> {
}
