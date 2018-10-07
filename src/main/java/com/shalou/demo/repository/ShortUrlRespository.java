package com.shalou.demo.repository;

import com.shalou.demo.domain.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShortUrlRespository extends JpaRepository<ShortUrl, Integer>, JpaSpecificationExecutor<ShortUrl> {
    public ShortUrl findShortUrlByShortUrl(String shortUrl);

    public ShortUrl findShortUrlByLongUrl(String longUrl);
}
