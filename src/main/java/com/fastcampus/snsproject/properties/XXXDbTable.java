package com.fastcampus.snsproject.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("xxx.db")
public record XXXDbTable(String tableName) {}
